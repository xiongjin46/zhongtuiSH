package com.push.PushMerchant.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.push.PushMerchant.base.PushApplicationContext;

/**
 * @ClassName: SharePrefUtil
 * @Description: 用戶信息存儲
 * @author: YYL
 * <p>
 * create at 2016/11/10 14:42
 */
public class SharePrefUtil {
    private static final String PREF_NAME = "pref.share";// 用在用户信息的存储
    private static final String SETTING = "setting.pref";// 用在系统设置的存储
    private static Context sContext = PushApplicationContext.context;
    private static SharedPreferences sPreferences = sContext.getSharedPreferences(PREF_NAME, 0);
    // 持久化存储，退出登录不会被清除
    private static SharedPreferences sSettings = sContext.getSharedPreferences(SETTING, 0);
    public static String PREFERENCE_NAME = "lzbAndroidCommon";

    /**
     * @throws
     * @Description: 清除用户
     * @param:
     */
    public static void clearUser() {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.clear();
        editor.apply();
        editor.commit();
    }

    /**
     * @throws
     * @Description: 用户信息的存储
     * @param: @param key @param: @param value
     */
    public static void putIntSetting(String key, int value) {
        SharedPreferences.Editor editor = sSettings.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getIntSetting(String key, int value) {
        return sSettings.getInt(key, value);
    }

    public static void putLongSetting(String key, long value) {
        SharedPreferences.Editor editor = sSettings.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static long getLongSetting(String key, long value) {
        return sSettings.getLong(key, value);
    }

    public static void putStringSetting(String key, String value) {
        SharedPreferences.Editor editor = sSettings.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getStringSetting(String key, String def) {
        return sSettings.getString(key, def);
    }

    public static void putBooleanSetting(String key, boolean value) {
        SharedPreferences.Editor editor = sSettings.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getBooleanSetting(String key, boolean def) {
        return sSettings.getBoolean(key, def);
    }

    public static void putIntUser(String key, int value) {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getIntUser(String key, int def) {
        return sPreferences.getInt(key, def);
    }

    public static void putLongUser(String key, long value) {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static long getLongUser(String key, long def) {
        return sPreferences.getLong(key, def);
    }

    public static boolean getBoolenUser(String key, boolean def) {
        return sPreferences.getBoolean(key, def);
    }

    public static void putBooleanUser(String key, boolean value) {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static String getStringUser(String key, String def) {
        return sPreferences.getString(key, def);
    }

    public static void putStringUser(String key, String value) {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static long getLong(Context context, String key) {
        return getLong(context, key, -1);
    }

    public static long getLong(Context context, String key, long defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getLong(key, defaultValue);
    }

    public static boolean putLong(Context context, String key, long value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    public static void clearKey(String key){
        SharedPreferences.Editor editor = sSettings.edit();
        editor.remove(key);
        editor.commit();
    }
    /**
     * 设置引导页为已读
     *
     * @param context
     * @param key
     * @param value
     */
    public static void fire(Context context, String key, boolean value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value).commit();
    }

    /**
     * 获取引导页的状态
     *
     * @param context
     * @param key
     * @return
     */
    public static boolean isFirst(Context context, String key) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getBoolean(key, true);
    }


}
