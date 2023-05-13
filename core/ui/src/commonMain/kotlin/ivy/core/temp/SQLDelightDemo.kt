package ivy.core.temp

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import ivy.Database
import ivy.sqldelight.HockeyPlayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

context(Database)
fun peopleFlow(): Flow<List<HockeyPlayer>> = personQueries.selectAll()
    .asFlow()
    .mapToList(Dispatchers.IO)
