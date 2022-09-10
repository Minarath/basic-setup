package com.iunctainc.iuncta.app.di.base.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.iunctainc.iuncta.app.R;

public class BaseCustomTextDialog<V extends ViewDataBinding> extends Dialog {
    private Context context;
    private V binding;
    private @LayoutRes
    int layoutId;
    private Listener<V> listener;

    public BaseCustomTextDialog(@NonNull Context context, @LayoutRes int layoutId, Listener<V> listener) {
        super(context, R.style.Dialog);
        this.context = context;
        this.layoutId = layoutId;
        this.listener = listener;
    }

    public V getBinding() {
        init();
        return binding;
    }

    private void init() {
        if (binding == null)
            binding = DataBindingUtil.inflate(LayoutInflater.from(context), layoutId, null, false);
        if (listener != null) {
         //   binding.setVariable(com.company.cordulaahrens.BR.callback, listener);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(binding.getRoot());
        if(listener!=null)
            listener.onBind(binding);
    }



    public interface Listener<V>{
        void onViewClick(View view);
        default void onBind(V bind){

        }
    }
}
