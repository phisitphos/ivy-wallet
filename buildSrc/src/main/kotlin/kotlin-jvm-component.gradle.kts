import ivy.jvm
import ivy.libs

plugins {
    id("org.jetbrains.kotlin.multiplatform")
}

kotlin {
    jvm {
        jvmToolchain(libs.jvm)
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
}
