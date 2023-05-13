plugins {
    id("shared-ui-library")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {

            }
        }
    }
}

android {
    namespace = "ivy.core.ui"
}