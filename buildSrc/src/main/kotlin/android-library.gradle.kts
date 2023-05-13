import ivy.androidCompileSdk
import ivy.androidMinSdk
import ivy.jvm
import ivy.libs

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.multiplatform")
}

kotlin {
    android()
}

android {

    compileSdk = libs.androidCompileSdk

    defaultConfig {
        minSdk = libs.androidMinSdk
    }

    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(libs.jvm)
        targetCompatibility = JavaVersion.toVersion(libs.jvm)
    }
}
