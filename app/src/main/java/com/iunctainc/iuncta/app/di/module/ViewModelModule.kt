package com.iunctainc.iuncta.app.di.module

import androidx.lifecycle.ViewModelProvider
import com.iunctainc.iuncta.app.di.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module(
    includes = [
        ActivityViewModelModule::class,
        FragmentViewModelModule::class
    ]
)

abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}