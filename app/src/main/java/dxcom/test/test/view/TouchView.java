package dxcom.test.test.view;

import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * <br>package: dxcom.test.test.view  TouchView
 * <br>.author: dongxiang
 * <br>...date: 2018/5/24  17:05
 * <br>.descrp:
 * <br>..using:
 * <br>.e-mail:dongxiang_android_sdk@aliyun.com
 */

public class TouchView extends View implements View.OnTouchListener{
    public static final String TAG=TouchView.class.getSimpleName();
    public TouchView(Context context) {
        super(context);
        setOnTouchListener(this);
    }

    public TouchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
    }

    public TouchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Looper.prepare();
        setOnTouchListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TouchView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setOnTouchListener(this);
    }




    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "2  TouchView.onTouchEvent: event "+event+";enable = "+isEnabled() );
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.e(TAG, "1   View.OnTouchListener.onTouch: event "+event +";enable = "+isEnabled());
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e(TAG, "0  TouchView.dispatchTouchEvent: event "+event+";enable = "+isEnabled() );
        return super.dispatchTouchEvent(event);
    }
}
