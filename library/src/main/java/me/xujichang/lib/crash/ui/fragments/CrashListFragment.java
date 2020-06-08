package me.xujichang.lib.crash.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import me.xujichang.lib.crash.R;
import me.xujichang.lib.crash.handler.CrashHandler;
import me.xujichang.lib.crash.room.entity.CrashLog;
import me.xujichang.lib.crash.ui.CrashViewModel;
import me.xujichang.lib.crash.ui.adapters.CrashListAdapter;

/**
 *
 */
public class CrashListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private CrashListAdapter mAdapter;
    private CrashViewModel mViewModel;

    public CrashListFragment() {
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
        CrashHandler
                .getInstance()
                .getLogLiveData()
                .observe(getViewLifecycleOwner(), new Observer<List<CrashLog>>() {
                    @Override
                    public void onChanged(List<CrashLog> pCrashLogs) {
                        mAdapter.changeList(pCrashLogs);
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vView = inflater.inflate(R.layout.fragment_crash_list, container, false);
        initView(vView);
        return vView;
    }

    private void initView(View pView) {
        mRecyclerView = pView.findViewById(R.id.rv_crash);

        mAdapter = new CrashListAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setItemCallback(new CrashListAdapter.ItemClickListener() {
            @Override
            public void onItemClick(CrashLog pLog) {
                mViewModel.setCurrentCrashLog(pLog);
                toDetail();
            }
        });
    }

    private void toDetail() {
        NavHostFragment.findNavController(this).navigate(R.id.action_to_crashDetailFragment);
    }
}