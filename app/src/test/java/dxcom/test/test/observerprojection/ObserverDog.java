package dxcom.test.test.observerprojection;

import android.util.Log;

import java.util.Observable;
import java.util.Observer;

/**
 * <br>package: dxcom.test.test.observerprojection
 * <br>.author: dongxiang
 * <br>...date: 2018/5/4  15:39
 * <br>.descrp:
 * <br>..using:
 * <br>.e-mail:dongxiang_android_sdk@aliyun.com
 */

public class ObserverDog implements Observer {
    public ObserverDog(Observable observable) {
        observable.addObserver(this);
    }
    @Override
    public void update(Observable o, Object arg) {
        System.out.println("ObserverDog  "+"  update: arg = "+arg+";o.class.getName = "+o.getClass().getName() +";o.tostring = "+o);

    }


}
