package ivy.core.persistence.setup

import app.cash.sqldelight.db.SqlDriver
import ivy.Database

fun createDatabase(sqlDriver: SqlDriver): Database {
    return Database(sqlDriver)
}
