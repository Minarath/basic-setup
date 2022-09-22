package com.smart.sample.app.di.base.view

import android.os.Bundle
import android.text.TextUtils
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.smart.sample.app.data.beans.Constants
import com.smart.sample.app.data.beans.connection.ConnectionBean
import com.smart.sample.app.data.local.SharedPref
import com.smart.sample.app.di.base.viewmodel.BaseViewModel
import com.smart.sample.app.ui.main.models.SmartSampleAppLoginResponse
import com.smart.sample.app.util.event.SingleMessageEvent.MessageObserver
import com.smart.sample.app.util.misc.ConnectionHandler
import com.smart.sample.app.util.view.MessageUtils
import com.pixplicity.easyprefs.library.Prefs
import javax.inject.Inject

abstract class AppActivity<B : ViewDataBinding?, V : BaseViewModel?> : BaseActivity<B, V>() {

    @JvmField
    protected val TAG = this.javaClass.simpleName

    @set:Inject
    open var sharedPref: SharedPref? = null

    @set:Inject
    open var connectionHandler: ConnectionHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeBaseEvents()
    }

    private fun subscribeBaseEvents() {
        viewModel!!.normal.observe(this, object : MessageObserver {
            override fun onMessageReceived(msgResId: Int) {
                MessageUtils.normal(this@AppActivity, getString(msgResId))
            }

            override fun onMessageReceived(msg: String) {
                if (!TextUtils.isEmpty(msg)) MessageUtils.normal(this@AppActivity, msg)
            }
        })
        viewModel!!.success.observe(this, object : MessageObserver {
            override fun onMessageReceived(msgResId: Int) {
                MessageUtils.success(this@AppActivity, getString(msgResId))
            }

            override fun onMessageReceived(msg: String) {
                if (!TextUtils.isEmpty(msg)) MessageUtils.success(this@AppActivity, msg)
            }
        })
        viewModel!!.error.observe(this, object : MessageObserver {
            override fun onMessageReceived(msgResId: Int) {
                MessageUtils.error(this@AppActivity, getString(msgResId))
            }

            override fun onMessageReceived(msg: String) {
                if (!TextUtils.isEmpty(msg)) MessageUtils.error(this@AppActivity, msg)
            }
        })
        viewModel!!.warn.observe(this, object : MessageObserver {
            override fun onMessageReceived(msgResId: Int) {
                MessageUtils.warning(this@AppActivity, getString(msgResId))
            }

            override fun onMessageReceived(msg: String) {
                if (!TextUtils.isEmpty(msg)) MessageUtils.warning(this@AppActivity, msg)
            }
        })
        viewModel!!.info.observe(this, object : MessageObserver {
            override fun onMessageReceived(msgResId: Int) {
                MessageUtils.info(this@AppActivity, getString(msgResId))
            }

            override fun onMessageReceived(msg: String) {
                if (!TextUtils.isEmpty(msg)) MessageUtils.info(this@AppActivity, msg)
            }
        })
        connectionHandler!!.observe(this, Observer observe@{ connectionBean: ConnectionBean? ->
            if (connectionBean == null) return@observe
            if (connectionBean.type == ConnectionBean.State.NONE) {
                onInternetRefresh(false)
            } else {
                onInternetRefresh(true)
            }
        })
    }

    protected open fun onInternetRefresh(isConnected: Boolean) {}
    override fun onBackPressed() {
        super.onBackPressed(true)
    }

    public fun getData(): SmartSampleAppLoginResponse {
       return Gson().fromJson(Prefs.getString(Constants.PrefsKeys.USER_DATA, ""), SmartSampleAppLoginResponse::class.java)
    }
}