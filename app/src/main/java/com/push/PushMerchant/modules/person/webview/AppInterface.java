package com.push.PushMerchant.modules.person.webview;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;


import com.push.PushMerchant.base.PushApplicationContext;

import java.util.HashMap;
import java.util.Map;

public class AppInterface extends JsHandler {
    private static final String TAG = "AppInterface";
    private static final String PKG_WEIXIN = "com.tencent.mm";
    private static final String PKG_ALI = "com.eg.android.AlipayGphone";

    // 给H5相关返回码
    public static final int RET_OK = 0;
    public static final int RET_CANCEL = 1;
    public static final int RET_FAIL = -1;
    public static final int RET_EXECTPITON = -2;
    public static final int RET_ERROR = -3;

    public static final String KEY_RETURN = "ret";
    public static final String KEY_SHARE_TYPE = "sharetype";
    public static final String KEY_LOGIN_TYPE = "logintype";
    public static final String KEY_CONTENT = "content";
    public static final String KEY_TITLE = "title";
    public static final String KEY_IMAGE_URL = "imageurl";
    public static final String KEY_LINK = "link";

    // 登录类型
    public static final int LOGIN_WECHAT = 1;
    public static final int LOGIN_QQ = 2;
    public static final int LOGIN_PHONE = 3;
    public static final int LOGIN_WEIBO = 4;

    public static final int REQUEST_CODE_LOGIN = 10000;
    public static final int REQUEST_CODE_SHARE = 10001;

    public static final String HOST_LZB = "lzb";
    public static final String HOST_EVENT = "event"; //活动
    public static final String HOST_TRIBE = "tribe"; //部落


    public static final String HOST_SHARE = "share";//右上角分享按钮的显示与否


    public static final String HOST_LOGIN = "login";

    public static final String HOST_WXPAY = "wxpay";
    public static final String HOST_ALIPAY = "alipay";

    public static final String HOST_APP_STATE = "appinstallstate";
    public static final String HOST_CLOSE_WEBVIEW = "closewebview";
    public static final String HOST_SHOW_TITLE = "showtitlebar";

    public static final String[] PROCESS_HOSTS = new String[]{
            HOST_LOGIN,
            HOST_SHARE,
            HOST_CLOSE_WEBVIEW,
            HOST_WXPAY,
            HOST_ALIPAY,
            HOST_CLOSE_WEBVIEW,
            HOST_SHOW_TITLE,
            HOST_APP_STATE
    };

    public AppInterface() {
//        WeixinPayEntry.getInstance().registerListener(this);
//        AliPayEntry.getInstance().registerListener(this);
    }

    @Override
    public void release() {
        super.release();

//        WeixinPayEntry.getInstance().unregisterListener(this);
//        AliPayEntry.getInstance().unregisterListener(this);
    }

    @Override
    public String getInterfaceName() {
        return TAG;
    }


    /**
     * 检查app是否安装
     */
    public String appInstallState(Map<String, String> args) {
        String packageName = args.get("package");

        int result = 0;
        if (!isAppInstall(packageName)) {
            // app 未安装
            result = 0;
        } else {
            result = 1;
        }

        HashMap<String, String> map = new HashMap<String, String>();
        map.put(KEY_RETURN, result + "");

        return generateResponse(map);
    }

//    /**
//     * 关闭当前webview
//     *
//     * @return
//     */
//    public void closeWebView() {
//        if (BaseActivity.sTopActivity instanceof BrowserActivity) {
//            BaseActivity.sTopActivity.finish();
//        }
//    }


    private boolean isAppInstall(String packageName) {
        PackageManager pm = PushApplicationContext.context.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(packageName, 0);
        } catch (Exception e) {
        }

        return pi != null;
    }

    /**
     * 是否需要当前interface处理，如果不需要，丢给LinkProxyActivity处理
     *
     * @param host
     * @return
     */
    public boolean canHandleHost(String host) {
        for (String h : PROCESS_HOSTS) {
            if (h.equals(host)) {
                return true;
            }
        }

        return false;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;

        if (requestCode == REQUEST_CODE_LOGIN) {
            int loginType = data.getIntExtra(KEY_LOGIN_TYPE, 0);

            HashMap<String, String> map = new HashMap<String, String>();
            map.put(KEY_RETURN, RET_OK + "");
            map.put(KEY_LOGIN_TYPE, loginType + "");
            String result = generateResponse(map);

            jsBridgeListener.onComplete(result);
        } else if (requestCode == REQUEST_CODE_SHARE) {
            int shareType = data.getIntExtra(KEY_SHARE_TYPE, 0);
            int ret = data.getIntExtra(KEY_RETURN, 0);

            HashMap<String, String> map = new HashMap<String, String>();
            map.put(KEY_RETURN, ret + "");
            map.put(KEY_SHARE_TYPE, shareType + "");
            String result = generateResponse(map);
            jsBridgeListener.onComplete(result);
        }
    }

}
