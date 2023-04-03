package ivy.core.data

import ivy.core.data.common.*
import ivy.core.data.primitives.MonthDate
import ivy.core.data.primitives.NonNegativeDouble
import ivy.core.data.primitives.NotBlankTrimmedString
import ivy.core.data.primitives.Percent
import java.awt.Color
import java.time.Instant
import java.util.*

sealed interface AccountType {
    object Cash : AccountType
    object Bank : AccountType
    data class LoanReceivable(
        val interestRate: Percent?
    ) : AccountType

    object Stocks : AccountType
    object Bonds : AccountType
    object MutualFunds : AccountType
    object ETFs : AccountType
    object Cryptocurrencies : AccountType
    object Commodities : AccountType
    object RealEstate : AccountType
    object Savings : AccountType
    object OtherAssets : AccountType
    data class CreditCard(
        val limit: NonNegativeDouble?,
        val dueDate: MonthDate?,
        val closingDate: MonthDate?
    ) : AccountType

    data class LoanPayable(
        val interestRate: Percent?
    ) : AccountType

    object LinesOfCredit : AccountType
    object Overdraft : AccountType
    object AccountsPayable : AccountType
    object OtherLiabilities : AccountType
}

@JvmInline
value class AccountId(override val value: UUID) : UniqueId

@JvmInline
value class AssetCode(val code: NotBlankTrimmedString)

data class Account(
    val name: NotBlankTrimmedString,
    val description: NotBlankTrimmedString?,
    val type: AccountType,
    val assetCode: AssetCode,
    val folderId: AccountFolderId?,
    val icon: ItemIconId?,
    val color: Color?,

    override val archived: Boolean,
    override val order: Double,

    // Metadata
    override val id: AccountId,
    override val deleted: Boolean,
    override val lastUpdated: Instant
) : Syncable<AccountId>, Archiveable, Reorderable
