object Deps {
    object Arrow {
        //https://arrow-kt.io/docs/quickstart/#setup
        const val version = "1.1.6-alpha.85"
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
}