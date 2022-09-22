package com.smart.sample.app.di.module.androidcomponent

import com.facebook.login.LoginFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun loginFragment(): LoginFragment
/*
    @ContributesAndroidInjector
    abstract fun notificationFragment(): NotificationFragment


    @ContributesAndroidInjector
    abstract fun profileFragment(): ProfileFragment


    @ContributesAndroidInjector
    abstract fun settingFragment(): SettingFragment


    @ContributesAndroidInjector
    abstract fun tokenFragment(): TokenFragment

    @ContributesAndroidInjector
    abstract fun walletFragment(): WalletFragment

    @ContributesAndroidInjector
    abstract fun homeFragment(): HomeFragment*/
}