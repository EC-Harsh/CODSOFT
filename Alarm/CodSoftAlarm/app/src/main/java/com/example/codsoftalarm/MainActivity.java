package com.example.codsoftalarm;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import  android.icu.util.*;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    private RingtoneManager ringtoneManager;
    ArrayList<AlarmModel> arrAlarm;
    TextView currentTime ;
    FloatingActionButton addAlarm;
    Handler hndler;
    TimePicker t;
    RecyclerView rv;
    RecyclerAlarmAdapter rvadap;
    AlarmManager alarmManager;
    Button ss ;
    private Uri alarmSound;


    void createNotification(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            CharSequence name ="reminder";
            String desc="Reminder for alarm";
            int imp= NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("foralarm",name,imp);
            channel.setDescription(desc);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);


        }
    }
    void loadData(RecyclerView rv){
        SharedPreferences sp =getApplicationContext().getSharedPreferences("Data",MODE_PRIVATE);
        SharedPreferences.Editor ed= sp.edit();
        Gson gs = new Gson();
        String json = sp.getString("alarm_list",null);
        Type type = new TypeToken<ArrayList<AlarmModel>>(){}.getType();
        arrAlarm=gs.fromJson(json,type);
        if(arrAlarm==null){
             arrAlarm = new ArrayList<AlarmModel>();
        }
        else{
        rvadap = new RecyclerAlarmAdapter(MainActivity.this,arrAlarm);
        rv.setAdapter(rvadap);

        for(int i =0;i<arrAlarm.size();i++){
            Date t = Calendar.getInstance().getTime();
            String reg=":";
           String times[]= arrAlarm.get(i).time.split(reg);
           int hour = Integer.parseInt(times[0]);
           int min=Integer.parseInt((times[1]));
            if (arrAlarm.get(i).Status == true ) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, min);
                calendar.set(Calendar.SECOND, 0);

                // Create a new AlarmManager instance for each alarm
                if (calendar.getTimeInMillis() > System.currentTimeMillis()) {
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
                intent.putExtra("alarmSound", alarmSound.toString());
                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
        }}

        }
    }
    void saveData(int hr,int min,boolean status,String Label,RecyclerView rv){
        SharedPreferences sp =getApplicationContext().getSharedPreferences("Data",MODE_PRIVATE);
        SharedPreferences.Editor ed= sp.edit();
        Gson gs = new Gson();
        arrAlarm.add(new AlarmModel(hr,min,status,Label));
        //saveData(t.getHour(),t.getMinute(),status.isChecked(),l.getText().toString()););
        //arrAlarm.add(new AlarmModel(time,status,Label));
        String json = gs.toJson(arrAlarm);
        ed.putString("alarm_list",json);
        ed.apply();
        loadData(rv);

    }


    private Runnable updateTimeRunnable = new Runnable() {
        @Override
        public void run() {
            // Get the current time
            String ct = getCurrentTime();

            // Update the TextView with the current time
            currentTime.setText(ct);

            // Post the Runnable again to update the time after a delay
            hndler.postDelayed(this, 1000); // Update every 1 second
        }
    };




    TimeZone timeZone = TimeZone.getTimeZone("Asia/Kolkata");
    private String getCurrentTime() {
        // Get the current time in the format you want (e.g., HH:mm:ss)
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.US);
        sdf.setTimeZone(timeZone);
        return sdf.format(new Date());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        createNotification();
        alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ss = findViewById(R.id.selectsound);
        ringtoneManager = new RingtoneManager(this);
        ringtoneManager.setType(RingtoneManager.TYPE_ALARM);
        ss.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                selectAlarmSound(view);
            }
        });
        addAlarm = findViewById(R.id.addAlarm);
         rv = findViewById(R.id.recview);
        rv.setLayoutManager(new LinearLayoutManager(this));
        loadData(rv);
        addAlarm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.alarm_setter);
                TimePicker t = dialog.findViewById(R.id.time);
                t.setHour(12);
                t.setMinute(0);
                //MaterialSwitch vibrate = dialog.findViewById(R.id.vibrationMode);
                MaterialSwitch alarmstat = dialog.findViewById(R.id.alarmStatus);
                FloatingActionButton b = dialog.findViewById(R.id.tb);
                MaterialSwitch status=dialog.findViewById(R.id.alarmStatus);
                TextView l=dialog.findViewById(R.id.editText);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        saveData(t.getHour(),t.getMinute(),status.isChecked(),l.getText().toString(),rv);
                        dialog.dismiss();
                        //loadData(rv,t.getHour(),t.getMinute());
                    }
                }


                );dialog.show();
            }
        });
        currentTime = findViewById(R.id.curtime);
        hndler=new Handler();
        hndler.post(updateTimeRunnable);






    }
    public void selectAlarmSound(View view) {
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Alarm Sound");
        startActivityForResult(intent, 1);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            Uri selectedRingtoneUri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            // Save the selected ringtone URI
            SharedPreferences sharedPreferences = getSharedPreferences("alarm_sound", MODE_PRIVATE);
            sharedPreferences.edit().putString("selected_ringtone_uri", selectedRingtoneUri.toString()).apply();
        }
    }
}