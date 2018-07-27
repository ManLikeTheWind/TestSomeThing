package dxcom.test.test;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * package: dxcom.test.test
 * author: dongxiang
 * date: 2018/4/916:02
 * descrp:
 * using:
 */

public class AdpaterMain extends BaseAdapter {
    private List<Map<String,String>>data;
    private LayoutInflater layoutInflater;

    public AdpaterMain(List<Map<String, String>> data,LayoutInflater layoutInflater) {
        this.layoutInflater=layoutInflater;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Map<String,String> map=data.get(position);
        ViewHolder holder=null;
        if (convertView==null||convertView.getTag()==null){
            holder=new ViewHolder();
            convertView=layoutInflater.inflate(R.layout.item_dispatched_order_dialog,null,false);
            holder.dispatched_work_order_no= (TextView) convertView.findViewById(R.id.dispatched_work_order_no);
            holder.dispatched_work_order_ne_id= (TextView) convertView.findViewById(R.id.dispatched_work_order_ne_id);
            holder.dispatched_ssd_id_type= (TextView) convertView.findViewById(R.id.dispatched_ssd_id_type);
            holder.dispatched_work_order_sla= (TextView) convertView.findViewById(R.id.dispatched_work_order_sla);
            holder.dispatched_work_order_station_name= (TextView) convertView.findViewById(R.id.dispatched_work_order_station_name);
            holder.dispatched_work_order_ne_address= (TextView) convertView.findViewById(R.id.dispatched_work_order_ne_address);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        holder.dispatched_work_order_no.setText(map.get("dispatched_work_order_no"));
        holder.dispatched_work_order_ne_id.setText(map.get("dispatched_work_order_ne_id"));
        holder.dispatched_ssd_id_type.setText(map.get("dispatched_ssd_id_type"));
        holder.dispatched_work_order_sla.setText(map.get("dispatched_work_order_sla"));
        holder.dispatched_work_order_station_name.setText(map.get("dispatched_work_order_station_name"));
        holder.dispatched_work_order_ne_address.setText(map.get("dispatched_work_order_ne_address"));
        return convertView;
    }

    class ViewHolder{
        TextView dispatched_work_order_no;
        TextView dispatched_work_order_ne_id;
        TextView dispatched_ssd_id_type;
        TextView dispatched_work_order_sla;
        TextView dispatched_work_order_station_name;
        TextView dispatched_work_order_ne_address;
    }
}
