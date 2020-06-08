package me.xujichang.lib.crash.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import me.xujichang.lib.crash.R;
import me.xujichang.lib.crash.room.entity.CrashLog;

/**
 * Des:
 *
 * @author xujichang
 * Created by xujichang on 2020/6/8.
 * Copyright (c) 2020 xujichang All rights reserved.
 */
public class CrashListAdapter extends RecyclerView.Adapter<CrashListAdapter.Holder> {
    private List<CrashLog> mCrashLogs = new ArrayList<>();

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_crash_log, parent, false), mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bindData(mCrashLogs.get(position));
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

    public void setItemCallback(ItemClickListener pItemCallback) {
        mListener = pItemCallback;
    }

    static class Holder extends RecyclerView.ViewHolder {
        private ItemClickListener mListener;
        private TextView mMessage;
        private TextView mTime;

        public Holder(@NonNull View itemView, ItemClickListener pListener) {
            super(itemView);
            mListener = pListener;
            mMessage = itemView.findViewById(R.id.tv_crash_title);
            mTime = itemView.findViewById(R.id.tv_crash_time);
        }

        public void bindData(CrashLog pLog) {
            mMessage.setText(pLog.getExceptionMessage());
            mTime.setText(pLog.getTimeLabel());
            if (null != mListener) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onItemClick(pLog);
                    }
                });
            }

        }
    }

    private ItemClickListener mListener;

    public void setListener(ItemClickListener pListener) {
        mListener = pListener;
    }

    public interface ItemClickListener {
        void onItemClick(CrashLog pLog);
    }
}
