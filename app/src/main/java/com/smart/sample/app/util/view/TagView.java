package com.smart.sample.app.util.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

import com.smart.sample.app.R;


public class TagView extends ViewGroup {

    private float bg_radius;
    private float bg_border_width;
    private int bg_color;
    private int bg_alpha, stroke_alpha;
    private int mleft;
    private int mright;
    private int mtop;
    private int mbottom;
    private before bf;
    private ViewDragHelper help;
    private OnClickListener listener;
    private Paint p = new Paint();
    private Handler hld = new Handler();


    public TagView(Context context) {
        this(context, null);
    }

    public TagView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.TagView, defStyleAttr, 0);

        try {

            bg_radius = attr.getDimension(R.styleable.TagView_bg_radius, 5);
            bg_alpha = attr.getInteger(R.styleable.TagView_bg_alpha, 100);
            stroke_alpha = attr.getInteger(R.styleable.TagView_st_alpha, 255);
            bg_color = attr.getColor(R.styleable.TagView_bg_color, Color.parseColor("#FFCCCC"));
            bg_border_width = attr.getFloat(R.styleable.TagView_bg_border_width, 1);

            mleft = (int) attr.getDimension(R.styleable.TagView_mleft, 10.0f);
            mright = (int) attr.getDimension(R.styleable.TagView_mright, 10.0f);
            mtop = (int) attr.getDimension(R.styleable.TagView_mtop, 5.0f);
            mbottom = (int) attr.getDimension(R.styleable.TagView_mbottom, 5.0f);

        } catch (Exception e) {

            Log.i("Unit", "init " + e.toString());

        } finally {

            attr.recycle();
        }
        setWillNotDraw(false);
        p = new Paint();
        p.setColor(bg_color);
        help = ViewDragHelper.create(this, call);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
// TODO: 3/4/20
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int maxWidth = 0;
        int num = getChildCount();
        int lPadding = getPaddingLeft();
        int rPadding = getPaddingRight();
        int tPadding = getPaddingTop();
        int bPadding = getPaddingBottom();

        int mheight = 0;
        if (getChildCount() > 0)
            mheight = getChildAt(0).getMeasuredHeight();
        int height = tPadding + bPadding + mheight + mtop + mbottom;
        int width = lPadding + rPadding;

        for (int i = 0; i < num; i++) {
            View c = getChildAt(i);

            if (width + c.getMeasuredWidth() + mleft + mright > widthSize) {
                maxWidth = Math.max(width, maxWidth);
                width = lPadding + rPadding + c.getMeasuredWidth() + mleft + mright;
                if (height + c.getHeight() + mtop + mbottom > heightSize)
                    break;
                height += c.getMeasuredHeight() + mtop + mbottom;
            } else {
                width += c.getMeasuredWidth() + mleft + mright;
            }
        }
        maxWidth = Math.max(width, maxWidth);
        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? widthSize : maxWidth, (heightMode == MeasureSpec.EXACTLY) ? heightSize : height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int num = getChildCount();
        int lPadding = getPaddingLeft();
        int rPadding = getPaddingRight();
        int tPadding = getPaddingTop();
        int bPadding = getPaddingBottom();

        int cw = lPadding;
        int ch = tPadding;
        int right = getWidth() - rPadding;
        int bottom = getHeight() - bPadding;

        for (int i = 0; i < num; i++) {
            View c = getChildAt(i);
            if (cw + c.getMeasuredWidth() + mleft + mright > right) {
                if (ch + c.getMeasuredHeight() + mtop + mbottom > bottom)
                    break;
                cw = lPadding + mleft + c.getMeasuredWidth();
                ch += mtop + mbottom + c.getMeasuredHeight();
                c.layout(lPadding + mleft, ch + mtop, cw, ch + mtop + c.getMeasuredHeight());
                cw += mright;
            } else {
                c.layout(cw + mleft, ch + mtop, cw + mleft + c.getMeasuredWidth(), ch + c.getMeasuredHeight() + mtop);
                cw = cw + mleft + mright + c.getMeasuredWidth();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        p.setAlpha(stroke_alpha);
        p.setStrokeWidth(bg_border_width);
        p.setStyle(Paint.Style.STROKE);
        canvas.drawRoundRect(0, 0, getWidth(), getHeight(), bg_radius, bg_radius, p);

        p.setStyle(Paint.Style.FILL);
        p.setAlpha(bg_alpha);
        canvas.drawRoundRect(0, 0, getWidth(), getHeight(), bg_radius, bg_radius, p);
    }

    public void addTagString(String name, @Nullable Object tags) {
        if (name == null)
            return;
        TextView tv = new TextView(getContext(), null);
        tv.setGravity(Gravity.CENTER);
        tv.setClickable(true);
        tv.setTag(tags);
        if (listener != null)
            tv.setOnClickListener(listener);
        tv.setText(name);
        if (bf != null)
            bf.execute(tv);
        addView(tv);
    }

    public void setTag(String[] tag) {
        if (tag.length == 0)
            return;
        removeAllViews();
        for (int i = 0; i < tag.length; i++) {
            TextView tv = new TextView(getContext(), null);
            tv.setClickable(true);
            if (listener != null)
                tv.setOnClickListener(listener);
            tv.setText(tag[i]);
            if (bf != null)
                bf.execute(tv);
            addView(tv);
        }
    }

    public void addListener(OnClickListener listener) {
        if (listener == null)
            throw new RuntimeException("listener is null");
        this.listener = listener;
    }

    public OnClickListener getListener() {
        return listener;
    }

    public void addBefore(before bf) {
        this.bf = bf;
    }

    public before getBefore() {
        return bf;
    }

    public float getBg_border_width() {
        return bg_border_width;
    }

    public void setBg_border_width(float bg_border_width) {
        this.bg_border_width = bg_border_width;
    }

    public int getBg_alpha() {
        return bg_alpha;
    }

    public void setBg_alpha(int bg_alpha) {
        this.bg_alpha = bg_alpha;
    }

    public int getBg_color() {
        return bg_color;
    }

    public void setBg_color(int bg_color) {
        this.bg_color = bg_color;
    }

    public float getBg_radius() {
        return bg_radius;
    }

    public void setBg_radius(float bg_radius) {
        this.bg_radius = bg_radius;
    }

    public int getMbottom() {
        return mbottom;
    }

    public void setMbottom(int mbottom) {
        this.mbottom = mbottom;
    }

    public int getMleft() {
        return mleft;
    }

    public void setMleft(int mleft) {
        this.mleft = mleft;
    }

    public int getMright() {
        return mright;
    }

    public void setMright(int mright) {
        this.mright = mright;
    }

    public int getMtop() {
        return mtop;
    }

    public void setMtop(int mtop) {
        this.mtop = mtop;
    }

    public interface before {
        public void execute(TextView v);
    }

    static class Position {
        private int x;
        private int y;

        private Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }

    private ViewDragHelper.Callback call = new ViewDragHelper.Callback() {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (dx > 0) {
                left = Math.min(getWidth() - child.getMeasuredWidth(), left);
            } else {
                left = Math.max(0, left);
            }
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            if (dy > 0) {
                top = Math.min(getHeight() - child.getMeasuredHeight(), top);
            } else {
                top = Math.max(0, top);
            }
            return top;
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return 10;
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return 10;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            int pos = SearchIndex(releasedChild);
            if (pos == -1)
                return;
            removeView(releasedChild);
            addView(releasedChild, pos);
        }

    };

    public int SearchIndex(View c) {
        int x = c.getLeft();
        int y = c.getTop();
        int num = getChildCount();
        int index = -1;
        for (int i = 0; i < num; i++) {
            View cc = getChildAt(i);
            if (x >= cc.getLeft() && y >= cc.getTop()) {
                if (x <= cc.getLeft() + cc.getMeasuredWidth() && y <= cc.getTop() + cc.getMeasuredHeight()) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }

    @Override
    public void computeScroll() {
        if (help.continueSettling(true))
            ViewCompat.postInvalidateOnAnimation(this);

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
         help.processTouchEvent(ev);
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        return help.shouldInterceptTouchEvent(ev);
    }
}