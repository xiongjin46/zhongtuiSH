
package com.push.PushMerchant.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.support.multidex.MultiDexApplication;
import com.push.PushMerchant.constants.IntentFlag;
import com.push.PushMerchant.modules.home.bean.AppVersionBean;
import com.push.PushMerchant.util.SharePrefUtil;
import com.push.PushMerchant.view.handler.EventController;
import com.push.PushMerchant.view.handler.EventDispatcher;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhangshaowen on 16/8/9.
 */
public class PushApplicationContext extends MultiDexApplication {

    public static Context context = null;
    public static String sPackageName = "";
    public static final Map<String, WeakReference<BaseActivity>> mActivitys = new ConcurrentHashMap<>();
    public static EventController sEventController = EventController.getsInstance();
    public static EventDispatcher sEventDispatcher = EventDispatcher.getsInstance(sEventController);
    public static AppVersionBean appVersion;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        SharePrefUtil.putBooleanSetting(IntentFlag.OUTTOKEN, false);
    }

    public static void addActivity(BaseActivity act) {
        mActivitys.put(act.getClass().getName(), new WeakReference<>(act));
    }

    public static void finishActivity(BaseActivity act) {
        mActivitys.remove(act.getClass().getName());
    }

    /**
     * 关闭所有activity
     */
    public static void closeAllActivitys() {
        for (Map.Entry<String, WeakReference<BaseActivity>> entry : mActivitys.entrySet()) {
            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
            WeakReference<BaseActivity> ref = mActivitys.get(entry.getKey());
            Activity act = ref != null ? ref.get() : null;
            if (act == null) {
                mActivitys.remove(entry.getKey());
            } else if (!act.isFinishing()) {
                act.finish();
            } else {
                // 不在finish后立刻执行，而在下一次调用的时候执行
                mActivitys.remove(entry.getKey());
            }
        }
    }

    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return
     */
    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {

                return appProcess.processName;
            }
        }
        return null;
    }


}
