import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Usuario } from '../models/usuario.m';
import { Observable } from 'rxjs';
import { Libro } from '../models/libro.m';
import { Prestamo } from '../models/prestamo.m';
import { Reserva } from '../models/reserva.m';
import { Page } from '../models/page.m';

@Injectable({
  providedIn: 'root',
})
export class Api {
  private baseUrl = 'http://127.0.0.1:8080';

  constructor(private http: HttpClient) {}

  getUsuarios(page: number, size: number, query?: string): Observable<Page<Usuario>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    if (query && query.trim().length > 0) {
      params = params.set('query', query);
    }

    return this.http.get<Page<Usuario>>(`${this.baseUrl}/usuarios`, { params });
  }

  getTodosUsuarios(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(`${this.baseUrl}/usuarios/todos`);
  }

  getLibros(page: number = 0, size: number = 10, query?: string): Observable<Page<Libro>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    if (query && query.trim().length > 0) {
      params = params.set('query', query);
    }

    return this.http.get<Page<Libro>>(`${this.baseUrl}/libros`, { params });
  }

  getTodosLibros(): Observable<Libro[]> {
    return this.http.get<Libro[]>(`${this.baseUrl}/libros/todos`);
  }

  getPrestamos(page: number = 0, size: number = 10, query?: string): Observable<Page<Prestamo>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    if (query && query.trim().length > 0) {
      params = params.set('query', query);
    }

    return this.http.get<Page<Prestamo>>(`${this.baseUrl}/prestamos`, { params });
  }

  createLibro(libro: Libro): Observable<Libro> {
    return this.http.post<Libro>(`${this.baseUrl}/libros`, libro);
  }

  createUsuario(usuario: Usuario): Observable<Usuario> {
    return this.http.post<Usuario>(`${this.baseUrl}/usuarios`, usuario);
  }

  createPrestamo(prestamo: Prestamo): Observable<Prestamo> {
    return this.http.post<Prestamo>(`${this.baseUrl}/prestamos`, prestamo);
  }

  deleteUsuario(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/usuarios/${id}`);
  }

  quitarSancionUsuario(id: number): Observable<void> {
    return this.http.put<void>(`${this.baseUrl}/usuarios/${id}/quitar-sancion`, {});
  }

  deleteLibro(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/libros/${id}`);
  }

  deletePrestamo(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/prestamos/${id}`); 
  }

  updateLibro(id: number, libro: Libro): Observable<Libro> {
    return this.http.put<Libro>(`${this.baseUrl}/libros/${id}`, libro);
  }

  updateUsuario(id: number, usuario: Usuario): Observable<Usuario> {
    return this.http.put<Usuario>(`${this.baseUrl}/usuarios/${id}`, usuario);
  }

  updatePrestamo(id: number, prestamo: Partial<Prestamo>): Observable<Prestamo> {
    return this.http.put<Prestamo>(`${this.baseUrl}/prestamos/${id}`, prestamo);
  }

  devolverPrestamo(id: number, estadoDevolucion: string): Observable<void> {
    return this.http.put<void>(`${this.baseUrl}/prestamos/${id}/devolver`, { estadoDevolucion });
  }

  renovarPrestamo(id: number): Observable<Prestamo> {
    return this.http.put<Prestamo>(`${this.baseUrl}/prestamos/${id}/renovar`, {});
  }

  getReservas(page: number = 0, size: number = 10): Observable<Page<Reserva>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    return this.http.get<Page<Reserva>>(`${this.baseUrl}/reservas`, { params });
  }

  crearReserva(usuarioId: number, libroId: number): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/reservas`, { usuarioId, libroId });
  }

  cumplirReserva(id: number): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/reservas/${id}/prestamo`, {});
  }
}
