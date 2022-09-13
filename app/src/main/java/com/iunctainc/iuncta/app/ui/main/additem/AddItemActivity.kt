package com.iunctainc.iuncta.app.ui.main.additem

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.iunctainc.iuncta.app.R
import com.iunctainc.iuncta.app.data.remote.helper.Status
import com.iunctainc.iuncta.app.databinding.ActivityAdditemBinding
import com.iunctainc.iuncta.app.di.base.view.AppActivity
import com.iunctainc.iuncta.app.ui.main.barcode.SimpleScannerActivity
import com.iunctainc.iuncta.app.util.event.SingleRequestEvent
import com.iunctainc.iuncta.app.util.showToast
import android.app.Activity
import androidx.activity.result.ActivityResult

import androidx.activity.result.contract.ActivityResultContracts

import com.iunctainc.iuncta.app.ui.main.models.CategoryItem
import com.iunctainc.iuncta.app.ui.main.models.DataItem
import java.util.ArrayList


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
                R.id.txtSubmit -> {
                    checkDataAndCallApi()
                }
            }
        })
        viewModel.obrCategory1.observe(this, SingleRequestEvent.RequestObserver { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    showProgressDialog(getString(R.string.plz_wait))
                }
                Status.SUCCESS -> {
                    Log.e(">>>>", "subscribeToEvents: SUCCESS" + resource.data)
                    dismissProgressDialog()
                    setAdapterToSpinner(resource.data.data)

                }
                Status.WARN -> {
                    checkIntentAndSetData(false,null)
                    dismissProgressDialog()
                    Log.e(">>>>", "subscribeToEvents: WARN")
                }
                Status.ERROR -> {
                    checkIntentAndSetData(false,null)
                    dismissProgressDialog()
                    Log.e(">>>>", "subscribeToEvents: ERROR")
                }
            }
        })
        viewModel.obrAddItem.observe(this, SingleRequestEvent.RequestObserver { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    showProgressDialog(getString(R.string.plz_wait))
                }
                Status.SUCCESS -> {
                    Log.e(">>>>", "subscribeToEvents: SUCCESS" + resource.data)
                    dismissProgressDialog()
                    onBackPressed()
                }
                Status.WARN -> {
                    dismissProgressDialog()
                    Log.e(">>>>", "subscribeToEvents: WARN")
                }
                Status.ERROR -> {
                    dismissProgressDialog()
                    Log.e(">>>>", "subscribeToEvents: ERROR")
                    showToast("" + resource.message)
                }
            }
        })
        vm.getCategory1("" + getData().data?.companies?.get(0)?.companyId)

    }

    var adapter: AlgorithmAdapter? = null
    var category1Items: List<CategoryItem?>? = null

    private fun setAdapterToSpinner(data: List<CategoryItem?>?) {
        category1Items = data
        adapter = AlgorithmAdapter(this, data as ArrayList<CategoryItem>?)
        binding.spItemList.adapter = adapter
        if (data?.isNotEmpty() == true) {
            checkIntentAndSetData(true, data)
        }
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

    private fun checkDataAndCallApi() {
        if (binding.edItemName.text.toString().isEmpty()) {
            showToast("Please Enter Item Name")
        } else if (binding.edBarcode.text.toString().isEmpty()) {
            showToast("Please Enter Item Barcode")
        }/* else if (binding.includeStock.edOpeningStock.text.toString().isEmpty()) {
            showToast("Please Enter Opening Stock")
        }*/ /*else if (binding.includeStock.edMinimumStock.text.toString().isEmpty()) {
            showToast("Please Enter Minimum Stock")
        } else if (binding.includeStock.edLocation.text.toString().isEmpty()) {
            showToast("Please Enter Location")
        }else if (binding.includePricing.edPrice.text.toString().isEmpty()) {
            showToast("Please Enter Price")
        } else if (binding.includePricing.edCostPrice.text.toString().isEmpty()) {
            showToast("Please Enter Cost Price")
        } else if (binding.includePricing.edtax.text.toString().isEmpty()) {
            showToast("Please Enter Tax/Vat")
        }  */ else {
            viewModel.obrAddItem(
                getData().data?.companies?.get(0)?.companyId!!,
                binding.includePricing.edPrice.text.toString().toInt(),
                binding.includePricing.edCostPrice.text.toString().toInt(),
                binding.includeStock.edOpeningStock.text.toString().toInt(),
                binding.includePricing.edtax.text.toString().toInt(),
                binding.includePricing.edDiscount.text.toString().toInt(),
                category1Items?.get(binding.spItemList.selectedItemPosition)?.category1Id!!,
                null, null, binding.edItemName.text.toString(),
                binding.edBarcode.text.toString(), binding.includeStock.edLocation.text.toString(),
                binding.includeStock.edMinimumStock.text.toString().toInt()
            )
        }
    }

    private fun checkIntentAndSetData(isCategory: Boolean, categoryListItem: List<CategoryItem?>?) {
        if (intent.hasExtra("data")) {
            val data = intent.extras?.get("data") as DataItem
            binding.edItemName.setText("" + data.name)
            binding.edBarcode.setText("" + data.barcode)

            binding.includePricing.edCostPrice.setText("" + data.costPrice)
            binding.includePricing.edPrice.setText("" + data.salesPrice)
            binding.includePricing.edtax.setText("" + data.vat)
            binding.includePricing.edDiscount.setText("" + data.discount)

            binding.includeStock.edClosingStock.setText("" + data.closing_stock)
            binding.includeStock.edOpeningStock.setText("" + data.opgStock)
            binding.includeStock.edMinimumStock.setText("" + data.min_stock)
            binding.includeStock.edLocation.setText("" + data.location)

            binding.edBarcode.isEnabled = false
            binding.imgBarcode.isEnabled = false

            if (isCategory) {
                for (i in 0 until categoryListItem?.size!!) {
                    if (categoryListItem[i]?.category1Id == data.category1?.category1Id) {
                        binding.spItemList.setSelection((i))
                    }
                }
            }

        }
    }
}
