package com.smart.sample.app.di.base.viewmodel

import android.app.Dialog
import android.view.View
import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import com.smart.sample.app.util.event.SingleActionEvent
import com.smart.sample.app.util.event.SingleMessageEvent
import io.reactivex.disposables.CompositeDisposable

/**
 * This will serve as the base class for all Activity ViewModels
 *
 *
 */
abstract class BaseViewModel : ViewModel() {
    protected val TAG = this.javaClass.simpleName
    @JvmField
    protected val compositeDisposable = CompositeDisposable()
    @JvmField
    var trashDialog: Dialog? = null
    @JvmField
    val obrClick = SingleActionEvent<View>()
    val normal = SingleMessageEvent()
    @JvmField
    val success = SingleMessageEvent()
    @JvmField
    val error = SingleMessageEvent()
    @JvmField
    val info = SingleMessageEvent()
    @JvmField
    val warn = SingleMessageEvent()
    fun onClick(view: View) {
        obrClick.call(view)
    }

    @CallSuper
    override fun onCleared() {
        if (trashDialog != null && trashDialog!!.isShowing) {
            trashDialog!!.dismiss()
        }
        compositeDisposable.clear()
        super.onCleared()
    }
}