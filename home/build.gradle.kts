plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("com.android.library")
    id("com.google.devtools.ksp")
}

kotlin {
    android()
    jvm("desktop") {
        jvmToolchain(Desktop.jvmToolchain)
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":common"))
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
