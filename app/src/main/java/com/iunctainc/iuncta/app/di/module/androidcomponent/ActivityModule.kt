package com.iunctainc.iuncta.app.di.module.androidcomponent

import com.iunctainc.iuncta.app.ui.main.editprofile.EditProfileActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun EditProfileActivity(): EditProfileActivity

}