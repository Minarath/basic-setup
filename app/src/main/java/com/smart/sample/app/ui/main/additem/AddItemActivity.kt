package com.smart.sample.app.ui.main.additem

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.smart.sample.app.R
import com.smart.sample.app.data.remote.helper.Status
import com.smart.sample.app.databinding.ActivityAdditemBinding
import com.smart.sample.app.di.base.view.AppActivity
import com.smart.sample.app.ui.main.barcode.SimpleScannerActivity
import com.smart.sample.app.util.event.SingleRequestEvent
import com.smart.sample.app.util.showToast
import android.app.Activity
import android.app.Dialog
import android.view.LayoutInflater
import android.widget.AdapterView
import android.widget.FrameLayout
import androidx.activity.result.ActivityResult

import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.smart.sample.app.databinding.DialogAddNewCatBinding

import com.smart.sample.app.ui.main.models.CategoryItem
import com.smart.sample.app.ui.main.models.DataItem

import java.util.ArrayList
import android.util.DisplayMetrics

class AddItemActivity : AppActivity<ActivityAdditemBinding, AddItemActivityVM>() {
    var catList: ArrayList<CategoryItem?>? = ArrayList()
    lateinit var addCateDialog: Dialog

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
                    catList = resource.data.data as ArrayList<CategoryItem?>?
                    setAdapterToSpinner()

                }
                Status.WARN -> {
                    checkIntentAndSetData(false, null)
                    dismissProgressDialog()
                    Log.e(">>>>", "subscribeToEvents: WARN")
                }
                Status.ERROR -> {
                    checkIntentAndSetData(false, null)
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
                    val data=Intent().putExtra("data",resource.data)
                    setResult(RESULT_OK,data)
                    finish()
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

        viewModel.obrAddCategory.observe(this, SingleRequestEvent.RequestObserver { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    showProgressDialog(getString(R.string.plz_wait))
                }
                Status.SUCCESS -> {
                    Log.e(">>>>", "subscribeToEvents: SUCCESS" + resource.data)
                    dismissProgressDialog()
                    if (catList?.size != 0) {
                        catList?.add((catList!!.size - 1), resource.data.data)
                        if (catList!!.size == 1) {
                            binding.spItemList.setSelection(0)
                        } else {
                            binding.spItemList.setSelection(catList!!.size - 2)
                        }
                        spinnerAdapter?.notifyDataSetChanged()
                        addCateDialog.dismiss()
                    } else {
                        catList?.add(resource.data.data)
                        setAdapterToSpinner()
                        addCateDialog.dismiss()
                    }
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

    var spinnerAdapter: AlgorithmAdapter? = null

    private fun setAdapterToSpinner() {
        catList?.add(CategoryItem(9090, getString(R.string.add_new_item), null))
        spinnerAdapter = AlgorithmAdapter(this, catList as ArrayList<CategoryItem>?) {
        }
        binding.spItemList.adapter = spinnerAdapter
        if (catList?.isNotEmpty() == true) {
            checkIntentAndSetData(true, catList)
        }
        binding.spItemList.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (catList?.get(position)?.name.equals(resources.getString(R.string.add_new_item))) {
                    binding.spItemList.setSelection(0)
                    showAddNewCatDialog()
                }
            }
        }
    }

    private fun showAddNewCatDialog() {
        addCateDialog = Dialog(this)
        var mBinding = DataBindingUtil.inflate<DialogAddNewCatBinding>(LayoutInflater.from(this), R.layout.dialog_add_new_cat, null, false)
        addCateDialog.setContentView(mBinding.root)
        addCateDialog.window?.setBackgroundDrawableResource(R.color.transparent)
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels
        addCateDialog.window?.setLayout((width - 40), FrameLayout.LayoutParams.WRAP_CONTENT);
        addCateDialog.show()
        mBinding.txtAdd.setOnClickListener {
            if (mBinding.edCategory.text.toString().isNotEmpty()) {
                viewModel.addCat1(getData().data?.companies?.get(0)?.companyId!!.toString(), mBinding.edCategory.text.toString())
            }
        }
        mBinding.txtCat.setOnClickListener {
            addCateDialog.dismiss()
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
            if (intent.hasExtra("data")) {
                val data = intent.extras?.get("data") as DataItem
                viewModel.obrAddItem(
                    ""+data.itemId,
                    ""+getData().data?.companies?.get(0)?.companyId!!,
                    ""+checkIsEmpty(binding.includePricing.edPrice.text.toString()),
                    ""+checkIsEmpty(binding.includePricing.edCostPrice.text.toString()),
                    ""+checkIsEmpty(binding.includeStock.edOpeningStock.text.toString()),
                    ""+checkIsEmpty(binding.includePricing.edtax.text.toString()),
                    ""+checkIsEmpty(binding.includePricing.edDiscount.text.toString()),
                    ""+catList?.get(binding.spItemList.selectedItemPosition)?.category1Id!!,
                    null, null, binding.edItemName.text.toString(),
                    binding.edBarcode.text.toString(), binding.includeStock.edLocation.text.toString(),
                    ""+checkIsEmpty(binding.includeStock.edMinimumStock.text.toString()), true
                )
            } else {
                viewModel.obrAddItem(
                    null,
                    ""+getData().data?.companies?.get(0)?.companyId!!,
                    ""+checkIsEmpty(binding.includePricing.edPrice.text.toString()),
                    ""+checkIsEmpty(binding.includePricing.edCostPrice.text.toString()),
                    ""+checkIsEmpty(binding.includeStock.edOpeningStock.text.toString()),
                    ""+checkIsEmpty(binding.includePricing.edtax.text.toString()),
                    ""+checkIsEmpty(binding.includePricing.edDiscount.text.toString()),
                    ""+catList?.get(binding.spItemList.selectedItemPosition)?.category1Id!!,
                    null, null, binding.edItemName.text.toString(),
                    binding.edBarcode.text.toString(), binding.includeStock.edLocation.text.toString(),
                    ""+checkIsEmpty(binding.includeStock.edMinimumStock.text.toString()), false
                )
            }
        }

    }

    private fun checkIntentAndSetData(isCategory: Boolean, categoryListItem: List<CategoryItem?>?) {
        if (intent.hasExtra("data")) {
            binding.txtSubmit.text = resources.getString(R.string.update)
            val data = intent.extras?.get("data") as DataItem
            binding.edItemName.setText("" + data.name)
            binding.edBarcode.setText("" + data.barcode)
            binding.includePricing.edCostPrice.setText(checkIsEmpty(data.costPrice))
            binding.includePricing.edPrice.setText(checkIsEmpty(data.salesPrice))
            binding.includePricing.edtax.setText(checkIsEmpty(data.vat))
            binding.includePricing.edDiscount.setText(checkIsEmpty(data.discount))
            binding.includeStock.edOpeningStock.setText(checkIsEmpty(data.opgStock))
            binding.includeStock.edMinimumStock.setText(checkIsEmpty(data.min_stock))
            if(data.location==null){
                binding.includeStock.edLocation.setText("")
            }else{
                binding.includeStock.edLocation.setText(data.location)
            }

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

    private fun checkIsEmpty(values: String): String {
        if (values.isEmpty()) {
            return "0"
        } else {
            return values
        }
    }

    private fun checkIsEmpty(values: Double?): String {
        if (values == 0.0) {
            return ""
        } else {
            return "" + values
        }
    }

    private fun checkIsEmpty(values: Int?): String {
        if (values == 0) {
            return ""
        } else {
            return "" + values
        }
    }

}
