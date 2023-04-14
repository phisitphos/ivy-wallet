package ivy.core.persistence.setup

import app.cash.sqldelight.db.SqlDriver

expect class SQLDelightDriverFactory {
    fun createDriver(): SqlDriver
}
