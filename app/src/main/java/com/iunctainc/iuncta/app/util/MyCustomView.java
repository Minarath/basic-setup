package com.iunctainc.iuncta.app.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.databinding.DataBindingUtil;

import com.iunctainc.iuncta.app.R;
import com.iunctainc.iuncta.app.databinding.CellCustomeViewBinding;

public class MyCustomView extends LinearLayout {
    CellCustomeViewBinding binding;
    public Double maxProgress;
    public Double progress;
    CountDownTimer timer;
    Long milliSecond, countDownInterval;
    TimerTask timerTask;
    Context context;
  public   boolean isRunning = false;

    public MyCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.cell_custome_view, null, false);
        this.addView(binding.getRoot());


        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.MyCustomView, 0, 0);
        try {
            milliSecond = Long.parseLong((attributes.getInt(R.styleable.MyCustomView_maxSeconds, 0) * 1000) + "");

        } catch (Exception e) {

        }
        try {
            countDownInterval = Long.parseLong((attributes.getInt(R.styleable.MyCustomView_timeInterval, 0) * 1000) + "");

        } catch (Exception e) {

        }
    }

    public MyCustomView(Context context, Long milliSecond, Long countDownInterval, TimerTask timerTask) {
        super(context);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.cell_custome_view, null, false);
        this.context = context;
        this.milliSecond = milliSecond;
        this.countDownInterval = countDownInterval;
        this.timerTask = timerTask;
    }

    public void setMaxProgress(Double maxProgress) {
        this.maxProgress = maxProgress;
        binding.donutKey.setMaxProgress(maxProgress);
    }

    public void setProgress(Double progress) {
        this.progress = progress;

        binding.donutKey.setProgress(progress, maxProgress);
    }

    public void initTimer() {
        timer = new CountDownTimer(milliSecond, countDownInterval) {
            @Override
            public void onTick(long timerObj) {
                long progress = 120 - (timerObj / 1000);
                binding.donutKey.setProgress(Double.parseDouble("" + progress), Double.parseDouble("120"));
                Log.e(">>>>", "onTick: progress " + progress);
            }

            @Override
            public void onFinish() {
                timerTask.onFinish();
            }
        };
    }

    public void startTimer() {
        isRunning = true;
        timer.start();
    }

    public void cancleTimer() {
        isRunning = true;
        timer.cancel();
    }

    public interface TimerTask {
        void onFinish();
    }
}

