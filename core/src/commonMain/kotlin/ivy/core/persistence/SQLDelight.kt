package ivy.core.persistence

import app.cash.sqldelight.db.SqlDriver
import ivy.Database

expect class DriverFactory {
    fun createDriver(): SqlDriver
}

fun createDatabase(driverFactory: DriverFactory): Database {
    val driver = driverFactory.createDriver()
    return Database(driver)
}
