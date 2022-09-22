package com.smart.sample.app.di.base.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.ArrayRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.smart.sample.app.BR;
import com.smart.sample.app.R;
import com.smart.sample.app.databinding.DialogPickListBinding;
import com.smart.sample.app.databinding.RowPickListBinding;
import com.smart.sample.app.di.base.adapter.SimpleRecyclerViewAdapter;
import com.smart.sample.app.util.decoration.ListVerticalItemDecoration;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ListDialog extends Dialog {
    private Context context;
    private DialogPickListBinding binding;
    private final Listener listener;
    private final List<Item> itemList;
    private final String title;

    public ListDialog(@NonNull Context context, String title, @NonNull List<Item> itemList, Listener listener) {
        super(context, R.style.Dialog);
        this.context = context;
        this.listener = listener;
        this.title = title;
        this.itemList = itemList;
    }

    public ListDialog(@NonNull Context context, String title, @ArrayRes int array, Listener listener) {
        super(context, R.style.Dialog);
        this.context = context;
        this.listener = listener;
        this.title = title;
        String[] arr = context.getResources().getStringArray(array);
        itemList = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            itemList.add(new Item(arr[i], i));
        }
    }


    private void init() {
        if (binding == null)
            binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_pick_list, null, false);
        binding.tvTitle.setText(title);
        if (listener != null) {
            binding.ivCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    listener.onNothingSelected();
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(binding.getRoot());
        setCancelable(false);
        setRv();
    }

    private void setRv() {
        SimpleRecyclerViewAdapter<Item, RowPickListBinding> adapter = new SimpleRecyclerViewAdapter<>(R.layout.row_pick_list, BR.bean, new SimpleRecyclerViewAdapter.SimpleCallback<RowPickListBinding,Item>() {
            @Override
            public void onViewCreated(@NotNull SimpleRecyclerViewAdapter.SimpleViewHolder<RowPickListBinding> holder, int position,Item item) {

            }

            @Override
            public void onItemClick(@NotNull View v, Item item, int pos) {

            }

            @Override
            public void onPositionClick(@Nullable View v, int pos) {

            }

            @Override
            public void onItemClick(View v, Item item) {
                dismiss();
                if (listener != null)
                    listener.onSelectItem(item);
            }
        });
        adapter.setList(itemList);
        binding.rvOne.setLayoutManager(new LinearLayoutManager(context));
        binding.rvOne.addItemDecoration(new ListVerticalItemDecoration(context, R.dimen.dp1));
        binding.rvOne.setAdapter(adapter);
    }

    public interface Listener {
        void onSelectItem(Item item);

        void onNothingSelected();
    }


    public static class Item {
        public String text;
        public int position;

        public Item(String text, int position) {
            this.text = text;
            this.position = position;
        }
    }

}
