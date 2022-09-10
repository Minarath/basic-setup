package com.iunctainc.iuncta.app.util.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.iunctainc.iuncta.app.R
import com.iunctainc.iuncta.app.data.beans.NotificationListData
import com.iunctainc.iuncta.app.ui.main.editprofile.EditProfileActivity


class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.e(">>>>", "Message Notification Body: ${remoteMessage.data}")
        remoteMessage.notification?.let {
            Log.e(">>>>", "Message Notification Body: ${it.body}")
            android.os.Handler(Looper.getMainLooper()).postDelayed({
                sendNotification(remoteMessage.data)
            }, 200)

        }
    }

    override fun onNewToken(token: String) {
        Log.e(TAG, "onNewToken: " + token)
    }

    private fun sendNotification(data: MutableMap<String, String>) {
        Log.e(">>>", "sendNotification: ${data.keys}")
        val notificationData = NotificationListData(
            clientImage = data["client_profile"].toString(),
            sendBy = data["send_by"]?.toInt(),
            loginType = data["login_type"]?.toInt(),
            notificationId = data["notification_id"]?.toInt(),
            clientName = data["client_name"].toString(),
            icon = data["icon"],
            type = data["type"]?.toInt(),
            message = data["message"].toString(),
            sendTo = data["send_to"]?.toInt(),
            verify = 0
        )
        var intent: Intent? = null
        /*intent = if (!data["login_type"].equals("") && data["login_type"].equals("5")) {
            EditProfileActivity().newIntent(baseContext).putExtra("login_type", "5")
        } else {
            EditProfileActivity().newIntent(baseContext, data["login_type"]!!.toInt(), notificationData)
        }*/
        intent= EditProfileActivity().newIntent(baseContext).putExtra("login_type", "5")
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val channelId = "123"
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_notification)).setShowWhen(false)
            .setColor(ContextCompat.getColor(applicationContext, R.color.pink_dark))
            .setContentTitle(data["title"])
            .setContentText(data["message"])
            .setAutoCancel(true)
            .setStyle(NotificationCompat.BigTextStyle())
            .setContentIntent(pendingIntent)
            .setSound(defaultSoundUri)
            .setPriority(Notification.PRIORITY_MAX)
            .setWhen(0)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0, notificationBuilder.build())

    }

    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }
}