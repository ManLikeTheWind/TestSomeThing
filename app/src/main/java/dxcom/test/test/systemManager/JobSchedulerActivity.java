package dxcom.test.test.systemManager;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import dxcom.test.test.OSSApplication;
import dxcom.test.test.R;

/**
 * 主要阐述：JobSchedulerManager 使用android.content.Context.JOB_SCHEDULER_SERVICE:
 *
 * 总是提示需要
 * java.lang.IllegalArgumentException: Error: requested job be persisted without holding RECEIVE_BOOT_COMPLETED permission.
 * 和  android.permission.BIND_JOB_SERVICE
 * 不清楚原因
 */
public class JobSchedulerActivity extends AppCompatActivity {
    public static final String TAG=JobSchedulerActivity.class.getSimpleName();
    JobScheduler jobScheduler;
    int jobID=889988;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_schedul);
        jobScheduler= (JobScheduler) OSSApplication.get().getSystemService(Context.JOB_SCHEDULER_SERVICE);

    }

    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.job_scheduler_add:
                    addOneJob(jobID);
                    break;
                case R.id.job_scheduler_cancle:
                    cancleJob(jobID);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void addOneJob(int jobID)throws Exception{
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ComponentName jobService=new ComponentName("dxcom.test.test","dxcom.test.test.service.MDXJobService");

            JobInfo.Builder builder=new JobInfo.Builder(jobID,jobService);

            long initialBackoffMillis = System.currentTimeMillis()+1*10*1000;
            builder.setBackoffCriteria(initialBackoffMillis, JobInfo.BACKOFF_POLICY_LINEAR);

            PersistableBundle persistableBundle=new PersistableBundle();
            persistableBundle.putString("data_packagename",JobSchedulerActivity.class.getName()+"_dxxxxx_");
            builder.setExtras(persistableBundle);

//            long minLatencyMillis=1*1000;
//            builder.setMinimumLatency(minLatencyMillis);
//
//            long maxExecutionDelayMillis=System.currentTimeMillis()+2*10*1000;
//            builder.setOverrideDeadline(maxExecutionDelayMillis);

            long setPeriodic=10*1000;
            builder.setPeriodic(setPeriodic);

            builder.setPersisted(true);

            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE);

            builder.setRequiresCharging(false);

            builder.setRequiresDeviceIdle(false);


            JobInfo jobInfo =builder.build();

            int schedule = jobScheduler.schedule(jobInfo);
            StringBuffer sb=new StringBuffer();
            sb.append(" schedule = "+schedule+";\n");
            switch (schedule) {
                case JobScheduler.RESULT_SUCCESS:
                    sb.append("name = JobScheduler.RESULT_SUCCESS;\n");
                    break;
                case JobScheduler.RESULT_FAILURE:
                    sb.append("name = JobScheduler.RESULT_FAILURE;\n");
                    break;
                default:
                    sb.append("name = Unkwnow;\n");
                    break;
            }
            Log.e(TAG, "addOneJob: " );
        }

    }

    private void cancleJob(int jobID){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            jobScheduler.cancel(jobID);
        }
    }

}
