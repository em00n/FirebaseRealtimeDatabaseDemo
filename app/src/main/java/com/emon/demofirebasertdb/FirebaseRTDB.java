package com.emon.demofirebasertdb;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class FirebaseRTDB extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
