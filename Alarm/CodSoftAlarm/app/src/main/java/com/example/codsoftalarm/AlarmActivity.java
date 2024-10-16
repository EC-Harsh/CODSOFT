package com.example.codsoftalarm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;




    public class AlarmActivity extends Activity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_alarm);

            // Get the alarm sound Uri from the intent
            Intent intent = getIntent();
            String alarmSoundUri = intent.getStringExtra("alarmSoundUri");

            // Display the alarm sound Uri
            TextView alarmSoundTextView = findViewById(R.id.alarm_sound_text_view);
            alarmSoundTextView.setText("Alarm sound: " + alarmSoundUri);

            // Add a button to stop the alarm
            Button stopAlarmButton = findViewById(R.id.stop_alarm_button);
            stopAlarmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Stop the alarm
                    Intent stopAlarmIntent = new Intent(AlarmActivity.this, AlarmReceiver.class);
                    stopAlarmIntent.setAction("DISMISS");
                    sendBroadcast(stopAlarmIntent);

                    // Finish the activity
                    finish();
                }
            });
        }
    }

