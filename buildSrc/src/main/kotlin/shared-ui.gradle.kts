plugins {
    id("android-library")
    id("kotlin-jvm-component")
    id("kotlin-ios-component")
    id("kotlin-compose-component")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.runtime)
                implementation(project(":core:logic"))
            }
        }
    }
}
