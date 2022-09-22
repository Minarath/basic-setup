package com.smart.sample.app.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.bumptech.glide.Glide;
import com.smart.sample.app.R;

public class FireBaseUtils {

    @Nullable
    public static Bitmap getBitmapFromURL(Context context, String strURL) {
        try {
            return new AsyncTask<String, Void, Bitmap>() {
                protected Bitmap doInBackground(String... params) {
                    try {
                        return Glide.with(context)
                                .asBitmap()
                                .load("" + params[0]).circleCrop().override(150, 150)
                                .placeholder(R.drawable.blank_portrait)
                                .submit()
                                .get();
                    } catch (Exception e) {
                        return null;
                    }
                }
            }.execute(strURL).get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static NotificationCompat.Builder getNotificationBuilder(Context context, NotificationManager notificationManager) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", context.getString(R.string.app_name), NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Set notification priority");
            channel.setShowBadge(true);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            notificationManager.createNotificationChannel(channel);

        }
        return new NotificationCompat.Builder(context, "default");
    }

}
