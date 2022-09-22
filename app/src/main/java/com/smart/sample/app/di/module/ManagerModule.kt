package com.smart.sample.app.di.module

import com.smart.sample.app.util.misc.RxBus
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