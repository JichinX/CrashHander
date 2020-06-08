package me.xujichang.lib.crash.handler;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.xujichang.lib.crash.room.CrashDatabase;
import me.xujichang.lib.crash.room.entity.CrashLog;

/**
 * Des:替换系统的异常处理
 *
 * @author xujichang
 * Created by xujichang on 2020/6/8.
 * Copyright (c) 2020 xujichang All rights reserved.
 */
public class CrashHandler extends SimpleActivityLifecycleCallbacks implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "CrashHandler";
    private static final String HAS_NEW_CRASH = "has_new_crash";
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private Application mApplication;
    private String mDeviceInfo;
    private String mAppName;
    private String mPackageName;
    private String mAppVersion;
    private String mExceptionInfo;
    private String mOtherInfo;
    private List<Activity> mActivities;
    private File mCrashFile;
    private CrashDatabase mDatabase;
    private ExecutorService mExecutorService;
    private String mExceptionMessage;
    private SharedPreferences mPreferences;

    private CrashHandler() {
        mActivities = new ArrayList<>();
        mExecutorService = Executors.newSingleThreadExecutor();
    }

    public static CrashHandler getInstance() {
        return ClassHolder.instance;
    }

    public String getActivities2str() {
        StringBuilder vBuilder = new StringBuilder();
        for (Activity vActivity : mActivities) {
            vBuilder.append(vActivity.getLocalClassName()).append("\n");
        }
        return vBuilder.toString();
    }

    public LiveData<List<CrashLog>> getLogLiveData() {
        updateFlag(false);
        return mDatabase.crashDao().getCrashList();
    }

    /**
     * 检测是否有新的Crash信息
     *
     * @return
     */
    public boolean checkNewCrash() {
        return mPreferences.getBoolean(HAS_NEW_CRASH, false);
    }

    private static class ClassHolder {
        public static CrashHandler instance = new CrashHandler();
    }

    public void init(Application pApplication) {
        mDatabase = CrashDatabase.getInstance(pApplication);
        mApplication = pApplication;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        mPreferences = PreferenceManager.getDefaultSharedPreferences(pApplication);
        Thread.setDefaultUncaughtExceptionHandler(this);
        pApplication.registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {

        if (!handleException(t, e) && null != mDefaultHandler) {
            mDefaultHandler.uncaughtException(t, e);
        } else {
            try {
                Thread.sleep(500);
            } catch (InterruptedException pE) {
                pE.printStackTrace();
            }
            exit();
        }
    }

    private void exit() {
        exitAllActivity();
        Process.killProcess(Process.myPid());
        System.exit(1);
    }

    private void exitAllActivity() {
        for (Activity vActivity : mActivities) {
            vActivity.finish();
        }
    }

    /**
     * 自定义处理
     *
     * @param pT
     * @param pE
     * @return
     */
    private boolean handleException(Thread pT, Throwable pE) {
        if (null == pE) {
            return false;
        }
        mDeviceInfo = collectionDeviceInfo();
        collectionAppInfo(mApplication);
        mExceptionMessage = pE.getLocalizedMessage();
        mExceptionInfo = collectionExceptionInfo(pT, pE);
        mOtherInfo = collectionOtherInfo();
        flush();
        return true;
    }

    private void flush() {
        flushLog(this);
    }

    private void flushLog(CrashHandler pHandler) {
        Log.i(TAG, "flushLog: " + pHandler);
        CrashDatabase vDatabase = pHandler.mDatabase;
        CrashLog vLog = new CrashLog(pHandler);
        vDatabase.crashDao().insertCrash(vLog);
        updateFlag(true);
    }

    private void updateFlag(boolean pNew) {
        mPreferences.edit().putBoolean(HAS_NEW_CRASH, pNew).apply();
    }

    private String collectionOtherInfo() {
        return "";
    }

    private String collectionExceptionInfo(Thread pT, Throwable pE) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        pE.printStackTrace(printWriter);
        for (Throwable cause = pE.getCause(); cause != null; cause = cause.getCause()) {
            cause.printStackTrace(printWriter);
        }
        printWriter.close();
        return writer.toString();
    }

    private void collectionAppInfo(Application pApplication) {
        if (null != pApplication) {
            mPackageName = pApplication.getPackageName();
            AppInfoManager vManager = new AppInfoManager(pApplication);
            mAppName = vManager.getAppName();
            mAppVersion = vManager.getAppVersion();
        }
    }

    private String collectionDeviceInfo() {
        Field[] vFields = Build.class.getDeclaredFields();
        StringBuilder vBuilder = new StringBuilder();
        for (Field vField : vFields) {
            try {
                vField.setAccessible(true);
                vBuilder.append(vField.getName()).append(" : ").append(vField.get(null)).append("\n");
            } catch (IllegalAccessException pE) {
                pE.printStackTrace();
            }
        }
        vBuilder.append("SDK : ").append(Build.VERSION.SDK_INT).append("\n");
        vBuilder.append("RELEASE : ").append(Build.VERSION.RELEASE).append("\n");
        return vBuilder.toString();
    }

    public String getDeviceInfo() {
        return mDeviceInfo;
    }

    public void setDeviceInfo(String pDeviceInfo) {
        mDeviceInfo = pDeviceInfo;
    }

    public String getAppName() {
        return mAppName;
    }

    public void setAppName(String pAppName) {
        mAppName = pAppName;
    }

    public String getPackageName() {
        return mPackageName;
    }

    public void setPackageName(String pPackageName) {
        mPackageName = pPackageName;
    }

    public String getAppVersion() {
        return mAppVersion;
    }

    public void setAppVersion(String pAppVersion) {
        mAppVersion = pAppVersion;
    }

    public String getExceptionInfo() {
        return mExceptionInfo;
    }

    public void setExceptionInfo(String pExceptionInfo) {
        mExceptionInfo = pExceptionInfo;
    }

    public String getOtherInfo() {
        return mOtherInfo;
    }

    public void setOtherInfo(String pOtherInfo) {
        mOtherInfo = pOtherInfo;
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        mActivities.add(activity);
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        mActivities.remove(activity);
    }

    private class FlushRunnable implements Runnable {
        private CrashHandler mCrashHandler;

        public FlushRunnable(CrashHandler pCrashHandler) {
            mCrashHandler = pCrashHandler;
        }

        @Override
        public void run() {
            flushLog(mCrashHandler);
        }
    }

    @Override
    public String toString() {
        return "CrashHandler{" +
                "mDefaultHandler=" + mDefaultHandler +
                ", mApplication=" + mApplication +
                ", mDeviceInfo='" + mDeviceInfo + '\'' +
                ", mAppName='" + mAppName + '\'' +
                ", mPackageName='" + mPackageName + '\'' +
                ", mAppVersion='" + mAppVersion + '\'' +
                ", mExceptionInfo='" + mExceptionInfo + '\'' +
                ", mOtherInfo='" + mOtherInfo + '\'' +
                ", mActivities=" + mActivities +
                ", mCrashFile=" + mCrashFile +
                ", mDatabase=" + mDatabase +
                '}';
    }

    public String getExceptionMessage() {
        return mExceptionMessage;
    }
}
