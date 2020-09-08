package com.sita.android.sitaproject.Models;

import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDexApplication;
import com.sita.android.sitaproject.Activities.LoginPage;

public class MyApplication extends MultiDexApplication {

    Context context;
    @Override
    public void onCreate() {

        super.onCreate();
        context = this;
        SafetyMethod();
    }

    private void SafetyMethod() {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                Intent intent = new Intent(getApplicationContext(),LoginPage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}
