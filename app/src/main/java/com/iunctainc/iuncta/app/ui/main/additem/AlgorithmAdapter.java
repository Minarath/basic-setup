package com.iunctainc.iuncta.app.ui.main.additem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.iunctainc.iuncta.app.R;
import com.iunctainc.iuncta.app.ui.main.models.CategoryItem;

import java.util.ArrayList;
  
public class AlgorithmAdapter extends ArrayAdapter<CategoryItem> {
    public AlgorithmAdapter(Context context,ArrayList<CategoryItem> algorithmList)
    {
        super(context, 0, algorithmList);
    }
  
    @NonNull
    @Override
    public View getView(int position, @Nullable
                                      View convertView, @NonNull ViewGroup parent)
    {
        return initView(position, convertView, parent);
    }
  
    @Override
    public View getDropDownView(int position, @Nullable
                                              View convertView, @NonNull ViewGroup parent)
    {
        return initView(position, convertView, parent);
    }
  
    private View initView(int position, View convertView,
                          ViewGroup parent)
    {
        // It is used to set our custom view.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.algorithm_spinner, parent, false);
        }
        TextView textViewName = convertView.findViewById(R.id.text_view);
        CategoryItem currentItem = getItem(position);
        if (currentItem != null) {
            textViewName.setText(currentItem.getName());
        }
        return convertView;
    }
}