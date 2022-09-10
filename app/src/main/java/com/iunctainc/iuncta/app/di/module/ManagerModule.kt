package com.iunctainc.iuncta.app.di.module

import com.iunctainc.iuncta.app.util.misc.RxBus
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object ManagerModule {
    @JvmStatic
    @get:Provides
    @get:Singleton
    val bus: RxBus
        get() = RxBus()
}