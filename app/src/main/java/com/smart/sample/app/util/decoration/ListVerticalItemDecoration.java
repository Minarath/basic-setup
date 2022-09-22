package com.smart.sample.app.util.decoration;

import android.content.Context;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListVerticalItemDecoration extends RecyclerView.ItemDecoration {
    private static final int DEFAULT_SPACE_IN_DP = 16;
    private int space;
    private boolean addtopspace = false;
    private boolean addBottomSpace = false;

    public ListVerticalItemDecoration(@NonNull Context context) {
        this.space = (int) (TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                DEFAULT_SPACE_IN_DP,
                context.getResources().getDisplayMetrics()));
    }

    public ListVerticalItemDecoration(@NonNull Context context, @DimenRes int dimenResId) {
        this.space = context.getResources().getDimensionPixelSize(dimenResId);
    }

    public ListVerticalItemDecoration(@NonNull Context context, @DimenRes int dimenResId, boolean addtopspace) {
        this.space = context.getResources().getDimensionPixelSize(dimenResId);
        this.addtopspace = addtopspace;
    }

    public ListVerticalItemDecoration(@NonNull Context context, @DimenRes int dimenResId, boolean addtopspace, boolean addBottomSpace) {
        this.space = context.getResources().getDimensionPixelSize(dimenResId);
        this.addtopspace = addtopspace;
        this.addBottomSpace = addBottomSpace;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        // Add top margin only for the first item to avoid double space between items
        if (addtopspace) {
            if (parent.getChildLayoutPosition(view) == 0) {
                outRect.top = space;
            } else {
                outRect.top = 0;
            }
        } else {
            outRect.top = 0;
        }
        outRect.left = 0;
        outRect.right = 0;
        if (addBottomSpace)
            outRect.bottom = space;
        else if (parent.getAdapter() != null) {
            outRect.bottom = parent.getChildLayoutPosition(view) == (parent.getAdapter().getItemCount() - 1) ? 0 : space;
        } else
            outRect.bottom = space;
    }
}
