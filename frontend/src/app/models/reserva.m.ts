import { Libro } from "./libro.m";
import { Usuario } from "./usuario.m";

export interface Reserva {
    id: number;
    usuario: Usuario;
    libro: Libro;
    fechaReserva: string;
    activa: boolean;
}
