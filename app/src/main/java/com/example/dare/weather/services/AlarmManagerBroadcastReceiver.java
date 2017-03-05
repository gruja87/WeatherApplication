package com.example.dare.weather.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.example.dare.weather.AlarmActivity;
import com.example.dare.weather.R;

public class AlarmManagerBroadcastReceiver extends WakefulBroadcastReceiver {

    private NotificationManager alarmNotificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {

        alarmNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, AlarmActivity.class), 0);

        NotificationCompat.Builder alamNotificationBuilder = new NotificationCompat.Builder(
                context).setContentTitle("Alarm").setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Wake Up! Wake Up!"))
                .setAutoCancel(false)
                .setContentText("Wake Up! Wake Up!");

        alamNotificationBuilder.setContentIntent(contentIntent);
        alarmNotificationManager.notify(1, alamNotificationBuilder.build());
    }

}
