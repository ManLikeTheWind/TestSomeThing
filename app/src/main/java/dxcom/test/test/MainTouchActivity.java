package dxcom.test.test;

import android.app.IntentService;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import dxcom.test.test.view.TouchView;

public class MainTouchActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    public static final String TAG=MainTouchActivity.class.getSimpleName();
    TouchView touchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_touch);
        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.btn_enable);
        toggleButton.setOnCheckedChangeListener(this);
         touchView = (TouchView) findViewById(R.id.touch_view);
        toggleButton.setChecked(false);
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        touchView.setEnabled(isChecked);
        Log.e(TAG, "onCheckedChanged:  touchView.isEnabled =  " + touchView.isEnabled());

    }


}
