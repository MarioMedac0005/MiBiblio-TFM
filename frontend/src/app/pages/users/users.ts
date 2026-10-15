import { Component, OnInit, signal } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Api as ApiService } from '../../services/api';
import { Usuario } from '../../models/usuario.m';
import { CommonModule, DatePipe } from '@angular/common';
import { debounceTime, distinctUntilChanged } from 'rxjs';

import { ToastService } from '../../shared/services/toast.service';

@Component({
  selector: 'app-users',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, DatePipe],
  templateUrl: './users.html',
  styleUrl: './users.css',
})
export class UsersPage implements OnInit {
  usuarios: Usuario[] = [];
  isModalOpen = signal(false);
  usuarioForm: FormGroup;
  isDeleteModalOpen = signal(false);
  selectedUsuario: number | null = null;
  searchUsuario = new FormControl('');
  currentQuery: string = '';
  isEditarModo = false;
  editarUsuarioId: number | null = null;

  currentPage = 0;
  pageSize = 10;
  totalElements = 0;
  totalPages = 0;
  Math = Math;

  constructor(
    private api: ApiService,
    private fb: FormBuilder,
    private toast: ToastService,
  ) {
    this.usuarioForm = this.fb.group({
      nombre: ['', Validators.required],
      apellidos: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      telefono: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    this.loadUsuarios();
    this.searchUsuario.valueChanges
      .pipe(debounceTime(300), distinctUntilChanged())
      .subscribe((value) => {
        this.currentQuery = value?.trim() ?? '';
        this.currentPage = 0;
        this.loadUsuarios();
      });
  }

  loadUsuarios() {
    this.api.getUsuarios(this.currentPage, this.pageSize, this.currentQuery).subscribe({
      next: (res) => {
        this.usuarios = res.content;
        this.totalElements = res.totalElements;
        this.totalPages = res.totalPages;
        this.currentPage = res.number;
      },
      error: (err) => console.error('Error loading usuarios', err),
    });
  }

  nextPage() {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.loadUsuarios();
    }
  }

  previousPage() {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.loadUsuarios();
    }
  }

  goToPage(page: number) {
    if (page >= 0 && page < this.totalPages) {
      this.currentPage = page;
      this.loadUsuarios();
    }
  }

  openModal() {
    this.usuarioForm.reset();
    this.isModalOpen.set(true);
  }

  closeModal() {
    this.isModalOpen.set(false);
  }

  onSubmit() {
    if (this.usuarioForm.invalid) {
      this.usuarioForm.markAllAsTouched();
      return;
    }

    if (this.isEditarModo && this.editarUsuarioId) {
      this.api.updateUsuario(this.editarUsuarioId, this.usuarioForm.value).subscribe({
        next: () => {
          this.toast.success('Usuario actualizado correctamente');
          this.loadUsuarios();
          this.closeModal();
        },
        error: (err) => {
          this.toast.error('Error actualizando el usuario');
          console.error('Error actualizando el usuario', err);
        },
      });
    } else {
      this.api.createUsuario(this.usuarioForm.value).subscribe({
        next: () => {
          this.toast.success('Usuario creado correctamente');
          this.loadUsuarios();
          this.closeModal();
        },
        error: (err) => {
          this.toast.error('Error creando usuario');
          console.error('Error creando usuario', err);
        },
      });
    }
  }

  closeDeleteModal() {
    this.isDeleteModalOpen.set(false);
  }

  openDeleteModal(userId: number) {
    this.selectedUsuario = userId;
    this.isDeleteModalOpen.set(true);
  }

  deleteUsuario() {
    if (!this.selectedUsuario) return;

    this.api.deleteUsuario(this.selectedUsuario).subscribe({
      next: () => {
        this.toast.success('Usuario eliminado correctamente');
        this.loadUsuarios();
        this.closeDeleteModal();
        this.selectedUsuario = null;
      },
      error: (err) => {
        this.toast.error('Error eliminando al usuario');
        console.error(`Error eliminando al usuario: ${err}`);
      },
    });
  }

  editarUsuario(usuario: Usuario) {
      this.isEditarModo = true;
      this.editarUsuarioId = usuario.id;
  
      this.usuarioForm.patchValue({
        nombre: usuario.nombre,
        apellidos: usuario.apellidos,
        email: usuario.email,
        telefono: usuario.telefono,
      });
  
      this.isModalOpen.set(true);
    }

    quitarSancion(usuario: Usuario) {
      if (!usuario.sancionado) return;
      this.api.quitarSancionUsuario(usuario.id).subscribe({
        next: () => {
          this.toast.success(`Sanción retirada a ${usuario.nombre}`);
          this.loadUsuarios();
        },
        error: (err) => {
          this.toast.error('Error al quitar la sanción');
          console.error(err);
        }
      });
    }
}