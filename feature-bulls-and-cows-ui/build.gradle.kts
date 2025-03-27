plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.beletskiy.bac.ui"
    compileSdk = 35

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
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
    implementation(project(":feature-bulls-and-cows-data"))
    implementation(project(":resources"))

    implementation(libs.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose) // Hilt and Navigation

    // Compose
    implementation(platform(libs.androidx.compose.bom)) // Compose BOM
    implementation(libs.androidx.material3) // Material Design 3
    implementation(libs.androidx.ui) // main APIs for the underlying toolkit systems
    implementation(libs.androidx.ui.tooling.preview) // Android Studio Preview support
    implementation(libs.androidx.activity.compose) // Optional - Integration with activities
    implementation(libs.androidx.ui.graphics) // ???
    implementation(libs.compose.navigation) // Compose Navigation
}
