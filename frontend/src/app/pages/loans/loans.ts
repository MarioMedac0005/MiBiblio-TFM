import { Component, OnInit, signal } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Prestamo } from '../../models/prestamo.m';
import { Libro } from '../../models/libro.m';
import { Usuario } from '../../models/usuario.m';
import { Api } from '../../services/api';
import { CommonModule, DatePipe, NgIf } from '@angular/common';
import { debounceTime, distinctUntilChanged } from 'rxjs';
import { ToastService } from '../../shared/services/toast.service';

@Component({
  selector: 'app-loans',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, DatePipe],
  templateUrl: './loans.html',
  styleUrl: './loans.css',
})
export class LoansPage implements OnInit {
  prestamos: Prestamo[] = [];
  libros: Libro[] = [];
  usuarios: Usuario[] = [];
  isModalOpen = signal(false);
  prestamoForm: FormGroup;
  isDeleteModalOpen = signal(false);
  selectedPrestamo: number | null = null;
  searchPrestamo = new FormControl('');
  currentQuery: string = '';
  isEditarModo = false;
  editarPrestamoId: number | null = null;

  currentPage = 0;
  pageSize = 10;
  totalElements = 0;
  totalPages = 0;
  Math = Math;

  constructor(
    private api: Api,
    private fb: FormBuilder,
    private toast: ToastService,
  ) {
    this.prestamoForm = this.fb.group({
      usuarioId: ['', Validators.required],
      libroId: ['', Validators.required],
      fechaPrestamo: ['', Validators.required],
      fechaDevolucion: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    this.loadPrestamos();
    this.loadSelectData();
    this.searchPrestamo.valueChanges
      .pipe(debounceTime(300), distinctUntilChanged())
      .subscribe((value) => {
        this.currentQuery = value?.trim() ?? '';
        this.currentPage = 0;
        this.loadPrestamos();
      });
  }

  loadPrestamos() {
    this.api.getPrestamos(this.currentPage, this.pageSize, this.currentQuery).subscribe({
      next: (res) => {
        this.prestamos = res.content;
        this.totalElements = res.totalElements;
        this.totalPages = res.totalPages;
        this.currentPage = res.number;
      },
      error: (err) => console.error('Error loading prestamos', err),
    });
  }

  nextPage() {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.loadPrestamos();
    }
  }

  previousPage() {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.loadPrestamos();
    }
  }

  goToPage(page: number) {
    if (page >= 0 && page < this.totalPages) {
      this.currentPage = page;
      this.loadPrestamos();
    }
  }

  loadSelectData() {
    this.api.getTodosLibros().subscribe((res) => {
      this.libros = res;
    });
    this.api.getTodosUsuarios().subscribe((res) => {
      this.usuarios = res;
    });
  }

  openModal() {
    const today = new Date().toISOString().split('T')[0];
    const nextWeek = new Date(Date.now() + 7 * 24 * 60 * 60 * 1000).toISOString().split('T')[0];
    this.prestamoForm.reset({
      fechaPrestamo: today,
      fechaDevolucion: nextWeek,
    });
    this.isModalOpen.set(true);
  }

  closeModal() {
    this.isModalOpen.set(false);
  }

  onSubmit() {
    if (this.prestamoForm.valid) {
      const payload = { ...this.prestamoForm.value };
      this.api.createPrestamo(payload).subscribe({
        next: () => {
          this.toast.success('Préstamo creado correctamente');
          this.loadPrestamos();
          this.closeModal();
        },
        error: (err) => {
          const errMsg = typeof err.error === 'string' ? err.error : (err.error?.message || 'Error creando préstamo');
          this.toast.error(errMsg);
          console.error('Error creating prestamo', err);
        },
      });
    } else {
      this.prestamoForm.markAllAsTouched();
    }
  }

  closeDeleteModal() {
    this.isDeleteModalOpen.set(false);
  }

  openDeleteModal(prestamoId: number) {
    this.selectedPrestamo = prestamoId;
    this.isDeleteModalOpen.set(true);
  }

  deletePrestamo() {
    if (!this.selectedPrestamo) return;

    this.api.deletePrestamo(this.selectedPrestamo).subscribe({
      next: () => {
        this.toast.success('Préstamo eliminado correctamente');
        this.loadPrestamos();
        this.closeDeleteModal();
        this.selectedPrestamo = null;
      },
      error: (err) => {
        const errMsg = typeof err.error === 'string' ? err.error : (err.error?.message || 'Error eliminando el préstamo');
        this.toast.error(errMsg);
        console.error('Error eliminando el prestamo', err);
      },
    });
  }

  isDevolverModalOpen = signal(false);
  estadoDevolucion = new FormControl('Perfecto', Validators.required);
  selectedDevolverPrestamoId: number | null = null;

  openDevolverModal(prestamoId: number) {
    this.selectedDevolverPrestamoId = prestamoId;
    this.estadoDevolucion.setValue('Perfecto');
    this.isDevolverModalOpen.set(true);
  }

  closeDevolverModal() {
    this.isDevolverModalOpen.set(false);
    this.selectedDevolverPrestamoId = null;
  }

  confirmarDevolucion() {
    if (!this.selectedDevolverPrestamoId || !this.estadoDevolucion.value) return;

    this.api.devolverPrestamo(this.selectedDevolverPrestamoId, this.estadoDevolucion.value).subscribe({
      next: () => {
        this.toast.success('Libro devuelto correctamente');
        this.loadPrestamos();
        this.closeDevolverModal();
      },
      error: (err) => {
        const errMsg = typeof err.error === 'string' ? err.error : (err.error?.message || 'Error devolviendo el libro');
        this.toast.error(errMsg);
        console.error('Error devolviendo el libro', err);
      }
    });
  }

  renovarPrestamo(prestamo: Prestamo) {
    this.api.renovarPrestamo(prestamo.id).subscribe({
      next: () => {
        this.toast.success('Préstamo renovado correctamente');
        this.loadPrestamos();
      },
      error: (err) => {
        const errMsg = typeof err.error === 'string' ? err.error : (err.error?.message || 'Error renovando el préstamo');
        this.toast.error(errMsg);
        console.error('Error renovando préstamo', err);
      }
    });
  }
}
