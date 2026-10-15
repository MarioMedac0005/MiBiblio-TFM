package com.example.tfg_gestion_mario.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Gestión de Biblioteca")
                        .description(
                                "API REST para la gestión integral de una biblioteca. "
                                + "Permite administrar usuarios, libros y préstamos, incluyendo "
                                + "operaciones CRUD completas y control de disponibilidad de ejemplares.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Mario")
                                .email("mario@example.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .tags(List.of(
                        new Tag().name("Usuarios")
                                .description("Operaciones para gestionar los usuarios de la biblioteca"),
                        new Tag().name("Libros")
                                .description("Operaciones para gestionar el catálogo de libros"),
                        new Tag().name("Préstamos")
                                .description("Operaciones para gestionar los préstamos de libros")
                ));
    }
}
