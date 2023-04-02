plugins {
    id("org.jetbrains.compose")
    id("com.android.application")
    kotlin("android")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":core"))
    implementation("androidx.activity:activity-compose:1.7.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.core:core-ktx:1.9.0")

    implementation(project(":home"))
}

android {
    compileSdk = Android.compileSdk
    defaultConfig {
        applicationId = Android.applicationId
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk
        versionCode = Android.version.first
        versionName = Android.version.second
    }
    compileOptions {
        sourceCompatibility = Java.version
        targetCompatibility = Java.version
    }

    signingConfigs {
        getByName("debug") {
            storeFile = file("../debug.jks")
            storePassword = "IVY7834!DEbug"
            keyAlias = "debug"
            keyPassword = "IVY7834!DEbug"
        }

        create("release") {
            storeFile = file("../sign.jks")
            storePassword = System.getenv("SIGNING_STORE_PASSWORD")
            keyAlias = System.getenv("SIGNING_KEY_ALIAS")
            keyPassword = System.getenv("SIGNING_KEY_PASSWORD")
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
        create("demo") {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            isDefault = false

            signingConfig = signingConfigs.getByName("debug")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            applicationIdSuffix = ".demo"
            matchingFallbacks.add("release")
        }
    }
}