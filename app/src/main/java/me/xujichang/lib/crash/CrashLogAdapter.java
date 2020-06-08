package me.xujichang.lib.crash;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import me.xujichang.lib.crash.databinding.ItemCrashLogBinding;
import me.xujichang.lib.crash.room.entity.CrashLog;

/**
 * Des:
 *
 * @author xujichang
 * Created by xujichang on 2020/6/8.
 * Copyright (c) 2020 xujichang All rights reserved.
 */
public class CrashLogAdapter extends RecyclerView.Adapter<CrashLogAdapter.Holder> {
    private List<CrashLog> mCrashLogs = new ArrayList<>();

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemCrashLogBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bindData(mCrashLogs.get(position), mDetailCallback);
    }

    @Override
    public int getItemCount() {
        return mCrashLogs.size();
    }

    public void changeList(List<CrashLog> pCrashLogs) {
        mCrashLogs.clear();
        mCrashLogs.addAll(pCrashLogs);
        notifyDataSetChanged();
    }

    public static class Holder extends RecyclerView.ViewHolder {
        private ItemCrashLogBinding mLogBinding;
        private CrashLog mCrashLog;
        private DetailCallback mDetailCallback;

        public Holder(@NonNull View itemView) {
            super(itemView);
        }

        public Holder(ItemCrashLogBinding pInflate) {
            super(pInflate.getRoot());
            mLogBinding = pInflate;
        }

        public void bindData(CrashLog pCrashLog, DetailCallback pDetailCallback) {
            mCrashLog = pCrashLog;
            mDetailCallback = pDetailCallback;
            mLogBinding.tvCrashTime.setText(pCrashLog.getTimeLabel());
            mLogBinding.tvCrashTitle.setText(pCrashLog.getExceptionMessage());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mDetailCallback) {
                        mDetailCallback.onDetail(mCrashLog);
                    }
                }
            });
        }
    }

    private DetailCallback mDetailCallback;

    public void setDetailCallback(DetailCallback pDetailCallback) {
        mDetailCallback = pDetailCallback;
    }

    public interface DetailCallback {
        void onDetail(CrashLog pLog);
    }
}
