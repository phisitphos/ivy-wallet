package ivy.core

import androidx.compose.runtime.*
import app.cash.sqldelight.db.SqlDriver
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
import org.kodein.di.DI
import org.kodein.di.bindInstance
import org.kodein.di.bindSingleton
import org.kodein.di.instance

fun coreDi(sqlDriverProvider: () -> SqlDriver) = DI {
    bindSingleton<SqlDriver> { sqlDriverProvider() }
    bindSingleton { createHttpClient() }
    bindSingleton { createDatabase(instance()) }
    bindSingleton<AccountCacheService> { IvyAccountCacheService(instance(), instance(), instance()) }

    bindInstance<AccountPersistence> { IvyAccountPersistence() }
    bindInstance<AccountCachePersistence> { IvyAccountCachePersistence() }
    bindInstance<TransactionPersistence> { IvyTransactionPersistence() }
    bindInstance<ExchangeRatesProvider> { FawazahmedExchangeRatesProvider() }
}

val LocalDI = compositionLocalOf<ImmutableDI> { error("CompositionLocal DI not provided!") }

@Composable
inline fun <reified S, reified E, reified VM : IvyViewModel<S, E>> viewModel(
    tag: String = "",
    crossinline producer: @DisallowComposableCalls (di: ImmutableDI) -> VM
): VM {
    val di = LocalDI.current
    return remember(VM::class, tag) {
        producer(di)
    }
}

@Immutable
data class ImmutableDI(
    val di: DI
)
