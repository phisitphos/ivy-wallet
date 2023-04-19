import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    // Enable the 'context receivers' language feature in the Kotlin compiler
    sourceSets.all {
        languageSettings.apply {
            enableLanguageFeature("ContextReceivers")
        }
    }

    jvm {
        jvmToolchain(Java.jvmToolchain)
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(project(":core"))
                implementation(project(":ivy-wallet"))
            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = Desktop.packageName
            packageVersion = Desktop.version
        }

        buildTypes.release {
            proguard {
                configurationFiles.from("proguard-rules.pro")
            }
        }
    }
}
