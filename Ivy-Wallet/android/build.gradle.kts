plugins {
    id("org.jetbrains.compose")
    id("com.android.application")
    kotlin("android")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":common"))
    implementation("androidx.activity:activity-compose:1.7.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.core:core-ktx:1.9.0")

    implementation(project(":home"))
}

android {
    compileSdk = Android.compileSdk
    defaultConfig {
        applicationId = "ivy.android"
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk
        versionCode = Android.version.first
        versionName = Android.version.second
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}