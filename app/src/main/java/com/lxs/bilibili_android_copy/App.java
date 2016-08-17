package com.lxs.bilibili_android_copy;

import android.app.Application;
import android.content.Context;

import com.lxs.bilibili_android_copy.utils.PersistTool;

public class App extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        if (!PersistTool.IsInited()) {
            PersistTool.init(this);
        }

    }

    public static Context getContext(){
        return mContext;
    }
}
