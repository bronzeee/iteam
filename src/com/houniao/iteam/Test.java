package com.houniao.iteam;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Administrator on 13-11-22.
 */
public class Test {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("192.168.5.118", 9556);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter pw = new PrintWriter(socket.getOutputStream());
        pw.write("connect!");
        pw.flush();
        while(true){
            if(br.ready()){
                System.out.println(br.readLine());
            }
        }
    }
}
