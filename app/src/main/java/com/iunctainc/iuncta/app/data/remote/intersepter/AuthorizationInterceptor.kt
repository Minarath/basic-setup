package com.iunctainc.iuncta.app.data.remote.intersepter

import com.iunctainc.iuncta.app.di.base.MyApplication
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val mainResponse = chain.proceed(chain.request())
        if (mainResponse.code == 401) {
            MyApplication.instance?.restartApp()
        }
        return mainResponse
    }
}