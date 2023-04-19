plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("com.android.library")
    id("com.google.devtools.ksp")
    id("app.cash.sqldelight") version Deps.SQLDelight.version
    kotlin("plugin.serialization")
}

kotlin {
    // Enable the 'context receivers' language feature in the Kotlin compiler
    sourceSets.all {
        languageSettings.apply {
            enableLanguageFeature("ContextReceivers")
        }
    }

    android()
    jvm {
        jvmToolchain(Java.jvmToolchain)
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)

                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Deps.Coroutines.version}")

                // region Kodein (DI)
                api("org.kodein.di:kodein-di:${Deps.Kodein.version}")
                // endregion

                // region ArrowKt (Functional Programming)
                api(platform("io.arrow-kt:arrow-stack:${Deps.Arrow.version}"))
                api("io.arrow-kt:arrow-core")
                api("io.arrow-kt:arrow-fx-coroutines")
                api("io.arrow-kt:arrow-fx-stm")
                api("io.arrow-kt:arrow-optics")
                // endregion

                // region SQLDelight
                implementation("app.cash.sqldelight:coroutines-extensions:${Deps.SQLDelight.version}")
                // endregion

                // region KotlinX Serialization
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${Deps.KotlinXSerialization.version}")
                // endregion

                // region Ktor
                implementation("io.ktor:ktor-client-core:${Deps.Ktor.version}")
                implementation("io.ktor:ktor-client-logging:${Deps.Ktor.version}")
                implementation("io.ktor:ktor-client-content-negotiation:${Deps.Ktor.version}")
                implementation("io.ktor:ktor-serialization-kotlinx-json:${Deps.Ktor.version}")
                // endregion
            }
        }

        val androidMain by getting {
            dependencies {
                dependsOn(commonMain)
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Deps.Coroutines.version}")
                implementation("io.ktor:ktor-client-okhttp:${Deps.Ktor.version}")
                implementation("app.cash.sqldelight:android-driver:${Deps.SQLDelight.version}")
            }
        }

        val jvmMain by getting {
            dependencies {
                dependsOn(commonMain)
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:${Deps.Coroutines.version}")
                implementation("io.ktor:ktor-client-apache:${Deps.Ktor.version}")
                implementation("app.cash.sqldelight:sqlite-driver:${Deps.SQLDelight.version}")
            }
        }

        val commonTest by getting {
            dependencies {
                dependsOn(commonMain)
                dependsOn(jvmMain)
                implementation(kotlin("test"))

                // region Kotest
                implementation("org.junit.jupiter:junit-jupiter:${Deps.Kotest.versionJUnit5}")
                val kotestVersion = Deps.Kotest.version
                implementation("io.kotest:kotest-runner-junit5:$kotestVersion")
                implementation("io.kotest:kotest-assertions-core:$kotestVersion")
                implementation("io.kotest:kotest-property:$kotestVersion")
                implementation("io.kotest:kotest-framework-datatest:$kotestVersion")

                // Kotest - Arrow extensions
                val kotestArrow = Deps.Kotest.versionArrowExt
                implementation("io.kotest.extensions:kotest-assertions-arrow:$kotestArrow")
                implementation("io.kotest.extensions:kotest-assertions-arrow-fx-coroutines:$kotestArrow")
                implementation("io.kotest.extensions:kotest-property-arrow:$kotestArrow")
                // endregion
            }
        }
    }
}

android {
    compileSdk = Android.compileSdk
    namespace = "ivy.core"
    defaultConfig {
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk
    }
    compileOptions {
        sourceCompatibility = Java.version
        targetCompatibility = Java.version
    }

    testOptions {
        unitTests.all {
            it.useJUnitPlatform()
        }
    }
}

dependencies {
//    val arrowKsp = Deps.Arrow.Optics.ksp
//    add("kspCommonMain", arrowKsp)
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("ivy")
        }
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = Java.jvmToolchain.toString()
    }
}

tasks.named<Test>("jvmTest") {
    useJUnitPlatform()
}