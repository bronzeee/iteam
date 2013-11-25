package com.houniao.iteam;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;

/**
 * Created by Administrator on 13-11-25.
 */
public class CustomPopupWindow{
    private PopupWindow popupWindow;
    private View popupWindow_view;

    CustomPopupWindow(Activity context , int view, int width, int height) {
        popupWindow_view = context.getLayoutInflater().inflate(view, null, false);
        popupWindow = new PopupWindow(popupWindow_view, width, height, true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 点击其他地方消失
        popupWindow_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
                return false;
            }
        });
    }

    public void setAnimationStyle(int anim) {
        // 设置动画效果
        popupWindow.setAnimationStyle(anim);
    }

    public PopupWindow getPopupWindow(){
        return popupWindow;
    }

    public View findViewById(int view) {
        return popupWindow_view.findViewById(view);
    }
}