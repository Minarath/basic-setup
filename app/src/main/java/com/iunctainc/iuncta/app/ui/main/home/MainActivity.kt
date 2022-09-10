package com.iunctainc.iuncta.app.ui.main.home

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import com.facebook.internal.Utility
import com.google.gson.Gson
import com.iunctainc.iuncta.app.R
import com.iunctainc.iuncta.app.data.beans.Constants
import com.iunctainc.iuncta.app.data.beans.PaymentListItem
import com.iunctainc.iuncta.app.data.remote.helper.Status
import com.iunctainc.iuncta.app.databinding.ActivityEditprofileBinding
import com.iunctainc.iuncta.app.databinding.ActivityLoginBinding
import com.iunctainc.iuncta.app.databinding.ActivityMainBinding
import com.iunctainc.iuncta.app.di.base.view.AppActivity
import com.iunctainc.iuncta.app.ui.main.models.SmartSaleLoginResponse
import com.iunctainc.iuncta.app.util.event.SingleRequestEvent
import com.iunctainc.iuncta.app.util.showToast
import com.pixplicity.easyprefs.library.Prefs

class MainActivity : AppActivity<ActivityMainBinding, MainActivityVM>() {

    fun newIntent(context: Context): Intent {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        return intent
    }

    override fun getBindingActivity(): BindingActivity<MainActivityVM> {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        return BindingActivity(R.layout.activity_main, MainActivityVM::class.java)
    }

    override fun subscribeToEvents(vm: MainActivityVM) {
        vm.obrClick.observe(this, Observer { view ->
            when (view.id) {
                R.id.txtLogin -> {
                }
            }
        })
    }
}
