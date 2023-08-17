@file:Suppress("UnstableApiUsage")

// Without these suppressions version catalog usage here and in other build
// files is marked red by IntelliJ:
// https://youtrack.jetbrains.com/issue/KTIJ-19369.
@Suppress(
    "DSL_SCOPE_VIOLATION",
    "MISSING_DEPENDENCY_CLASS",
    "UNRESOLVED_REFERENCE_WRONG_RECEIVER",
    "FUNCTION_CALL_EXPECTED",
)
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.mapsSecretsPlugin)
}

android {
    namespace = "com.example.tournavigation"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.tournavigation"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

secrets {
    defaultPropertiesFileName = "local.defaults.properties"
}

dependencies {
    implementation(libs.android.coreKtx)
    implementation(libs.lifecycle.runtimeKtx)
    implementation(libs.bundles.compose)

    testImplementation(libs.test.junit)

    androidTestImplementation(libs.android.test.junit)
    androidTestImplementation(libs.android.test.espresso)
    androidTestImplementation(libs.android.test.composeJunit)
    debugImplementation(libs.android.test.composeManifest)

    implementation(libs.maps.compose)
    implementation(libs.maps.playServices)
}
