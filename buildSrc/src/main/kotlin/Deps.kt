object Deps {
    object Coroutines {
        //https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-core
        const val version = "1.7.0-Beta"
    }

    object Arrow {
        //https://apidocs.arrow-kt.io/
        const val version = "1.2.0-RC"

        object Optics {
            val ksp = "io.arrow-kt:arrow-optics-ksp-plugin:$version"
        }
    }

    object Kotest {
        //https://kotest.io/
        const val version: String = "5.4.2"

        //https://github.com/kotest/kotest-extensions-arrow
        const val versionArrowExt = "1.3.0"

        //https://search.maven.org/search?q=g:org.junit.jupiter
        const val versionJUnit5 = "5.9.2"
    }

    object SQLDelight {
        //https://cashapp.github.io/sqldelight/
        const val version = "2.0.0-alpha05"
    }

    object KotlinXSerialization {
        //https://kotlinlang.org/docs/serialization.html
        const val version = "1.5.0"
    }

    object Ktor {
        //https://ktor.io/docs/client-dependencies.html
        const val version = "2.2.4"
    }

    object Circuit {
        //https://slackhq.github.io/circuit/setup/
        const val version = "0.8.0"
    }
}