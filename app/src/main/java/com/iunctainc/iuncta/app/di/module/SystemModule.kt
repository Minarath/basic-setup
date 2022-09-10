package com.iunctainc.iuncta.app.di.module

import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.location.LocationManager
import android.net.ConnectivityManager
import com.iunctainc.iuncta.app.util.location.LiveLocationDetecter
import dagger.Module
import dagger.Provides
import dagger.android.support.DaggerApplication
import javax.inject.Singleton

@Module
object SystemModule {
    @Singleton
    @Provides
    fun connectivityManager(daggerApplication: DaggerApplication): ConnectivityManager {
        return daggerApplication.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Singleton
    @Provides
    fun locationManager(daggerApplication: DaggerApplication): LocationManager {
        return daggerApplication.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    @Singleton
    @Provides
    fun notificationManager(daggerApplication: DaggerApplication): NotificationManager {
        return daggerApplication.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    @Singleton
    @Provides
    fun packageManager(daggerApplication: DaggerApplication): PackageManager {
        return daggerApplication.packageManager
    }

    @Singleton
    @Provides
    fun assetManager(daggerApplication: DaggerApplication): AssetManager {
        return daggerApplication.assets
    }

    @Singleton
    @Provides
    fun getLocation(daggerApplication: DaggerApplication?): LiveLocationDetecter {
        return LiveLocationDetecter(daggerApplication)
    }
}