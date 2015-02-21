package com.sjsu.demo.safetymatedemo;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;


public class SendAlertService extends IntentService {


    public SendAlertService() {
        super("SendAlertService");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean isAlertSent = false;
        String messageToSend = null;
        try {
            Bundle extras = intent.getExtras();

            if (extras != null) {
                if (extras.containsKey("numArray")) {
                    String[] numArray = extras.getStringArray("numArray");
                    Toast.makeText(getBaseContext(), "count" + numArray.length, Toast.LENGTH_SHORT);
                    System.out.println(numArray.length);
                    try {
                        for (int i = 0; i < numArray.length; i++) {
                            SmsManager sms = SmsManager.getDefault();
                            double latitude = extras.getDouble("latitude");
                            double longitude = extras.getDouble("longitude");
                            String loc = latitude +","+longitude;
                            if (loc != null) {
                                messageToSend = new String("Alert! I'm in trouble! I'm at " + loc + "\n" + "http://maps.google.com/maps?q=loc:" + loc);
                            } else {
                                messageToSend = "Alert! I'm in trouble";
                            }
                            ArrayList<String> msgParts = sms.divideMessage(messageToSend);
                            sms.sendMultipartTextMessage(numArray[i], null, msgParts, null, null);
                            Toast.makeText(getBaseContext(), "SMS Sent to " + numArray[i], Toast.LENGTH_SHORT).show();
                            isAlertSent = true;

                        }

                    } catch (Exception e) {
                        Toast.makeText(getBaseContext(), "SMS could not be sent", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                        isAlertSent = false;
                    }
                    if(isAlertSent){
                        Log.d("Jeena","Building notification");
                        NotificationCompat.WearableExtender wearableExtender =
                                new NotificationCompat.WearableExtender()
                                        .setHintShowBackgroundOnly(true);

                        Notification notification =
                                new NotificationCompat.Builder(this)
                                        .setSmallIcon(R.mipmap.ic_launcher)
                                        .setContentTitle("SafetyMate")
                                        .setContentText("Alert message sent to Emergency Contacts!")
                                        .extend(wearableExtender)
                                        .build();

                        NotificationManagerCompat notificationManager =
                                NotificationManagerCompat.from(this);

                        int notificationId = 1;
                        notificationManager.notify(notificationId, notification);
                    }
                }


            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Toast.makeText(getBaseContext(), "On Handle Intent", Toast.LENGTH_SHORT).show();

    }
}
