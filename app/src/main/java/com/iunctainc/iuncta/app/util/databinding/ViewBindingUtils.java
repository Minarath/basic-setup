package com.iunctainc.iuncta.app.util.databinding;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import java.text.NumberFormat;
import java.util.Locale;

public class ViewBindingUtils {
    @BindingAdapter("visibleGone")
    public static void visibleGone(View view, boolean isVisible) {
        view.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("visibleHidden")
    public static void visibleHidden(View view, boolean isVisible) {
        view.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
    }

    @BindingAdapter("onFocusChange")
    public static void onFocusChange(EditText text, final View.OnFocusChangeListener listener) {
        text.setOnFocusChangeListener(listener);
    }


    @BindingAdapter("animate_alpha")
    public static void setAlphaVisible(final View view, Boolean visible) {
        if (visible != null) {
            if (visible) {
                view.animate().alpha(1);
            } else {
                view.animate().alpha(0);
            }
        }
    }


    @BindingAdapter("changetextfade")
    public static void setFadeVisible(final TextView view, final String text) {
        if (text == null)
            return;
        view.animate().cancel();
        view.animate().alpha(0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setText(text);
                view.animate().alpha(1);
            }
        });

    }


    @BindingAdapter("amount")
    public static void amount(final TextView view, final double amount) {
        try {
            NumberFormat us = NumberFormat.getCurrencyInstance(Locale.US);
            view.setText(us.format(amount));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @BindingAdapter("show_hide")
    public static void show_hide(final View view, Boolean amount) {
        if (amount != null) {
            view.setVisibility(amount ? View.VISIBLE : View.GONE);
        }


    }
}
