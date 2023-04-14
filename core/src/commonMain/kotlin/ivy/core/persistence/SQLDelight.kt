package ivy.core.persistence

import ivy.Database

fun createDatabase(driverFactory: SQLDelightDriverFactory): Database {
    val driver = driverFactory.createDriver()
    return Database(driver)
}
