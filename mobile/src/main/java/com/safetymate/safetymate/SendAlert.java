package com.safetymate.safetymate;

import android.content.Context;
import android.support.v4.app.NotificationManagerCompat;
import java.util.TimerTask;

public class SendAlert extends TimerTask {
    Context context;
    int notificationId;
    public SendAlert(int notificationId)
    {
        this.context = SafetyMate.getContext();
        this.notificationId = notificationId;
    }
    @Override
    public void run() {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(notificationId);
        //TODO store values in the database
        //Send messages to emergency contacts
        /*
        Notification notification =
                new NotificationCompat.Builder(this.context)
                        .setContentTitle("Safety Mate")
                        .setContentText("Message sent to emergency contacts")
                        .setAutoCancel(true)
                        .setVisibility(1) //public visibility
                        .build();
        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(context);

        // Issue the notification with notification manager.
        notificationManager.notify(2, notification);
         */
        //Start Navigation
    }
}
