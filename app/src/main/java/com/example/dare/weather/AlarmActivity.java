package com.example.dare.weather;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.dare.weather.services.AlarmManagerBroadcastReceiver;

import java.util.Calendar;


public class AlarmActivity extends Activity {

    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private TimePicker alarmTimePicker;
    private static AlarmActivity inst;
    private TextView alarmTextView;
    private AlarmManagerBroadcastReceiver broadcastReceiver;
    private String format = "";

    public static AlarmActivity instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        alarmTimePicker = (TimePicker) findViewById(R.id.alarmTimePicker);
        alarmTextView = (TextView) findViewById(R.id.alarmText);
        ToggleButton alarmToggle = (ToggleButton) findViewById(R.id.alarmToggle);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        broadcastReceiver = new AlarmManagerBroadcastReceiver();
    }

    public void onToggleClicked(View view) {
        if (((ToggleButton) view).isChecked()) {
            Toast.makeText(inst, "Alarm on", Toast.LENGTH_SHORT).show();
            Calendar calendar = Calendar.getInstance();
            int hour = alarmTimePicker.getCurrentHour();
            int min = alarmTimePicker.getCurrentMinute();
            showTime(hour,min);
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, min);
            Intent myIntent = new Intent(AlarmActivity.this, AlarmManagerBroadcastReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),0,myIntent,0);
//            pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, 0, myIntent, 0);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.ELAPSED_REALTIME_WAKEUP,pendingIntent);

        } else {
//            alarmManager.cancel(pendingIntent);
            setAlarmText("");
            Toast.makeText(inst, "Alarm off", Toast.LENGTH_SHORT).show();
        }
    }

    public void showTime(int hour, int min) {
        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }

        alarmTextView.setText(new StringBuilder().append(hour).append(" : ").append(min)
                .append(" ").append(format));
    }

    public void setAlarmText(String alarmText) {
        alarmTextView.setText(alarmText);
    }
}
