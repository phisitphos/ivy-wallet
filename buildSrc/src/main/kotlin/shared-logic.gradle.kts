import ivy.*

plugins {
    kotlin("multiplatform")
    id("android-library")
    id("kotlin-js-library")
    id("kotlin-jvm-component")
    id("kotlin-ios-component")
    id("kotlin-compose-component")
}

kotlin {
    // Enable the 'context receivers' language feature in the Kotlin compiler
    sourceSets.all {
        languageSettings.apply {
            enableLanguageFeature("ContextReceivers")
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinCoroutinesCore)
                implementation(libs.kotlinStdlib)
                implementation(compose.runtime)
                implementation(libs.arrowCore)
                implementation(libs.kotlinxDatetime)
                implementation(libs.thirdpartyUUID)
            }
        }

        commonTest {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.kotestAssertions)
                implementation(libs.kotestEngine)
                implementation(libs.kotestProperty)
                implementation(libs.kotestDatatest)
                implementation(libs.kotestArrow)
            }
        }

        named("jvmTest") {
            dependencies {
                implementation(libs.kotestJunit5)
            }
        }
    }
}