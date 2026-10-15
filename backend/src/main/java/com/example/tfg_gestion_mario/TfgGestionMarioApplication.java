package com.example.tfg_gestion_mario;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.tfg_gestion_mario.entity.Libro;
import com.example.tfg_gestion_mario.entity.Prestamo;
import com.example.tfg_gestion_mario.entity.Usuario;
import com.example.tfg_gestion_mario.repository.LibroRepository;
import com.example.tfg_gestion_mario.repository.PrestamoRepository;
import com.example.tfg_gestion_mario.repository.UsuarioRepository;

@SpringBootApplication
public class TfgGestionMarioApplication {

    public static void main(String[] args) {
		SpringApplication.run(TfgGestionMarioApplication.class, args);
	}

	@Bean
	CommandLineRunner initData
	(
		UsuarioRepository usuarioRepo,
		LibroRepository libroRepo,
		PrestamoRepository prestamoRepo
	) {

		return args -> {

			if (usuarioRepo.count() == 0 && libroRepo.count() == 0) {

				List<Usuario> usuarios = List.of(
					new Usuario(null, "Juan", "Pérez", "juan1@email.com", "600000001", LocalDateTime.now(), false),
					new Usuario(null, "Ana", "García", "ana2@email.com", "600000002", LocalDateTime.now(), false),
					new Usuario(null, "Luis", "Martínez", "luis3@email.com", "600000003", LocalDateTime.now(), false),
					new Usuario(null, "Marta", "López", "marta4@email.com", "600000004", LocalDateTime.now(), false),
					new Usuario(null, "Carlos", "Sánchez", "carlos5@email.com", "600000005", LocalDateTime.now(), false),
					new Usuario(null, "Laura", "Ramírez", "laura6@email.com", "600000006", LocalDateTime.now(), false),
					new Usuario(null, "Pedro", "Torres", "pedro7@email.com", "600000007", LocalDateTime.now(), false),
					new Usuario(null, "Lucía", "Flores", "lucia8@email.com", "600000008", LocalDateTime.now(), false),
					new Usuario(null, "Javier", "Gómez", "javier9@email.com", "600000009", LocalDateTime.now(), false),
					new Usuario(null, "Sofía", "Díaz", "sofia10@email.com", "600000010", LocalDateTime.now(), false),
					new Usuario(null, "Miguel", "Ruiz", "miguel11@email.com", "600000011", LocalDateTime.now(), false),
					new Usuario(null, "Elena", "Moreno", "elena12@email.com", "600000012", LocalDateTime.now(), false),
					new Usuario(null, "Raúl", "Jiménez", "raul13@email.com", "600000013", LocalDateTime.now(), false),
					new Usuario(null, "Paula", "Navarro", "paula14@email.com", "600000014", LocalDateTime.now(), false),
					new Usuario(null, "David", "Castro", "david15@email.com", "600000015", LocalDateTime.now(), false),
					new Usuario(null, "Carmen", "Ortega", "carmen16@email.com", "600000016", LocalDateTime.now(), false),
					new Usuario(null, "Alberto", "Delgado", "alberto17@email.com", "600000017", LocalDateTime.now(), false),
					new Usuario(null, "Irene", "Vega", "irene18@email.com", "600000018", LocalDateTime.now(), false),
					new Usuario(null, "Fernando", "Reyes", "fernando19@email.com", "600000019", LocalDateTime.now(), false),
					new Usuario(null, "Sara", "Cruz", "sara20@email.com", "600000020", LocalDateTime.now(), false)
				);

				usuarioRepo.saveAll(usuarios);

				List<Libro> libros = List.of(
					new Libro(null, "El Quijote", "Cervantes", "ISBN001", "Planeta", LocalDate.of(1605,1,1), 5, LocalDateTime.now()),
					new Libro(null, "Cien años de soledad", "García Márquez", "ISBN002", "Sudamericana", LocalDate.of(1967,5,30), 4, LocalDateTime.now()),
					new Libro(null, "1984", "George Orwell", "ISBN003", "Secker", LocalDate.of(1949,6,8), 6, LocalDateTime.now()),
					new Libro(null, "El Hobbit", "Tolkien", "ISBN004", "Allen & Unwin", LocalDate.of(1937,9,21), 3, LocalDateTime.now()),
					new Libro(null, "Fahrenheit 451", "Bradbury", "ISBN005", "Ballantine", LocalDate.of(1953,10,19), 7, LocalDateTime.now()),
					new Libro(null, "La sombra del viento", "Zafón", "ISBN006", "Planeta", LocalDate.of(2001,4,17), 5, LocalDateTime.now()),
					new Libro(null, "El código Da Vinci", "Dan Brown", "ISBN007", "Doubleday", LocalDate.of(2003,3,18), 8, LocalDateTime.now()),
					new Libro(null, "Los juegos del hambre", "Suzanne Collins", "ISBN008", "Scholastic", LocalDate.of(2008,9,14), 6, LocalDateTime.now()),
					new Libro(null, "Harry Potter 1", "J.K. Rowling", "ISBN009", "Bloomsbury", LocalDate.of(1997,6,26), 10, LocalDateTime.now()),
					new Libro(null, "Harry Potter 2", "J.K. Rowling", "ISBN010", "Bloomsbury", LocalDate.of(1998,7,2), 9, LocalDateTime.now()),
					new Libro(null, "It", "Stephen King", "ISBN011", "Viking", LocalDate.of(1986,9,15), 4, LocalDateTime.now()),
					new Libro(null, "Drácula", "Bram Stoker", "ISBN012", "Constable", LocalDate.of(1897,5,26), 3, LocalDateTime.now()),
					new Libro(null, "Orgullo y prejuicio", "Jane Austen", "ISBN013", "T. Egerton", LocalDate.of(1813,1,28), 5, LocalDateTime.now()),
					new Libro(null, "El principito", "Saint-Exupéry", "ISBN014", "Reynal", LocalDate.of(1943,4,6), 7, LocalDateTime.now()),
					new Libro(null, "Moby Dick", "Herman Melville", "ISBN015", "Harper", LocalDate.of(1851,11,14), 2, LocalDateTime.now()),
					new Libro(null, "Hamlet", "Shakespeare", "ISBN016", "N/A", LocalDate.of(1603,1,1), 6, LocalDateTime.now()),
					new Libro(null, "La Odisea", "Homero", "ISBN017", "Clásicos", LocalDate.of(-700,1,1), 4, LocalDateTime.now()),
					new Libro(null, "El Alquimista", "Coelho", "ISBN018", "HarperCollins", LocalDate.of(1988,1,1), 8, LocalDateTime.now()),
					new Libro(null, "Ensayo sobre la ceguera", "Saramago", "ISBN019", "Alfaguara", LocalDate.of(1995,1,1), 3, LocalDateTime.now()),
					new Libro(null, "El nombre del viento", "Patrick Rothfuss", "ISBN020", "DAW Books", LocalDate.of(2007,3,27), 6, LocalDateTime.now())
				);

				libroRepo.saveAll(libros);

				List<Prestamo> prestamos = new ArrayList<>();

				for (int i = 0; i < 20; i++) {
					prestamos.add(new Prestamo(
						null,
						usuarios.get(i),
						libros.get(i),
						LocalDate.now().minusDays(i),
						LocalDate.now().plusDays(15 - i),
						i % 2 == 0,
						LocalDateTime.now(),
						0,
						null
					));
				}

				prestamoRepo.saveAll(prestamos);
			}
		};
	}

}
