package ivy.core.logic.persistence.setup

import app.cash.sqldelight.db.SqlDriver

expect class SQLDelightDriverFactory {
    fun createDriver(): SqlDriver
}
