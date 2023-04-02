package ivy.core.persistence

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun playWithDb(driverFactory: DriverFactory): List<String> = withContext(Dispatchers.IO) {
    val db = createDatabase(driverFactory)
    db.personQueries.insert(1, "John")
    db.personQueries.insert(2, "Iliyan")
    db.personQueries.selectAll().executeAsList().map { it.full_name }
}
