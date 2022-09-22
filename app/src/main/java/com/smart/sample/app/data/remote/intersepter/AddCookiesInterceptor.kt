package com.smart.sample.app.data.remote.intersepter

import android.content.Context
import com.pixplicity.easyprefs.library.Prefs
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.*

class AddCookiesInterceptor(private val context: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val preferences = Prefs.getStringSet(PREF_COOKIES, HashSet()) as HashSet<String>

        for (cookie in preferences) {
            builder.addHeader("Cookie", cookie)
        }
        builder.addHeader("Content-Type", "application/json")
        builder.addHeader("Accept", "application/json")
        return chain.proceed(builder.build())
    }

    companion object {
        const val PREF_COOKIES = "PREF_COOKIES"
    }
}