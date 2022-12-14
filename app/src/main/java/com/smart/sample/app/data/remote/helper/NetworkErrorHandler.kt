package com.smart.sample.app.data.remote.helper

import android.content.Context
import com.smart.sample.app.R
import com.smart.sample.app.di.base.MyApplication.Companion.instance
import dagger.android.DaggerApplication
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection

class NetworkErrorHandler(context: DaggerApplication) {

    private val context: Context

    fun getErrMsg(throwable: Throwable): String {
        var errMsg = context.getString(R.string.error_found)
        throwable.printStackTrace()
        if (throwable is HttpException) {
            errMsg = getErrorMessage(throwable)
            if (throwable.code() == HttpURLConnection.HTTP_OK) instance?.restartApp()
        } else if (throwable is IOException) {
            errMsg = context.getString(R.string.network_error)
        } else {
            if (throwable.message != null) errMsg = throwable.message!!
        }
        return errMsg
    }

    private fun getErrorMessage(throwable: Throwable): String {
        return try {
            val httpException = throwable as HttpException
            val errorBody = httpException.response()?.errorBody()
            var errMsg = context.getString(R.string.error_found)
            if (errorBody != null) {
                val jsonObject = JSONObject(errorBody.string())
                errMsg = jsonObject.getString("error")
            }
            errMsg
        } catch (e: Exception) {
            (throwable as HttpException).message()
        }
    }

    init {
        this.context = context
    }
}