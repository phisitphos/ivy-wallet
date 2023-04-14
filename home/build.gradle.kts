plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("com.android.library")
    id("com.google.devtools.ksp")
}

kotlin {
    // Enable the 'context receivers' language feature in the Kotlin compiler
    sourceSets.all {
        languageSettings.apply {
            enableLanguageFeature("ContextReceivers")
        }
    }

    android()
    jvm("desktop") {
        jvmToolchain(Java.jvmToolchain)
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":core"))
            }
        }
        val commonTest by getting {
            dependencies {
                dependsOn(commonMain)
                implementation(kotlin("test"))
            }
        }
    }
}

android {
    compileSdk = Android.compileSdk
    namespace = "ivy.home"
    defaultConfig {
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk
    }
    compileOptions {
        sourceCompatibility = Java.version
        targetCompatibility = Java.version
    }
}
