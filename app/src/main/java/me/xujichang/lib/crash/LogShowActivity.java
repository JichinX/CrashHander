package me.xujichang.lib.crash;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import me.xujichang.lib.crash.databinding.ActivityShowLogBinding;
import me.xujichang.lib.crash.handler.CrashHandler;
import me.xujichang.lib.crash.room.entity.CrashLog;

/**
 * Des:显示Log
 *
 * @author xujichang
 * Created by xujichang on 2020/6/8.
 * Copyright (c) 2020 xujichang All rights reserved.
 */
public class LogShowActivity extends AppCompatActivity {
    private ActivityShowLogBinding mBinding;
    private CrashLogAdapter mLogAdapter;
    private CrashVIewModel mModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityShowLogBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        initRv();
        initData();
        initViewModel();
    }

    private void initViewModel() {
        mModel = ViewModelProviders.of(this).get(CrashVIewModel.class);

    }

    private void initRv() {
        mLogAdapter = new CrashLogAdapter();
        mLogAdapter.setDetailCallback(new CrashLogAdapter.DetailCallback() {
            @Override
            public void onDetail(CrashLog pLog) {
                startToDetail(pLog);
            }
        });
        RecyclerView vRecyclerView = mBinding.rvLogs;
        vRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        vRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        vRecyclerView.setAdapter(mLogAdapter);
    }

    private void startToDetail(CrashLog pLog) {
            mModel.setCurrentDetail(pLog);
            startActivity(new Intent(this,LogDetailActivity.class));
    }

    private void initData() {
        CrashHandler
                .getInstance()
                .getLogLiveData()
                .observe(this, new Observer<List<CrashLog>>() {
                    @Override
                    public void onChanged(List<CrashLog> pCrashLogs) {
                        mLogAdapter.changeList(pCrashLogs);
                    }
                });

    }
}
