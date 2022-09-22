package com.smart.sample.app.di.base.sheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.smart.sample.app.BR;
import com.smart.sample.app.R;
import com.smart.sample.app.databinding.RowMediaBinding;
import com.smart.sample.app.databinding.SheetMediaPickerBinding;

import java.util.List;


public class MediaSheet extends BottomSheetDialogFragment {

    private SheetMediaPickerBinding binding;
    private final ClickListener listener;
    private final List<Item> itemList;
    private final String title;

    public MediaSheet(@NonNull String title, @NonNull List<Item> itemList, ClickListener listener) {
        this.listener = listener;
        this.itemList = itemList;
        this.title = title;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.sheet_media_picker, container, false);
        binding.setVariable(BR.callback, listener);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.tvHeader.setText(title);
        binding.llOne.removeAllViews();
        for (Item item : itemList) {
            RowMediaBinding mediaBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.row_media, binding.llOne, false);
            mediaBinding.setItem(item);
            mediaBinding.setCallback(listener);
            binding.llOne.addView(mediaBinding.getRoot());
        }
    }


    public interface ClickListener {
        void onClick(Item item);

        void onCancel();
    }

    public static class Item {
        @DrawableRes
        public int icon;
        public String text;
        public int id;

        public Item(int icon, String text, int id) {
            this.icon = icon;
            this.text = text;
            this.id = id;
        }
    }
}
