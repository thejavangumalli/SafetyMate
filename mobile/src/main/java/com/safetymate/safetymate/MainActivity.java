package com.safetymate.safetymate;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.NotificationCompat.WearableExtender;

import com.google.android.gms.wearable.WearableListenerService;


public class MainActivity extends Activity {

    DBConnection db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*db = new DBConnection(this);
        db.addThreshold(78);
        final Button button = (Button) findViewById(R.id.button_notify);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


            }
        });*/
    }


    public void testSqliteDB(View view)
    {
       db.getThreshold();
    }

}
