package ivy.core.logic.di

import javax.inject.Scope
import kotlin.reflect.KClass

@Scope
annotation class SingleIn(val scope: KClass<*>)

abstract class AppScope private constructor()
