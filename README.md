# MiBiblio — Sistema de Gestión de Biblioteca

Plataforma web integral para la gestión de bibliotecas, que incluye catálogo de libros, préstamos, reservas y administración de usuarios.

---

## 📁 Estructura del Proyecto

El repositorio está organizado como un monorepo que contiene tanto el backend como el frontend:

* **[`backend/`](backend/)**: API REST desarrollada con **Java 17** y **Spring Boot**.
* **[`frontend/`](frontend/)**: Aplicación web desarrollada con **Angular** y **Tailwind CSS**.

---

## 🚀 Requisitos Previos

* **Java 17** o superior
* **Node.js** (versión 18 o superior) y **npm**
* **MySQL Server** (en ejecución en el puerto 3306)

---

## 🛠️ Puesta en Marcha en Local

### 1. Base de Datos (MySQL)
Asegúrate de que el servidor MySQL está en marcha y crea la base de datos:
```sql
CREATE DATABASE `tfg-gestion-mario` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 2. Backend (Spring Boot)
1. Entra en la carpeta del backend:
   ```bash
   cd backend
   ```
2. Ejecuta la aplicación usando el wrapper de Maven:
   ```bash
   ./mvnw spring-boot:run
   ```
3. El backend arrancará en: `http://localhost:8080`
   * Documentación Swagger / OpenAPI disponible en: `http://localhost:8080/swagger-ui.html`

### 3. Frontend (Angular)
1. Abre una nueva terminal y entra en la carpeta del frontend:
   ```bash
   cd frontend
   ```
2. Instala las dependencias (si es la primera vez):
   ```bash
   npm install
   ```
3. Inicia el servidor de desarrollo:
   ```bash
   npm start
   ```
4. La aplicación estará accesible en: `http://localhost:4200`

---

## 🔑 Credenciales de Acceso por Defecto

* **Email:** `admin@admin.com`
* **Contraseña:** `Usuario123`
