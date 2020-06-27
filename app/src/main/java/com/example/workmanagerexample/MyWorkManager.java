package com.example.workmanagerexample;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorkManager extends Worker {
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
        return Result.success();
        
    }

    private void createNotificationChannel() {
        Context context = getApplicationContext();

        Intent intent = new Intent(context, MainActivity.class);
        intent.setAction(context.getString(R.string.app_name));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("Foreground Service")
                .setContentText("Notification")
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setContentIntent(pendingIntent)
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager == null) {
            return;
        }

        notificationManager.notify(1, notification);
        Log.d(TAG, "createNotificationChannel: ");

    }
}