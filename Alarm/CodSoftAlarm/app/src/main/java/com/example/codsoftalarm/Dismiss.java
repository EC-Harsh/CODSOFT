package com.example.codsoftalarm;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Dismiss extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmReceiver alarmReceiver = new AlarmReceiver();

        // Stop the media player
        if (alarmReceiver.mediaPlayer != null) {
            alarmReceiver.mediaPlayer.stop();
            alarmReceiver.mediaPlayer.release();
            alarmReceiver.mediaPlayer = null;
        }
        // Cancel the alarm
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent dismissIntent = new Intent(context, AlarmReceiver.class);
        PendingIntent dismissPendingIntent = PendingIntent.getBroadcast(context, 0, dismissIntent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager.cancel(dismissPendingIntent);

        // Cancel the current notification
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(123);

        // Show a toast message to indicate that the alarm has been dismissed
        Toast.makeText(context, "Alarm dismissed", Toast.LENGTH_SHORT).show();

    }
}
