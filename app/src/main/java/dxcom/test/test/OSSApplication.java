package dxcom.test.test;

import android.app.Application;

/**
 * package: dxcom.test.test
 * author: dongxiang
 * date: 2018/4/10  17:53
 * descrp:
 * using:
 * e-mail:dongxiang_android_sdk@aliyun.com
 */

public class OSSApplication extends Application {
    public static OSSApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }
    public static OSSApplication get(){
        return instance;
    }
}
