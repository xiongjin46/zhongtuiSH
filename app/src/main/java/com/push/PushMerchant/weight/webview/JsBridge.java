package com.push.PushMerchant.weight.webview;

import android.net.Uri;
import android.text.TextUtils;
import android.webkit.WebView;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Js与Native交互桥梁
 */
public class JsBridge {
    private static final String TAG = "JsBridge";

    private static final int INDEX_CALLBACK = 0;


    //    HashMap<String, JsHandler> jsHandlers;
    public AppInterface jsHandler;

    public JsBridge() {
//        jsHandlers = new HashMap<String, JsHandler>();
        jsHandler = new AppInterface();
    }

    public void release() {
        jsHandler.release();
    }

    /*public void registerHandler(JsHandler jsHandler, String objName) {
        jsHandlers.put(objName, jsHandler);
    }

    */

    /**
     * 删除注册的handler, objectName为null表示删除所有handler(为减少方法数，这里不加clearAllHandler了)
     *//*
    public void unregisterHandler(String objectName) {
        if (objectName == null) {
            jsHandlers.clear();
        } else {
            jsHandlers.remove(objectName);
        }
    }*/
    public void invoke(String methodName, Map<String, String> args, final JsBridgeListener listener) {
        JsHandler handler = jsHandler;
        jsHandler.jsBridgeListener = listener;
        if (handler != null) {
            // 存在对应的handler，由handler处理
            handler.invoke(methodName, args, listener);
        } else {
            // 未找到handler ，回调告知无此API
            if (listener != null) {
                listener.onNoMatchMethod();
            }
        }
    }

    public boolean canHandleUrl(WebView webView, String url) {
        if (url == null) return false;

        final Uri uri = Uri.parse(url);
        if (!uri.getScheme().equals("zhuazhua")) return false;

        String query = uri.getEncodedQuery();
        // zhuazhua://methodname/callback?key1=11&key2=22
        HashMap<String, String> args = new HashMap<String, String>();
        try {
            if (!TextUtils.isEmpty(query)) {
                String[] array = query.split("&");
                if (array != null) {
                    for (int i = 0; i < array.length; i++) {
                        String data = array[i];
                        String[] kv = data.split("=");
                        args.put(kv[0], URLDecoder.decode(kv[1]));
                    }
                }
            }
        } catch (Throwable e) {
        }

        List<String> segments = uri.getPathSegments();
        String methodName = uri.getHost();
        String callback = null;
        if (!jsHandler.canHandleHost(methodName)) {
//            JumpUtil.jumpLinkProxyActivity(BaseActivity.sTopActivity, url);
            return true;
        }
        if (segments.size() > INDEX_CALLBACK) {
            callback = segments.get(INDEX_CALLBACK);
        }

        invoke(methodName, args, new JsBridgeListener(webView, url, callback));
        return true;
    }
}
