package dxcom.test.test.notify;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import android.widget.RemoteViews;

import dxcom.test.test.R;
import dxcom.test.test.packageutils.PackageUtils;


/**
 * package: dxcom.test.test.notify
 * author: dongxiang
 * date: 2018/4/11  10:18
 * descrp:
 * using:
 * e-mail:dongxiang_android_sdk@aliyun.com
 */

public class Notify {

    public static  final String TAG=Notify.class.getSimpleName();
    public static void sendNotify(Context context){
        Notification messagenotification = new Notification();
        int messagenotificationid=10;
        NotificationManager messageNotificationManager = (NotificationManager) context .getSystemService(android.content.Context.NOTIFICATION_SERVICE);
        messagenotification.icon = R.mipmap.ic_launcher;
        /* 设置通知栏的内容 */
        messagenotification.tickerText = context.getString(R.string.app_name);
            /* 发送通知的时间戳 */
        messagenotification.when = System.currentTimeMillis();
//            /* 设置来通知时候的声音 */
        messagenotification.sound = Uri.parse("android.resource://"+PackageUtils.getAppProcessName(context)+"/"+R.raw.u_have_new_msg);
//       messagenotification.sound = Settings.System.DEFAULT_NOTIFICATION_URI;
        // audioStreamType的值必须AudioManager中的值，代表着响铃的模式
        messagenotification.audioStreamType = android.media.AudioManager.ADJUST_LOWER;

//        数组第一个参数表示延迟震动时间
//        第二个参数表示震动持续时间
//        第三个参数表示震动后的休眠时间
//        第四个参数又表示震动持续时间
//        第五个参数也表示正到休眠时间
        long[] pattern = {0, 100, 1000, 300, 200, 100, 500, 200, 100};
        messagenotification.vibrate = pattern;


        // 点击通知之后，通知在状态栏上消失
        messagenotification.flags = Notification.FLAG_AUTO_CANCEL;

        // 绑定自定义通知消息,所对应的布局
        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.notification);

        // 修改通知栏消息图标尺寸,原来的图标为"app_new",modefied by zhaoxy 2016-08-19
        //contentView.setImageViewResource(R.id.image, R.drawable.app);
        contentView.setImageViewResource(R.id.image, R.mipmap.ic_launcher);
        messagenotification.contentView = contentView;

        Log.e(TAG,"sendNotify packageProcessName = "+ PackageUtils.getAppProcessName(context));
        // 推送消息
        messageNotificationManager.notify(messagenotificationid, messagenotification);
    }

}
