package com.iunctainc.iuncta.app.di.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pixplicity.easyprefs.library.Prefs
import com.iunctainc.iuncta.app.R
import com.iunctainc.iuncta.app.data.beans.*
import com.iunctainc.iuncta.app.data.repo.welcome.WelcomeRepo
import com.iunctainc.iuncta.app.di.component.DaggerAppComponent
import com.iunctainc.iuncta.app.ui.main.splash.SplashActivity
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import io.reactivex.exceptions.OnErrorNotImplementedException
import io.reactivex.exceptions.ProtocolViolationException
import io.reactivex.plugins.RxJavaPlugins
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyApplication : DaggerApplication() {

    private var isAuthErrorForRefreshToken: Boolean = false
    private lateinit var welcomeRepo: WelcomeRepo
    private var context: Context? = null



    @RequiresApi(api = Build.VERSION_CODES.N)
    override fun onCreate() {
        super.onCreate()
        instance = this

        Prefs.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()

        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(
                    CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                            .setDefaultFontPath("fonts/mark_pro.ttf")
                            .setFontAttrId(R.attr.fontPath)
                            .build()
                    )
                )
                .build()
        )
    }

    fun restartApp() {
        CoroutineScope(Dispatchers.Main).launch {
            Prefs.clear()
            val intent: Intent = SplashActivity().newIntent(context!!)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context?.startActivity(intent)
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }

    private fun attachErrorHandler() {
        RxJavaPlugins.setErrorHandler { throwable ->
            if (throwable is OnErrorNotImplementedException || throwable is ProtocolViolationException) {
                Log.e(TAG, "attachErrorHandler: ")
            }
        }
    }


    fun setCurrentActivity(context: Context) {
        this.context = context
    }

    fun getCurrentActivity(): Context? {
        return context
    }

    fun getUserAuthentication(): Auth? {
        return Gson().fromJson(Prefs.getString(Constants.PrefsKeys.AUTHENTICATION, ""), Auth::class.java)
    }

    fun getToken(): String {
        return "Bearer ${getUserAuthentication()?.accessToken}"
    }

    fun setAuthRepo(welcomeRepo: WelcomeRepo) {
        this.welcomeRepo = welcomeRepo
    }

    fun refreshToken() {
        /*welcomeRepo.refreshTokenNew(object : ApiCallback<Response<ApiResponse<Authentication>>>() {

            override fun onSuccess(response: Response<ApiResponse<Authentication>>) {
                Log.e("success", "onSuccess: ")
                setAuthError(false)
                Prefs.putString(Constants.PrefsKeys.AUTHENTICATION, Gson().toJson(response.body()?.data, Authentication::class.java))
            }

            override fun onFailed(message: String) {
                Log.e("failed", "onFailed: ")
                showToast(message)
                setAuthError(true)
            }

            override fun onErrorThrow(exception: Exception) {
                Log.e("error", "onErrorThrow: ")
                showToast(exception.message.toString())
                setAuthError(true)
            }

            override fun onLoading() {
                Log.e("load", "onLoading: ")
            }
        })*/
    }

    fun isAuthError(): Boolean {
        return this.isAuthErrorForRefreshToken
    }

    fun setAuthError(isAuthErrorForRefresh: Boolean) {
        this.isAuthErrorForRefreshToken = isAuthErrorForRefresh
    }


    fun getUserData(): Data {
        return Gson().fromJson(Prefs.getString(Constants.PrefsKeys.USER_DATA, ""), object : TypeToken<Data>() {}.type)
    }


    companion object {
        private val TAG = MyApplication::class.java.simpleName

        @SuppressLint("StaticFieldLeak")
        @JvmStatic
        var instance: MyApplication? = null
            private set
    }
}