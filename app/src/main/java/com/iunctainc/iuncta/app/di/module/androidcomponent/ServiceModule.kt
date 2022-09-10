package com.iunctainc.iuncta.app.di.module.androidcomponent

import com.iunctainc.iuncta.app.util.services.MyFirebaseMessagingService
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ServiceModule {

    @ContributesAndroidInjector
    abstract fun fireBaseServices(): MyFirebaseMessagingService
}