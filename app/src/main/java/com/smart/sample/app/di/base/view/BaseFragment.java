package com.smart.sample.app.di.base.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.CallSuper;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.smart.sample.app.BR;
import com.smart.sample.app.BuildConfig;
import com.smart.sample.app.R;
import com.smart.sample.app.data.local.SharedPref;
import com.smart.sample.app.di.base.viewmodel.BaseViewModel;
import com.smart.sample.app.util.spinner.SpinnerLoader;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public abstract class BaseFragment<B extends ViewDataBinding, V extends BaseViewModel> extends DaggerFragment {

    @Inject
    SharedPref sharedPref;
    @Inject
    public ViewModelProvider.Factory viewModelFactory;

    protected V viewModel;
    public B binding;
    protected Context baseContext;
    @Nullable
    private SpinnerLoader progressDialog;

    protected abstract BindingFragment<V> getBindingFragment();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.baseContext = context;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @CallSuper
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        BindingFragment<V> bindingFragment = getBindingFragment();
        if (bindingFragment == null) {
            throw new NullPointerException("Binding fragment cannot be null");
        }

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(bindingFragment.getClazz());

        binding = DataBindingUtil.inflate(inflater, bindingFragment.getLayoutResId(), container, false);
        binding.setVariable(BR.vm, viewModel);

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        subscribeToEvents(viewModel);
    }


    protected abstract void subscribeToEvents(V vm);


    protected final void showProgressDialog(@Nullable String msg) {
        if (progressDialog == null) {
            progressDialog = new SpinnerLoader(baseContext);
        }
        progressDialog.show();
    }

    protected final void showProgressDialog(@StringRes int msgResId) {
        showProgressDialog(getString(msgResId));
    }

    /**
     * Dismiss progress dialog
     */
    protected final void dismissProgressDialog() {
        if (progressDialog != null && this.progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    protected void startNewActivity(Intent intent, boolean finishExisting) {
        try {
            startActivity(intent);
            if (finishExisting)
                getActivity().finish();
            if (BuildConfig.EnableAnim)
                getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void startNewActivity(Intent intent, boolean ainmate, boolean finishExisting) {
        try {
            startActivity(intent);
            if (finishExisting)
                getActivity().finish();
            if (BuildConfig.EnableAnim && ainmate)
                getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void animateActivity() {
        if (BuildConfig.EnableAnim)
            getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

    protected void startNewActivity(Intent intent) {
        startNewActivity(intent, false);
    }

    protected static class BindingFragment<V extends BaseViewModel> {
        @LayoutRes
        private int layoutResId;
        private Class<V> clazz;

        public BindingFragment(@LayoutRes int layoutResId, Class<V> clazz) {
            this.layoutResId = layoutResId;
            this.clazz = clazz;
        }

        public int getLayoutResId() {
            return layoutResId;
        }

        public Class<V> getClazz() {
            return clazz;
        }
    }
}
