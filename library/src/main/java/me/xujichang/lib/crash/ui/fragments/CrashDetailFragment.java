package me.xujichang.lib.crash.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.xujichang.lib.crash.R;
import me.xujichang.lib.crash.databinding.FragmentCrashDetailBinding;
import me.xujichang.lib.crash.room.entity.CrashLog;
import me.xujichang.lib.crash.ui.CrashViewModel;

/**
 *
 */
public class CrashDetailFragment extends Fragment {

    private CrashViewModel mViewModel;
    private FragmentCrashDetailBinding mBinding;

    public CrashDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(requireActivity()).get(CrashViewModel.class);
        CrashLog vLog = mViewModel.getCurrentCrashLog();
        patch(vLog);
    }

    private void patch(CrashLog pLog) {
        mBinding.tvAppInfo.setText(pLog.getTimeLabel());
        mBinding.tvCrashInfo.setText(pLog.getExceptionInfo());
        mBinding.tvDeviceInfo.setText(pLog.getDeviceInfo());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentCrashDetailBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }
}