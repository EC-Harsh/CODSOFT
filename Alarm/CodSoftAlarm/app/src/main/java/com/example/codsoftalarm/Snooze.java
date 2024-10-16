package com.example.codsoftalarm;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Snooze extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmReceiver alarmReceiver = new AlarmReceiver();
        alarmReceiver.mediaPlayer.stop();
        // Get the current alarm time
        long currentTime = System.currentTimeMillis();
        long alarmTime = currentTime + 10 * 60 * 1000; // Snooze for 10 minutes

        // Update the alarm time
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent snoozeIntent = new Intent(context, AlarmReceiver.class);
        PendingIntent snoozePendingIntent = PendingIntent.getBroadcast(context, 0, snoozeIntent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime, snoozePendingIntent);

        // Cancel the current notification
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(123);

        // Show a toast message to indicate that the alarm has been snoozed
        Toast.makeText(context, "Alarm snoozed for 10 minutes", Toast.LENGTH_SHORT).show();
    }
}
