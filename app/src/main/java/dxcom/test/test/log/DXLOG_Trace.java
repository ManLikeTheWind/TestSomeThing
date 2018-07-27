package dxcom.test.test.log;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;


import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dxcom.test.test.OSSApplication;

/**
 * package: com.ztesoft.android.manager.system.utils
 * author: dongxiang
 * date: 2018/4/9 8:49
 * descrp:
 * using:
 */

public class DXLOG_Trace {

    private static  Resourcer resourcer=new Resourcer();
    private static  Thread threadConsumer1=new Thread(new Consumer(resourcer),"消费者-1-");//消费者线程
    private static boolean isThreadConsumer1Start=false;

    public static final String PRECLASS=DXLOG_Trace.class.getSimpleName();


    public static final void e(String TAG,String content){
        e(TAG, PRECLASS+"  "+content ,null);
    }
    public static final void e(String TAG,String content,Throwable thr){
        resourcer.create("TAG = "+TAG+"   "+content);
        if (!isThreadConsumer1Start){
            threadConsumer1.start();
            isThreadConsumer1Start=true;
        }


        int len=1000;
        if(content.length() > len) {
            for(int i=0;i<content.length();i+=len){
                if(i+len<content.length())
                    Log.e(TAG+i,content.substring(i, i+len));
                else
                    Log.e(TAG+i,content.substring(i, content.length()));
            }
        } else{
            Log.e(TAG,content);
        }
    }




}



