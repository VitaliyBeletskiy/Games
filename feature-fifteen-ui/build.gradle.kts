plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.beletskiy.fifteen.ui"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    val javaVersion = JavaVersion.toVersion(libs.versions.java.get())
    compileOptions {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }
    kotlinOptions {
        jvmTarget = javaVersion.toString()
    }
}

dependencies {

    implementation(libs.core.ktx)

    // Compose
    implementation(platform(libs.androidx.compose.bom)) // Compose BOM
    implementation(libs.androidx.material3) // Material Design 3
    implementation(libs.androidx.ui.tooling.preview) // Android Studio Preview support
//    implementation("androidx.lifecycle:lifecycle-viewmodel-compose") // Optional (if you're using ViewModels)
    implementation(libs.compose.navigation) // if you're navigating inside the module

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.espresso.core)
}
