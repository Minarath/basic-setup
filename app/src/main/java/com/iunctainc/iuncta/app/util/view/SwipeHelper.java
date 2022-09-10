package com.iunctainc.iuncta.app.util.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.SparseArray;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.FontRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by macminiandroid on 26/03/18.
 */

public abstract class SwipeHelper extends ItemTouchHelper.SimpleCallback {
    public static final int BUTTON_WIDTH = 160;
    private RecyclerView recyclerView;
    private List<UnderlayButton> buttons;
    private GestureDetector gestureDetector;
    private int swipedPos = -1;
    private float swipeThreshold = 0.8f;
    private SparseArray<List<UnderlayButton>> buttonsBuffer;
    private Queue<Integer> recoverQueue;
    private static Context context;

    public SwipeHelper(Context context, final RecyclerView recyclerView) {
        super(0, ItemTouchHelper.LEFT);
        this.context = context;
        this.recyclerView = recyclerView;
        this.buttons = new ArrayList<>();
        GestureDetector.SimpleOnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                for (UnderlayButton button : buttons) {
                    if (button.onClick(e.getX(), e.getY()))
                        break;
                }
                return true;
            }
        };
        this.gestureDetector = new GestureDetector(context, gestureListener);
        View.OnTouchListener onTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent e) {
                try {
                    if (swipedPos < 0) return false;
                    Point point = new Point((int) e.getRawX(), (int) e.getRawY());
                    RecyclerView.ViewHolder swipedViewHolder = recyclerView.findViewHolderForAdapterPosition(swipedPos);
                    View swipedItem = swipedViewHolder.itemView;
                    Rect rect = new Rect();
                    swipedItem.getGlobalVisibleRect(rect);
                    if (e.getAction() == MotionEvent.ACTION_DOWN || e.getAction() == MotionEvent.ACTION_UP || e.getAction() == MotionEvent.ACTION_MOVE) {
                        if (rect.top < point.y && rect.bottom > point.y)
                            gestureDetector.onTouchEvent(e);
                        else {
                            recoverQueue.add(swipedPos);
                            swipedPos = -1;
                            recoverSwipedItem();
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return false;
            }
        };
        this.recyclerView.setOnTouchListener(onTouchListener);
        buttonsBuffer = new SparseArray<>();
        recoverQueue = new LinkedList<Integer>() {
            @Override
            public boolean add(Integer o) {
                if (contains(o))
                    return false;
                else
                    return super.add(o);
            }
        };
        attachSwipe();
    }


    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int pos = viewHolder.getAdapterPosition();

        if (swipedPos != pos)
            recoverQueue.add(swipedPos);

        swipedPos = pos;

        if (buttonsBuffer.get(swipedPos) != null)
            buttons = buttonsBuffer.get(swipedPos);
        else
            buttons.clear();

        buttonsBuffer.clear();
        swipeThreshold = 0.5f * buttons.size() * BUTTON_WIDTH;
        recoverSwipedItem();
    }

    @Override
    public float getSwipeThreshold(RecyclerView.ViewHolder viewHolder) {
        return swipeThreshold;
    }

    @Override
    public float getSwipeEscapeVelocity(float defaultValue) {
        return 0.1f * defaultValue;

    }

    @Override
    public float getSwipeVelocityThreshold(float defaultValue) {
        return 5.0f * defaultValue;

    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        int pos = viewHolder.getAdapterPosition();
        float translationX = dX;
        View itemView = viewHolder.itemView;

        if (pos < 0) {
            swipedPos = pos;
            return;
        }

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            if (dX < 0) {
                List<UnderlayButton> buffer = new ArrayList<>();

                if (buttonsBuffer.get(pos) == null) {
                    instantiateUnderlayButton(viewHolder, buffer);
                    buttonsBuffer.put(pos, buffer);
                } else {
                    buffer = buttonsBuffer.get(pos);
                }

                translationX = dX * buffer.size() * BUTTON_WIDTH / itemView.getWidth();
                drawButtons(c, itemView, buffer, pos, translationX);
            }
        }

        super.onChildDraw(c, recyclerView, viewHolder, translationX, dY, actionState, isCurrentlyActive);
    }

    private synchronized void recoverSwipedItem() {
        while (!recoverQueue.isEmpty()) {
            int pos = recoverQueue.poll();
            if (pos > -1) {
                recyclerView.getAdapter().notifyItemChanged(pos);
            }
        }
    }

    private void drawButtons(Canvas c, View itemView, List<UnderlayButton> buffer, int pos, float dX) {
        float right = itemView.getRight();
        float dButtonWidth = (-1) * dX / buffer.size();
        for (UnderlayButton button : buffer) {
            float left = right - dButtonWidth;
            button.onDraw(c, new RectF(
                            left,
                            itemView.getTop(),
                            right,
                            itemView.getBottom()
                    ),
                    pos
            );
            right = left;
        }
    }

    public void attachSwipe() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(this);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public abstract void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons);

    public static class UnderlayButton {
        @Nullable
        private String text;
        @DrawableRes
        private int imageResId;
        @ColorRes
        private int bgColor;
        private int textSize = 40;
        @ColorRes
        private int textColor;
        private int pos;
        @Nullable
        private Typeface typeface;
        private RectF clickRegion;
        private UnderlayButtonClickListener clickListener;

        private UnderlayButton(@Nullable String text, int imageResId, @ColorRes int bgColor, @ColorRes int textColor, @FontRes int font, UnderlayButtonClickListener clickListener) {
            this.text = text;
            this.imageResId = imageResId;
            this.bgColor = bgColor;
            this.textColor = textColor;
            this.clickListener = clickListener;
            if (font != 0)
                typeface = ResourcesCompat.getFont(context, font);
        }

        public static class Builder {
            @Nullable
            private String text;
            @DrawableRes
            private int imageResId;
            private int textSize;
            @ColorRes
            private int bgColor;
            @ColorRes
            private int textColor;
            @FontRes
            private int font;
            private UnderlayButtonClickListener clickListener;

            public Builder() {

            }

            public Builder setText(@Nullable String text) {
                this.text = text;
                return this;
            }

            public Builder setTextSize(int textSize) {
                this.textSize = textSize;
                return this;
            }

            public Builder setImageResId(@DrawableRes int imageResId) {
                this.imageResId = imageResId;
                return this;
            }

            public Builder setBgColor(@ColorRes int bgColor) {
                this.bgColor = bgColor;
                return this;
            }

            public Builder setTextColor(@ColorRes int textColor) {
                this.textColor = textColor;
                return this;
            }

            public Builder setFont(@FontRes int font) {
                this.font = font;
                return this;
            }

            public Builder withListener(UnderlayButtonClickListener clickListener) {
                this.clickListener = clickListener;
                return this;
            }

            public UnderlayButton build() {
                return new UnderlayButton(text, imageResId, bgColor, textColor, font, clickListener);
            }
        }

        public boolean onClick(float x, float y) {
            if (clickRegion != null && clickRegion.contains(x, y)) {
                clickListener.onClick(pos);
                return true;
            }

            return false;
        }

        public void onDraw(Canvas c, RectF rect, int pos) {
            Paint p = new Paint();

            // Draw background
            if (bgColor == 0)
                p.setColor(Color.TRANSPARENT);
            else
                p.setColor(ContextCompat.getColor(context, bgColor));

            c.drawRect(rect, p);

            // Draw Text
            if (textColor == 0)
                p.setColor(Color.WHITE);
            else
                p.setColor(ContextCompat.getColor(context, textColor));
            p.setTextSize(textSize);
            if (typeface != null)
                p.setTypeface(typeface);
            Rect r = new Rect();
            float cHeight = rect.height();
            float cWidth = rect.width();
            p.setTextAlign(Paint.Align.LEFT);
            p.getTextBounds(text, 0, text.length(), r);
            float x = cWidth / 2f - r.width() / 2f - r.left;
            float y = cHeight / 2f + r.height() / 2f - r.bottom;
            c.drawText(text, rect.left + x, rect.top + y, p);
            clickRegion = rect;
            this.pos = pos;
        }
    }

    public interface UnderlayButtonClickListener {
        void onClick(int pos);
    }
}