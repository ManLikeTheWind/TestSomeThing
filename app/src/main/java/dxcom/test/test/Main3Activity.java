package dxcom.test.test;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import dxcom.test.test.packageutils.ActivityUtils;

public class Main3Activity extends AppCompatActivity {
    public static  String TAG="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        TAG=this.getClass().getSimpleName();

    }


    public void onclick(View v){
        Intent i;
        switch (v.getId()){
            case R.id.bt3_2:
                i=new Intent(this,Main2Activity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i);
                break;
            case R.id.bt3_2_finish:
                i=new Intent(this,Main2Activity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i);
                finish();
                break;
            case R.id.btn3_showDetails_actvitis:
                ActivityUtils.getActivitiesByApplication(this.getApplication(),TAG);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        boolean appActivity = ActivityUtils.isAppActivity(Main3Activity.this);
                        boolean backgroundRunning = ActivityUtils.isBackgroundRunning(Main3Activity.this);
                        Log.e(TAG, "onclick: appActivity = "+appActivity+":backgroundRunning = "+backgroundRunning );

                    }
                },3000);
                break;
        }
    }
}
