package com.houniao.iteam.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.ServiceCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Administrator on 13-11-22.
 */
public class CommunicateService extends Service {
    private LocalBroadcastManager mLocalBroadcastManager;
    static final int MSG_UPDATE = 1;
    private int mCurUpdate = 0;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_UPDATE: {
                    mCurUpdate++;
                    Intent intent = new Intent(CustomServiceAction.ACTION_UPDATE);
                    intent.putExtra("value", mCurUpdate);
                    mLocalBroadcastManager.sendBroadcast(intent);
                    Message nmsg = mHandler.obtainMessage(MSG_UPDATE);
                    mHandler.sendMessageDelayed(nmsg, 1000);
                } break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        mLocalBroadcastManager.sendBroadcast(new Intent(CustomServiceAction.ACTION_STARTED));
/*        Runnable runnable = new Runnable(){
            @Override
            public void run() {
                try {
                    Socket socket = new Socket("192.168.5.104", 9556);
                    BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter pw = new PrintWriter(socket.getOutputStream());
                    pw.write("connect!");
                    pw.flush();
                    while(true){
                        if(br.ready()){
                            Log.i("CommunicateService", br.readLine());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(runnable).start();*/

        // Prepare to do update reports.
        mHandler.removeMessages(MSG_UPDATE);
        Log.i("CommunicateService","start");
        Message msg = mHandler.obtainMessage(MSG_UPDATE);
        mHandler.sendMessageDelayed(msg, 1000);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Tell any local interested parties about the start.
        mLocalBroadcastManager.sendBroadcast(new Intent(CustomServiceAction.ACTION_STARTED));

        return ServiceCompat.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        // Tell any local interested parties about the stop.
        mLocalBroadcastManager.sendBroadcast(new Intent(CustomServiceAction.ACTION_STOPPED));
        mHandler.removeMessages(MSG_UPDATE);
    }
}
