package com.safetymate.safetymate;
import android.util.Log;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.WearableListenerService;

public class WearService extends WearableListenerService {
    Alert alert;
    @Override
    public void onCreate() {
        super.onCreate();
        alert = new Alert(getApplicationContext());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        super.onDataChanged(dataEvents);
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);
        Log.i(WearService.class.getSimpleName(), "heartrate received " + messageEvent.getPath());
        if(Float.parseFloat(messageEvent.getPath()) > 75)
        {
            alert.sendNotification();
        }
    }

    @Override
    public void onPeerConnected(Node peer) {
        super.onPeerConnected(peer);
        Log.i(WearService.class.getSimpleName(), "Watch Connected ");
    }

    @Override
    public void onPeerDisconnected(Node peer) {
        super.onPeerDisconnected(peer);
        Log.i(WearService.class.getSimpleName(), "Watch Disconnected");
    }
}
