package com.iunctainc.iuncta.app.di.component;


 import com.iunctainc.iuncta.app.di.module.ApiModule;
import com.iunctainc.iuncta.app.di.module.AppModule;
import com.iunctainc.iuncta.app.di.module.LocalModule;
import com.iunctainc.iuncta.app.di.module.ManagerModule;
import com.iunctainc.iuncta.app.di.module.NetworkModule;
import com.iunctainc.iuncta.app.di.module.RepositoryModule;
import com.iunctainc.iuncta.app.di.module.SystemModule;
import com.iunctainc.iuncta.app.di.module.ViewModelModule;
import com.iunctainc.iuncta.app.di.module.androidcomponent.AndroidComponentsModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import dagger.android.support.DaggerApplication;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        LocalModule.class,
        ManagerModule.class,
        ApiModule.class,
        RepositoryModule.class,
        NetworkModule.class,
        SystemModule.class,
        AndroidComponentsModule.class,
        ViewModelModule.class


})
public interface AppComponent extends AndroidInjector<DaggerApplication> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<DaggerApplication> {
    }


}
