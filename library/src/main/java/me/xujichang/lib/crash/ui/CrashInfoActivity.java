package me.xujichang.lib.crash.ui;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import me.xujichang.lib.crash.R;

/**
 * Des:
 *
 * @author xujichang
 * Created by xujichang on 2020/6/8.
 * Copyright (c) 2020 xujichang All rights reserved.
 */
public class CrashInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash_info);
    }
}
