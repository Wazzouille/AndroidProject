package com.example.etu26132.e_night;

import android.app.Application;
import android.content.Context;

/**
 * Created by Degraux on 11-12-15.
 */
public class MonApp extends Application {
    private static MonApp instance;

    @Override
    public void onCreate(){
        super.onCreate();
        instance = this;
    }

    public static MonApp getInstance(){
        return instance;
    }

    public static Context getAppContext(){
        return instance.getApplicationContext();
    }
}
