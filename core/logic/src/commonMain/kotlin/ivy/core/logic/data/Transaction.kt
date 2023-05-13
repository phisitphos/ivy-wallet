package ivy.core.logic.data

import com.benasher44.uuid.UUID
import ivy.core.logic.data.common.Archiveable
import ivy.core.logic.data.common.Reorderable
import ivy.core.logic.data.common.Syncable
import ivy.core.logic.data.common.UniqueId
import ivy.core.logic.data.primitives.NotBlankTrimmedString
import kotlinx.datetime.Instant
import kotlin.jvm.JvmInline

/**
 * Represents a unique identifier for a [Transaction] instance.
 */
@JvmInline
value class TransactionId(override val value: UUID) : UniqueId

/**
 * Represents a tag associated with a [Transaction].
 */
data class TransactionTag(
    val tag: NotBlankTrimmedString,
    override val archived: Boolean,
    override val order: Double
) : Archiveable, Reorderable

/**
 * Represents a financial transaction, which can be an [Income], [Expense], or [Transfer].
 */
sealed interface Transaction : Syncable<TransactionId> {
    /** Unique identifier for the transaction. */
    override val id: TransactionId

    /** Identifier of the associated account. */
    val accountId: ivy.core.logic.data.AccountId

    /** Monetary value of the transaction. */
    val value: MonetaryValue

    /** Time at which the transaction occurred. */
    val time: Instant

    /** Indicates if the transaction is settled. */
    val settled: Boolean

    /** Identifier of the associated category, if any. */
    val categoryId: CategoryId?

    /** Title of the transaction. */
    val title: NotBlankTrimmedString

    /** Description of the transaction, if any. */
    val description: NotBlankTrimmedString?

    /** List of tags associated with the transaction. */
    val tags: List<TransactionTag>

    /** List of attachment references associated with the transaction. */
    val attachments: List<AttachmentRef>

    /** Identifier of the associated recurrence pattern, if any. */
    val recurrenceId: RecurrenceId?

    /** Transaction fee, if any. **/
    val fee: TransactionFee?

    /** Indicates if the transaction is hidden. */
    val hidden: Boolean
}

/**
 * Represents an income transaction.
 */
data class Income(
    override val id: TransactionId,
    override val accountId: ivy.core.logic.data.AccountId,
    override val value: MonetaryValue,
    override val time: Instant,
    override val categoryId: CategoryId?,
    override val title: NotBlankTrimmedString,
    override val description: NotBlankTrimmedString?,
    override val tags: List<TransactionTag>,
    override val attachments: List<AttachmentRef>,
    override val recurrenceId: RecurrenceId?,
    override val settled: Boolean,
    override val fee: TransactionFee.OneSided?,
    override val hidden: Boolean,

    // Metadata
    override val deleted: Boolean,
    override val lastUpdated: Instant
) : Transaction

/**
 * Represents an expense transaction.
 */
data class Expense(
    override val id: TransactionId,
    override val accountId: ivy.core.logic.data.AccountId,
    override val value: MonetaryValue,
    override val time: Instant,
    override val categoryId: CategoryId?,
    override val title: NotBlankTrimmedString,
    override val description: NotBlankTrimmedString?,
    override val tags: List<TransactionTag>,
    override val attachments: List<AttachmentRef>,
    override val recurrenceId: RecurrenceId?,
    override val settled: Boolean,
    override val fee: TransactionFee.OneSided?,
    override val hidden: Boolean,

    // Metadata
    override val deleted: Boolean,
    override val lastUpdated: Instant
) : Transaction

/**
 * Represents a transfer transaction between accounts.
 */
data class Transfer(
    override val id: TransactionId,
    override val accountId: ivy.core.logic.data.AccountId,
    override val value: MonetaryValue,
    val destinationAccountId: ivy.core.logic.data.AccountId,
    val destinationValue: MonetaryValue,
    override val time: Instant,
    override val categoryId: CategoryId?,
    override val title: NotBlankTrimmedString,
    override val description: NotBlankTrimmedString?,
    override val tags: List<TransactionTag>,
    override val attachments: List<AttachmentRef>,
    override val recurrenceId: RecurrenceId?,
    override val settled: Boolean,
    override val fee: TransactionFee.Transfer?,
    override val hidden: Boolean,

    // Metadata
    override val deleted: Boolean,
    override val lastUpdated: Instant
) : Transaction

sealed interface TransactionFee {
    val value: MonetaryValue

    data class OneSided(override val value: MonetaryValue) : TransactionFee

    data class Transfer(
        override val value: MonetaryValue,
        val accountId: ivy.core.logic.data.AccountId
    ) : TransactionFee
}
