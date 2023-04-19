package ivy.core

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
import org.kodein.di.DI
import org.kodein.di.bindInstance
import org.kodein.di.bindSingleton
import org.kodein.di.instance

fun coreDI(sqlDriver: SqlDriver) = DI {
    bindSingleton { createHttpClient() }
    bindSingleton { createDatabase(sqlDriver) }
    bindSingleton<AccountCacheService> { IvyAccountCacheService(instance(), instance(), instance()) }

    bindInstance<AccountPersistence> { IvyAccountPersistence() }
    bindInstance<AccountCachePersistence> { IvyAccountCachePersistence() }
    bindInstance<TransactionPersistence> { IvyTransactionPersistence() }
    bindInstance<ExchangeRatesProvider> { FawazahmedExchangeRatesProvider() }
}
