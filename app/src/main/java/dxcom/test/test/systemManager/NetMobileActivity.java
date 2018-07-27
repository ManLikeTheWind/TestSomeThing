package dxcom.test.test.systemManager;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import dxcom.test.test.R;

public class NetMobileActivity extends AppCompatActivity {
    public static final String TAG = NetMobileActivity.class.getSimpleName();
    List<Byte> memoryByte = new ArrayList<>();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.analy_memory:
                analyMemory();
                break;
            case R.id.analy_method_time:
                analyMehodTime();
                break;
            case R.id.analy_net_avaliable:
                runThread_ping_ip();
                break;
            case R.id.analy_net_type_mobile_connect:
                runThread_MobileNetWorkType();
                break;
            case R.id.analy_net_type_suffer_internet_connect:
                runThread_Mobile_Wifi();
                break;
        }
    }

    private void analyMemory() {
        byte bT = 99;
        for (int i = 0; i < 100; i++) {
            memoryByte.add(bT);
        }
    }

    private void analyMehodTime() {
        Log.e(TAG, "analyMehodTime: analyMehodTime --start");
        Log.e(TAG, "analyMehodTime: methodTime__01");
        methodTime__01();

        Log.e(TAG, "analyMehodTime: methodTime_02");
        methodTime_02();

        Log.e(TAG, "analyMehodTime: analyMehodTime --end");

    }

    private void methodTime__01() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void methodTime_02() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void runThread_ping_ip() {
        new Thread() {
            @Override
            public void run() {
                StringBuffer sb = new StringBuffer();
                try {
                    String ip = "www.baidu.com";
                    String cmdL = "ping -c 1 -w 100 ";// ping网址1次  -c 应该为 -n；
                    sb.append(cmdL + ip + "\n");
                    Process process = Runtime.getRuntime().exec(cmdL + ip);// ping网址1次

                    Log.e(TAG, "run:runThread_ping_ip 方案一 ============== start ");
                    int waitFor = process.waitFor();
                    if (waitFor == 0) {
                        sb.append("connect success；----\n");
                    } else {
                        sb.append("connect failed；----\n");
                    }
                    Log.e(TAG, "run:runThread_ping_ip 方案一 ============== end ");

                    Log.e(TAG, "run:runThread_ping_ip 方案二 ============== start");
                    InputStream inputStream = process.getInputStream();

                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                    StringBuffer sbBR = new StringBuffer("no net-work");
                    String tempBR ;
                    while ((tempBR = br.readLine()) != null) {
                        sbBR.append(tempBR);
                    }
                    sb.append(sbBR + "\n");
                    Log.e(TAG, "run:runThread_ping_ip process.getInputStream sbBR = " + sbBR);
                    Log.e(TAG, "run:runThread_ping_ip 方案二 ============== end");

                } catch (IOException e) {
                    sb.append("IOException " + e.getMessage() + "\n");
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    sb.append("InterruptedException " + e.getMessage() + "\n");
                    e.printStackTrace();
                } finally {
                    Log.e(TAG, "run:runThread_ping_ip sb = " + sb);
                }
            }
        }.start();
    }


    public void runThread_MobileNetWorkType() {
        new Thread() {
            @Override
            public void run() {
                Application application = NetMobileActivity.this.getApplication();
                TelephonyManager telephonyManager = (TelephonyManager) application.getSystemService(Context.TELEPHONY_SERVICE);
                int networkType = telephonyManager.getNetworkType();
                String type = "networkType = " + networkType + ";name =";
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        type += " 2G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        type += " 3G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        type += " 4G";
                        break;
                    default:
                        type += " Unknown";
                        break;
                }
                Log.e(TAG, "runThread_MobileNetWorkType run: type = " + type);
            }
        }.start();
    }

    public void runThread_Mobile_Wifi() {
        new Thread() {
            @Override
            public void run() {
                Application application = NetMobileActivity.this.getApplication();
//                判断数据连接是否打开
                ConnectivityManager connectivityManager = (ConnectivityManager) application
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                NetworkInfo networkInfoTemp = null;
                networkInfoTemp=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_BLUETOOTH);
                if (networkInfo!=null){
                    StringBuffer sb=new StringBuffer();
                    int type = networkInfo.getType();
                    String typeName = networkInfo.getTypeName();
                    String extraInfo = networkInfo.getExtraInfo();
                    NetworkInfo.DetailedState detailedState = networkInfo.getDetailedState();
                    sb.append("\nextraInfo = "+extraInfo+";\ndetailedState = "+detailedState+";\ntypeName = "+typeName+";\n type = "+type+";\n"+";type-cost = ");
                    switch (type){
                        case ConnectivityManager.TYPE_MOBILE:
                            sb.append("ConnectivityManager.TYPE_MOBILE");
                            networkInfoTemp=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                            break;
                        case ConnectivityManager.TYPE_WIFI:
                            networkInfoTemp=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                            sb.append("ConnectivityManager.TYPE_WIFI");
                            break;
                        case ConnectivityManager.TYPE_BLUETOOTH:
                            networkInfoTemp=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_BLUETOOTH);
                            sb.append("ConnectivityManager.TYPE_BLUETOOTH");
                            break;
                        case ConnectivityManager.TYPE_DUMMY:
                            networkInfoTemp=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_DUMMY);
                            sb.append("ConnectivityManager.TYPE_DUMMY");
                            break;
                        case ConnectivityManager.TYPE_ETHERNET:
                            networkInfoTemp=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
                            sb.append("ConnectivityManager.TYPE_ETHERNET");
                            break;
                        case ConnectivityManager.TYPE_MOBILE_DUN:
                            networkInfoTemp=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE_DUN);
                            sb.append("ConnectivityManager.TYPE_MOBILE_DUN");
                            break;
                        case ConnectivityManager.TYPE_VPN:
                            networkInfoTemp=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_VPN);
                            sb.append("ConnectivityManager.TYPE_VPN");
                            break;
                        case ConnectivityManager.TYPE_WIMAX:
                            networkInfoTemp=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIMAX);
                            sb.append("ConnectivityManager.TYPE_WIMAX");
                            break;
                        case ConnectivityManager.TYPE_MOBILE_HIPRI:
                            networkInfoTemp=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE_HIPRI);
                            sb.append("ConnectivityManager.TYPE_MOBILE_HIPRI");
                            break;
                        case ConnectivityManager.TYPE_MOBILE_MMS:
                            networkInfoTemp=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE_MMS);
                            sb.append("ConnectivityManager.TYPE_MOBILE_MMS");
                            break;
                        case ConnectivityManager.TYPE_MOBILE_SUPL:
                            networkInfoTemp=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE_SUPL);
                            sb.append("ConnectivityManager.TYPE_MOBILE_SUPL");
                            break;
                        default:
                            sb.append("未知");
                            break;
                    }
                    Log.e(TAG, "run:runThread_Mobile_Wifi  networkInfo = "+sb );
                }

            }
        }.start();


    }

}
