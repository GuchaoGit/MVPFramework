package com.guc.mvpframework;

import android.app.Application;
import android.content.Context;

/**
 * Created by guc on 2018/9/5.
 * 描述：
 */
public class MyApp extends Application {
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }
}
