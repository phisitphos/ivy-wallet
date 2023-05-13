import ivy.*

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-compose-component")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(libs.jvm))
    }
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

    applicationVariants.all {
        registerGeneratedResFolders(files(tasks.named("sharedDrawables")))
    }
}

dependencies {
    implementation(libs.androidxAppcompat)
    implementation(libs.androidxCoreKtx)
    implementation(libs.androidxActivityCompose)
}
