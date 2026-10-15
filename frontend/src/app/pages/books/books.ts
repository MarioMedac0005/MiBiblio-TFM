import { Component, OnInit, signal } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Libro } from '../../models/libro.m';
import { Usuario } from '../../models/usuario.m';
import { Api } from '../../services/api';
import { CommonModule, DatePipe } from '@angular/common';
import { debounceTime, distinctUntilChanged } from 'rxjs';
import { ToastService } from '../../shared/services/toast.service';

@Component({
  selector: 'app-books',
  imports: [CommonModule, DatePipe, ReactiveFormsModule],
  templateUrl: './books.html',
  styleUrl: './books.css',
})
export class BooksPage implements OnInit {
  libros: Libro[] = [];
  usuarios: Usuario[] = [];
  isModalOpen = signal(false);
  libroForm: FormGroup;
  isDeleteModalOpen = signal(false);
  selectedLibro: number | null = null;
  searchLibro = new FormControl('');
  currentQuery: string = '';
  isEditarModo = false;
  editarLibroId: number | null = null;

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
    this.libroForm = this.fb.group({
      titulo: ['', Validators.required],
      autor: ['', Validators.required],
      isbn: ['', Validators.required],
      editorial: ['', Validators.required],
      fechaPublicacion: ['', [Validators.required, Validators.min(1000)]],
      ejemplaresDisponibles: [1, [Validators.required, Validators.min(0)]],
    });
  }

  ngOnInit(): void {
    this.loadLibros();
    this.loadSelectData();
    this.searchLibro.valueChanges
      .pipe(debounceTime(300), distinctUntilChanged())
      .subscribe((value) => {
        this.currentQuery = value?.trim() ?? '';
        this.currentPage = 0;
        this.loadLibros();
      });
  }

  loadLibros() {
    this.api.getLibros(this.currentPage, this.pageSize, this.currentQuery).subscribe({
      next: (res) => {
        this.libros = res.content;
        this.totalElements = res.totalElements;
        this.totalPages = res.totalPages;
        this.currentPage = res.number;
      },
      error: (err) => console.error('Error loading libros', err),
    });
  }

  loadSelectData() {
    this.api.getTodosUsuarios().subscribe((res) => {
      this.usuarios = res;
    });
  }

  nextPage() {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.loadLibros();
    }
  }

  previousPage() {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.loadLibros();
    }
  }

  goToPage(page: number) {
    if (page >= 0 && page < this.totalPages) {
      this.currentPage = page;
      this.loadLibros();
    }
  }

  openModal() {
    this.isEditarModo = false;
    this.editarLibroId = null;
    this.libroForm.reset({ ejemplaresDisponibles: 1 });
    this.isModalOpen.set(true);
  }

  closeModal() {
    this.isModalOpen.set(false);
  }

  onSubmit() {
    if (this.libroForm.invalid) {
      this.libroForm.markAllAsTouched();
      return;
    }

    const payload = { ...this.libroForm.value };
    if (payload.fechaPublicacion) {
      const year = payload.fechaPublicacion.toString().substring(0, 4);
      payload.fechaPublicacion = `${year}-01-01`;
    }

    if (this.isEditarModo && this.editarLibroId) {
      this.api.updateLibro(this.editarLibroId, payload).subscribe({
        next: () => {
          this.toast.success('Libro actualizado correctamente');
          this.loadLibros();
          this.closeModal();
        },
        error: (err) => {
          this.toast.error(err.error?.message || (typeof err.error === 'string' ? err.error : 'Error actualizando el libro'));
          console.error('Error actualizando el libro', err);
        },
      });
    } else {
      this.api.createLibro(payload).subscribe({
        next: () => {
          this.toast.success('Libro creado correctamente');
          this.loadLibros();
          this.closeModal();
        },
        error: (err) => {
          this.toast.error(err.error?.message || (typeof err.error === 'string' ? err.error : 'Error creando libro'));
          console.error('Error creando libro', err);
        },
      });
    }
  }

  closeDeleteModal() {
    this.isDeleteModalOpen.set(false);
  }

  openDeleteModal(libroId: number) {
    this.selectedLibro = libroId;
    this.isDeleteModalOpen.set(true);
  }

  deleteLibro() {
    if (!this.selectedLibro) return;

    this.api.deleteLibro(this.selectedLibro).subscribe({
      next: () => {
        this.toast.success('Libro eliminado correctamente');
        this.loadLibros();
        this.closeDeleteModal();
        this.selectedLibro = null;
      },
      error: (err) => {
        this.toast.error('Error eliminando el libro');
        console.error(`Error eliminando el libro: ${err}`);
      },
    });
  }

  editarLibro(libro: Libro) {
    this.isEditarModo = true;
    this.editarLibroId = libro.id;

    const anio = libro.fechaPublicacion ? parseInt(libro.fechaPublicacion.toString().substring(0, 4)) : '';

    this.libroForm.patchValue({
      titulo: libro.titulo,
      autor: libro.autor,
      isbn: libro.isbn,
      editorial: libro.editorial,
      fechaPublicacion: anio,
      ejemplaresDisponibles: libro.ejemplaresDisponibles,
    });

    this.isModalOpen.set(true);
  }

  isReservaModalOpen = signal(false);
  usuarioReservaId = new FormControl<number | null>(null, Validators.required);
  selectedReservaLibroId: number | null = null;

  openReservaModal(libroId: number) {
    this.selectedReservaLibroId = libroId;
    this.usuarioReservaId.reset();
    this.isReservaModalOpen.set(true);
  }

  closeReservaModal() {
    this.isReservaModalOpen.set(false);
    this.selectedReservaLibroId = null;
  }

  crearReserva() {
    if (!this.selectedReservaLibroId || !this.usuarioReservaId.value) return;

    this.api.crearReserva(this.usuarioReservaId.value, this.selectedReservaLibroId).subscribe({
      next: () => {
        this.toast.success('Reserva creada correctamente');
        this.closeReservaModal();
      },
      error: (err) => {
        this.toast.error(err.error?.message || 'Error creando la reserva');
        console.error('Error creando reserva', err);
      }
    });
  }
}
