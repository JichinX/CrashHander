package me.xujichang.lib.crash;

import android.app.Application;

import me.xujichang.lib.crash.handler.CrashHandler;

/**
 * Des:处理Crash
 *
 * @author xujichang
 * Created by xujichang on 2020/6/8.
 * Copyright (c) 2020 xujichang All rights reserved.
 */
public class CrashApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(this);
    }
}
