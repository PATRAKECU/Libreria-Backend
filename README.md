# 📚 Librería Saint Patrick - Backend

Este proyecto implementa el backend de una librería virtual utilizando Spring Boot, Spring Data JPA y MySQL. Forma parte del proyecto de construcción de una plataforma eCommerce.

---

## 🚀 Tecnologías utilizadas

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- MySQL 8.x
- Maven
- IntelliJ IDEA
- Git & GitHub

---

## 🧠 Arquitectura del sistema

El backend sigue una arquitectura basada en:

- **Controladores REST** (`@RestController`) Cada subclase tiene su propio controlador para gestionar las peticiones a la clase en específico.
- **Servicios** para lógica de negocio. Existe un sólo servicio denominado "ProductoService" que realiza las acciones que afectan a todas las clases y subclases de este servicio.
- **Repositorios JPA** para acceso a datos
- **Entidades con herencia** (`@Inheritance(strategy = SINGLE_TABLE)`) para modelar productos como `Libro`, `Cafe`, `Separador`, `Soporte`

---

## 🗃️ Integración con base de datos

- Base de datos relacional: **MySQL**
- Configuración en `application.properties`
- Carga inicial de datos vía `data.sql` en `src/main/resources`
- Generación automática de IDs con `@GeneratedValue(strategy = GenerationType.IDENTITY)`

---

## 📦 Endpoints disponibles

```http
GET     /api/libros
GET     /api/cafes
GET     /api/separadores
GET     /api/soportes

```
---

## 📦 Capturas de la aplicación en funcionamiento

![Libros](docs/ControladorLibros.png)  
![Cafés](docs/ControladorCafes.png)
![Soportes](docs/ControladorSoportes.png)
![Separadores](docs/ControladorSeparadores.png)
![Tabla con datos](docs/Tabla.png)
![Método GET](docs/GET.png)  
![Método POST](docs/POST.png)
![Método PUT](docs/PUT.png)
![Método DELETE](docs/DELETE.png)