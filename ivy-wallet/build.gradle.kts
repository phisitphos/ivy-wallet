plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("com.android.library")
    id("com.google.devtools.ksp")
    id("com.squareup.anvil")
    id("kotlin-parcelize")
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
                implementation(project(":home"))
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
    namespace = "ivy.root"
    defaultConfig {
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk
    }
    compileOptions {
        sourceCompatibility = Java.version
        targetCompatibility = Java.version
    }
}

// region DI (Dagger2 + Anvil)
dependencies {
    add("ksp", "com.slack.circuit:circuit-codegen:${Deps.Circuit.version}")
}

ksp {
    arg("anvil.merge.component", "true")
    arg("anvil.generate.factory", "true")
}

anvil {
    // Enable Dagger Factory generation
    generateDaggerFactories.set(true)
}
// endregion
