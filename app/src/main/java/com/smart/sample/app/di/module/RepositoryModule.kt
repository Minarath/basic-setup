package com.smart.sample.app.di.module

import com.smart.sample.app.data.local.SharedPref
import com.smart.sample.app.data.remote.api.DashApi
import com.smart.sample.app.data.remote.api.WelcomeApi
import com.smart.sample.app.data.repo.dash.DashRepo
import com.smart.sample.app.data.repo.dash.DashRepoImpl
import com.smart.sample.app.data.repo.welcome.WelcomeRepo
import com.smart.sample.app.data.repo.welcome.WelcomeRepoImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object RepositoryModule {
    @Singleton
    @Provides
    fun welcomeRepo(welcomeApi: WelcomeApi, sharedPref: SharedPref): WelcomeRepo {
        return WelcomeRepoImpl(welcomeApi, sharedPref)
    }

    @Singleton
    @Provides
    fun dashRepo(dashApi: DashApi, sharedPref: SharedPref): DashRepo {
        return DashRepoImpl(dashApi, sharedPref)
    }
}