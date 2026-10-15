export interface Usuario {
    id: number;
    nombre: string;
    apellidos: string;
    email: string;
    telefono: string;
    fechaCreacion: Date;
    sancionado: boolean;
}