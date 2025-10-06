# Práctica 3 — Aplicaciones nativas (Android)

**Alumno(a):** 🔧 _Tu nombre aquí_  
**Boleta / Grupo:** 🔧 _Boleta y grupo_  
**Asignatura:** Desarrollo de aplicaciones móviles nativas  
**Profesor(a):** 🔧 _Nombre del profesor/a_  
**Fecha de entrega:** **lunes 06 de octubre de 2025**

> Este repositorio contiene las soluciones a la **Práctica 3** del curso, centradas en el desarrollo de **aplicaciones Android nativas** que interactúan con los recursos del dispositivo.  
> **Incluye:**  
> - **Ejercicio 1:** Gestor de archivos  
> - **Ejercicio 2:** Aplicación de cámara y micrófono  
> - *(Sin contenido del Ejercicio 3 en este documento).*

---

## Tabla de contenido

- [Objetivo de la práctica](#objetivo-de-la-práctica)  
- [¿Qué es una aplicación nativa?](#qué-es-una-aplicación-nativa)  
- [Estructura del repositorio](#estructura-del-repositorio)  
- [Requisitos del sistema](#requisitos-del-sistema)  
- [Instalación y ejecución](#instalación-y-ejecución)  
- [Temas institucionales](#temas-institucionales)  
- [Ejercicio 1 — Gestor de archivos](#ejercicio-1--gestor-de-archivos)  
- [Ejercicio 2 — Cámara y micrófono](#ejercicio-2--cámara-y-micrófono)  
- [Permisos requeridos y justificación](#permisos-requeridos-y-justificación)  
- [Capturas de pantalla](#capturas-de-pantalla)  
- [APK instalables](#apk-instalables)  
- [Notas de arquitectura y buenas prácticas](#notas-de-arquitectura-y-buenas-prácticas)  
- [Pruebas (resumen)](#pruebas-resumen)  
- [Licencia](#licencia)

---

## Objetivo de la práctica

Desarrollar **aplicaciones Android nativas** que utilicen **APIs y componentes del sistema** (cámara, micrófono, almacenamiento, MediaStore, etc.) y que sigan lineamientos de **Material Design**, **gestión de permisos en tiempo de ejecución** y **persistencia de datos**.

---

## ¿Qué es una aplicación nativa?

Una **aplicación nativa** se construye con las herramientas y lenguajes **oficiales de la plataforma** (Kotlin/Java + Android SDK), accediendo directamente a recursos del **hardware** y del **framework** (Activities, Fragments, Services, Room, DataStore, etc.). Se distribuye en **APK/AAB** y ofrece **máximo rendimiento** y acceso a funcionalidades del sistema.

---

## Estructura del repositorio

```
/ (raíz)
├─ ej1-file-manager/           # Proyecto Android - Gestor de archivos
│  ├─ app/
│  ├─ README.md                # (opcional) README específico del Ejercicio 1
│  └─ ...
├─ ej2-camera-audio/           # Proyecto Android - Cámara y micrófono
│  ├─ app/
│  ├─ README.md                # (opcional) README específico del Ejercicio 2
│  └─ ...
├─ apks/                       # APKs generados para entrega
│  ├─ ej1-file-manager-release.apk
│  └─ ej2-camera-audio-release.apk
├─ screenshots/                # Evidencias (ver sección "Capturas de pantalla")
│  ├─ ej1/
│  └─ ej2/
├─ docs/
│  └─ informe-tecnico.pdf      # Informe técnico (PDF)
└─ README.md                   # Este archivo
```

> Cada subproyecto puede incluir su **README.md** con instrucciones específicas si lo deseas, pero **este documento único** concentra la información necesaria para la entrega de los **Ejercicios 1 y 2**.

---

## Requisitos del sistema

- **Android Studio:** 🔧 _Versión usada por ti (p. ej. Koala/Ladybug o superior)_  
- **Android Gradle Plugin (AGP):** 🔧 _Ej. 8.x_  
- **Gradle:** 🔧 _Ej. 8.x_  
- **JDK:** 17  
- **minSdk:** **24** (Android 7.0)  
- **targetSdk / compileSdk:** 🔧 _Ej. 34 o superior_  
- **Lenguaje:** Kotlin

> Si usas versiones distintas, actualiza estos datos aquí y en los `build.gradle(.kts)` correspondientes.

---

## Instalación y ejecución

1. Clona el repositorio:
   ```bash
   git clone https://github.com/🔧tu-usuario/🔧tu-repo-practica-3.git
   cd 🔧tu-repo-practica-3
   ```
2. Abre **Android Studio** y selecciona el subproyecto que quieras ejecutar:
   - `ej1-file-manager/`
   - `ej2-camera-audio/`
3. **Sync Gradle** y **Run** en un **emulador** o **dispositivo físico**.
4. Para generar **APK de release** en cada módulo:
   ```bash
   ./gradlew :app:assembleRelease
   ```
   El APK aparecerá en `app/build/outputs/apk/release/` del subproyecto.

---

## Temas institucionales

Los dos proyectos incluyen **temas personalizables** con adaptación a **modo claro/oscuro**:

- **Tema Guinda (IPN):** `#6B2E5F`  
- **Tema Azul (ESCOM):** `#003D6D`

> La selección de tema se persiste con **DataStore/SharedPreferences** y se puede cambiar desde **Ajustes** in-app.

---

## Ejercicio 1 — Gestor de archivos

**Resumen:** Explorador de almacenamiento **interno/externo** con navegación jerárquica, vistas de lista/cuadrícula, miniaturas, apertura de archivos con **intents** y operaciones básicas.

### Funcionalidades clave
- Explorar directorios y visualizar estructura de **carpetas/archivos**  
- Detalles: **nombre, tamaño, fecha, tipo**  
- Apertura de **.txt, .md, .log, .json, .xml** in-app  
- Visualización de **imágenes** con **zoom, desplazamiento y rotación**  
- **Intents** para abrir tipos no soportados  
- Gestión: **crear, renombrar, copiar, mover, eliminar**  
- **Historial** y **favoritos** persistentes (Room/DataStore)  
- **Búsqueda** por nombre/tipo/fecha  
- **Caché de miniaturas** para rendimiento

### Interfaz
- Navegación con **breadcrumbs / barra de ruta**  
- **Iconos** por tipo de archivo  
- **Lista/cuadrícula** adaptable y **responsive**  
- **Temas Guinda/Azul** + claro/oscuro

### Almacenamiento y seguridad
- Soporte a **Scoped Storage (Android 10+)**  
- Uso de **MediaStore** y/o **Storage Access Framework (SAF)** cuando aplica  
- Manejo de **excepciones** para rutas inaccesibles/archivos corruptos  
- Respeto a restricciones de seguridad según versión

---

## Ejercicio 2 — Cámara y micrófono

**Resumen:** Captura de **fotos** (CameraX/Camera2) y **grabación de audio** (MediaRecorder), con **galería** y **reproductor** integrados.

### Funcionalidades clave
- **Cámara**: previsualización en tiempo real, **flash** (auto/on/off), **temporizador** (3/5/10s), **switch** frontal/trasera, **filtros** básicos (grises, sepia, brillo/contraste)  
- **Audio**: **MediaRecorder**, nivel de audio en tiempo real, **iniciar/pausar/reanudar/detener**, temporizador configurable, selección de **calidad**  
- **Gestión de archivos**: fotos **JPEG/PNG** con **EXIF**, audio **AAC/MP3/M4A**; guardado con **MediaStore** en colecciones adecuadas  
- **Galería**: cuadrícula de miniaturas, visor con **gestos**, edición básica (recorte/rotación/ajustes), **compartir** por intents, info **EXIF**  
- **Reproductor**: lista de grabaciones (duración/fecha), controles completos, **waveform**, renombrar/compartir/eliminar  
- **Organización**: álbumes/categorías, etiquetas, búsqueda/ordenamiento, selección múltiple  
- **UI/UX**: gestos y animaciones fluidas; feedback visual/sonoro/háptico; **Temas Guinda/Azul**

### Persistencia
- **MediaStore** para archivos multimedia  
- **Room** para metadatos (fecha, ubicación, etiquetas, configuración)  
- **Caché de miniaturas** y opciones de **exportar/compartir**

---

## Permisos requeridos y justificación

> Los permisos se solicitan **en tiempo de ejecución** y solo cuando son necesarios, con **mensajes de racional** (explicación) y manejo de **denegaciones permanentes**.

### Ejercicio 1 — Gestor de archivos
| Permiso | Android | ¿Para qué se usa? |
|---|---|---|
| `READ_MEDIA_IMAGES`, `READ_MEDIA_VIDEO`, `READ_MEDIA_AUDIO` | 13+ | Leer medios al generar miniaturas/galería y abrir archivos multimedia. |
| `READ_EXTERNAL_STORAGE` | 12- | Lectura de archivos en almacenamiento compartido (pre-Android 13). |
| `WRITE_EXTERNAL_STORAGE` | 28- | Operaciones de escritura en dispositivos antiguos (Android 9 o menor). |
| **Sin permiso** (SAF: `ACTION_OPEN_DOCUMENT`/`TREE`) | 10+ | Acceso a archivos/carpeta elegida por el usuario sin permisos globales. |
| `MANAGE_EXTERNAL_STORAGE` *(evitar)* | 11+ | Solo para casos “All files access”. **No se solicita** salvo demostración académica controlada. |

> **Estrategia recomendada:** priorizar **SAF/MediaStore** y evitar `MANAGE_EXTERNAL_STORAGE`. En APIs modernas, usar `READ_MEDIA_*` en lugar de `READ_EXTERNAL_STORAGE`.

### Ejercicio 2 — Cámara y micrófono
| Permiso | Android | ¿Para qué se usa? |
|---|---|---|
| `CAMERA` | Todos | Capturar fotografías y previsualizar con CameraX/Camera2. |
| `RECORD_AUDIO` | Todos | Grabar audio con MediaRecorder y mostrar nivel de entrada. |
| `READ_MEDIA_IMAGES` | 13+ | Leer fotos para la galería/visor/edición. |
| `READ_MEDIA_AUDIO` | 13+ | Leer grabaciones para el reproductor. |
| `FOREGROUND_SERVICE_*` *(según versión)* | 14+ | Servicios en primer plano para captura/recording de larga duración con notificación persistente. |
| `POST_NOTIFICATIONS` | 13+ | Mostrar notificaciones de progreso/estado (opcional). |

> En Android 10+ el guardado de multimedia se hace vía **MediaStore** sin permisos de escritura global.

---

## Capturas de pantalla

Coloca las capturas en `./screenshots/ej1` y `./screenshots/ej2` con la siguiente convención:

```
screenshots/
  ej1/
    guinda_claro_*.png
    guinda_oscuro_*.png
    azul_claro_*.png
    azul_oscuro_*.png
  ej2/
    guinda_claro_*.png
    guinda_oscuro_*.png
    azul_claro_*.png
    azul_oscuro_*.png
```

> **Requisito:** incluir las **4 combinaciones** de tema/modo (Guinda claro/oscuro, Azul claro/oscuro) por cada ejercicio.

---

## APK instalables

Los APK de entrega se encuentran en `./apks/` con nombres descriptivos:

- `ej1-file-manager-release.apk`  
- `ej2-camera-audio-release.apk`

**Instalación (dispositivo físico):**
1. Activa **Instalar apps de orígenes desconocidos**.  
2. Copia el APK al dispositivo y ábrelo, o usa:
   ```bash
   adb install -r apks/ej1-file-manager-release.apk
   adb install -r apks/ej2-camera-audio-release.apk
   ```

---

## Notas de arquitectura y buenas prácticas

- **Patrón:** MVVM con capas **UI**, **Domain** y **Data**; **DI** con 🔧 _Hilt/Koin_ (según implementación).  
- **Persistencia:** **Room** (metadatos), **DataStore** (preferencias/tema), **MediaStore** (multimedia).  
- **UI:** **Material Design**, componentes **Jetpack** (Lifecycle, ViewModel, Navigation, Paging si aplica).  
- **Rendimiento:** caché de miniaturas, cargas diferidas, uso de **coroutines/Flow**.  
- **Permisos:** flujos de solicitud con **rationales** y manejo de **denegaciones permanentes** (enlace a ajustes).  
- **Compatibilidad:** rutas separadas por **nivel de API** (p. ej. `READ_MEDIA_*` vs `READ_EXTERNAL_STORAGE`).  
- **Calidad:** **lint**, **ktlint/Detekt** (opcional), comentarios en español en secciones complejas, nombres descriptivos.

---

## Pruebas (resumen)

- **Dispositivos/Emuladores:** 🔧 _Modelo/Android X_, 🔧 _Modelo/Android Y_  
- **Casos principales:**  
  - **Ejercicio 1:** navegación de carpetas, operaciones (crear/mover/eliminar), apertura por intents, búsqueda/favoritos.  
  - **Ejercicio 2:** captura con flash/temporizador, cambio de cámara, grabación pausar/reanudar, guardado en MediaStore, visor/galería, reproductor con waveform.  
- **Permisos:** flujos de concesión/denegación (incluye **denegación permanente**) validados.  
- **Rendimiento:** carga de miniaturas y scroll fluido en listas grandes.  

> El **informe técnico** detallado se entrega en `./docs/informe-tecnico.pdf` (fuera del alcance de este README).

