package com.smart.sample.app.di.component;


 import com.smart.sample.app.di.module.ApiModule;
import com.smart.sample.app.di.module.AppModule;
import com.smart.sample.app.di.module.LocalModule;
import com.smart.sample.app.di.module.ManagerModule;
import com.smart.sample.app.di.module.NetworkModule;
import com.smart.sample.app.di.module.RepositoryModule;
import com.smart.sample.app.di.module.SystemModule;
import com.smart.sample.app.di.module.ViewModelModule;
import com.smart.sample.app.di.module.androidcomponent.AndroidComponentsModule;

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
