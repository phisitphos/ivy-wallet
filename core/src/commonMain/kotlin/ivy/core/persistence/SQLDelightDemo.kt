package ivy.core.persistence

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun playWithDb(SQLDelightDriverFactory: SQLDelightDriverFactory): List<String> = withContext(Dispatchers.IO) {
    val db = createDatabase(SQLDelightDriverFactory)
    db.personQueries.insert(1, "John")
    db.personQueries.insert(2, "Iliyan")
    db.personQueries.selectAll().executeAsList().map { it.full_name }
}
