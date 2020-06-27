package com.example.workmanagerexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
                //Intent intent = new Intent(MainActivity.this,WorkManager.class);
                StartWorker();
            }
        });
    }

    static void StartWorker() {
        Log.d(TAG, "StartWorker: ");
        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(MyWorkManager.class, 3, TimeUnit.SECONDS).build();
        WorkManager.getInstance().enqueueUniquePeriodicWork("aaa", ExistingPeriodicWorkPolicy.REPLACE,periodicWorkRequest);

    }
}