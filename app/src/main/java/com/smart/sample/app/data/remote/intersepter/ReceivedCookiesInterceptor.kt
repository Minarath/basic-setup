package com.smart.sample.app.data.remote.intersepter

import android.content.Context
import com.pixplicity.easyprefs.library.Prefs
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.*

class ReceivedCookiesInterceptor(private val context: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        if (originalResponse.headers("Set-Cookie").isNotEmpty()) {
            val cookies = HashSet<String>()
            for (header in originalResponse.headers("Set-Cookie")) {
                cookies.add(header)
            }
            Prefs.putStringSet(AddCookiesInterceptor.PREF_COOKIES, cookies)
        }
        return originalResponse
    }
}