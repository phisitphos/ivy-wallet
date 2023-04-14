package ivy.core.persistence

import app.cash.sqldelight.db.SqlDriver

expect class SQLDelightDriverFactory {
    fun createDriver(): SqlDriver
}
