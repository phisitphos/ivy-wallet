package ivy.core.data

import ivy.core.data.primitives.NonNegativeDouble
import ivy.core.data.primitives.Percent
import ivy.core.data.time.MonthDate

/**
 * Represents the two main classes of accounts: Assets and Liabilities.
 */
enum class AccountClass {
    Asset, Liability
}

/**
 * A sealed interface for different types of accounts. Each account type has a specific [AccountClass].
 */
sealed interface AccountType {
    val accountClass: AccountClass
}

/**
 * Cash account: Represents the physical money you have (e.g., bills and coins).
 */
object Cash : AccountType {
    override val accountClass: AccountClass = AccountClass.Asset
}

/**
 * Bank account: Represents the money you have in a bank (e.g., checking and savings accounts).
 */
object Bank : AccountType {
    override val accountClass: AccountClass = AccountClass.Asset
}

/**
 * Loan Receivable: Represents the money that someone owes you, such as a personal loan.
 * @property interestRate The interest rate for the loan (optional).
 */
data class LoanReceivable(
    val interestRate: Percent?
) : AccountType {
    override val accountClass: AccountClass = AccountClass.Asset
}

/**
 * Stocks account: Represents ownership in a company through shares.
 */
object Stocks : AccountType {
    override val accountClass: AccountClass = AccountClass.Asset
}

/**
 * Bonds account: Represents loans made to companies or governments, which pay you interest.
 */
object Bonds : AccountType {
    override val accountClass: AccountClass = AccountClass.Asset
}

/**
 * Mutual Funds account: Represents pooled investments in stocks, bonds, or other assets.
 */
object MutualFunds : AccountType {
    override val accountClass: AccountClass = AccountClass.Asset
}

/**
 * ETFs (Exchange-Traded Funds) account: Represents a type of investment fund traded on stock exchanges.
 */
object ETFs : AccountType {
    override val accountClass: AccountClass = AccountClass.Asset
}

/**
 * Cryptocurrencies account: Represents digital or virtual currencies that use cryptography for security.
 */
object Cryptocurrencies : AccountType {
    override val accountClass: AccountClass = AccountClass.Asset
}

/**
 * Commodities account: Represents investments in physical goods like gold, silver, or agricultural products.
 */
object Commodities : AccountType {
    override val accountClass: AccountClass = AccountClass.Asset
}

/**
 * Real Estate account: Represents investments in property or land.
 */
object RealEstate : AccountType {
    override val accountClass: AccountClass = AccountClass.Asset
}

/**
 * Savings account: Represents money set aside for future use, often with a bank or other financial institution.
 */
object Savings : AccountType {
    override val accountClass: AccountClass = AccountClass.Asset
}

/**
 * Other Assets account: Represents any other types of assets not covered by the other account types.
 */
object OtherAssets : AccountType {
    override val accountClass: AccountClass = AccountClass.Asset
}

/**
 * Credit Card account: Represents money you owe to a credit card company.
 * @property limit The credit limit for the card (optional).
 * @property dueDate The day of the month when the payment is due (optional).
 * @property closingDate The day of the month when the billing cycle ends (optional).
 */
data class CreditCard(
    val limit: NonNegativeDouble?,
    val dueDate: MonthDate?,
    val closingDate: MonthDate?
) : AccountType {
    override val accountClass: AccountClass = AccountClass.Liability
}

/**
 * Loan Payable: Represents money you owe to someone else, such as a bank or a friend.
 * @property interestRate The interest rate for the loan (optional).
 */
data class LoanPayable(
    val interestRate: Percent?
) : AccountType {
    override val accountClass: AccountClass = AccountClass.Liability
}

/**
 * Lines of Credit account: Represents a flexible loan from a bank or other financial institution.
 */
object LinesOfCredit : AccountType {
    override val accountClass: AccountClass = AccountClass.Liability
}

/**
 * Overdraft account: Represents a short-term loan from a bank when your account balance goes below zero.
 */
object Overdraft : AccountType {
    override val accountClass: AccountClass = AccountClass.Liability
}

/**
 * Accounts Payable account: Represents money you owe to others for goods or services received.
 */
object AccountsPayable : AccountType {
    override val accountClass: AccountClass = AccountClass.Liability
}

/**
 * Other Liabilities account: Represents any other types of liabilities not covered by the other account types.
 */
object OtherLiabilities : AccountType {
    override val accountClass: AccountClass = AccountClass.Liability
}
