package ivy

import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.the

internal
val Project.libs: VersionCatalog
    get() = the<VersionCatalogsExtension>().named("libs")

internal
val VersionCatalog.jvm: Int
    get() = findVersion("jvm").get().requiredVersion.toInt()

internal
val VersionCatalog.iosDeploymentTarget: String
    get() = findVersion("ios-deploymentTarget").get().requiredVersion

internal
val VersionCatalog.androidCompileSdk: Int
    get() = findVersion("android-sdk-compile").get().requiredVersion.toInt()

internal
val VersionCatalog.androidMinSdk: Int
    get() = findVersion("android-sdk-min").get().requiredVersion.toInt()

internal
val VersionCatalog.androidxAppcompat: Provider<MinimalExternalModuleDependency>
    get() = findLibrary("androidx-appcompat").get()

internal
val VersionCatalog.androidxCoreKtx: Provider<MinimalExternalModuleDependency>
    get() = findLibrary("androidx-core-ktx").get()

internal
val VersionCatalog.androidxActivityCompose: Provider<MinimalExternalModuleDependency>
    get() = findLibrary("androidx-activity-compose").get()

internal
val VersionCatalog.arrowCore: Provider<MinimalExternalModuleDependency>
    get() = findLibrary("arrow-core").get()

internal
val VersionCatalog.kotestAssertions: Provider<MinimalExternalModuleDependency>
    get() = findLibrary("kotest-assertions").get()

internal
val VersionCatalog.kotestEngine: Provider<MinimalExternalModuleDependency>
    get() = findLibrary("kotest-engine").get()

internal
val VersionCatalog.kotestProperty: Provider<MinimalExternalModuleDependency>
    get() = findLibrary("kotest-property").get()

internal
val VersionCatalog.kotestDatatest: Provider<MinimalExternalModuleDependency>
    get() = findLibrary("kotest-datatest").get()

internal
val VersionCatalog.kotestArrow: Provider<MinimalExternalModuleDependency>
    get() = findLibrary("kotest-arrow").get()

internal
val VersionCatalog.kotestJunit5: Provider<MinimalExternalModuleDependency>
    get() = findLibrary("kotest-junit5").get()

internal
val VersionCatalog.kotlinStdlib: Provider<MinimalExternalModuleDependency>
    get() = findLibrary("kotlin-stdlib").get()

internal
val VersionCatalog.kotlinCoroutinesCore: Provider<MinimalExternalModuleDependency>
    get() = findLibrary("kotlin-coroutines-core").get()

internal
val VersionCatalog.kotlinxDatetime: Provider<MinimalExternalModuleDependency>
    get() = findLibrary("kotlinx-datetime").get()

internal
val VersionCatalog.thirdpartyUUID: Provider<MinimalExternalModuleDependency>
    get() = findLibrary("thirdparty-uuid").get()


