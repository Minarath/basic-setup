package com.smart.sample.app.di.module

import com.smart.sample.app.data.remote.api.DashApi
import com.smart.sample.app.data.remote.api.WelcomeApi
import com.smart.sample.app.di.qualifier.ApiEndpoint
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
object ApiModule {
    @Singleton
    @Provides
    fun welcomeApi(@ApiEndpoint retrofit: Retrofit): WelcomeApi {
        return retrofit.create(WelcomeApi::class.java)
    }

    @Singleton
    @Provides
    fun dashApi(@ApiEndpoint retrofit: Retrofit): DashApi {
        return retrofit.create(DashApi::class.java)
    }
}