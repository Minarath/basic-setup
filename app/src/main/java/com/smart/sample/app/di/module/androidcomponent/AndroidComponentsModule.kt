package com.smart.sample.app.di.module.androidcomponent

import dagger.Module

@Module(
    includes = [
        ActivityModule::class,
        FragmentModule::class,
        ServiceModule::class,
        BroadcastReceiverModule::class,
        ContentProviderModule::class
    ]
)

abstract class AndroidComponentsModule 