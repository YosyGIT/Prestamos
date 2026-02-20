# Práctica 31: Préstamos

Práctica obligatoria del módulo de  **Programación** del ciclo de **DAW** del **CIFP Camino de la Miranda**

# 📚 Sistema de Gestión de Biblioteca

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Status](https://img.shields.io/badge/Status-Functional-success?style=for-the-badge)

Este proyecto es una aplicación de consola desarrollada en **Java** diseñada para gestionar de manera eficiente el flujo de préstamos y devoluciones de una biblioteca. El sistema implementa una lógica de negocio robusta que incluye el control de socios, gestión de stock de libros y un sistema automático de sanciones.

## 🛠️ Arquitectura del Sistema

La aplicación está organizada bajo el paquete `gestionlib` y utiliza Programación Orientada a Objetos (POO) con las siguientes clases clave:



* **`AppBiblioteca`**: Contiene el `main` y gestiona la interacción con el usuario a través de un menú interactivo.
* **`GestorBiblioteca`**: Actúa como el controlador central, manejando los arreglos de usuarios y préstamos.
* **`Usuario`**: Define los atributos del socio y gestiona su estado de sanción.
* **`Prestamo`**: Clase encargada de la lógica de fechas, cálculo de retrasos y vinculación socio-libro.

---

## 🚀 Funcionalidades Principales

El sistema permite realizar las siguientes operaciones:

1.  **Registro de Usuarios**: Validación de datos mediante **Regex** para emails y códigos de socio (`SOC00000`).
2.  **Préstamos Inteligentes**: Verifica si el libro está disponible y si el usuario no tiene sanciones activas antes de proceder.
3.  **Gestión de Devoluciones**: Calcula automáticamente si hay retraso y aplica sanciones en tiempo real.
4.  **Control de Sanciones**: Capacidad para listar usuarios sancionados y actualizar su estado automáticamente basándose en la fecha actual.
5.  **Validaciones de Formato**: Control estricto de fechas (`dd/MM/yyyy`) y códigos de libros (`LIB0000`).

---

## 💻 Ejemplo de Uso

Al iniciar la aplicación, se presenta el siguiente menú:

```text
1. Registrar nuevo usuario
2. Realizar préstamo de libro
3. Devolver libro
4. Consultar estado de usuario
5. Mostrar prestamo activo
6. Mostrar usuarios sancionados
7. Actualizar sanciones
8. Salir
```

---

## 📋 Requisitos Técnicos

* **Lenguaje**: Java 8 o superior.

* **Entorno**: Cualquier terminal o consola de IDE (IntelliJ, Eclipse, NetBeans).

* **Estructuras de datos**: Uso de arrays estáticos con límites configurables (50 usuarios / 200 préstamos).

---

## ⚙️ Instalación

1. **Clona este repositorio**:

`git clone [https://github.com/YosyGIT/tu-repositorio.git](https://github.com/YosyGIT/tu-repositorio.git)`

2. **Compila el código desde la carpeta raíz**:

`javac gestionlib/*.java`

3. **Ejecuta la aplicación**:

`java gestionlib.AppBiblioteca`

---
 
## 👤 Autor

* **YosyGIT** - Desarrollo y Lógica - [**Ver Código Fuente**](https://github.com/YosyGIT)