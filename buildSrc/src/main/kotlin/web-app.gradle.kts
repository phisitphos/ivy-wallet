plugins {
    id("kotlin-js-executable")
    id("kotlin-compose-component")
}

kotlin {
    sourceSets {
        named("jsMain") {
            resources.srcDir(files(tasks.named("sharedBitmaps")))
            dependencies {
                implementation(compose.html.core)
                implementation(compose.runtime)
            }
        }
    }
}
