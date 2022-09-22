package com.smart.sample.app.di.module.androidcomponent

import com.smart.sample.app.ui.main.additem.AddItemActivity
import com.smart.sample.app.ui.main.editprofile.EditProfileActivity
import com.smart.sample.app.ui.main.home.MainActivity
import com.smart.sample.app.ui.main.login.LoginActivity
import com.smart.sample.app.ui.main.splash.SplashActivity
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

    @ContributesAndroidInjector
    abstract fun AddItemActivity(): AddItemActivity

}