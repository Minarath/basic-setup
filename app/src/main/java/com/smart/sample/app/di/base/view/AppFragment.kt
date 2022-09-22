package com.smart.sample.app.di.base.view

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.smart.sample.app.data.beans.connection.ConnectionBean
import com.smart.sample.app.data.local.SharedPref
import com.smart.sample.app.di.base.viewmodel.BaseViewModel
import com.smart.sample.app.util.event.SingleMessageEvent.MessageObserver
import com.smart.sample.app.util.misc.ConnectionHandler
import com.smart.sample.app.util.view.MessageUtils
import javax.inject.Inject

abstract class AppFragment<B : ViewDataBinding?, V : BaseViewModel?> : BaseFragment<B, V>() {
    @JvmField
    @Inject
    var sharedPref: SharedPref? = null

    @JvmField
    @Inject
    var connectionHandler: ConnectionHandler? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscriveBaseEvents()
    }

    private fun subscriveBaseEvents() {
        val model: BaseViewModel = viewModel as BaseViewModel
        model.normal.observe(this, object : MessageObserver {
            override fun onMessageReceived(msgResId: Int) {
                MessageUtils.normal(baseContext, getString(msgResId))
            }

            override fun onMessageReceived(msg: String) {
                MessageUtils.normal(baseContext, msg)
            }
        })
        model.success.observe(this, object : MessageObserver {
            override fun onMessageReceived(msgResId: Int) {
                MessageUtils.success(baseContext, getString(msgResId))
            }

            override fun onMessageReceived(msg: String) {
                MessageUtils.success(baseContext, msg)
            }
        })
        model.error.observe(this, object : MessageObserver {
            override fun onMessageReceived(msgResId: Int) {
                MessageUtils.error(baseContext, getString(msgResId))
            }

            override fun onMessageReceived(msg: String) {
                MessageUtils.error(baseContext, msg)
            }
        })
        model.warn.observe(this, object : MessageObserver {
            override fun onMessageReceived(msgResId: Int) {
                MessageUtils.warning(baseContext, getString(msgResId))
            }

            override fun onMessageReceived(msg: String) {
                MessageUtils.warning(baseContext, msg)
            }
        })
        model.info.observe(this, object : MessageObserver {
            override fun onMessageReceived(msgResId: Int) {
                MessageUtils.info(baseContext, getString(msgResId))
            }

            override fun onMessageReceived(msg: String) {
                MessageUtils.info(baseContext, msg)
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
}