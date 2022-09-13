package com.iunctainc.iuncta.app.ui.main.home

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import com.iunctainc.iuncta.app.BR
import com.iunctainc.iuncta.app.R
import com.iunctainc.iuncta.app.data.remote.helper.Status
import com.iunctainc.iuncta.app.databinding.ActivityMainBinding
import com.iunctainc.iuncta.app.databinding.CellItemsBinding
import com.iunctainc.iuncta.app.di.base.adapter.SimpleRecyclerViewAdapter
import com.iunctainc.iuncta.app.di.base.view.AppActivity
import com.iunctainc.iuncta.app.ui.main.additem.AddItemActivity
import com.iunctainc.iuncta.app.ui.main.models.DataItem
import com.iunctainc.iuncta.app.ui.main.models.ItemsListResponse
import com.iunctainc.iuncta.app.util.event.SingleRequestEvent
import com.iunctainc.iuncta.app.util.showToast

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
                R.id.imgAddNewItem -> {
                    val intent = AddItemActivity().newIntent(this@MainActivity)
                    startNewActivity(intent, false)
                }
            }
        })
        Log.e(">>>>", "subscribeToEvents: " + getData().data)
        viewModel.obrGetItemList.observe(this, SingleRequestEvent.RequestObserver { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    showProgressDialog(getString(R.string.plz_wait))
                }
                Status.SUCCESS -> {
                    Log.e(">>>>", "subscribeToEvents: SUCCESS" + resource.data)
                    dismissProgressDialog()
                    setAdapterToItemList(resource.data!!)
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
        binding.txtTitle.text = getData().data?.companies?.get(0)?.name
    }

    var adapterItemList: SimpleRecyclerViewAdapter<DataItem, CellItemsBinding>? = null

    private fun setAdapterToItemList(data: ItemsListResponse) {
        if(adapterItemList!=null){
            adapterItemList!!.clearList()
        }
        adapterItemList = SimpleRecyclerViewAdapter(R.layout.cell_items, BR.bean, object : SimpleRecyclerViewAdapter.SimpleCallback<CellItemsBinding, DataItem> {
            override fun onItemClick(v: View, m: DataItem) {
                Log.e(">>>>", "onItemClick: " )
            }

            override fun onItemClick(v: View, m: DataItem, pos: Int) {
                Log.e(">>>>", "onItemClick: " )
                val intents = AddItemActivity().newIntent(this@MainActivity)
                intents.putExtra("data",m)
                startNewActivity(intents, false)
            }

            override fun onPositionClick(v: View, pos: Int) {
                super.onPositionClick(v, pos)
            }

            override fun onViewCreated(holder: SimpleRecyclerViewAdapter.SimpleViewHolder<CellItemsBinding>, position: Int, m: DataItem) {
                Log.e(">>>>", "onItemClick: " )
            }
        })
        binding.recItemList.adapter = adapterItemList
        adapterItemList!!.list = data.data as List<DataItem>
    }

    override fun onResume() {
        super.onResume()
        viewModel.getItemList(getData().data?.companies?.get(0)?.companyId.toString())
    }
}
