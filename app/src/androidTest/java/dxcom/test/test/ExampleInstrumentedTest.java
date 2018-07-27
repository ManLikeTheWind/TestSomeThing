package dxcom.test.test;

import android.content.ContentValues;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    public static final String[] neKeys = new String[] { "ne_name", "ne_id",
            "station_id", "hersteller", "bsc_ne_id", "bsc_ne_name" };
    public static final String TAG=ExampleInstrumentedTest.class.getSimpleName();
    /*
    {
      "body":{
          "autoDispatchNeList": [
              {
                "station_id": "100004792",
                "ne_id": "16EN0217",
                "ne_name": "16EN0217",
                "hersteller": "ZTE",
                "bsc_ne_id": null,
                "bsc_ne_name": null
              }
            ]
        }
    }
    * */
    @Test
    public void useAppContext() throws Exception {
        String data=" {\"body\":{\"autoDispatchNeList\": [ { \"station_id\": \"100004792\",\"ne_id\": \"16EN0217\",\"ne_name\": \"16EN0217\",\"hersteller\": \"ZTE\",\"bsc_ne_id\": \"\",\"bsc_ne_name\":  \"\"}]}}";
        System.out.println("addition_isCorrect: data = "+ data );
        JSONObject jsonObject=new JSONObject(data);

        if (jsonObject.optJSONObject("body").has("autoDispatchNeList")) {
            JSONArray autoDispatchNeList = jsonObject.optJSONObject("body").optJSONArray("autoDispatchNeList");

            if (null != autoDispatchNeList && autoDispatchNeList.length() > 0) {
                for (int i = 0; i < autoDispatchNeList.length(); i++) {
                    JSONObject object = autoDispatchNeList.getJSONObject(i);
                    ContentValues values = new ContentValues();
                    for (int j = 0; j < neKeys.length; j++) {
                        if(TextUtils.isEmpty(object.optString(neKeys[j]))
                                || "null".equalsIgnoreCase(object.optString(neKeys[j]))){

                            values.put(neKeys[j], "");

                        }else{
                            values.put(neKeys[j], object.optString(neKeys[j]));
                        }

                    }

                }
            }else {
                System.out.println("autoDispatchNeList is null or length is 0");
            }
        }




    }
}
