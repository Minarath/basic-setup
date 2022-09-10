package com.iunctainc.iuncta.app.data.remote.intersepter

import com.iunctainc.iuncta.app.di.base.MyApplication
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val mainResponse = chain.proceed(chain.request())
        if (mainResponse.code == 401) {
            if (!chain.request().url.toString().contains("login")) {
                if (MyApplication.instance?.isAuthError() == true) {
                    MyApplication.instance?.restartApp()
                } else {
//                    MyApplication.instance?.refreshToken()
                }
            }
        }
        return mainResponse
    }
}