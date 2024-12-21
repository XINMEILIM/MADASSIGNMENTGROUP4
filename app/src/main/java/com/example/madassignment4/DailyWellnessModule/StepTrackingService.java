package com.example.madassignment4.DailyWellnessModule;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.madassignment4.Database.DatabaseHelper;
import com.example.madassignment4.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StepTrackingService extends Service implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private DatabaseHelper dbHelper;

    private int stepCount = 0;
    private float distanceWalked = 0;
    private float caloriesBurned = 0;
    private long lastStepTime = 0;

    private static final long DEBOUNCE_TIME = 300; // Minimum time between steps in milliseconds

    @Override
    public void onCreate() {
        super.onCreate();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        dbHelper = new DatabaseHelper(this);

        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(1, createNotification());
        return START_STICKY;
    }

    private Notification createNotification() {
        NotificationChannel channel = new NotificationChannel(
                "StepTrackingChannel",
                "Step Tracking",
                NotificationManager.IMPORTANCE_LOW
        );
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);

        return new Notification.Builder(this, "StepTrackingChannel")
                .setContentTitle("Step Tracking")
                .setContentText("Tracking your steps in the background.")
                .setSmallIcon(R.drawable.running_man)
                .build();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);
        saveDailyStepData();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            long currentTime = System.currentTimeMillis();

            if ((Math.abs(x) > 5 || Math.abs(y) > 5 || Math.abs(z) > 5) &&
                    (currentTime - lastStepTime > DEBOUNCE_TIME)) {
                lastStepTime = currentTime;
                stepCount++;
                distanceWalked += 0.762f; // Average step length in meters
                caloriesBurned += 0.04f;  // Calories burned per step
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    private void saveDailyStepData() {
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String userId = "testUser"; // Replace with actual user ID

        dbHelper.saveDailyStepTracking(userId, currentDate, stepCount, distanceWalked, caloriesBurned);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

