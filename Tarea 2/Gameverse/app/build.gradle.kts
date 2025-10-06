plugins {
    id("com.android.application")
    kotlin("android")
    // ⬇️ Solo si vas a cargar sample data desde JSON con kotlinx-serialization
    // kotlin("plugin.serialization") version "<tuVersiónDeKotlin>"
}

android {
    namespace = "com.example.gameverse"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.gameverse"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        // Si usas vectores y quieres compat hasta API bajas (no es necesario en minSdk 24)
        // vectorDrawables { useSupportLibrary = true }
    }

    buildFeatures {
        viewBinding = true
        // compose = false  // no usamos Compose
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }

    // (Opcional) Si alguna librería trae licencias en META-INF y te molesta el build:
    // packaging {
    //     resources {
    //         excludes += "/META-INF/{AL2.0,LGPL2.1}"
    //     }
    // }
}

dependencies {
    // Base
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")

    // Material 3 + BottomSheet + Transitions de Material (FadeThrough, SharedAxis, ContainerTransform)
    implementation("com.google.android.material:material:1.12.0")

    // Transitions de AndroidX (Material las usa por debajo; la agrego explícita para evitar CNF)
    implementation("androidx.transition:transition:1.5.1")

    // UI
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.fragment:fragment-ktx:1.8.3")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.activity:activity-ktx:1.9.2")
    implementation("com.google.android.flexbox:flexbox:3.0.0") // opcional para grids fluidos

    // (Recomendado, aunque no estrictamente necesario para tu código actual)
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.6")
    implementation(libs.androidx.activity)

    // ⬇️ Solo si decides leer sample data desde JSON en /assets o /raw con kotlinx-serialization:
    // implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.2")

    // ⬇️ Si en el futuro quieres cargar imágenes desde URLs:
    // implementation("io.coil-kt:coil:2.6.0")

    implementation("androidx.palette:palette-ktx:1.0.0")
}
