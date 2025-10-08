import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
}

val javaVersion = JavaVersion.toVersion(libs.versions.java.get())

android {
    namespace = "com.beletskiy.bac.ui"
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
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }
}
kotlin {
    jvmToolchain(javaVersion.majorVersion.toInt())

    compilerOptions {
        jvmTarget.set(JvmTarget.fromTarget(javaVersion.toString()))
    }
}

dependencies {
    implementation(project(":feature-bulls-and-cows-data"))
    implementation(project(":shared"))

    implementation(libs.core.ktx)

    // Compose
    implementation(platform(libs.androidx.compose.bom)) // Compose BOM
    implementation(libs.androidx.material3) // Material Design 3
    implementation(libs.androidx.ui) // main APIs for the underlying toolkit systems
    implementation(libs.androidx.ui.tooling) // Android Studio Preview support
    implementation(libs.androidx.ui.tooling.preview) // Android Studio Preview support
    implementation(libs.androidx.ui.graphics) // ???
    implementation(libs.compose.navigation) // Compose Navigation
    implementation(libs.compose.icon.core)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose) // Hilt and Navigation

    // Serialization
    implementation(libs.kotlinx.serialization.json)
}
