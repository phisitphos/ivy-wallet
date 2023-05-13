import org.jetbrains.compose.desktop.application.dsl.TargetFormat.*

plugins {
    id("kotlin-jvm-component")
    id("kotlin-compose-component")
}

kotlin {
    sourceSets {
        named("jvmMain") {
            dependencies {
                implementation(compose.desktop.currentOs)
            }
        }
    }
}

compose.desktop {
    application {
        nativeDistributions {
            targetFormats(Deb, Rpm, Dmg, Msi)
            licenseFile.set(rootDir.resolve("LICENSE"))
        }
    }
}
