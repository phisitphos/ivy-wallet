dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}
rootProject.name = "Ivy Wallet"
include(":app")
include(":common")
include(":ui-common")
include(":reports")
include(":accounts")
include(":categories")
include(":home")
include(":more-menu")
include(":planned-payments")
include(":transaction-details")
include(":pie-charts")
include(":budgets")
include(":loans")
include(":settings")
include(":onboarding")
include(":item-transactions")
include(":search-transactions")
include(":screens")
include(":data-model")
include(":exchange")
include(":donate")
include(":main")
include(":app-base")
include(":ui-components-old")
include(":customer-journey")
include(":temp-domain")
include(":temp-persistence")
include(":widgets")
include(":app-locked")
include(":balance-prediction")
include(":import-csv-backup")
include(":temp-network")
include(":billing")
include(":web-view")
include(":android-notifications")
include(":state")
include(":core-actions")
include(":core-functions")
include(":sync:public")
include(":sync:base")
include(":sync:ivy-server")
include(":network")