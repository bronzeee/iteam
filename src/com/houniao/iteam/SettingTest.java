package com.houniao.iteam;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 13-11-25.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SettingTest extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting_test);
    }
}
