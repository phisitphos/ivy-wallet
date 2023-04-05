package ivy.core

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.*
import io.kotest.property.checkAll
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Task(
    val id: String,
    val completed: Boolean,
    val title: String,
    val description: String?,
    val orderNum: Double,
    val info: TaskInfo
)

@Serializable
data class TaskInfo(
    val creator: String,
    val priority: Int
)

class KotlinXSerializationTest : FreeSpec({
    "serialize" {
        val task = Task(
            id = "1",
            completed = true,
            title = "title",
            description = "description",
            orderNum = 1.0,
            info = TaskInfo(
                creator = "creator",
                priority = 1
            )
        )
        val json = Json.encodeToString(Task.serializer(), task)
        json shouldBe """{"id":"1","completed":true,"title":"title","description":"description","orderNum":1.0,"info":{"creator":"creator","priority":1}}"""
    }

    "deserialize" {
        val json =
            """{"id":"1","completed":true,"title":"title","description":"description","orderNum":1.0,"info":{"creator":"creator","priority":1}}"""
        val task = Json.decodeFromString(Task.serializer(), json)
        task shouldBe Task(
            id = "1",
            completed = true,
            title = "title",
            description = "description",
            orderNum = 1.0,
            info = TaskInfo(
                creator = "creator",
                priority = 1
            )
        )
    }

    "invariant: serialize <=> deserialize" {
        val arbTask = arbitrary {
            Task(
                id = Arb.string().bind(),
                completed = Arb.boolean().bind(),
                title = Arb.string().bind(),
                description = Arb.string().orNull().bind(),
                orderNum = Arb.double(-1_000.0..1_000.0).removeEdgecases().bind(),
                info = TaskInfo(
                    creator = Arb.string().bind(),
                    priority = Arb.int(1..100).removeEdgecases().bind()
                )
            )
        }

        checkAll(arbTask) { original ->
            val json = Json.encodeToString(Task.serializer(), original)
            val deserialized = Json.decodeFromString(Task.serializer(), json)
            original shouldBe deserialized
        }
    }
})
