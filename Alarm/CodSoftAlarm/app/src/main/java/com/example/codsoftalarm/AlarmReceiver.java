package com.example.codsoftalarm;


import static android.content.Context.MODE_PRIVATE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {
    MediaPlayer mediaPlayer;
    NotificationManagerCompat notificationManager;
    public void onReceive(Context context, Intent intent) {String alarmSoundUri = intent.getStringExtra("alarmSound");

        // Create a notification intent
        Intent notificationIntent = new Intent(context, AlarmActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationIntent.putExtra("alarmSoundUri", alarmSoundUri);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        // Create a notification builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"foralarm");
        builder.setSmallIcon(R.drawable.ic_alarm);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_alarm));
        builder.setContentTitle("Alarm");
        builder.setContentText("Wake up!");
        builder.setAutoCancel(true);
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);

        builder.setContentIntent(pendingIntent);


        SharedPreferences sharedPreferences = context.getSharedPreferences("alarm_sound", MODE_PRIVATE);
        String selectedRingtoneUriString = sharedPreferences.getString("selected_ringtone_uri", "");
        Uri selectedRingtoneUri = Uri.parse(selectedRingtoneUriString);
        Ringtone ringtone = RingtoneManager.getRingtone(context, selectedRingtoneUri);
        ringtone.play();
        // Play the alarm sound
        Uri alarmUri = Uri.parse(alarmSoundUri);
        mediaPlayer = MediaPlayer.create(context, selectedRingtoneUri);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);
        // Get the alarm sound Uri from the intent

        // Add a dismiss action
        Intent dismissIntent = new Intent(context, Dismiss.class);
        dismissIntent.setAction("DISMISS");
        PendingIntent dismissPendingIntent = PendingIntent.getBroadcast(context, 0, dismissIntent, PendingIntent.FLAG_IMMUTABLE);
        builder.addAction(R.drawable.ic_dismiss, "Dismiss", dismissPendingIntent);

        // Add a snooze action
        Intent snoozeIntent = new Intent(context, Snooze.class);
        snoozeIntent.setAction("SNOOZE");
        PendingIntent snoozePendingIntent = PendingIntent.getBroadcast(context, 0, snoozeIntent, PendingIntent.FLAG_IMMUTABLE);
        builder.addAction(R.drawable.ic_snooze, "Snooze", snoozePendingIntent);

        // Show the notification
        notificationManager= NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
  notificationManager.notify(123, builder.build());
//        String action = intent.getAction();
//        if (action != null) {
//            if (action.equals("DISMISS")) {
//                // Dismiss the alarm
//                notificationManager.cancel(123);
//                mediaPlayer.stop();
//            } else if (action.equals("SNOOZE")) {
//                // Snooze the alarm for 5 minutes
//                Calendar calendar = Calendar.getInstance();
//                calendar.add(Calendar.MINUTE, 5);
//                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//                Intent snoozeAlarmIntent = new Intent(context, AlarmReceiver.class);
//                snoozeAlarmIntent.putExtra("alarmSound", alarmSoundUri);
//                PendingIntent snoozePendingInten = PendingIntent.getBroadcast(context, 0, snoozeAlarmIntent, PendingIntent.FLAG_IMMUTABLE);
//                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), snoozePendingInten);
//                mediaPlayer.stop();
//                mediaPlayer.release();
//            }
//        }

        // Handle dismiss and snooze actions

    }}





