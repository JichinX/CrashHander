package me.xujichang.lib.crash.room.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import me.xujichang.lib.crash.handler.CrashHandler;

/**
 * Des:崩溃信息
 *
 * @author xujichang
 * Created by xujichang on 2020/6/8.
 * Copyright (c) 2020 xujichang All rights reserved.
 */
@Entity(tableName = "tbl_crash_log")
public class CrashLog {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String mTimeLabel = "";
    private long mTimeMs;
    private String mAppVersion = "";
    private String mAppPackageName = "";
    private String mThreadName = "";
    private String mExceptionInfo = "";
    private String mActivities = "";
    private String mExtInfo = "";
    private String mDeviceInfo = "";
    private String mExceptionMessage = "";
    private boolean isRead = false;

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean pRead) {
        isRead = pRead;
    }

    public CrashLog(CrashHandler pHandler) {
        LocalDateTime vTime = LocalDateTime.now();
        mTimeLabel = getTimeStr(vTime);
        mTimeMs = vTime.toInstant(getZoneOffset()).toEpochMilli();
        mAppVersion = pHandler.getAppVersion();
        mAppPackageName = pHandler.getPackageName();
        mExceptionInfo = pHandler.getExceptionInfo();
        mActivities = pHandler.getActivities2str();
        mExtInfo = pHandler.getOtherInfo();
        mDeviceInfo = pHandler.getDeviceInfo();
        mExceptionMessage = pHandler.getExceptionMessage();
    }

    private String getTimeStr(LocalDateTime pTime) {
        DateTimeFormatter vFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return pTime.format(vFormatter);
    }

    public static ZoneOffset getZoneOffset() {
        Instant instant = Instant.now();
        ZoneId systemZone = ZoneId.systemDefault();
        return systemZone.getRules().getOffset(instant);
    }

    public CrashLog() {
    }

    public long getId() {
        return id;
    }

    public void setId(long pId) {
        id = pId;
    }

    public String getTimeLabel() {
        return mTimeLabel;
    }

    public void setTimeLabel(String pTimeLabel) {
        mTimeLabel = pTimeLabel;
    }

    public long getTimeMs() {
        return mTimeMs;
    }

    public void setTimeMs(long pTimeMs) {
        mTimeMs = pTimeMs;
    }

    public String getAppVersion() {
        return mAppVersion;
    }

    public void setAppVersion(String pAppVersion) {
        mAppVersion = pAppVersion;
    }

    public String getAppPackageName() {
        return mAppPackageName;
    }

    public void setAppPackageName(String pAppPackageName) {
        mAppPackageName = pAppPackageName;
    }

    public String getThreadName() {
        return mThreadName;
    }

    public void setThreadName(String pThreadName) {
        mThreadName = pThreadName;
    }

    public String getExceptionInfo() {
        return mExceptionInfo;
    }

    public void setExceptionInfo(String pExceptionInfo) {
        mExceptionInfo = pExceptionInfo;
    }

    public String getActivities() {
        return mActivities;
    }

    public void setActivities(String pActivities) {
        mActivities = pActivities;
    }

    public String getExtInfo() {
        return mExtInfo;
    }

    public void setExtInfo(String pExtInfo) {
        mExtInfo = pExtInfo;
    }

    public String getDeviceInfo() {
        return mDeviceInfo;
    }

    public void setDeviceInfo(String pDeviceInfo) {
        mDeviceInfo = pDeviceInfo;
    }

    public String getExceptionMessage() {
        return mExceptionMessage;
    }

    public void setExceptionMessage(String pExceptionMessage) {
        mExceptionMessage = pExceptionMessage;
    }
}
