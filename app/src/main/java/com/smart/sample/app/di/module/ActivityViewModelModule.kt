package com.smart.sample.app.di.module

import androidx.lifecycle.ViewModel
import com.smart.sample.app.di.mapkey.ViewModelKey
import com.smart.sample.app.ui.main.additem.AddItemActivityVM
import com.smart.sample.app.ui.main.editprofile.EditProfileActivityVM
import com.smart.sample.app.ui.main.home.MainActivityVM
import com.smart.sample.app.ui.main.login.LoginActivityVM
import com.smart.sample.app.ui.main.splash.SplashActivityVM
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ActivityViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(EditProfileActivityVM::class)
    abstract fun editProfileActivityVM(vm: EditProfileActivityVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashActivityVM::class)
    abstract fun splashActivityVM(vm: SplashActivityVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginActivityVM::class)
    abstract fun loginActivityVM(vm: LoginActivityVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityVM::class)
    abstract fun mainActivityVM(vm: MainActivityVM): ViewModel   @Binds


    @IntoMap
    @ViewModelKey(AddItemActivityVM::class)
    abstract fun addItemActivityVM(vm: AddItemActivityVM): ViewModel
}