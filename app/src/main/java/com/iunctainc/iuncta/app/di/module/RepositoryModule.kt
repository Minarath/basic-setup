package com.iunctainc.iuncta.app.di.module

import com.iunctainc.iuncta.app.data.local.SharedPref
import com.iunctainc.iuncta.app.data.remote.api.DashApi
import com.iunctainc.iuncta.app.data.remote.api.WelcomeApi
import com.iunctainc.iuncta.app.data.repo.dash.DashRepo
import com.iunctainc.iuncta.app.data.repo.dash.DashRepoImpl
import com.iunctainc.iuncta.app.data.repo.welcome.WelcomeRepo
import com.iunctainc.iuncta.app.data.repo.welcome.WelcomeRepoImpl
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