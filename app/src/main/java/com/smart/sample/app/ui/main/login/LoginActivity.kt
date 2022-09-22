package com.smart.sample.app.ui.main.login

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.smart.sample.app.R
import com.smart.sample.app.data.beans.Constants
import com.smart.sample.app.data.remote.helper.Status
import com.smart.sample.app.databinding.ActivityLoginBinding
import com.smart.sample.app.di.base.view.AppActivity
import com.smart.sample.app.ui.main.home.MainActivity
import com.smart.sample.app.ui.main.models.SmartSampleAppLoginResponse
import com.smart.sample.app.util.event.SingleRequestEvent
import com.smart.sample.app.util.showToast
import com.pixplicity.easyprefs.library.Prefs

class LoginActivity : AppActivity<ActivityLoginBinding, LoginActivityVM>() {

    companion object {
        const val TAG = "EditProfileActivity"
    }

    fun newIntent(context: Context): Intent {
        val intent = Intent(context, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        return intent
    }

    override fun getBindingActivity(): BindingActivity<LoginActivityVM> {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        return BindingActivity(R.layout.activity_login, LoginActivityVM::class.java)
    }

    override fun subscribeToEvents(vm: LoginActivityVM) {
        vm.obrClick.observe(this, Observer { view ->
            when (view.id) {
                R.id.txtLogin -> {
                    checkEmailAndPassword()
                }
            }
        })

        viewModel.obrLogin.observe(this, SingleRequestEvent.RequestObserver { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    showProgressDialog(getString(R.string.plz_wait))
                }
                Status.SUCCESS -> {
                    Log.e(">>>>", "subscribeToEvents: SUCCESS")
                    dismissProgressDialog()
                    saveDataAndRedirectToMainActivity(resource.data)

                }
                Status.WARN -> {
                    dismissProgressDialog()
                    Log.e(">>>>", "subscribeToEvents: WARN")
                }
                Status.ERROR -> {
                    dismissProgressDialog()
                    Log.e(">>>>", "subscribeToEvents: ERROR")
                }
            }
        })
    }

    private fun saveDataAndRedirectToMainActivity(data: SmartSampleAppLoginResponse?) {
        Prefs.putBoolean(Constants.PrefsKeys.ISLOGIN, true)
        Prefs.putString(Constants.PrefsKeys.USER_DATA, Gson().toJson(data))
        val intent = MainActivity().newIntent(this@LoginActivity)
        startNewActivity(intent, true)

    }

    private fun checkEmailAndPassword() {
        if (binding.edUsername.text.isEmpty()) {
            showToast("Please Enter User Name")
        } else if (binding.edPassword.text.isEmpty()) {
            showToast("Please Enter Password")
        } else {
            viewModel.doLogin(binding.edUsername.text.toString(), binding.edPassword.text.toString())
        }
    }

}
