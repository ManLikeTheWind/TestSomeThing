package dxcom.test.test;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import dxcom.test.test.db.DBConstansts;
import dxcom.test.test.db.DBManager;
import dxcom.test.test.log.DXLOG_Trace;
import dxcom.test.test.notify.Notify;
import dxcom.test.test.utils.ToastUtils;

public class MainActivity extends AppCompatActivity {
    View llview;
    GradientDrawable dg;
    private TextView tv;
    public static final String TAG=MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         llview=findViewById(R.id.dialog_dispatched_worklist_ll);
        ListView lv = (ListView) llview.findViewById(R.id.dispatched_order_list_view);
        lv.setAdapter(new AdpaterMain(DummyData.data, LayoutInflater.from(this)));

        tv= (TextView) findViewById(R.id.tv);
        tv.setOnClickListener(new View.OnClickListener() {
            boolean flag=true;
            @Override
            public void onClick(View v) {
                if (flag){
                    tv.setEllipsize(null);
                    flag = false;
                }else {
                    tv.setEllipsize(TextUtils.TruncateAt.END);
                    flag = true;
                }
                tv.setSingleLine(flag);

            }
        });



        dg= (GradientDrawable) llview.getBackground();
        dg.setColor(0xe9e9e9);

        Thread t1=new Thread(){
            @Override
            public void run() {
                for (int i=0;i<100;i++){
                    DXLOG_Trace.e(TAG,"i111111111111111 = "+i+" ; 四大皆空落实到发几款是打发技术打法进口量 sjksdfjjskdfjksdfjksdf");
                }
            }
        };
        Thread t2= new Thread(){
            @Override
            public void run() {
                for (int i=0;i<100;i++){
                    DXLOG_Trace.e(TAG,"i222222222  = "+i+" ; 四大皆空落实到发几款是打发技术打法进口量 sjksdfjjskdfjksdfjksdf");
                }
            }
        };
        t1.start();
        t2.start();




    }


    public void commonFunc(){
        Log.e(TAG, "commonFunc: actiivty.class"+ this.getClass().getName());

    }


    public void onclick(View view){
        switch (view.getId()){
            case R.id.dispatched_order_cancelTv:
                Notify.sendNotify(this);
                dg.setColor(0xfff900f9);
                break;
            case R.id.dispatched_order_okTv:
                dg.setColor(0xfff9f9f9);
                break;
            case R.id.bt2:
                startActivity(new Intent(this,Main2Activity.class));
                break;
            case R.id.bt_showToast:
//                ToastUtils.showToastAtViewTop(this,R.string.toast_signoff_should_show_call_entry,findViewById(R.id.bt_showToast));
                ToastUtils.showToastAtCenter(this,R.string.toast_signoff_should_show_call_entry);
                ToastUtils.showToastAtCenter(this,R.string.toast_signoff_should_show_call_entry2);
                ((Button)findViewById(R.id.bt_showToast)).setText(R.string.toast_signoff_should_show_call_entry);

                compileLaterNow("2015-06-06 12:00:00","yyyy-MM-dd HH:mm:ss",0);
                compileLaterNow("2015-06-06 12:00:00","yyyy-MM-dd HH:mm:ss",2);
                compileLaterNow("2015-06-06 12:00:00","yyyy-MM-dd HH:mm:ss",12);
                compileLaterNow("2015-06-06 12:00:00","yyyy-MM-dd HH:mm:ss",24);

                break;
            case R.id.bt_db_test:
                long insertTest = DBManager.get().insertTest();
                Log.e(TAG, "onclick: R.id.bt_db_test insertTest = "+insertTest );

                long insertCompany=DBManager.get().insertCompany();
                Log.e(TAG, "onclick: R.id.bt_db_test insertCompany = "+insertCompany );

                ArrayList insertDepartment=DBManager.get().insertDepartment();
                Log.e(TAG, "onclick: R.id.bt_db_test insertDepartment = "+insertDepartment );
                ArrayList<HashMap<String,String>> queryCompanyDepartment=DBManager.get().queryCompanyDepartment();
                Log.e(TAG,Thread.currentThread()+" queryCompanyDepartment.toString = "+ queryCompanyDepartment);
                break;
            case R.id.bt_db_test_insert_or_replace:
                Log.e(TAG, "onclick: bt_db_test_insert_or_replace 开始" );
                DBManager.get().queryDepartment();
                ArrayList insertDepartment2=DBManager.get().insertDepartment();
                DBManager.get().queryDepartment();
                Log.e(TAG, "onclick: bt_db_test_insert_or_replace 结束" );
                break;
            case R.id.bt_json_test:
                String json1="{\"body\":{\n" +
                        "\t\t\t\"bussinessLocationList\":\"\",\n" +
                        "\t\t\t\"neList\":[{\"ne_id\":\"17120043\",\"bsc_ne_id\":null}],\n" +
                        "\t\t\t\"RETURN\":[],\n" +
                        "\t\t\t\"listNull\":null\n" +
                        "}}";

                try {
                    JSONObject outObj=new JSONObject(json1);
                    JSONObject body = outObj.getJSONObject("body");


                    Object aReturn = body.get("RETURN");///arr
                    Object listNull = body.get("listNull");//obj
                    String listNull_str= body.getString("listNull");
                    boolean isNull=listNull==null;
                    boolean hasNull= body.has("listNull");

                    Object bussinessLocationList_common_obj = body.get("bussinessLocationList");//str

                    boolean result=has_ussinessLocationList(body);

                    String s=body.getString("neList");

                    String listNullToString= (String) listNull;
                    String dataNull=body.getString("listNull");

                    String bussinessLocationList_str = body.getString("bussinessLocationList");//str
                    JSONObject bussinessLocationList_obj = body.getJSONObject("bussinessLocationList");//str
                    JSONArray bussinessLocationList_Arr = body.getJSONArray("bussinessLocationList");


                } catch (Exception e) {
                    e.printStackTrace();
                }


                Log.e(TAG, "onclick: " );

                break;
            case R.id.bt_db_batchReplace:
                ArrayList<ContentValues> valuesArray=new ArrayList<>();
                ContentValues values=new ContentValues();

                values.put(DBConstansts.TABLE_CONSTRAINTS_UNIQUE_PERSON_NAME,"梅花0");
                values.put(DBConstansts.TABLE_CONSTRAINTS_UNIQUE_PERSON_ID_unique,"88");
                valuesArray.add(values);

                values=new ContentValues();
                values.put(DBConstansts.TABLE_CONSTRAINTS_UNIQUE_PERSON_NAME,"梅花1");
                values.put(DBConstansts.TABLE_CONSTRAINTS_UNIQUE_PERSON_ID_unique,"88");
                valuesArray.add(values);

                values=new ContentValues();
                values.put(DBConstansts.TABLE_CONSTRAINTS_UNIQUE_PERSON_NAME,"梅花2");
                values.put(DBConstansts.TABLE_CONSTRAINTS_UNIQUE_PERSON_ID_unique,"88");
                valuesArray.add(values);

                values=new ContentValues();
                values.put(DBConstansts.TABLE_CONSTRAINTS_UNIQUE_PERSON_NAME,"桃花0");
                values.put(DBConstansts.TABLE_CONSTRAINTS_UNIQUE_PERSON_ID_unique,"99");
                valuesArray.add(values);

                values=new ContentValues();
                values.put(DBConstansts.TABLE_CONSTRAINTS_UNIQUE_PERSON_NAME,"桃花1");
                values.put(DBConstansts.TABLE_CONSTRAINTS_UNIQUE_PERSON_ID_unique,"99");
                valuesArray.add(values);

                values=new ContentValues();
                values.put(DBConstansts.TABLE_CONSTRAINTS_UNIQUE_PERSON_NAME,"桃花2");
                values.put(DBConstansts.TABLE_CONSTRAINTS_UNIQUE_PERSON_ID_unique,"99");
                valuesArray.add(values);

                DBManager.get().replaceBatch(DBConstansts.TABLE_CONSTRAINTS_UNIQUE,null,valuesArray);




                break;

        }
    }



    public static boolean has_ussinessLocationList(JSONObject jsonBody){
        boolean result=false;
        try {
            result=jsonBody.has("bussinessLocationList");
            if (result){
                String bussinessLocationList=jsonBody.getString("bussinessLocationList");
                result= !bussinessLocationList.equals("null")&&!TextUtils.isEmpty(bussinessLocationList) ;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public static boolean compileLaterNow(String overDate, String dateFormat, int distinHours) {
        try {
            SimpleDateFormat sdf=new SimpleDateFormat(dateFormat);
            Date date1 = sdf.parse(overDate);
            Date nowDate = new Date(System.currentTimeMillis());

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(nowDate);
            Date dateCalendar1=calendar.getTime();

            calendar.add(Calendar.HOUR_OF_DAY, distinHours);

            Date dateCalendar2 = calendar.getTime();

            String dateCalendar1_Str=sdf.format(dateCalendar1);
            String dateCanlendar2_Str=sdf.format(dateCalendar2);

            boolean islaterNow=date1.before(dateCalendar2);

            Log.e(TAG, "compileLaterNow: overDate = "+overDate+";distinHours = "+distinHours+"; dateCalendar1_Str = "+dateCalendar1_Str+"; dateCanlendar2_Str = "+dateCanlendar2_Str+";islaterNow = "+islaterNow );
            return islaterNow;
        } catch (Exception e) {
            Log.e(TAG, "compileLaterNow", e);
        }
        return false;
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
//    }
}
