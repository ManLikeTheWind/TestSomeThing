package dxcom.test.test.packageutils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dxcom.test.test.utils.InvokeMethodOpen;

/**
 * package: dxcom.test.test.packageutils
 * author: dongxiang
 * date: 2018/4/13  18:25
 * descrp:
 * using:
 * e-mail:dongxiang_android_sdk@aliyun.com
 */

public class ActivityUtils {
    public static List<Activity> getActivitiesByApplication(Application application, String TAG) {
        List<Activity> list = new ArrayList<>();
        try {
            Object mLoadedApk= InvokeMethodOpen.invokeVar_get(Application.class,application,"mLoadedApk");
            Object mActivityThread=InvokeMethodOpen.invokeVar_get(mLoadedApk.getClass(),mLoadedApk,"mActivityThread");
            Object mActivities=InvokeMethodOpen.invokeVar_get(mActivityThread.getClass(),mActivityThread,"mActivities");
            /**注意这里一定写成Map，低版本这里用的是HashMap，高版本用的是ArrayMap;但是他们都实现了 Map*/
            if (mActivities instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<Object, Object> arrayMap = (Map<Object, Object>) mActivities;
                int i=0;
                for (Map.Entry<Object, Object> entry : arrayMap.entrySet()) {
                    Object value = entry.getValue();

                    Object activityObj=InvokeMethodOpen.invokeVar_get(value.getClass(),value,"activity");
                    Log.e(TAG, "getActivitiesByApplication:  i = "+i+";activityObj = "+activityObj.getClass().getName() );
                    i++;
                    list.add((Activity) activityObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            list = null;
        }
        return list;
    }

    /**判断应用是否在后台,是否锁屏*/
    public static boolean isBackgroundRunning(Context context) {
        if(context == null) return false; // 防止应用打开时没来得及初始化AppContext
        String processName = context.getPackageName();

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);

        if (activityManager == null) return false;
        // get running application processes
        List<ActivityManager.RunningAppProcessInfo> processList = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo process : processList) {
            if (process.processName.startsWith(processName)) {
                boolean isBackground = process.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                        && process.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE;
                boolean isLockedState = keyguardManager.inKeyguardRestrictedInputMode();
                if (isBackground || isLockedState)
                    return true;
                else
                    return false;
            }
        }
        return false;
    }

    public static boolean  isAppActivity(Context context){
        List<String> names = new ArrayList<String>();
        PackageManager packageManager = context.getPackageManager();
        // 属性
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

        for (ResolveInfo ri : resolveInfo) {
            names.add(ri.activityInfo.packageName);
        }
        ActivityManager  mActivityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> rti = mActivityManager.getRunningTasks(1);
        boolean isHomeAct= names.contains(rti.get(0).topActivity.getPackageName());

        return isHomeAct;
    }

    /**
     * 获取当前活动着的activity
     *
     * @param context
     * @return
     */
    public static String getRunningActivity(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTasks = am.getRunningTasks(1);
        ActivityManager.RunningTaskInfo rti = runningTasks.get(0);
        ComponentName component = rti.topActivity;
        return component.getClassName() == null ? "" : component.getClassName();
    }

}
