# 📍 GeoTracker Pro

**GeoTracker Pro** es una aplicación nativa de Android diseñada para el rastreo y monitoreo de rutas GPS en tiempo real. Utilizando la potencia de OpenStreetMap y servicios en segundo plano, la aplicación permite registrar recorridos con alta precisión, incluso cuando el dispositivo está bloqueado.

---

## 📱 Funcionalidades Principales

* **Rastreo en Tiempo Real:** Visualización de la ubicación actual y trazado de la ruta ("migas de pan") sobre el mapa.
* **Mapa Interactivo (OSM):** Integración con OpenStreetMap mediante `osmdroid` para una experiencia de navegación fluida sin costos de API.
* **Ejecución en Segundo Plano:** Implementación de un *Foreground Service* persistente que garantiza que el rastreo continúe aunque se minimice la app o se apague la pantalla.
* **Estadísticas en Vivo:** Panel de control con datos de Latitud, Longitud, Precisión (metros) y contador de puntos.
* **Gestión de Energía:** Selector de frecuencia de actualización personalizable para equilibrar la precisión y el consumo de batería.
* **Historial de Rutas:** Almacenamiento local persistente de los puntos recorridos para consulta posterior.
* **Interfaz Moderna:** UI desarrollada 100% en **Jetpack Compose** con Material Design 3.

---

## 🛠️ Ficha Técnica

El proyecto sigue una arquitectura **MVVM (Model-View-ViewModel)** y Clean Architecture básica para asegurar la escalabilidad y mantenibilidad del código.

### Tecnologías y Librerías:
* **Lenguaje:** Kotlin
* **UI Toolkit:** Jetpack Compose (Material 3)
* **Mapas:** osmdroid (OpenStreetMap Tools for Android)
* **Concurrencia:** Kotlin Coroutines & Flows.
* **Inyección de Dependencias:** Manual (ViewModel Factory / Provider).
* **Base de Datos:** Room (SQLite abstraction) para persistencia de coordenadas.
* **Servicios:** Android Foreground Service con notificaciones persistentes.

### Permisos Requeridos:
* `ACCESS_FINE_LOCATION`: Para obtener la ubicación precisa del GPS.
* `ACCESS_COARSE_LOCATION`: Para ubicación aproximada (red).
* `FOREGROUND_SERVICE`: Para mantener el rastreo activo fuera de la app.
* `POST_NOTIFICATIONS`: Para mostrar el estado del servicio en la barra de notificaciones (Android 13+).

---

## 🚀 Instalación y Ejecución

1.  Clonar el repositorio o descargar el código fuente.
2.  Abrir el proyecto en **Android Studio Koala** o superior.
3.  Sincronizar el proyecto con Gradle (`File > Sync Project with Gradle Files`).
4.  Conectar un dispositivo físico o usar un emulador.
5.  Ejecutar la aplicación (`Run 'app'`).

> **Nota:** Al ejecutar por primera vez, asegúrese de otorgar los permisos de ubicación "Mientras la app está en uso" y las notificaciones.

---

## 📂 Estructura del Proyecto

```text
com.example.geotracker
├── data             # Capa de datos (Room DB, DAO, Entidades)
├── tracking         # Lógica del servicio GPS y ViewModel
├── ui               # Componentes visuales (Screens, Composables)
├── theme            # Definiciones de estilo y tipografía
└── MainActivity.kt  # Punto de entrada y configuración de navegación