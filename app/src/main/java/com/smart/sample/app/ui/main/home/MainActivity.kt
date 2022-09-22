package com.smart.sample.app.ui.main.home

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smart.sample.app.BR
import com.smart.sample.app.R
import com.smart.sample.app.data.remote.helper.Status
import com.smart.sample.app.databinding.ActivityMainBinding
import com.smart.sample.app.databinding.CellItemsBinding
import com.smart.sample.app.databinding.DialogAddNewCatBinding
import com.smart.sample.app.di.base.adapter.SimpleRecyclerViewAdapter
import com.smart.sample.app.di.base.view.AppActivity
import com.smart.sample.app.ui.main.additem.AddItemActivity
import com.smart.sample.app.ui.main.models.DataItem
import com.smart.sample.app.ui.main.models.ItemsListResponse
import com.smart.sample.app.util.event.SingleRequestEvent
import com.smart.sample.app.util.showToast

class MainActivity : AppActivity<ActivityMainBinding, MainActivityVM>() {
    lateinit var deleteDialog: Dialog
    var lastSelectedItem: Int = 0

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
                    /*loadMoreItems = false
                    page = 1
                    adapterItemList?.clearList()*/
                    startActResult.launch(intent)
                    overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
//                    startNewActivity(intent, false)
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
                    if (loadMoreItems) {
                        //addDataTolist
                        if (resource.data.data?.isNotEmpty() == true) {
                            loadMoreItems = true
                            mList.addAll(resource.data.data as ArrayList<DataItem>)
                            adapterItemList?.addToList(resource.data.data as List<DataItem>)
                        } else {
                            loadMoreItems = false
                        }
                    } else {
                        //setAdapter
                        setAdapterToItemList(resource.data!!)
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
        binding.txtTitle.text = getData().data?.companies?.get(0)?.name
        viewModel.obrDelete.observe(this, SingleRequestEvent.RequestObserver { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    showProgressDialog(getString(R.string.plz_wait))
                }
                Status.SUCCESS -> {
                    Log.e(">>>>", "subscribeToEvents: SUCCESS" + resource.data)
                    dismissProgressDialog()
                    adapterItemList?.removeItem(lastSelectedItem)
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
        viewModel.getItemList(getData().data?.companies?.get(0)?.companyId.toString(), page)
    }

    var adapterItemList: SimpleRecyclerViewAdapter<DataItem, CellItemsBinding>? = null
    var mList = ArrayList<DataItem>()
    var loadMoreItems = false
    var page = 1
    private fun setAdapterToItemList(datas: ItemsListResponse) {
        mList.addAll(datas.data as ArrayList<DataItem>)
        loadMoreItems = true
        val manager = LinearLayoutManager(this)
//        binding.recItemList.layoutManager = manager
        if (adapterItemList != null) {
            adapterItemList!!.clearList()
        }
        adapterItemList = SimpleRecyclerViewAdapter(R.layout.cell_items, BR.bean, object : SimpleRecyclerViewAdapter.SimpleCallback<CellItemsBinding, DataItem> {
            override fun onItemClick(v: View, m: DataItem) {
                Log.e(">>>>", "onItemClick: ")
            }

            override fun onItemClick(v: View, m: DataItem, pos: Int) {
                Log.e(">>>>", "onItemClick: ")
                val intents = AddItemActivity().newIntent(this@MainActivity)
                intents.putExtra("data", m)
                /*loadMoreItems = false
                page = 1
                adapterItemList?.clearList()*/
                startNewActivity(intents, false)

            }

            override fun onPositionClick(v: View, pos: Int) {
                super.onPositionClick(v, pos)
                showDeleteDialog(pos, datas)
            }

            override fun onViewCreated(holder: SimpleRecyclerViewAdapter.SimpleViewHolder<CellItemsBinding>, position: Int, m: DataItem) {
            }
        })
//        binding.recItemList.adapter = adapterItemList
        adapterItemList!!.list = mList

        binding.scrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                    if (loadMoreItems) {
                        page += 1
                        viewModel.getItemList(getData().data?.companies?.get(0)?.companyId.toString(), page)
                        Log.e(">>>>", "setAdapterToItemList: " + page)

                    }
                }
            }
        })

        val mLinearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
       /* binding.recItemList.layoutManager = mLinearLayoutManager
        binding.recItemList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    Log.e("test", "reached the last element of recyclerview")
                    var visibleItemCount = mLinearLayoutManager.childCount
                    var totalItemCount = mLinearLayoutManager.itemCount
                    var pastVisiblesItems = mLinearLayoutManager.findFirstVisibleItemPosition()
                    if (loadMoreItems) {
                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                            Log.e(">>>>", "onScrolled: ")
                        }
                    }
                }
            }
        })*/
    }

/*    override fun onResume() {
        super.onResume()
        viewModel.getItemList(getData().data?.companies?.get(0)?.companyId.toString(), page)
    }*/

    private fun showDeleteDialog(pos: Int, data: ItemsListResponse) {
        lastSelectedItem = pos
        deleteDialog = Dialog(this)
        var mBinding = DataBindingUtil.inflate<DialogAddNewCatBinding>(LayoutInflater.from(this), R.layout.dialog_add_new_cat, null, false)
        deleteDialog.setContentView(mBinding.root)
        deleteDialog.window?.setBackgroundDrawableResource(R.color.transparent)
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels
        deleteDialog.window?.setLayout((width - 40), FrameLayout.LayoutParams.WRAP_CONTENT);
        mBinding.txtTitle.text = "Are you Sure Want To Delete This Item?"
        mBinding.txtCat.text = "No"
        mBinding.txtAdd.text = "Yes"
        mBinding.edCatHolder.visibility = View.GONE
        deleteDialog.show()
        mBinding.txtAdd.setOnClickListener {
            deleteDialog.dismiss()
            viewModel.deleteItemAsync("" + mList[pos].itemId)
        }
        mBinding.txtCat.setOnClickListener {
            deleteDialog.dismiss()
        }

    }

    private val startActResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            if (it.data?.hasExtra("data") == true) {
                Log.e(">>>", ":need to add itm in list" )
               /* val data= it.data!!.extras?.get("data") as DataItem
                mList.add(data)
                adapterItemList?.notifyDataSetChanged() */
                page += 1
                viewModel.getItemList(getData().data?.companies?.get(0)?.companyId.toString(), page)
            }
        }
    }
}
