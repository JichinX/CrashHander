package me.xujichang.lib.crash;

import android.Manifest;
import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.sample.breakpad.BreakpadInit;

import java.io.File;


/**
 * Des:
 *
 * @author xujichang
 * Created by xujichang on 2020/6/6.
 * Copyright (c) 2020 xujichang All rights reserved.
 */
public class App extends CrashApplication {
    private File externalReportPath;

    @Override
    public void onCreate() {
        super.onCreate();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
        } else {
            initExternalReportPath();
            BreakpadInit.initBreakpad(externalReportPath.getAbsolutePath());
        }
    }

    private void initExternalReportPath() {
        externalReportPath = new File(getExternalCacheDir(), "crashDump");
        if (!externalReportPath.exists()) {
            externalReportPath.mkdirs();
        }
    }
}
