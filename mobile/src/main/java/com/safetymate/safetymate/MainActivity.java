package com.safetymate.safetymate;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Intent;
import android.os.Bundle;
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
        db = new DBConnection(this);
        db.addThreshold(78);
        final Button button = (Button) findViewById(R.id.button_notify);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                int notificationId = 001;
                // Create an intent for the reply action
                Intent actionIntent = new Intent(getApplicationContext(), MainActivity.class);
                PendingIntent actionPendingIntent =
                        PendingIntent.getActivity(getApplicationContext(), 0, actionIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT);

                // Create the action
                NotificationCompat.Action action =
                        new NotificationCompat.Action.Builder(R.drawable.danger,
                                "Cancel", actionPendingIntent)
                                .build();
                long[] pattern = {0, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000};

                // Build the notification and add the action via WearableExtender
                Notification notification =
                        new NotificationCompat.Builder(getApplicationContext())
                                .setSmallIcon(R.drawable.danger)
                                .setContentTitle("Safety Mate")
                                .setContentText("Are you in Danger? Swipe right to stop sending notification")
                                .extend(new WearableExtender().addAction(action))
                                .setVibrate(pattern)
                                .build();
                // Get an instance of the NotificationManager service
                NotificationManagerCompat notificationManager =
                        NotificationManagerCompat.from(getApplicationContext());

                // Issue the notification with notification manager.
                notificationManager.notify(notificationId, notification);

            }
        });
    }


    public void testSqliteDB(View view)
    {
       db.getThreshold();
    }

}
