# Tarea 2.- Diseño de una Aventura Interactiva

**GameVerse** — App Android con navegación jerárquica e identidad visual temática de **videojuegos**. Presenta tres niveles (**Géneros → Franquicias → Juegos**) implementados con **Activities** y **Fragments**, listas con **RecyclerView** y **transiciones** Material entre pantallas.

> **Asignación**: navegación temática inmersiva con 3 niveles jerárquicos, UI creativa y transiciones no triviales.  
> **Fecha límite**: **jueves 25 de septiembre de 2025**.

---

## 🗺️ Estructura jerárquica

1. **Nivel 1 – Géneros** → `MainActivity` + `GenresFragment`  
   - Lista de géneros en **1 columna** (Acción, RPG, Plataformas, Shooter, etc.).  
   - Cards con borde “neón” cuyo color se calcula a partir del ícono del género.

2. **Nivel 2 – Franquicias** → `FranchiseActivity` + `FranchiseFragment`  
   - **Grid** de franquicias del género elegido en **2 columnas** (p. ej., *Halo*, *Devil May Cry*, *The Legend of Zelda*, *Final Fantasy*).  
   - Transición con nombre compartido desde la card seleccionada.

3. **Nivel 3 – Juego**  
   - **Lista de juegos por franquicia** → `GamesActivity` + `GamesListFragment` (1 columna).  
   - **Detalle del juego** → `GameActivity` + `GameFragment`:  
     - **Portada** del juego.  
     - **Ficha** en *chips*: Año, Publisher, Desarrolladores y Plataformas.  
     - **Descripción** con botón **“Leer más / Leer menos”**.  
     - **Datos curiosos** (*facts*) como viñetas opcionales.

> Nota: En esta versión final no se muestran POIs en mapa; el foco del tercer nivel es la **ficha detallada** del juego con portada y datos.

---

## 🛠️ Requisitos técnicos que cumple

- 3 **Activities** conectadas jerárquicamente (Género → Franquicia → Juego).  
- Cada Activity contiene **Fragments** y **layouts** propios.  
- **Transiciones** entre Activities/Fragments (Material Fade Through; nombres compartidos en cards).  
- **Código en Kotlin**, con **ViewBinding** y **ListAdapter + DiffUtil** en los *adapters*.  
- **UI consistente** con Toolbars y títulos contextuales.

---

## 🧩 Organización del proyecto

```
app/src/main/java/com/example/gameverse/
├── data/
│   ├── GameRepository.kt
│   └── models/{Genre.kt, Franchise.kt, Game.kt, Poi.kt}
├── ui/
│   ├── main/
│   │   ├── MainActivity.kt
│   │   ├── GenresFragment.kt
│   │   └── GenreAdapter.kt
│   ├── franchise/
│   │   ├── FranchiseActivity.kt
│   │   ├── FranchiseFragment.kt
│   │   └── FranchiseAdapter.kt
│   └── game/
│       ├── GamesActivity.kt
│       ├── GamesListFragment.kt
│       ├── GameActivity.kt
│       └── GameFragment.kt
└── util/
    ├── GridSpacingItemDecoration.kt
    ├── ResExt.kt
    └── Transitions.kt
```

**Layouts clave**
```
res/layout/
  activity_main.xml           activity_franchise.xml
  activity_games.xml          activity_game.xml
  fragment_genres.xml         item_genre_card.xml
  fragment_franchise.xml      item_franchise_card.xml
  fragment_games_list.xml     item_game_card.xml
  fragment_game.xml
```

---

## 📦 Datos y recursos

- **Fuente de datos**: `data/GameRepository.kt` (géneros, franquicias, juegos y *facts*).  
- **Portadas**: en `res/drawable/` usando el **id del juego** (p. ej. `game_dmc5.png` → `coverRes`).  
- **Íconos de géneros**: `res/drawable/…` (vector drawables). El borde “neón” toma el color dominante del ícono.  
- **Fondos/gradientes**: `res/drawable/bg_*.xml`.

---

## 🎬 Transiciones y navegación

- Entre Fragments/Activities: **MaterialFadeThrough** en `enter/exitTransition`.  
- Transición compartida desde las **cards** (asigna `transitionName` único por ítem).

---

## 🔧 Stack & requisitos

- **Kotlin**, **compileSdk 34**, **minSdk 24**, **targetSdk 34**, **JVM 17**.  
- **ViewBinding** habilitado.  
- Dependencias principales:
  ```kotlin
  implementation("androidx.core:core-ktx:1.13.1")
  implementation("androidx.appcompat:appcompat:1.7.0")
  implementation("com.google.android.material:material:1.12.0")
  implementation("androidx.constraintlayout:constraintlayout:2.1.4")
  implementation("androidx.fragment:fragment-ktx:1.8.3")
  implementation("androidx.recyclerview:recyclerview:1.3.2")
  implementation("androidx.activity:activity-ktx:1.9.2")
  implementation("com.google.android.flexbox:flexbox:3.0.0") // opcional
  ```

---

## 🚀 Cómo ejecutar

1. Abrir el proyecto en Android Studio.  
2. Sincronizar Gradle.  
3. Ejecutar en emulador o dispositivo (API 24+).

---

## 🧠 Decisiones de diseño

- Separar **los 3 niveles** en Activities independientes (requisito del proyecto).  
- **Grids y listas** según densidad (géneros 1 col, franquicias 2 col, juegos 1 col).  
- **Detalle del juego** en tarjetas para lectura rápida (chips + “Leer más”).  
- Efecto **“neón”** dinámico en los géneros para reforzar la temática.

---

## 🧩 Retos y soluciones

- **Calcular color dominante** del ícono para el borde neón → función utilitaria + animación sutil.  
- **Transiciones** sin parpadeos → Material Fade Through + coordinación de nombres compartidos.  
- **Consistencia de recursos** → convención `game_<id>.png` para portadas y `coverRes`.

---

## 📸 Capturas (sugeridas)

- `docs/capturas/nivel1_generos.png` (Géneros)  
- `docs/capturas/nivel2_franquicias.png` (Franquicias por género)  
- `docs/capturas/nivel3_juegos.png` (Juegos por franquicia)  
- `docs/capturas/detalle_juego.png` (Detalle con portada y cards)

