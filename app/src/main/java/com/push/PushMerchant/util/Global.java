package com.push.PushMerchant.util;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.push.PushMerchant.BuildConfig;
import com.push.PushMerchant.constants.GConstants;


/**
 * @ClassName: Global
 * @Description: 版本控制
 * @author: YYL
 * <p>
 * create at 2016/11/10 14:21
 */
public class Global {

    /**
     * DEBGU版本
     */
    public static final int APP_DEV = 0;
    /**
     * ALPHA测试版本
     */
    public static final int APP_ALPHA = 2;
    /**
     * 发布版本
     */
    public static final int APP_RELEASE = 1;

    public static int sAppStatus = -1;

    private static String sAndroidid;
    private static String sImei;
    private static String sImsi;
    private static String sImei2;
    private static String sImsi2;

    public static void init() {
        genPhoneTerminal();
        sAppStatus = SharePrefUtil.getIntSetting(GConstants.KEY_ENVIRONMENT, -1);
    }

    public static boolean isDev() {
        return APP_DEV == BuildConfig.APP_STATUS;
    }

    public static boolean isRelease() {
        return BuildConfig.APP_STATUS == APP_RELEASE;
    }

    public static String getAndroidid() {
        if (TextUtils.isEmpty(sAndroidid)) {
            init();
        }
        return sAndroidid;
    }

    private static void genPhoneTerminal() {
        final String androidId = DeviceUtils.getAndroidIdInPhone();
        sAndroidid = androidId;
        String[] imeiInfo = new String[2];
        imeiInfo = DeviceUtils.getImeiFromNative();
        if (DeviceUtils.isAvailableImeiImsi(imeiInfo, 0)) {
            sImei = imeiInfo[0];
        } else {
            sImei = DeviceUtils.getImei();
        }
        if (DeviceUtils.isAvailableImeiImsi(imeiInfo, 1)) {
            sImei2 = imeiInfo[1];
        }
        String[] imsiInfo = new String[2];
        imsiInfo = DeviceUtils.getImsiFromNative();
        if (DeviceUtils.isAvailableImeiImsi(imsiInfo, 0)) {
            sImsi = imsiInfo[0];
        } else {
            sImsi = DeviceUtils.getImsi();
        }
        if (DeviceUtils.isAvailableImeiImsi(imsiInfo, 1)) {
            sImsi2 = imsiInfo[1];
        }
    }

    public static String getCommonUrl() {
        String cgi = "";
        if (sAppStatus != -1) {
            switch (sAppStatus) {
                case APP_ALPHA:
                    cgi = GConstants.CGI_TEST;
                    break;
                case APP_DEV:
                    cgi = GConstants.CGI_DEV;
                    break;
                case APP_RELEASE:
                    cgi = GConstants.CGI_RELEASE;
                    break;
            }
        } else {
            if (BuildConfig.APP_STATUS == APP_DEV) {
                cgi = GConstants.CGI_DEV;
            } else if (BuildConfig.APP_STATUS == APP_ALPHA) {
                cgi = GConstants.CGI_TEST;
            } else {
                cgi = GConstants.CGI_RELEASE;
            }
        }
        return cgi;
    }

    public static String getOSSPath() {
        String cgi = "";
        if (sAppStatus != -1) {
            switch (sAppStatus) {
                case APP_ALPHA:
                    cgi = GConstants.OSS_TEST;
                    break;
                case APP_DEV:
                    cgi = GConstants.OSS_DEV;
                    break;
                case APP_RELEASE:
                    cgi = GConstants.OSS_ONLINE;
                    break;
            }
        } else {
            if (BuildConfig.APP_STATUS == APP_DEV) {
                cgi = GConstants.OSS_DEV;
            } else if (BuildConfig.APP_STATUS == APP_ALPHA) {
                cgi = GConstants.OSS_TEST;
            } else {
                cgi = GConstants.OSS_ONLINE;
            }
        }
        return cgi;
    }

    public static String getMessageSuffix() {
        String suffix = "";
        if (sAppStatus != -1) {
            switch (sAppStatus) {
                case APP_ALPHA:
                    suffix = "_test";
                    break;
                case APP_DEV:
                    suffix = "_dev";
                    break;
                case APP_RELEASE:
                    suffix = "_release";
                    break;
            }
        } else {
            if (BuildConfig.APP_STATUS == APP_DEV) {
                suffix = "_dev";
            } else if (BuildConfig.APP_STATUS == APP_ALPHA) {
                suffix = "_test";
            } else {
                suffix = "_release";
            }
        }
        return suffix;
    }

    public static void setEnvironment(int status) {
        sAppStatus = status;
        SharePrefUtil.putIntSetting(GConstants.KEY_ENVIRONMENT, status);
    }

    public static int getEnvironment() {
        if (sAppStatus != -1) {
            return sAppStatus;
        }

        return BuildConfig.APP_STATUS;
    }

    //版本名
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    //版本号
    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }


    public static String getAgreement() {
        return GConstants.AGREEMENT;
    }
}
