package dxcom.test.test.observerprojection;

import java.util.Observable;
import java.util.Observer;

/**
 * <br>package: dxcom.test.test.observerprojection
 * <br>.author: dongxiang
 * <br>...date: 2018/5/4  15:31
 * <br>.descrp:
 * <br>..using:
 * <br>.e-mail:dongxiang_android_sdk@aliyun.com
 */

public class ObserverableAnimal extends Observable {

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        //this.data==null,data==null排除; this.data==null,data!=null添加
        //this.data!=null,data==null添加; this.data!=null,data!=null添加
        if (!(this.data==null&&data==null)){
            if(this.data==null&&data!=null
               ||this.data.equals(data)){
                this.data=data;
                setChanged();//改变通知者的状态
            }
        }
        notifyObservers(data+" - "+666666);//调用父类 Observable方法，通知所有观察者；
    }



    @Override
    public int hashCode() {
        return getData().hashCode();
    }

    @Override
    public String toString() {
        return "ObserverableAnimal{" +
                "data='" + data + '\'' +
                '}';
    }
}
