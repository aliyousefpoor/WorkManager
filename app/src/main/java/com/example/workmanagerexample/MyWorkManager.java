package com.example.workmanagerexample;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.concurrent.TimeUnit;


public class MyWorkManager extends Worker {
    private int count = 0;
    private static final String TAG = "MyWorkManager";
    public static final String CHANNEL_ID = "ForegroundServiceChannel";


    public MyWorkManager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        Log.d(TAG, "WorkManager: ");

    }

    @NonNull
    @Override
    public Result doWork() {
        //startWork();
        //Toast.makeText(getApplicationContext(), "Salam", Toast.LENGTH_LONG).show();

        Log.d(TAG, "doWork: ");
        savedata();
        createNotification();

        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(MyWorkManager.class)
                .setInitialDelay(5, TimeUnit.SECONDS).build();
        WorkManager.getInstance().enqueueUniqueWork("aaa", ExistingWorkPolicy.APPEND, oneTimeWorkRequest);


        return Result.success();

    }

    private void createNotification() {
        Context context = getApplicationContext();
        createNotificationChannel();
        Intent intent = new Intent(context, MainActivity.class);
        intent.setAction(context.getString(R.string.app_name));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(" Notification")
                .setContentText("Count:" + count)
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setContentIntent(pendingIntent)
                .build();


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager == null) {
            return;
        }

        notificationManager.notify(1, notification);
        // Toast.makeText(getApplicationContext(),"notification",Toast.LENGTH_LONG).show();
        Log.d(TAG, "createNotification: ");

    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT

            );
            NotificationManager manager = getApplicationContext().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);


        }
    }

    public void savedata(){
        final SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("counter", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        count = sharedPreferences.getInt("counter", count);
        count=count+4;
        editor.putInt("counter", count).commit();
    }
}