package me.xujichang.lib.crash.room;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import me.xujichang.lib.crash.room.dao.CrashDao;
import me.xujichang.lib.crash.room.entity.CrashLog;

/**
 * Des:存储崩溃日志
 *
 * @author xujichang
 * Created by xujichang on 2020/6/8.
 * Copyright (c) 2020 xujichang All rights reserved.
 */
@Database(entities = {CrashLog.class}, version = 1, exportSchema = false)
public abstract class CrashDatabase extends RoomDatabase {
    private static CrashDatabase instance;

    public static CrashDatabase getInstance(Application pApplication) {
        if (null == instance) {
            instance = create(pApplication);
        }
        return instance;
    }

    private static CrashDatabase create(Application pApplication) {

        return Room.databaseBuilder(pApplication, CrashDatabase.class, "db_crash")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

    public abstract CrashDao crashDao();
}
