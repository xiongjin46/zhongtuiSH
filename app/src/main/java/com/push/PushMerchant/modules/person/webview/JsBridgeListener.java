package com.push.PushMerchant.modules.person.webview;

import android.text.TextUtils;
import android.webkit.WebView;

import java.lang.ref.WeakReference;

public class JsBridgeListener {

	WeakReference<WebView> webViewRef;
    String url;
    String callback;

    /**
     *
     * @param wv 所使用的webview
     * @param url 当前回调的url
     */
    public JsBridgeListener(WebView wv, String url, String callback){
        webViewRef = new WeakReference<WebView>(wv);
        this.url = url;
        this.callback = callback;
    }
    /**
     *
     * @param result
     *
     * 正常返回时使用
     */
    public void onComplete(String result) {
        if (TextUtils.isEmpty(callback)) return;

        WebView webView = webViewRef.get();
        if(webView == null || TextUtils.isEmpty(callback)) return;

        String jsUrl = "javascript:void(Jsbridge." + callback + "(" + result + "));";
        webView.loadUrl(jsUrl);
    }
    
    public void onNoMatchMethod() {
        if (TextUtils.isEmpty(callback)) return;

        WebView webView = webViewRef.get();
        if(webView == null || TextUtils.isEmpty(callback)){
            return;
        }
        String jsUrl = "javascript:void(Jsbridge." + callback + "({'ret':1,'result':'no such method'}));";
        webView.loadUrl(jsUrl);
    }
    
    public void onCustomCallback(String js) {
        if (TextUtils.isEmpty(callback)) return;

        WebView wv = webViewRef.get();
        if(wv != null) {
            wv.loadUrl("javascript:"+js);
        }
    }
}
