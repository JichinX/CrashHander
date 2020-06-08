package me.xujichang.lib.crash.handler;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

/**
 * Des:
 *
 * @author xujichang
 * Created by xujichang on 2020/6/8.
 * Copyright (c) 2020 xujichang All rights reserved.
 */
public class AppInfoManager {
    private ApplicationInfo mApplicationInfo;
    private PackageInfo mPackageInfo;
    private PackageManager mPackageManager;

    public AppInfoManager(Context pContext) {
        mPackageManager = pContext.getPackageManager();
        try {
            mApplicationInfo = mPackageManager.getApplicationInfo(pContext.getPackageName(), 0);
            mPackageInfo = mPackageManager.getPackageInfo(pContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException pE) {
            pE.printStackTrace();
        }
    }

    public String getAppVersion() {
        return mPackageInfo.versionName;
    }

    public String getAppName() {
        return mApplicationInfo.loadLabel(mPackageManager).toString();
    }
}
