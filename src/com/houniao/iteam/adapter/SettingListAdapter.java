package com.houniao.iteam.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.houniao.iteam.R;
import com.houniao.iteam.bean.Setting;

import java.net.InetAddress;
import java.util.List;

/**
 * Created by Administrator on 13-11-22.
 */
public class SettingListAdapter extends ArrayAdapter<Setting> {
    private int resourceId;

    public SettingListAdapter(Context context, int resource) {
        super(context, resource);
        this.resourceId = resource;
    }

    public SettingListAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
        this.resourceId = resource;
    }

    public SettingListAdapter(Context context, int resource, Setting[] objects) {
        super(context, resource, objects);
        this.resourceId = resource;
    }

    public SettingListAdapter(Context context, int resource, int textViewResourceId, Setting[] objects) {
        super(context, resource, textViewResourceId, objects);
        this.resourceId = resource;
    }

    public SettingListAdapter(Context context, int resource, List<Setting> objects) {
        super(context, resource, objects);
        this.resourceId = resource;
    }

    public SettingListAdapter(Context context, int resource, int textViewResourceId, List<Setting> objects) {
        super(context, resource, textViewResourceId, objects);
        this.resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Setting setting = getItem(position);
        RelativeLayout settingItemLayout = new RelativeLayout(getContext());
        if (getCount() == 1) {
            settingItemLayout.setBackgroundResource(R.drawable.list_item_alone);
        } else if (position == 0) {
            settingItemLayout.setBackgroundResource(R.drawable.list_item_top);
        } else if (position == (getCount() - 1)) {
            settingItemLayout.setBackgroundResource(R.drawable.list_item_bottom);
        } else {
            settingItemLayout.setBackgroundResource(R.drawable.list_item_center);
        }
        String inflater = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater vi = (LayoutInflater) getContext().getSystemService(inflater);
        vi.inflate(resourceId, settingItemLayout, true);
        TextView settingName = (TextView) settingItemLayout.findViewById(R.id.setting_name);
        settingName.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "ionicons.ttf"));
        settingName.setText(setting.getSettingName());
        TextView rightIcon = (TextView) settingItemLayout.findViewById(R.id.right_icon);
        rightIcon.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "ionicons.ttf"));
        return settingItemLayout;
    }
}
