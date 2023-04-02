import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

group = "ivy"
version = "1.0-SNAPSHOT"

allprojects {
    apply(plugin = "checkstyle")

    // https://github.com/checkstyle/checkstyle
    configure<CheckstyleExtension> {
        configFile = file("${project.rootDir}/config/checkstyle/checkstyle.xml")
        toolVersion = "10.9.3"
        isIgnoreFailures = false
        isShowViolations = true
        maxWarnings = 0
    }

    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

plugins {
    kotlin("multiplatform") apply false
    kotlin("android") apply false
    id("com.android.application") apply false
    id("com.android.library") apply false
    id("org.jetbrains.compose") apply false
    id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false
    id("checkstyle")
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        // https://github.com/pinterest/ktlint
        classpath("com.pinterest:ktlint:0.45.1")
    }
}


// region Ktlint
tasks.register<JavaExec>("ktlint") {
    group = "verification"
    description = "Check Kotlin code style."
    main = "com.pinterest.ktlint.Main"
    classpath = project.buildscript.configurations["classpath"]
    val sourcePaths = subprojects.flatMap {
        it.extensions.getByType(KotlinProjectExtension::class.java).sourceSets.flatMap { sourceSet ->
            sourceSet.kotlin.sourceDirectories
        }
    }
    args(sourcePaths.map { it.absolutePath + "/**/*.kt" })
}
// endregion