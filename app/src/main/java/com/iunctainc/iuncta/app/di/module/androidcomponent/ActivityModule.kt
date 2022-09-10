package com.iunctainc.iuncta.app.di.module.androidcomponent

import com.iunctainc.iuncta.app.ui.main.editprofile.EditProfileActivity
import com.iunctainc.iuncta.app.ui.main.home.MainActivity
import com.iunctainc.iuncta.app.ui.main.login.LoginActivity
import com.iunctainc.iuncta.app.ui.main.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun EditProfileActivity(): EditProfileActivity


    @ContributesAndroidInjector
    abstract fun LoginActivity(): LoginActivity

    @ContributesAndroidInjector
    abstract fun MainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun SplashActivity(): SplashActivity

}