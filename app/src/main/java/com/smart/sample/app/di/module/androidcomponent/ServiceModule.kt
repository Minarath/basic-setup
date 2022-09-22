package com.smart.sample.app.di.module.androidcomponent

import com.smart.sample.app.util.services.MyFirebaseMessagingService
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ServiceModule {

    @ContributesAndroidInjector
    abstract fun fireBaseServices(): MyFirebaseMessagingService
}