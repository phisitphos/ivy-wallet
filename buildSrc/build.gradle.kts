plugins {
    `kotlin-dsl`
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(libs.versions.jvm.get().toInt()))
    }
}

dependencies {
    implementation(libs.plugins.kotlin.mpp)
    implementation(libs.plugins.kotlin.android)
    implementation(libs.plugins.kotlin.native.cocoapods)
    implementation(libs.plugins.android.application)
    implementation(libs.plugins.android.library)
    implementation(libs.plugins.compose)
}

// This is a future Gradle feature to make declaring dependencies to plugins simpler
// See an upvote https://docs.google.com/document/d/1P7aTeeVNhkhwxcS5sQNFrSsmqJOhDo3G8kUdhtp_vyM
fun DependencyHandler.implementation(pluginDependency: Provider<PluginDependency>): Dependency? =
    add("implementation", pluginDependency.map {
        "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version.requiredVersion}"
    }.get())
