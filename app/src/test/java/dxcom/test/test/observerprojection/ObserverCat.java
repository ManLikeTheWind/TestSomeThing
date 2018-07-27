package dxcom.test.test.observerprojection;

import java.util.Observable;
import java.util.Observer;

/**
 * <br>package: dxcom.test.test.observerprojection
 * <br>.author: dongxiang
 * <br>...date: 2018/5/4  15:30
 * <br>.descrp:
 * <br>..using:
 * <br>.e-mail:dongxiang_android_sdk@aliyun.com
 */

public class ObserverCat implements Observer {
    public ObserverCat(Observable observable) {
        observable.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("ObserverCat  "+"  update: arg = "+arg+";o.class.getName = "+o.getClass().getName() +";o.tostring = "+o
//                +"\n; Looper.getMainLooper() = "+Looper.getMainLooper()+";Looper.myLooper() = "+Looper.myLooper()
        );
    }
}
