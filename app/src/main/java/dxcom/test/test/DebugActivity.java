package dxcom.test.test;

import android.app.Service;
import android.location.LocationManager;
import android.os.Debug;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DebugActivity extends AppCompatActivity {
    LinearLayout tabhost;
    private static final String TAG = DebugActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);
        File d=new File("/storage/emulated/0/dx/");
        if(!d.exists()){
            d.mkdirs();
        }
        tabhost = (LinearLayout) findViewById(R.id.tabhost);

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void onclick(View view){
        switch (view.getId()){
            case R.id.btn:
                //关键语句
                File f=new File("/storage/emulated/0/dx/trace_test.trace");
                if( f.exists() ){
                    f.delete();
                }
                Debug.startMethodTracing("/storage/emulated/0/dx/trace_test.trace");
                long c=System.currentTimeMillis();
                Log.e(TAG, "onClick([view]): pre 0 ");
                for (int i = 0; i < 100; i++) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Log.e(TAG, "onClick([view]): aft dur = "+ (System.currentTimeMillis()-c));
                Debug.stopMethodTracing();
                break;
            case R.id.btn_viewstub:
                loadViewStub();
                break;
        }
    }

    private void loadViewStub(){
       ViewStub vs= (ViewStub) findViewById(R.id.vs);
        View inflateView = vs.inflate();
        int inflatedId = vs.getInflatedId();
        int id = vs.getId();
        String format = String.format("vs.class = %s; inflateView.class = %s;inflatedId = %d; id = %d;R.id.layout_tab = %d; R.id.my_view  = %d; ",
                vs.getClass().getName(), inflateView.getClass().getName(), inflatedId, id,R.id.layout_tab, R.id.my_view );
        Log.e(TAG, " format = : "+format);
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private View createTabView(String name){
        View tabView = getLayoutInflater().inflate(R.layout.tab, null);
        TextView textView = (TextView) tabView.findViewById(R.id.name);
        textView.setText(name);
        return tabView;
    }
}
