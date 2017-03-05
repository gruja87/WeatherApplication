package com.example.dare.weather.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;

public class AlarmService extends IntentService {
    private NotificationManager alarmNotificationManager;

    public AlarmService() {
        super("AlarmService");
    }

    @Override
    public void onHandleIntent(Intent intent) {
//        AlarmActivity inst = AlarmActivity.instance();
//        inst.setAlarmText("Alarm! Wake up! Wake up!");
//        sendNotification("Wake Up! Wake Up!");

        dkjfnskdfns();

    }

    public void dkjfnskdfns(){
        sendBroadcast(new Intent(".services.AlarmService"));
    }

//    private void sendNotification(String msg) {
//        Toast.makeText(this, "Preparing to send notification...: " + msg, Toast.LENGTH_LONG).show();
//        alarmNotificationManager = (NotificationManager) this
//                .getSystemService(Context.NOTIFICATION_SERVICE);
//
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
//                new Intent(this, AlarmActivity.class), 0);
//
//        NotificationCompat.Builder alamNotificationBuilder = new NotificationCompat.Builder(
//                this).setContentTitle("Alarm").setSmallIcon(R.mipmap.ic_launcher)
//                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
//                .setAutoCancel(false)
//                .setContentText(msg);
//
//
//        alamNotificationBuilder.setContentIntent(contentIntent);
//        alarmNotificationManager.notify(1, alamNotificationBuilder.build());
//        Toast.makeText(this, "Notification sent.", Toast.LENGTH_SHORT).show();
//    }
}
