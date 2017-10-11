package com.push.PushMerchant.modules.person.webview;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;


import com.push.PushMerchant.R;
import com.push.PushMerchant.constants.IntentFlag;
import com.push.PushMerchant.network.BaseModuleEngine;
import com.push.PushMerchant.util.Global;
import com.push.PushMerchant.view.EmptyView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2016/11/25.
 */

public class BrowserActivity extends BaseBrowserActivity {

    private String mUrl;
//
//    public class JavaScriptObject implements Serializable {
//        Context mContxt;
//
//        //sdk17版本以上加上注解
//        public JavaScriptObject(Context mContxt) {
//            this.mContxt = mContxt;
//        }
//
//        提供与js与原生交互的方法
//        @JavascriptInterface
//        public String getUserToken() {
//            String accessToken = AccountManager.getInstance().getAccessToken();
//            return accessToken;
//        }
//    }


    private void handleIntent(Intent intent) {
        String urlStr = intent.getStringExtra(IntentFlag.KEY_URL);
        if (!isLegalUri(urlStr)) {
            finish();
            return;
        }
        loadUrl(urlStr);
    }

    private void loadUrl(String url) {
        //@author zzh
        Map<String, String> headers = new HashMap<>();
        String xToken = "";
        headers.put(BaseModuleEngine.X_ACCESSTOKEN, xToken == null ? "" : xToken);
        headers.put(BaseModuleEngine.GUID, Global.getAndroidid());
        headers.put(BaseModuleEngine.WNUSERAGENT, "");
        if (url.equals(Global.getAgreement())) {
            setTitle("用户服务协议");
            // 服务协议设置这种方式
            WebSettings settings = mWebView.getSettings();
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        }
        if (url.contains("coupon.html")) {
//            url = url + "?perserCode=" + AccountManager.getInstance().getUserEntity().getObject().getPersonCode();
            mWebView.loadUrl(url);
        } else {
            mWebView.loadUrl(url);
        }
        mUrl = url;
//        ToastUtils.showToast(mUrl);
    }

    private boolean isLegalUri(String url) {
        if (TextUtils.isEmpty(url)) return false;

        Uri uri = Uri.parse(url.toString().trim());
        String schema = uri.getScheme();
        if ("http".equals(schema) || "https".equals(schema) || "file".equals(schema)) {
            return true;
        }

        return false;
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    @Override
    protected WebView getWebView() {
        if (mWebView == null) {
            mWebView = (WebView) findViewById(R.id.browser_web);
        }
        return mWebView;
    }

    @Override
    protected EmptyView getErrorView() {
        if (mErrorView == null) {
            mErrorView = (EmptyView) findViewById(R.id.list_empty_root);
        }
        return mErrorView;
    }

    @Override
    protected ProgressBar getLoadingView() {
        if (mLoadingView == null) {
            mLoadingView = (ProgressBar) findViewById(R.id.browser_progress);
        }
        return mLoadingView;
    }

    /**
     * h5界面加载完毕后,在当前界面需要执行的方法,例如加载js,修改界面
     *
     * @param view
     * @param url
     */
    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
//        Uri uri = Uri.parse(url.toString().trim());
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
//        if (mJsBridge.canHandleUrl(view, url)) {
//            return true;
//        }

        if (url.startsWith("tel:")) {
            try {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                startActivity(intent);
            } catch (Exception e) {
            }
            return true;
        }

        if (url.equals("about:blank;") || url.equals("about:blank")) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                // 3.0以下调用jsbridge会产生一个about:blank，并呼起系统浏览器
                return true;
            } else {
                return false;
            }
        }

        loadUrl(url);
        return true;
    }

    @Override
    protected void initDataBeforeView() {

    }

    @Override
    protected int loadViewLayout() {
        return R.layout.ac_browser;
    }

    @Override
    protected void initDataAfterView() {
//        mWebView.addJavascriptInterface(new JavaScriptObject(this), "lzb_android");
        handleIntent(getIntent());
    }
}
