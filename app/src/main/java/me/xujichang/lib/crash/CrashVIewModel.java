package me.xujichang.lib.crash;

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
public class CrashVIewModel extends AndroidViewModel {
    private CrashLog mCrashLog;

    public CrashVIewModel(@NonNull Application application) {
        super(application);
    }

    public void setCurrentDetail(CrashLog pLog) {
        mCrashLog = pLog;
    }

    public CrashLog getCrashLog() {
        return mCrashLog;
    }
}
