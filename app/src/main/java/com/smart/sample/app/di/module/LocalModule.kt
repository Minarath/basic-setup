package com.smart.sample.app.di.module

import android.content.Context
import android.content.SharedPreferences
import com.smart.sample.app.BuildConfig
import com.smart.sample.app.data.local.SharedPref
import com.smart.sample.app.data.local.SharedPrefImpl
import dagger.Module
import dagger.Provides
import dagger.android.support.DaggerApplication
import javax.inject.Singleton

@Module
object LocalModule {
    @Singleton
    @Provides
    fun sharedPreferences(daggerApplication: DaggerApplication): SharedPreferences {
        return daggerApplication.getSharedPreferences(
                BuildConfig.APPLICATION_ID+"_preferences",
            Context.MODE_PRIVATE
        )
    }

    @Singleton
    @Provides
    fun sharedPref(sharedPreferences: SharedPreferences): SharedPref {
        return SharedPrefImpl(sharedPreferences)
    }
}