# Práctica 1 - Instalación y Funcionamiento de los Entornos Móviles

El objetivo de esta práctica es fortalecer las habilidades de desarrollo móvil a través de la exploración e implementación de entornos de navegación, la gestión de actividades múltiples y la personalización de interfaces de usuario en aplicaciones nativas de Android.

---

## Ejercicio 1: Instalación de Herramientas

🎯 **Objetivo:** En este primer ejercicio, deberás instalar y configurar las siguientes herramientas esenciales en tu sistema (macOS, Linux o Windows) para el desarrollo de proyectos de Android.

### Android Studio
- Descarga e instala **Android Studio**.
- Configura el IDE (entorno de desarrollo integrado) y asegúrate de que el emulador esté funcionando correctamente para ejecutar aplicaciones Android.

### Herramientas adicionales
- **Java Development Kit (JDK):** Se recomienda usar Amazon Corretto, una distribución gratuita y certificada de OpenJDK. Deberás descargar e instalar la versión más reciente del JDK, la cual es necesaria para compilar y ejecutar programas en Java.
- **Maven:** Esta herramienta te permitirá automatizar la construcción de proyectos y gestionar sus dependencias. Una vez instalada, deberás configurarla en tu sistema.
- **Git:** Instala Git para el control de versiones de tus proyectos. Configúralo en tu equipo para trabajar con repositorios remotos.
- **GitHub:** Crea una cuenta en GitHub y un repositorio público donde almacenarás tus proyectos durante el curso.
- **Docker:** En lugar de XAMPP, utiliza Docker para la gestión de bases de datos.
- **Node.js:** Este es un entorno de ejecución de JavaScript del lado del servidor que permite ejecutar código JavaScript fuera de un navegador.
- **Flutter:** La instalación de Flutter es obligatoria para esta práctica.

**Opcional:** Adicionalmente, puedes elegir otra plataforma para desarrollo móvil nativo y explicar su proceso de instalación en tu documentación.

### Evidencias
- Toma capturas de pantalla del IDE mostrando el emulador con la aplicación **"Hello Android"** ejecutándose correctamente.

---

## Ejercicio 2: Transiciones entre Activities y Fragments

🎯 **Objetivo:** Crear una aplicación Android que demuestre el uso de Activities y Fragments para explicar diferentes elementos de interfaz de usuario.

### 📋 Instrucciones:
1. **Estructura de la App:**  
   Crea una Activity principal que contenga cinco Fragments, cada uno explicando un elemento de interfaz diferente:  
   - Fragment 1: TextFields (EditText)  
   - Fragment 2: Botones (Button, ImageButton)  
   - Fragment 3: Elementos de selección (CheckBox, RadioButton, Switch)  
   - Fragment 4: Listas (RecyclerView o ListView)  
   - Fragment 5: Elementos de información (TextView, ImageView, ProgressBar)  

2. **Navegación:**  
   Incluye un menú de navegación (botones o tabs) para cambiar entre fragments. Cada fragment debe ser accesible desde el menú principal.

3. **Contenido de cada Fragment:**  
   Cada fragment debe incluir:  
   - 📝 Un título descriptivo del elemento de UI.  
   - 🎨 Ejemplos visuales del elemento funcionando.  
   - 💡 Una explicación breve de para qué sirve (máximo 2-3 líneas).  
   - ⚡ Una demostración interactiva donde el usuario pueda probar el elemento.

4. **Flutter y Kotlin:**  
   Crea una versión de la aplicación en **Kotlin (Android nativo)** y otra versión en **Flutter**.

### ✅ Requisitos Técnicos:
- Una MainActivity con navegación entre fragments.  
- Cinco Fragments diferentes (uno por elemento de UI).  
- Cada fragment debe ser funcional e interactivo.  
- Diseño limpio y organizado.  
- Funcionalidad que conecte diferentes fragments.  