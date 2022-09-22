package com.smart.sample.app.ui.main.splash

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import com.smart.sample.app.R
import com.smart.sample.app.databinding.ActivitySplashBinding
import com.smart.sample.app.di.base.MyApplication
import com.smart.sample.app.di.base.view.AppActivity
import java.util.*
import android.app.NotificationManager
import com.smart.sample.app.data.beans.Constants
import com.smart.sample.app.ui.main.editprofile.EditProfileActivity
import com.smart.sample.app.ui.main.home.MainActivity
import com.smart.sample.app.ui.main.login.LoginActivity
import com.pixplicity.easyprefs.library.Prefs


class SplashActivity : AppActivity<ActivitySplashBinding, SplashActivityVM>() {

    fun newIntent(context: Context): Intent {
        val intent = Intent(context, SplashActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        return intent
    }

    private fun cancelNotification() {
        val ns = NOTIFICATION_SERVICE
        val nMgr = applicationContext.getSystemService(ns) as NotificationManager
        nMgr.cancelAll()
    }

    override fun getBindingActivity(): BindingActivity<SplashActivityVM> {
        return BindingActivity(R.layout.activity_splash, SplashActivityVM::class.java)
    }

    override fun subscribeToEvents(vm: SplashActivityVM) {
        cancelNotification()
        vm.obrClick.observe(this, Observer { v: View ->
            when (v.id) {

            }
        })

        vm.obrNext.observe(this, Observer {
            intent = Intent()
            intent = if (Prefs.getBoolean(Constants.PrefsKeys.ISLOGIN, false)) {
                MainActivity().newIntent(this@SplashActivity)
            } else {
                LoginActivity().newIntent(this@SplashActivity)
            }
            startNewActivity(intent, true)
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel!!.nextScreen()
    }

    companion object {
        fun newIntent(activity: MyApplication?): Intent {
            val intent = Intent(activity, EditProfileActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            return intent
        }
    }


}