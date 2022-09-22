package com.smart.sample.app.data.remote.helper

abstract class ApiCallback<T> {
    abstract fun onSuccess(response: T)
    abstract fun onFailed(message: String)
    abstract fun onErrorThrow(exception: Exception)
    abstract fun onLoading()
}