package com.safetymate.safetymate;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.WearableExtender;
import android.support.v4.app.NotificationManagerCompat;

/**
 * Created by RockerZ on 2/11/15.
 */
public class Alert{
    Context context;
    public Alert(Context context)
    {
        this.context = context;
    }
    public void sendNotification(){
        int notificationId = 001;
        // Create an intent for the cancel action
        Intent actionIntent = new Intent(this.context, CancelAlert.class);
        actionIntent.putExtra("notificationId",notificationId);
        PendingIntent actionPendingIntent =
                PendingIntent.getBroadcast(context, 0, actionIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
        // Create the action
        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(R.drawable.cancel,
                        "Cancel", actionPendingIntent)
                        .build();
        long[] pattern = {0, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000};

        // Build the notification and add the action via WearableExtender
        Notification notification =
                new NotificationCompat.Builder(this.context)
                        .setSmallIcon(R.drawable.danger)
                        .setContentTitle("Safety Mate")
                        .setContentText("Are you in Danger? Swipe right to stop sending notification")
                        .extend(new WearableExtender().addAction(action)) //Displays action only in wearable. Use .addAction to show buttons in phone too.
                        .setVibrate(pattern)
                        .setAutoCancel(true)
                        .setContentIntent(PendingIntent.getActivity(
                                                        this.context,
                                                        0,
                                                        new Intent(this.context,MainActivity.class),
                                                        PendingIntent.FLAG_UPDATE_CURRENT))
                        .setPriority(2) // high priority
                        .setVisibility(1) //public visibility
                        .build();
        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this.context);

        // Issue the notification with notification manager.
        notificationManager.notify(notificationId, notification);
    }
}
