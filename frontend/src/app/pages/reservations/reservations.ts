import { Component, OnInit, signal } from '@angular/core';
import { Api } from '../../services/api';
import { Reserva } from '../../models/reserva.m';
import { CommonModule, DatePipe } from '@angular/common';
import { ToastService } from '../../shared/services/toast.service';

@Component({
  selector: 'app-reservations',
  standalone: true,
  imports: [CommonModule, DatePipe],
  templateUrl: './reservations.html',
  styleUrl: './reservations.css'
})
export class Reservations implements OnInit {
  reservas: Reserva[] = [];
  isFulfillModalOpen = signal(false);
  selectedReserva: Reserva | null = null;
  
  currentPage = 0;
  pageSize = 10;
  totalElements = 0;
  totalPages = 0;
  Math = Math;

  constructor(
    private api: Api,
    private toast: ToastService
  ) {}

  ngOnInit(): void {
    this.loadReservas();
  }

  loadReservas() {
    this.api.getReservas(this.currentPage, this.pageSize).subscribe({
      next: (res) => {
        this.reservas = res.content;
        this.totalElements = res.totalElements;
        this.totalPages = res.totalPages;
        this.currentPage = res.number;
      },
      error: (err) => console.error('Error loading reservas', err),
    });
  }

  nextPage() {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.loadReservas();
    }
  }

  previousPage() {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.loadReservas();
    }
  }

  goToPage(page: number) {
    if (page >= 0 && page < this.totalPages) {
      this.currentPage = page;
      this.loadReservas();
    }
  }

  openFulfillModal(reserva: Reserva) {
    this.selectedReserva = reserva;
    this.isFulfillModalOpen.set(true);
  }

  closeFulfillModal() {
    this.isFulfillModalOpen.set(false);
    this.selectedReserva = null;
  }

  confirmarCumplirReserva() {
    if (!this.selectedReserva) return;

    this.api.cumplirReserva(this.selectedReserva.id).subscribe({
      next: () => {
        this.toast.success('Préstamo creado correctamente a partir de la reserva');
        this.closeFulfillModal();
        this.loadReservas();
      },
      error: (err) => {
        const errMsg = typeof err.error === 'string' ? err.error : (err.error?.message || 'Error creando préstamo desde reserva');
        this.toast.error(errMsg);
        console.error('Error cumpliendo reserva', err);
      }
    });
  }
}
