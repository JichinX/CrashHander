package me.xujichang.lib.crash.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import me.xujichang.lib.crash.room.entity.CrashLog;

/**
 * Des:
 *
 * @author xujichang
 * Created by xujichang on 2020/6/8.
 * Copyright (c) 2020 xujichang All rights reserved.
 */
public class CrashViewModel extends AndroidViewModel {

    private CrashLog mCurrentCrashLog;

    public CrashViewModel(@NonNull Application application) {
        super(application);
    }

    public void setCurrentCrashLog(CrashLog pCrashLog) {
        mCurrentCrashLog = pCrashLog;
    }

    public CrashLog getCurrentCrashLog() {
        return mCurrentCrashLog;
    }
}
