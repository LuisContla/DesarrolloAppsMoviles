# Tarea 2.- Diseño de una Aventura Interactiva

**GameQuest** — App Android con navegación jerárquica inmersiva y temática de **videojuegos**. Presenta tres niveles (Géneros → Sagas/Franquicias → Videojuegos) con **Activities** y **Fragments**, **puntos de interés** interactivos y **transiciones creativas** entre pantallas.

> **Asignación**: navegación temática inmersiva con 3 niveles jerárquicos, UI creativa, puntos de interés y transiciones no triviales.  
> **Fecha límite**: **jueves 25 de septiembre de 2025**.

---

## 🗺️ Estructura jerárquica

1. **Nivel 1 – Géneros** → `GenreActivity`  
   - Grid/lista de géneros (Acción, Aventura, RPG, Puzzle, etc.).  
   - Fragments: `GenreListFragment` (galería), `GenreInfoFragment` (introducción y tips).
2. **Nivel 2 – Sagas/Franquicias** → `FranchiseActivity`  
   - Lista de sagas dentro del género elegido (p. ej., *The Legend of Zelda*, *Final Fantasy*).  
   - Fragments: `FranchiseOverviewFragment` (tarjetas con portada y año), `FranchiseMediaFragment` (arte/tráilers).
3. **Nivel 3 – Videojuego** → `GameActivity`  
   - Vista de un juego específico de la saga (p. ej., *Final Fantasy VII*).  
   - Fragments: `GameDetailFragment` (sinopsis, ficha técnica), `GameGalleryFragment` (imágenes/video/OST).

> 🔁 **Puntos de interés (POIs)** aparecen en **los tres niveles** (subgéneros, entregas clave, niveles/jefes/ubicaciones icónicas). Al tocar un POI se muestra **texto**, **imagen** y opcionalmente **video** (por ejemplo, un tráiler local/embebido).

---

## 🛠️ Requisitos técnicos

- **Mínimo:** 3 **Activities** conectadas jerárquicamente.  
- Cada Activity incluye **Fragments** y **layouts** propios.  
- POIs implementados en **al menos 3 niveles**.  
- **Transiciones** creativas (Activities y Fragments).  
- **Kotlin**, **código limpio y comentado** (KDoc + comentarios puntuales).  

**Stack sugerido**  
- Android Studio 2023.3+ (o más reciente)  
- Kotlin 1.9+ · AGP 8.x · Gradle Wrapper incluido  
- minSdk 24 · targetSdk 34/35  
- Librerías: Material 3, AppCompat, ConstraintLayout/MotionLayout, Coil/Glide

---

## 📸 Capturas

> Coloca tus imágenes en `docs/capturas/` y actualiza estos paths.

- Nivel 1 — Géneros: `docs/capturas/nivel1_generos.png`  
- Nivel 2 — Sagas/Franquicias: `docs/capturas/nivel2_sagas.png`  
- Nivel 3 — Videojuego: `docs/capturas/nivel3_juego.png`  
- POI — Ejemplo de detalle: `docs/capturas/poi_detalle.png`