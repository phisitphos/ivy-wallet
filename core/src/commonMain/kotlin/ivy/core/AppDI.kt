package ivy.core

import app.cash.sqldelight.db.SqlDriver
import io.ktor.client.*
import ivy.core.domain.AccountCacheService
import ivy.core.domain.IvyAccountCacheService
import ivy.core.exchangerates.ExchangeRatesProvider
import ivy.core.exchangerates.provider.FawazahmedExchangeRatesProvider
import ivy.core.network.createHttpClient
import ivy.core.persistence.AccountCachePersistence
import ivy.core.persistence.AccountPersistence
import ivy.core.persistence.TransactionPersistence
import ivy.core.persistence.impl.IvyAccountCachePersistence
import ivy.core.persistence.impl.IvyAccountPersistence
import ivy.core.persistence.impl.IvyTransactionPersistence
import ivy.core.persistence.setup.createDatabase
import ivy.core.viewmodel.IvyViewModel

class Persistence(
    private val accountPersistence: IvyAccountPersistence,
    private val accountCachePersistence: IvyAccountCachePersistence,
    private val transactionPersistence: IvyTransactionPersistence
) : AccountPersistence by accountPersistence,
    AccountCachePersistence by accountCachePersistence,
    TransactionPersistence by transactionPersistence

data class IvyWalletDI(
    val httpClient: Lazy<HttpClient>,
    val persistence: Lazy<Persistence>,
    val exchangeProvider: Lazy<ExchangeRatesProvider>,
    val accountCacheService: Lazy<AccountCacheService>
)

val viewModels = mutableMapOf<String, IvyViewModel<*, *>>()

inline fun <reified UiState, reified Event, reified VM : IvyViewModel<UiState, Event>> IvyWalletDI.viewModel(
    key: String,
    crossinline produce: IvyWalletDI.() -> VM
): VM {
    return viewModels.computeIfAbsent(key) { produce() } as VM
}

fun ivyWalletDI(
    sqlDriver: SqlDriver,
    block: IvyWalletDI.() -> Unit
) {
    val database = lazy { createDatabase(sqlDriver) }
    val httpClient = lazy { createHttpClient() }
    val transactionPersistence = lazy { IvyTransactionPersistence() }
    val accountPersistence = lazy { IvyAccountPersistence() }
    val accountCachePersistence = lazy { IvyAccountCachePersistence() }
    val persistence = lazy {
        Persistence(
            accountPersistence = accountPersistence.value,
            transactionPersistence = transactionPersistence.value,
            accountCachePersistence = accountCachePersistence.value
        )
    }
    val di = IvyWalletDI(
        httpClient = httpClient,
        persistence = persistence,
        exchangeProvider = lazy { FawazahmedExchangeRatesProvider() },
        accountCacheService = lazy {
            IvyAccountCacheService(
                transactionPersistence = transactionPersistence.value,
                accountPersistence = accountPersistence.value,
                accountCachePersistence = accountCachePersistence.value
            )
        }
    )
    with(di) {
        block()
    }
}
