package ivy.core.data

import ivy.core.data.common.UniqueId
import ivy.core.data.primitives.PositiveInt
import ivy.core.data.time.DayOfMonth
import java.time.DayOfWeek
import java.time.Instant
import java.util.*

/**
 * Represents a unique identifier for a [Recurrence] instance.
 */
@JvmInline
value class RecurrenceId(override val value: UUID) : UniqueId

/**
 * Represents a recurring transaction pattern.
 */
sealed interface Recurrence {
    /**
     * Unique identifier for the recurrence pattern.
     */
    val id: RecurrenceId

    /**
     * Identifier of the transaction this recurrence is associated with.
     */
    val targetId: TransactionId

    /**
     * Start time of the recurrence pattern.
     */
    val start: Instant

    /**
     * End time of the recurrence pattern, if applicable.
     */
    val end: Instant?

    /**
     * Represents a daily recurring transaction pattern.
     */
    data class Daily(
        override val id: RecurrenceId,
        override val targetId: TransactionId,
        val interval: PositiveInt,
        override val start: Instant,
        override val end: Instant? = null
    ) : Recurrence

    /**
     * Represents a weekly recurring transaction pattern.
     */
    data class Weekly(
        override val id: RecurrenceId,
        override val targetId: TransactionId,
        val interval: PositiveInt,
        override val start: Instant,
        val daysOfWeek: Set<DayOfWeek>,
        override val end: Instant? = null
    ) : Recurrence

    /**
     * Represents a monthly recurring transaction pattern.
     */
    data class Monthly(
        override val id: RecurrenceId,
        override val targetId: TransactionId,
        val interval: PositiveInt,
        override val start: Instant,
        val daysOfMonth: Set<DayOfMonth>,
        override val end: Instant? = null
    ) : Recurrence
}
