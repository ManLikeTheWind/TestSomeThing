package dxcom.test.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * package: dxcom.test.test
 * author: dongxiang
 * date: 2018/4/916:03
 * descrp:
 * using:
 */

public class DummyData {

    public static List<Map<String,String>> data=new ArrayList<>();

    static {
            loadData();
    }

    public static void loadData(){
        Map<String,String>map;
        String dataS;
//      dataS=""+i+i+i+i+i+i+i+i;
        for (int i=0;i<10;i++){
            map=new HashMap<>();
            dataS="";
            map.put("dispatched_work_order_no","work_order_no "+dataS);
            map.put("dispatched_work_order_ne_id","work_order_ne_id "+dataS);
            map.put("dispatched_ssd_id_type","ssd_id_type "+dataS);
            map.put("dispatched_work_order_sla","work_order_sla "+dataS);
            map.put("dispatched_work_order_station_name","work_order_station_name "+dataS);
            map.put("dispatched_work_order_ne_address","work_order_ne_address "+dataS);
            data.add(map);
        }
    }




}
