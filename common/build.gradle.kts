plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("com.android.library")
    id("com.google.devtools.ksp")
    id("app.cash.sqldelight") version Deps.SQLDelight.version
}

kotlin {
    android()
    jvm("desktop") {
        jvmToolchain(Desktop.jvmToolchain)
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)

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
            }
        }
        val commonTest by getting {
            dependencies {
                dependsOn(commonMain)
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
                // endregion
            }
        }

        val androidMain by getting {
            dependencies {
                dependsOn(commonMain)
                implementation("app.cash.sqldelight:android-driver:${Deps.SQLDelight.version}")
            }
        }

        val desktopMain by getting {
            dependencies {
                dependsOn(commonMain)
                implementation("app.cash.sqldelight:sqlite-driver:${Deps.SQLDelight.version}")
            }
        }
    }
}

android {
    compileSdk = Android.compileSdk
    namespace = "ivy.common"
    defaultConfig {
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
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