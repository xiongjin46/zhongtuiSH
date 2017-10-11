package com.push.PushMerchant.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.push.PushMerchant.base.PushApplicationContext;

/**
 * @ClassName: NetworkUtil
 * @Description: 网络相关工具类
 * @author: YYL
 *
 * create at 2016/11/10 15:13
 */
public class NetworkUtil {

    private static boolean isNetworkActive = true;

    // 接入点名称
    private static NetInfo netInfo = new NetInfo();

    /* 中国移动 */
    public static final int OPERATOR_CHINA_MOBILE = 0;
    /* 中国联通 */
    public static final int OPERATOR_CHINA_UNICOM = 1;
    /* 中国电信 */
    public static final int OPERATOR_CHINA_TELECOM = 2;
    /* 未知运营商 */
    public static final int OPERATOR_UNKNOWN = -1;


    public static boolean isNetworkActive() {
        if (netInfo.apn == APN.UN_DETECT||netInfo.apn == APN.NO_NETWORK||netInfo.apn == APN.UNKNOWN) {
            refreshNetwork();
        }
        return isNetworkActive;
    }

    /**
     * 网络连接变化的时候需要重新刷新一下当前的apn
     */
    public static void refreshNetwork() {
        netInfo = getNetInfo(PushApplicationContext.context);
    }

    /* 获取接入点名称 Access Point Name , context不能为null */
    public static NetInfo getNetInfo(Context context) {
        NetInfo result = new NetInfo();
        NetworkInfo info = null;
        ConnectivityManager connManager = null;
        try {
            connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            // OPPO的机器上这个地方会抛异常crash+
            if (connManager != null)
                info = connManager.getActiveNetworkInfo();
            if (info == null || !info.isAvailable()) {
                isNetworkActive = false;
                result.apn = APN.NO_NETWORK;
                return result;
            }
        } catch (Throwable e) {
            //增加error捕获，部分酷派机器上可能没有getActiveNetworkInfo方法
        }

        isNetworkActive = true;
        if (info != null && info.getType() == ConnectivityManager.TYPE_WIFI) {// wifi网络判定
            result.apn = APN.WIFI;

            // 为NetInfo添加路由器mac地址与wifi名称 20140319 by gengeng
            WifiManager wifiManager = (WifiManager) PushApplicationContext.context.getSystemService(Context.WIFI_SERVICE);
            if (wifiManager != null) {
                try {
                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                    if (wifiInfo != null) {
                        result.bssid = wifiInfo.getBSSID();
                        result.ssid = wifiInfo.getSSID();
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }

            return result;
        } else {
            return getMobileNetInfo(context);
        }
    }


    /* 判断是否是wap类网络 */
    public static boolean isWap() {
        String host = Proxy.getDefaultHost();
        return !TextUtils.isEmpty(host);
    }
    /* 获取移动网络接入点类型 , context不能为null */
    public static NetInfo getMobileNetInfo(Context context) {
        NetInfo result = new NetInfo();
        final boolean isWap = isWap();
        result.isWap = isWap;                                        // iswap赋值

        TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        final String networkOperator = telManager.getNetworkOperator();
        result.networkOperator = networkOperator;                    // networkOperator赋值
        final int networkType = telManager.getNetworkType();
        result.networkType = networkType;                            // networkType赋值

        final int operator = getSimOperator(networkOperator);
        switch (operator) {
            case OPERATOR_CHINA_MOBILE: // 中国移动
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                        if (isWap) {
                            result.apn = APN.CMWAP;
                        } else {
                            result.apn = APN.CMNET;
                        }
                        return result;
                    default:
                        if (isWap) {
                            result.apn = APN.UNKNOW_WAP;
                        } else {
                            result.apn = APN.UNKNOWN;
                        }
                        return result;
                }
            case OPERATOR_CHINA_UNICOM: // 中国联通
                // 先判断是2g还是3g网络
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:    // 联通3g
                    case 15:                                    // TelephonyManager.NETWORK_TYPE_HSPAP api 13+
                        if (isWap) {
                            result.apn = APN.WAP3G;
                        } else {
                            result.apn = APN.NET3G;
                        }
                        return result;
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE: // 联通2g
                        if (isWap) {
                            result.apn = APN.UNIWAP;
                        } else {
                            result.apn = APN.UNINET;
                        }
                        return result;
                    default:
                        if (isWap) {
                            result.apn = APN.UNKNOW_WAP;
                        } else {
                            result.apn = APN.UNKNOWN;
                        }
                        return result;
                }
            case OPERATOR_CHINA_TELECOM: // 中国电信
                if (isWap) {
                    result.apn = APN.CTWAP;
                } else {
                    result.apn = APN.CTNET;
                }
                return result;
            default:
                if (isWap) {
                    result.apn = APN.UNKNOW_WAP;
                } else {
                    result.apn = APN.UNKNOWN;
                }
                return result;
        }
    }

    /* 获取移动网络运营商 , context不能为null */
    public static int getSimOperator(String networkOperator) {
        if (!TextUtils.isEmpty(networkOperator)) {
            if (networkOperator.equals("46000") || networkOperator.equals("46002") || networkOperator.equals("46007")) {
                return OPERATOR_CHINA_MOBILE;
            } else if (networkOperator.equals("46001")) {
                return OPERATOR_CHINA_UNICOM;
            } else if (networkOperator.equals("46003")) {
                return OPERATOR_CHINA_TELECOM;
            }
        }
        return OPERATOR_UNKNOWN;
    }
}
