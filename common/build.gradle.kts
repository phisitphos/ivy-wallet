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
    sourceSets["main"].manifest.srcFile("../android/src/main/AndroidManifest.xml")
    defaultConfig {
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
//    val arrowKsp = Deps.Arrow.Optics.ksp
//    add("kspCommonMain", arrowKsp)
}