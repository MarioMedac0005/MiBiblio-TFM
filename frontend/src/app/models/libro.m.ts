export interface Libro {
    id: number;
    titulo: string;
    autor: string;
    isbn: string;
    editorial: string;
    fechaPublicacion: number;
    ejemplaresDisponibles: number;
    fechaCreacion: Date;
}