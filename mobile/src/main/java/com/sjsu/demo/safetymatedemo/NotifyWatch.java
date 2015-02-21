package com.sjsu.demo.safetymatedemo;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.WearableExtender;
import android.support.v4.app.NotificationManagerCompat;

import com.google.android.gms.location.DetectedActivity;

import java.util.Timer;

public class NotifyWatch {

    public void sendNotification(DetectedActivity userActivity){
        SafetyMate.getActivityDetector().StopDetection();
        Context context= SafetyMate.getContext();
        SafetyMate.setNotificationState(true);
        SafetyMate.setUserActivity(userActivity);
        //TODO Send alerts based on the activity
        int notificationId = 001;
        // Create an intent for the cancel action
        Intent actionIntent = new Intent(context, CancelAlert.class);
        actionIntent.putExtra("notificationId",notificationId);
        PendingIntent actionPendingIntent =
                PendingIntent.getBroadcast(context, 0, actionIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
        // Create the action
        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(R.mipmap.cancel,
                        "Cancel", actionPendingIntent)
                        .build();
        long[] pattern = {0, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000};

        // Build the notification and add the action via WearableExtender
        Notification notification =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.danger)
                        .setContentTitle("Safety Mate")
                        .setContentText("Are you in Danger? Swipe right to stop sending notification")
                        .extend(new WearableExtender().addAction(action)) //Displays action only in wearable. Use .addAction to show buttons in phone too.
                        .setVibrate(pattern)
                        .setAutoCancel(true)
                        .setContentIntent(PendingIntent.getActivity(
                                context,
                                0,
                                new Intent(context,MainActivity.class),
                                PendingIntent.FLAG_UPDATE_CURRENT))
                        .setPriority(2) // high priority
                        .setVisibility(1) //public visibility
                        .build();
        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(context);

        // Issue the notification with notification manager.
        notificationManager.notify(notificationId, notification);
        //TODO once notification is sent wait for few minutes before sending out other notification
        Timer timer = new Timer();
        timer.schedule(new SendAlert(notificationId),15*1000);
    }
}
