package com.houniao.iteam;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.*;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.*;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.*;
import com.baidu.mapapi.BMapManager;
import com.houniao.iteam.service.CommunicateService;

public class TeamMainActivity extends FragmentActivity {
    private ViewPager mPager;
    private ImageView ivBottomLine;
    private ArrayList<LinearLayout> textViews = new ArrayList<LinearLayout>();
    private ArrayList<Integer> positions = new ArrayList<Integer>();
    private Class<Fragment>[] tabParams = new Class[]{
            TestFragment.class,
            TestFragment.class,
            MapFragment.class,
            SettingFragment.class
    };
    private int[] itemNames = new int[]{
            R.string.team,
            R.string.message,
            R.string.location,
            R.string.setting
    };
    private int[] icons = new int[]{
            R.string.people,
            R.string.chatboxes,
            R.string.earth,
            R.string.gear
    };
    private int currIndex = 0;
    private Resources resources;
    private LocalBroadcastManager mLocalBroadcastManager;
    private BroadcastReceiver mReceiver;
    private PopupWindow closeWindow;

    BMapManager mBMapMan = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mBMapMan = new BMapManager(getApplication());
        mBMapMan.init("827550e53162134a2973e545099b0ede", null);
        setContentView(R.layout.activity_team_main);
        resources = getResources();
        //界面初始化
        init();

        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        //启动service

        if (!isServiceRunning()) {
            startService(new Intent(this, CommunicateService.class));
        }
        //stopService(new Intent(TeamMainActivity.this, CommunicateService.class));
    }

    private boolean isServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (CommunicateService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void init() {
        mPager = (ViewPager) findViewById(R.id.vPager);
        ArrayList<Fragment> fragmentsList = new ArrayList<Fragment>();
        TextView textView;
        LinearLayout textViewPanel = (LinearLayout) findViewById(R.id.textViewPanel);

        ivBottomLine = (ImageView) findViewById(R.id.iv_bottom_line);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels / itemNames.length;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dm.widthPixels / itemNames.length - 20, 2);
        params.setMargins(10, 0, 0, 0);
        ivBottomLine.setLayoutParams(params);
        for (int i = 0; i < itemNames.length; i++) {
            LinearLayout relativeLayout = new LinearLayout(this);
            relativeLayout.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.weight = 1;
            relativeLayout.setLayoutParams(layoutParams);
            relativeLayout.setOrientation(LinearLayout.VERTICAL);
            final Typeface tf = Typeface.createFromAsset(getAssets(), "ionicons.ttf");
            TextView imageView = new TextView(this);
            imageView.setText(icons[i]);
            imageView.setTypeface(tf);
            imageView.setTextSize(32);
            imageView.setGravity(Gravity.CENTER);
            imageView.setTag("ImageView");
            imageView.setTextColor(resources.getColor(i == currIndex ? R.color.lightWhite : R.color.white));
            imageView.setId(0x7f059900 + i);
            relativeLayout.addView(imageView);
            textView = new TextView(this);
            textView.setText(resources.getText(itemNames[i]));
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(resources.getColor(i == currIndex ? R.color.lightWhite : R.color.white));
            textView.setTextSize(12);
            textView.setTag("TextView");
            relativeLayout.addView(textView);
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPager.setCurrentItem(textViews.indexOf(view));
                }
            });
            textViewPanel.addView(relativeLayout);
            textViews.add(relativeLayout);
            positions.add(width * i);
            try {
                fragmentsList.add(tabParams[i].newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        mPager.setAdapter(new CustomFragmentPagerAdapter(getSupportFragmentManager(), fragmentsList));
        mPager.setCurrentItem(currIndex);
        mPager.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {
                Animation animation = new TranslateAnimation(positions.get(currIndex), positions.get(i), 0, 0);
                ((TextView) textViews.get(i).findViewWithTag("TextView")).setTextColor(resources.getColor(R.color.lightWhite));
                ((TextView) textViews.get(currIndex).findViewWithTag("TextView")).setTextColor(resources.getColor(R.color.white));
                ((TextView) textViews.get(i).findViewWithTag("ImageView")).setTextColor(resources.getColor(R.color.lightWhite));
                ((TextView) textViews.get(currIndex).findViewWithTag("ImageView")).setTextColor(resources.getColor(R.color.white));
                currIndex = i;
                animation.setFillAfter(true);
                animation.setDuration(300);
                ivBottomLine.startAnimation(animation);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    /**
     * 获取PopupWindow实例
     */
    private void getCloseWindow() {
        if (null != closeWindow) {
            closeWindow.dismiss();
            return;
        } else {
            DisplayMetrics displaymetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

            CustomPopupWindow customPopupWindow = new CustomPopupWindow(this, R.layout.popup_window, displaymetrics.widthPixels, 150);
            customPopupWindow.setAnimationStyle(R.anim.popup_in_bottom2top);
            Button close = (Button) customPopupWindow.findViewById(R.id.open);
            close.setTypeface(Typeface.createFromAsset(getAssets(), "ionicons.ttf"));
            final Activity self = this;
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(self, android.R.style.Theme_Holo_Dialog));
                    builder.setMessage(R.string.exit_message);
                    builder.setTitle(R.string.exit_title);
                    builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            stopService(new Intent(TeamMainActivity.this, CommunicateService.class));
                            self.finish();
                        }
                    });
                    builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }
            });
            closeWindow = customPopupWindow.getPopupWindow();
        }
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        Log.i("CommunicateService", "onMenuItemSelected");
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            getCloseWindow();
            closeWindow.showAsDropDown(findViewById(R.id.iv_bottom_line), 0, -7);
            //closeWindow.showAtLocation(getCurrentFocus(), Gravity.BOTTOM, 10,10);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        Log.i("CommunicateService", "onBackPressed");
/*        Intent i = new Intent(Intent.ACTION_MAIN);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addCategory(Intent.CATEGORY_HOME);
        startActivity(i);*/
        super.onBackPressed();
    }
}