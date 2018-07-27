package dxcom.test.test.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import dxcom.test.test.R;

/**
 * package: dxcom.test.test.utils
 * <br> author: dongxiang
 * <br>   date: 2018/4/18  18:51
 * <br>  descrp:
 * <br>  using:
 * <br>  e-mail:dongxiang_android_sdk@aliyun.com
 */

public class ToastUtils {

    public static void showToastAtCenter(Context context, int resId){
        Toast toast= new Toast(context);
        toast.setDuration(3*1000);

        View viewToast=LayoutInflater.from(context).inflate(R.layout.layout_coustomer_toast_center,null);
        TextView toast_tv_show = (TextView) viewToast.findViewById(R.id.toast_tv_show);
        toast_tv_show.setText(resId);

        toast.setView(viewToast);
        toast.setGravity(Gravity.CENTER,0 , 0);

        toast.show();
    }

    public static void showToastAtViewTop(Context context, int resId, View view){
        showToastAtViewTop(context,context.getString(resId),view);
    }

    public static  void showToastAtViewTop(Context context, String showData, View view){

        Toast toast= new Toast(context);;
        toast.setDuration(Toast.LENGTH_SHORT);

        View viewToast=LayoutInflater.from(context).inflate(R.layout.layout_coustomer_toast_at_view_top,null);
        TextView toast_tv_show = (TextView) viewToast.findViewById(R.id.toast_tv_show);
        toast_tv_show.setText(showData);

        toast.setView(viewToast);


        viewToast.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int toastWidth = viewToast.getMeasuredWidth();    //  获取测量后的宽度
        int toastHeight = viewToast.getMeasuredHeight();  //获取测量后的高度

        int location[]=new int[2];
        view.getLocationOnScreen(location);

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();

        int[]xyR2xyToast=xyR2xyToast(new int[]{displayMetrics.widthPixels,displayMetrics.heightPixels},location);

        int xoff= (int) (xyR2xyToast[0] + view.getWidth() *0.5f+0.5);
        int yoff= (int) (xyR2xyToast[1] - toastHeight*0.5f-0.5);

        Log.e("ToastUtils","location.toString"+ Arrays.toString(location)+";view.getWidth = "+view.getWidth()+";view.getHeight = "+view.getHeight()+";xyR2xyToast.tostring = "+Arrays.toString(xyR2xyToast)+";toastWidth = "+toastWidth+";toastHeight = "+toastHeight+"; xoff = "+xoff+";yoff = "+yoff);
        toast.setGravity(Gravity.CENTER,xoff , yoff);

        toast.show();
    }


    private static int[] xyR2xyToast(int screenXY[],int viewPositionXY[]){
        int resultXY[]=new int[2];
        resultXY[0]= (int) (-0.5f*screenXY[0]+viewPositionXY[0]+0.5f);
        resultXY[1]= (int) (-0.5f*screenXY[1]+viewPositionXY[1]-0.5f);
        return resultXY;
    }





}
