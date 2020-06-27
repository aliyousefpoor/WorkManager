package com.example.workmanagerexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ");
                StartWorker();
                Toast.makeText(getApplicationContext(), "Started", Toast.LENGTH_LONG).show();
            }
        });
    }

    static void StartWorker() {
        Log.d(TAG, "StartWorker: ");
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(MyWorkManager.class)
                .setInitialDelay(5, TimeUnit.SECONDS).build();
        WorkManager.getInstance().enqueueUniqueWork("aaa", ExistingWorkPolicy.APPEND, oneTimeWorkRequest);

    }
}