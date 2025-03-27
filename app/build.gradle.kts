plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.beletskiy.games"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.beletskiy.games"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//        testInstrumentationRunner = "com.beletskiy.games.GamesTestRunner" temporarily commented
        vectorDrawables {
            useSupportLibrary = true
        }
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
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":feature-bulls-and-cows-ui"))
    implementation(project(":feature-tic-tac-toe-ui"))
    implementation(project(":feature-fifteen-ui"))
    implementation(project(":resources"))

    implementation(libs.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Compose
    implementation(platform(libs.androidx.compose.bom)) // Compose BOM
    implementation(libs.androidx.material3) // Material Design 3
    implementation(libs.androidx.ui) // main APIs for the underlying toolkit systems
    implementation(libs.androidx.ui.tooling.preview) // Android Studio Preview support
    implementation(libs.androidx.activity.compose) // Optional - Integration with activities
    implementation(libs.androidx.ui.graphics) // ???
    implementation(libs.compose.navigation) // Compose Navigation

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose) // Hilt and Navigation

    // Unit Testing
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)

    // Instrumented Testing
    androidTestImplementation(platform(libs.androidx.compose.bom)) // Compose BOM
    androidTestImplementation(libs.androidx.ui.test.junit4) // UI Tests
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.hilt.android) // Hilt
    kspAndroidTest(libs.hilt.compiler) // Hilt
    androidTestImplementation(libs.hilt.android.testing) // Hilt
    androidTestImplementation(libs.androidx.navigation.testing) // Navigation

    debugImplementation(libs.androidx.ui.tooling) // Android Studio Preview support
    debugImplementation(libs.androidx.ui.test.manifest) // UI Tests
}
