package com.isd.capacitor.plugins;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import java.time.Instant;

public class PedometerService extends Service implements SensorEventListener {
    int steps = 0;
    long lastUpdate = Instant.now().getEpochSecond();

    SensorManager sensorManager;
    Sensor stepCounterSensor;
    Sensor stepDetectorSensor;

    private PedometerPlugin mPlugin = null;
    private boolean bounded = false;

    private final static String CHANNEL_NAME = "pedometer_service_channel";
    private final static String NOTIFICATION_TITLE = "Step Tracker";
    private final static int IMPORTANCE = NotificationManager.IMPORTANCE_MIN;
    private final static int NOTIFICATION_ID = 312;
    private final static int SENSOR_DELAY = SensorManager.SENSOR_DELAY_UI;

    private final IBinder binder = new MyBinder();

    public class MyBinder extends Binder {
        PedometerService getService() {
            return PedometerService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("Service", "Created");

        Notification notification = getNotification();

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        stepDetectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        sensorManager.registerListener(this, stepCounterSensor, SENSOR_DELAY);
        sensorManager.registerListener(this, stepDetectorSensor, SENSOR_DELAY);

        startForeground(NOTIFICATION_ID, notification);
    }

    // should not be called.
    // use bindService instead.
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("Service", "Started");

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.i("Service", "Destroyed!");
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            newSteps = (int) event.values[0];
            dSteps = newSteps - steps;
            steps = newSteps;
            long startTime = lastUpdate;
            lastUpdate = event.timestamp;
            Log.i("Service", "onSensorChanged " + steps);
    
            if (isBounded()) {
                mPlugin.fireSteps(dSteps, startTime, lastUpdate);
            }
        }
    }

    public void setCallback(PedometerPlugin plugin) {
        mPlugin = plugin;
        bounded = true;
    }

    public void removeCallback() {
        mPlugin = null;
        bounded = false;
    }

    public boolean isBounded() {
        return bounded;
    }

    public int getSteps() {
        return steps;
    }

    // should not be called.
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    private Notification getNotification() {
        String channel = createChannel();

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, channel)
                .setSmallIcon(android.R.drawable.ic_menu_mylocation).setContentTitle(NOTIFICATION_TITLE);

        // Uncomment to remove visible notification.
        // mBuilder.setVisibility(NotificationCompat.VISIBILITY_SECRET);

        return mBuilder
                .setPriority(IMPORTANCE)
                .setCategory(Notification.CATEGORY_SERVICE)
                .setNumber(0)
                .build();
    }

    private synchronized String createChannel() {
        NotificationManager mNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationChannel mChannel = new NotificationChannel(CHANNEL_NAME, NOTIFICATION_TITLE, IMPORTANCE);

        mChannel.enableLights(true);
        mChannel.setLightColor(Color.BLUE);
        if (mNotificationManager != null) {
            mNotificationManager.createNotificationChannel(mChannel);
        } else {
            stopSelf();
        }
        return CHANNEL_NAME;
    }
}
