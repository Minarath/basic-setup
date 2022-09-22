package com.smart.sample.app.di.base.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

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
import com.smart.sample.app.data.repo.welcome.WelcomeRepo;
import com.smart.sample.app.di.base.MyApplication;
import com.smart.sample.app.di.base.viewmodel.BaseViewModel;
import com.smart.sample.app.util.spinner.SpinnerLoader;

import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity<B extends ViewDataBinding, V extends BaseViewModel> extends DaggerAppCompatActivity {
    @Inject
    SharedPref sharedPref;
    @Inject
    public ViewModelProvider.Factory viewModelFactory;
    protected V viewModel;
    protected B binding;
    @Nullable
    private SpinnerLoader progressDialog;

    @Inject
    WelcomeRepo welcomeRepo;

    protected abstract BindingActivity<V> getBindingActivity();

    @CallSuper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(MyApplication.getInstance()).setCurrentActivity(this);
        Objects.requireNonNull(MyApplication.getInstance()).setAuthRepo(welcomeRepo);
        BindingActivity<V> bindingActivity = getBindingActivity();
        if (bindingActivity == null) {
            throw new NullPointerException("Binding activity cannot be null");
        }
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(bindingActivity.getClazz());
        binding = DataBindingUtil.setContentView(this, bindingActivity.getLayoutResId());
        binding.setVariable(BR.vm, viewModel);
        subscribeToEvents(viewModel);

    }


    protected abstract void subscribeToEvents(V vm);

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    protected Dialog pDialog;

    protected final void showProgressDialog(@Nullable String msg) {
        if (progressDialog == null) {
            progressDialog = new SpinnerLoader(this);
        }
        progressDialog.show();


    }

    protected final void showProgressDialog(@StringRes int msgResId) {
        showProgressDialog(getString(msgResId));
    }


/*
    protected final void showProgressDialog(@StringRes int msgResId) {
        showProgressDialog(getString(msgResId));
    }*/


    /**
     * Dismiss progress dialog
     */
    protected final void dismissProgressDialog() {
        if (progressDialog != null && this.progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    protected static class BindingActivity<V extends BaseViewModel> {
        @LayoutRes
        private int layoutResId;
        private Class<V> clazz;

        public BindingActivity(@LayoutRes int layoutResId, Class<V> clazz) {
            this.layoutResId = layoutResId;
            this.clazz = clazz;
        }

        int getLayoutResId() {
            return layoutResId;
        }

        Class<V> getClazz() {
            return clazz;
        }
    }


    protected void startNewActivity(Intent intent, boolean finishExisting) {
        startNewActivity(intent, finishExisting, true);
    }

    protected void startNewActivity(Intent intent, boolean finishExisting, boolean animate) {
        startActivity(intent);
        if (finishExisting)
            finish();
        if (animate)
            animateActivity();
    }

    protected void startNewActivity(Intent intent) {
        startNewActivity(intent, false, true);
    }


    protected void onBackPressed(boolean animate) {
        super.onBackPressed();
        if (BuildConfig.EnableAnim && animate)
            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);

    }

    public void animateActivity() {
        if (BuildConfig.EnableAnim)
            overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

    protected void finish(boolean animate) {
        finish();
        if (BuildConfig.EnableAnim && animate)
            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
    }


}