package com.houniao.iteam;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.houniao.iteam.adapter.SettingListAdapter;
import com.houniao.iteam.bean.Setting;
import com.houniao.iteam.service.CustomServiceAction;

/**
 * Created by Administrator on 13-11-22.
 */
public class SettingFragment extends Fragment {
    private LocalBroadcastManager mLocalBroadcastManager;
    private BroadcastReceiver mReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mLocalBroadcastManager = LocalBroadcastManager.getInstance(getActivity());

        Log.i("CommunicateService", "start filter");
        IntentFilter filter = new IntentFilter();/*
        filter.addAction(CustomServiceAction.ACTION_STARTED);
        filter.addAction(CustomServiceAction.ACTION_STOPPED);*/
        filter.addAction(CustomServiceAction.ACTION_UPDATE);
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getAction()) {
                    case CustomServiceAction.ACTION_UPDATE: {
                        Log.i("CommunicateService", intent.getIntExtra("value", 0) + "");
                        break;
                    }
                }
            }
        };
        mLocalBroadcastManager.registerReceiver(mReceiver, filter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_layout, container, false);
        ListView list = (ListView) view.findViewById(R.id.list);
        ArrayAdapter<com.houniao.iteam.bean.Setting> adapter = new SettingListAdapter(getActivity(), R.layout.setting_list_layout);

        adapter.add(new Setting() {{
            setSettingName(R.string.setting1);
        }});

        adapter.add(new Setting() {{
            setSettingName(R.string.setting2);
        }});

        adapter.add(new Setting() {{
            setSettingName(R.string.setting3);
        }});

        list.setAdapter(adapter);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocalBroadcastManager.unregisterReceiver(mReceiver);
    }
}
