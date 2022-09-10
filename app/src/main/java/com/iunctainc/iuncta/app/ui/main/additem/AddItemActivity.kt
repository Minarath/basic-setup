package com.iunctainc.iuncta.app.ui.main.additem

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.facebook.internal.Utility
import com.google.gson.Gson
import com.iunctainc.iuncta.app.R
import com.iunctainc.iuncta.app.data.beans.Constants
import com.iunctainc.iuncta.app.data.beans.PaymentListItem
import com.iunctainc.iuncta.app.data.remote.helper.Status
import com.iunctainc.iuncta.app.databinding.ActivityAdditemBinding
import com.iunctainc.iuncta.app.databinding.ActivityEditprofileBinding
import com.iunctainc.iuncta.app.databinding.ActivityLoginBinding
import com.iunctainc.iuncta.app.databinding.ActivityMainBinding
import com.iunctainc.iuncta.app.di.base.view.AppActivity
import com.iunctainc.iuncta.app.ui.main.barcode.SimpleScannerActivity
import com.iunctainc.iuncta.app.ui.main.models.SmartSaleLoginResponse
import com.iunctainc.iuncta.app.util.event.SingleRequestEvent
import com.iunctainc.iuncta.app.util.showToast
import com.pixplicity.easyprefs.library.Prefs
import android.app.Activity
import androidx.activity.result.ActivityResult

import androidx.activity.result.ActivityResultCallback

import androidx.activity.result.contract.ActivityResultContracts

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult


class AddItemActivity : AppActivity<ActivityAdditemBinding, AddItemActivityVM>() {

    fun newIntent(context: Context): Intent {
        val intent = Intent(context, AddItemActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        return intent
    }

    override fun getBindingActivity(): BindingActivity<AddItemActivityVM> {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        return BindingActivity(R.layout.activity_additem, AddItemActivityVM::class.java)
    }

    override fun subscribeToEvents(vm: AddItemActivityVM) {
        vm.obrClick.observe(this, Observer { view ->
            when (view.id) {
                R.id.imgBack -> {
                    onBackPressed()
                }
                R.id.txtPrice -> {
                    managePriceBox()
                }
                R.id.txtStock -> {
                    manageStockBox()
                }
                R.id.imgBarcode -> {
                    manageCamera()
                }
            }
        })
        viewModel.obrCategory1.observe(this, SingleRequestEvent.RequestObserver { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    showProgressDialog(getString(R.string.plz_wait))
                }
                Status.SUCCESS -> {
                    Log.e(">>>>", "subscribeToEvents: SUCCESS"+resource.data)
                    dismissProgressDialog()

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
        vm.getCategory1(""+getData().data?.companies?.get(0)?.companyId)
    }

    private fun manageStockBox() {
        binding.txtStock.background = ActivityCompat.getDrawable(this, R.drawable.img_left_side)
        binding.txtPrice.setBackgroundResource(0)
        binding.txtStock.setTextColor(ActivityCompat.getColor(this, R.color.white))
        binding.txtPrice.setTextColor(ActivityCompat.getColor(this, R.color.text_color))
        binding.includeStock.lenMain.visibility = View.VISIBLE
        binding.includePricing.lenMain.visibility = View.GONE
    }

    private fun managePriceBox() {
        binding.txtPrice.background = ActivityCompat.getDrawable(this, R.drawable.img_right_side)
        binding.txtStock.setBackgroundResource(0)
        binding.txtPrice.setTextColor(ActivityCompat.getColor(this, R.color.white))
        binding.txtStock.setTextColor(ActivityCompat.getColor(this, R.color.text_color))
        binding.includeStock.lenMain.visibility = View.GONE
        binding.includePricing.lenMain.visibility = View.VISIBLE
    }

    private fun manageCamera() {
        val permissionArrays = arrayOf(Manifest.permission.CAMERA)
        if (checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(permissionArrays, 100);
            return;
        } else {
            val intent = Intent(this, SimpleScannerActivity::class.java)
            startForResult.launch(intent)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val isPermitted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
        if (isPermitted) {
            val intent = Intent(this, SimpleScannerActivity::class.java)
            startForResult.launch(intent)
        }
    }

    // You can do the assignment inside onAttach or onCreate, i.e, before the activity is displayed
    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val text = result.data
            binding.edBarcode.setText(text?.extras?.get("data").toString())
        }
    }

}
