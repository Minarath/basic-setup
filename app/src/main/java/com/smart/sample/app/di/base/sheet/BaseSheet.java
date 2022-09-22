package com.smart.sample.app.di.base.sheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.smart.sample.app.BR;


public class BaseSheet<V extends ViewDataBinding> extends BottomSheetDialogFragment {

    private V binding;
    private ClickListener<V> listener;
    @LayoutRes
    private int layoutId;

    public BaseSheet(int layoutId, ClickListener<V> listener) {
        this.listener = listener;
        this.layoutId = layoutId;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false);
        binding.setVariable(BR.callback, listener);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (this.listener != null)
            this.listener.onViewCreated(binding);
    }


    public interface ClickListener<V extends ViewDataBinding> {
        void onClick(@NonNull View view);

        void onViewCreated(V binding);
    }


}
