package com.iunctainc.iuncta.app.di.module

import com.iunctainc.iuncta.app.data.remote.api.DashApi
import com.iunctainc.iuncta.app.data.remote.api.WelcomeApi
import com.iunctainc.iuncta.app.di.qualifier.ApiEndpoint
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