package dxcom.test.test.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

/**
 * <br>package: dxcom.test.test.service
 * <br>.author: dongxiang
 * <br>...date: 2018/5/17  8:54
 * <br>.descrp:
 * <br>..using:
 * <br>.e-mail:dongxiang_android_sdk@aliyun.com
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MDXJobService extends JobService {
    public static final String TAG=MDXJobService.class.getSimpleName();
    @Override
    public boolean onStartJob(JobParameters params) {
        Log.e(TAG, "onStartJob: params = "+params );
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.e(TAG, "onStartJob: params = "+params );
        return false;
    }



}
