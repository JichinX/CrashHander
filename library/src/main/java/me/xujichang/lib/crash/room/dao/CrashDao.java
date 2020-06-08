package me.xujichang.lib.crash.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import me.xujichang.lib.crash.room.entity.CrashLog;

/**
 * Des:
 *
 * @author xujichang
 * Created by xujichang on 2020/6/8.
 * Copyright (c) 2020 xujichang All rights reserved.
 */
@Dao
public abstract class CrashDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertCrash(CrashLog pLog);

    @Query("SELECT * FROM tbl_crash_log ORDER BY mTimeMs DESC")
    public abstract LiveData<List<CrashLog>> getCrashList();
}
