export interface Prestamo {
  id: number;
  usuarioNombre: string;
  libroTitulo: string;
  fechaPrestamo: Date;
  fechaDevolucion: Date;
  devuelto: boolean;
  renovaciones: number;
  estadoDevolucion?: string;
}