package dxcom.test.test.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * <br>package: dxcom.test.test.service  MService
 * <br>.author: dongxiang
 * <br>...date: 2018/7/27  19:48
 * <br>.descrp:
 * <br>..using:
 * <br>.e-mail:dongxiang_android_sdk@aliyun.com
 */

public class MService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return new ThisBinder(this);
    }
    static class ThisBinder extends Binder{
        MService mService;
        public ThisBinder(MService mService) {
            this.mService=mService;
        }

        public MService getmService() {
            return mService;
        }
    }
}
