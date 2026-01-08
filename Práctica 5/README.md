# 📱 Practica 5 – Desarrollo de Aplicaciones Móviles Nativas

## 📌 Descripción general
Este proyecto corresponde a la **Práctica 5** de la asignatura **Desarrollo de aplicaciones móviles nativas**.
Consiste en el desarrollo de una aplicación Android nativa utilizando **Kotlin + XML**, que integra el consumo de **APIs REST**, persistencia local de datos, sincronización offline/online, funcionalidades de búsqueda, favoritos y un sistema de administración con roles.

La aplicación está diseñada bajo un enfoque **offline-first**, permitiendo el uso de información almacenada localmente aun cuando no existe conexión a internet.

## 🎯 Objetivo del proyecto
Desarrollar una aplicación móvil que:
- Consuma APIs REST propias y públicas
- Implemente consultas avanzadas
- Almacene y sincronice datos localmente
- Mantenga la funcionalidad sin conexión
- Distinga roles de usuario (USER / ADMIN)
- Integre búsquedas, favoritos y recomendaciones

## 🧩 Funcionalidades principales

### 👤 Autenticación y sesión
- Registro y login de usuarios
- Persistencia de sesión
- Control de acceso según rol
- Ocultamiento dinámico de opciones según permisos

### 🔍 Búsqueda
- Búsqueda de series usando la API pública TVMaze
- Búsqueda de elementos desde la API propia (Mi API)
- Selector de fuente de búsqueda
- Visualización en cards con imágenes
- Guardado automático del historial

### ⭐ Favoritos
- Agregar favoritos desde TVMaze y Mi API
- Visualización unificada
- Eliminación de favoritos
- Persistencia local y sincronización con backend
- Acceso offline

### 🤖 Recomendaciones
- Basadas en historial y favoritos
- Uso de cache local
- Funcionamiento sin conexión

### 🗂️ Mi API (ADMIN)
- Consumo de API REST propia
- Filtros avanzados
- Persistencia con Room
- CRUD de registros (crear y eliminar)
- Funcionalidad offline

### 🛠️ Panel de administrador
- Visualización de usuarios
- Acceso exclusivo
- Control de navegación

## ⚙️ Detalles técnicos
- Kotlin + XML
- Activities y Fragments
- Retrofit
- Room (SQLite)
- Coroutines
- Navigation Component
- Backend Node.js + Express + SQLite
- Autenticación JWT

## 📶 Funcionalidad sin conexión
La app permite visualizar búsquedas, favoritos, recomendaciones y datos sincronizados sin conexión a internet.

## 📚 Conclusiones
Este proyecto permitió integrar múltiples conceptos del desarrollo móvil moderno, como consumo de APIs, persistencia local, control de roles, sincronización de datos y diseño de interfaces reactivas.
Además, se reforzó el uso de buenas prácticas como separación de responsabilidades y manejo de errores de red.