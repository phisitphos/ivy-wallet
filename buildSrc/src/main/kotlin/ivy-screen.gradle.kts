plugins {
    id("shared-logic")
    id("shared-ui")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.runtime)
                implementation(project(":core:ui"))
            }
        }
    }
}