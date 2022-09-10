package com.iunctainc.iuncta.app.util.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class CallBroadcast extends BroadcastReceiver {
    public static final String VIDEO_CALL = "videocall";

    @Override
    public void onReceive(Context context, Intent i) {
        if (i != null && i.getAction() != null)
            switch (i.getAction()) {
                case VIDEO_CALL: {
//                    Intent intent = VideoCallActivity.newIntentFromService(context, (VideoCallActivity.InputData) i.getSerializableExtra("data"));
//                    context.startActivity(intent);
                }
                break;
            }

    }
}
