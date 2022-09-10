package com.iunctainc.iuncta.app.di.module

import androidx.lifecycle.ViewModel
import com.iunctainc.iuncta.app.di.mapkey.ViewModelKey
import com.iunctainc.iuncta.app.ui.main.editprofile.EditProfileActivityVM
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ActivityViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(EditProfileActivityVM::class)
    abstract fun editProfileActivityVM(vm: EditProfileActivityVM): ViewModel
}