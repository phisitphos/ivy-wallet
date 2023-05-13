pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
    }
}

plugins {
    `gradle-enterprise`
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.4.0"
}

rootProject.name = "Ivy-Wallet"

include(
    ":android",
    ":desktop",
    ":core",
    ":home",
    ":ivy-wallet"
)

for (project in rootProject.children) {
    project.projectDir = file("applications/${project.name}")
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
        maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven(url = "https://androidx.dev/storage/compose-compiler/repository/")
    }
}