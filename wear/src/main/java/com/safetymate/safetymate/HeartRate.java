package com.safetymate.safetymate;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.widget.TextView;

public class HeartRate extends Activity implements SensorEventListener {

    private TextView mTextView;
    Sensor mHeartRateSensor;
    SensorManager mSensorManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_rate);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stubnew);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
            }
        });
        mSensorManager = ((SensorManager)getSystemService(SENSOR_SERVICE));
        mHeartRateSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Register the listener
        if (mSensorManager != null){
            mSensorManager.registerListener(this, mHeartRateSensor, 2);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Unregister the listener
        if (mSensorManager!=null)
            mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        System.out.print("On Sensor Changed"+event.values[0]);
    //Update your data. This check is very raw. You should improve it when the sensor is unable to calculate the heart rate
        if (event.sensor.getType() == Sensor.TYPE_HEART_RATE) {
            if ((int)event.values[0]>0) {
                mTextView.setText("" + (int) event.values[0]);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d("test", "accuracy changed: " + accuracy);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this);
    }

}
