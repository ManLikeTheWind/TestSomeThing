package dxcom.test.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.ArrayMap;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dxcom.test.test.packageutils.ActivityUtils;

public class Main2Activity extends AppCompatActivity {
    public static  String TAG="";
    ListView listview_2;
    View listview_2_empty_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        TAG=this.getClass().getSimpleName();
        listview_2=(ListView)findViewById(R.id.listview_2);
        listview_2_empty_view=findViewById(R.id.listview_2_emptyview);



    }

    public void onclick(View v){
        Intent i;
        switch (v.getId()){
            case R.id.bt3:
                i=new Intent(this,Main3Activity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i);
                break;
            case R.id.bt3_finish:
                i=new Intent(this,Main3Activity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i);
                finish();
                break;
            case R.id.btn2_showDetails_actvitis:
                ActivityUtils.getActivitiesByApplication(this.getApplication(),TAG);
                break;
            case R.id.btn2_loadListView:
                load();

                break;
        }
    }
    private int icount=5;
    private void load(){
        if (icount>0) {
            icount = 0;
        }else {
            icount=5;
        }
        List<HashMap<String,String>> data=new ArrayList<>();
        HashMap<String,String> map=null;
        for (int i=0;i<icount;i++){
            map=new HashMap<>();
            map.put("data","data-data-data "+i);
            data.add(map);
        }
        SimpleAdapter simpleAdapter=new SimpleAdapter(this,data,android.R.layout.simple_list_item_1,new String[]{"data"},new int[]{android.R.id.text1});
        listview_2.setAdapter(simpleAdapter);
        listview_2.setEmptyView(listview_2_empty_view);
    }








}
