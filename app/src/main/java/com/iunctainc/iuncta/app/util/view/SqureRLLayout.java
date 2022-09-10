package com.iunctainc.iuncta.app.util.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

public class SqureRLLayout extends RelativeLayout {
    public SqureRLLayout(Context context) {
        super(context);
    }

    public SqureRLLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SqureRLLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SqureRLLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
