package com.iunctainc.iuncta.app.di.module

import androidx.lifecycle.ViewModel
import com.iunctainc.iuncta.app.di.mapkey.ViewModelKey
import com.iunctainc.iuncta.app.ui.main.editprofile.EditProfileActivityVM
import com.iunctainc.iuncta.app.ui.main.home.MainActivityVM
import com.iunctainc.iuncta.app.ui.main.login.LoginActivityVM
import com.iunctainc.iuncta.app.ui.main.splash.SplashActivityVM
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
    abstract fun mainActivityVM(vm: MainActivityVM): ViewModel
}