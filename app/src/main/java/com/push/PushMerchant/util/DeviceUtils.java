package com.push.PushMerchant.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import com.nineoldandroids.animation.ArgbEvaluator;
import com.push.PushMerchant.base.PushApplicationContext;

/**
 * @ClassName: DeviceUtils
 * @Description: 跟设备信息相关，需要在程序一启动就调用
 * @author: YYL
 * <p>
 * create at 2016/11/10 14:23
 */
public class DeviceUtils {


    public static String getAndroidIdInPhone() {
        return Settings.Secure.getString(PushApplicationContext.context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * dp转px
     *
     * @param context
     * @return
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {

        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获得IMEI 信息（卡1和卡2），关于双卡适配的入口
     */
    public static String[] getImeiFromNative() {
        Context context = PushApplicationContext.context;
        int result = -1;
        try {
            result = PushApplicationContext.context
                    .checkCallingOrSelfPermission(android.Manifest.permission.READ_PHONE_STATE);
        } catch (Exception e) {
        }
        if (result != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        String[] imeiInfo = new String[2];
        try {
//            if (!MobileIssueSettings.isSupportDualSimIMEI) {
//                imeiInfo = KapalaiAdapterUtil.getKAUInstance().getDualSimIMEIInfoMethod(context);
//            } else {
//                imeiInfo = KapalaiAdapterUtil.getKAUInstance().getDualSimIMEIInfoNormalMethod(context);
//            }
            if (imeiInfo != null && imeiInfo.length != 0) {
                return imeiInfo;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean isAvailableImeiImsi(String[] items, int itemPosition) {
        try {
            if (items[itemPosition] != null && !TextUtils.isEmpty(items[itemPosition])) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public static String getImei() {
        int result = -1;
        try {
            result = PushApplicationContext.context
                    .checkCallingOrSelfPermission(android.Manifest.permission.READ_PHONE_STATE);
        } catch (Exception e) {
        }
        if (result != PackageManager.PERMISSION_GRANTED) {
            return "";
        }
        try {
            TelephonyManager tm = (TelephonyManager) PushApplicationContext.context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String rs = tm.getDeviceId();
            if (TextUtils.isEmpty(rs)) {
                return "";
            }
            return rs;
        } catch (Throwable e) {
            return "";
        }

    }

    public static String getImsi() {
        int result = PushApplicationContext.context
                .checkCallingOrSelfPermission(android.Manifest.permission.READ_PHONE_STATE);
        if (result != PackageManager.PERMISSION_GRANTED) {
            return "";
        }
        try {
            final TelephonyManager tm = (TelephonyManager) PushApplicationContext.context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String rs = tm.getSubscriberId();
            if (TextUtils.isEmpty(rs)) {
                return "";
            }
            return rs;
        } catch (Exception e) {
            return "";
        }

    }

    /**
     * 获得IMSI 信息（卡1和卡2），关于双卡适配的入口
     */
    public static String[] getImsiFromNative() {
        Context context = PushApplicationContext.context;
        int result = context.checkCallingOrSelfPermission(android.Manifest.permission.READ_PHONE_STATE);
        if (result != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        String[] rs = new String[2];
        try {
//            if (!MobileIssueSettings.isSupportDualSimIMEI) {
//                rs = KapalaiAdapterUtil.getKAUInstance().getDualSimIMSIInfoMethod(context);
//            } else {
//                rs = KapalaiAdapterUtil.getKAUInstance().getDualSimIMSIInfoNormalMethod(context);
//            }
            if (rs != null && rs.length != 0) {
                return rs;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 隐藏软键盘
     */
    public static void hideKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (((Activity) context).getCurrentFocus() != null)
            imm.hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 显示软键盘
     *
     * @param context
     * @param view
     */
    public static void showKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, 0);

    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 文本颜色 透明度变化
     *
     * @param tv
     * @param startColor
     * @param endColor
     * @param alpha
     */
    public static void setTvColorAlpha(TextView tv, int startColor, int endColor, float alpha) {
        ArgbEvaluator evaluator = new ArgbEvaluator();
        int bgColor = (int) evaluator.evaluate(alpha, startColor, endColor);
        tv.setTextColor(bgColor);
    }

    /**
     * view颜色 透明度变化
     *
     * @param view
     * @param startColor
     * @param endColor
     * @param alpha
     */
    public static void setViewColorAlpha(View view, int startColor, int endColor, float alpha) {
        ArgbEvaluator evaluator = new ArgbEvaluator();
        int bgColor = (int) evaluator.evaluate(alpha, startColor, endColor);
        view.setBackgroundColor(bgColor);
    }

    /**
     * 获取设备的屏幕信息
     *
     * @param activity
     * @return
     */
//    public static DeviceInfo getDevicesPix(Activity activity) {
//        DeviceInfo deviceInfo = new DeviceInfo();
//        DisplayMetrics metric = new DisplayMetrics();
//        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
//        int width = metric.widthPixels;  // 屏幕宽度（像素）
//        int height = metric.heightPixels;  // 屏幕高度（像素）
//        float density = metric.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
//        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
//        deviceInfo.setWidth(width);
//        deviceInfo.setHeight(height);
//        deviceInfo.setDensity(density);
//        deviceInfo.setDensityDpi(densityDpi);
//        return deviceInfo;
//    }
    /**
     * 返回原值
     */
    public static int setInt(int num) {
        return num;
    }
}
