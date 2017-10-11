package com.push.PushMerchant.modules.person.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import com.push.PushMerchant.base.BaseToolbarActivity;
import com.push.PushMerchant.util.NetworkUtil;
import com.push.PushMerchant.view.EmptyView;

import java.lang.reflect.InvocationTargetException;

/**
 * @ClassName: BaseBrowserActivity
 * @Description: web 处理基类
 * @author: YYL
 * <p>
 * create at 2016/11/25 16:36
 */
public abstract class BaseBrowserActivity extends BaseToolbarActivity {
    private static final String TAG = "BaseBrowserActivity";

    public static final int FLAG_HARDWARE_ACCELERATED = 16777216;
    private final static int FILECHOOSER_RESULTCODE = 1;
    protected static final String MX2 = "Meizu_M040";

    protected WebViewClientImpl mWebViewClient;
    protected WebChromClientImpl mWebChromeClient;

    protected WebView mWebView;
    protected EmptyView mErrorView;
    protected ProgressBar mLoadingView;

    private boolean mReceivedError;

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mWebChromeClient = null;
        mWebViewClient = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            mWebView.getClass().getMethod("onResume").invoke(mWebView, (Object[]) null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            mWebView.getClass().getMethod("onPause").invoke(mWebView, (Object[]) null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        mWebViewClient = new WebViewClientImpl();
        mWebChromeClient = new WebChromClientImpl();

        mWebView = getWebView();
        mErrorView = getErrorView();
        mLoadingView = getLoadingView();

        mErrorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mReceivedError = false;
                mWebView.reload();
            }
        });
        if (mWebView == null) {
            throw new RuntimeException("webview is null.");
        }

        String mark = Build.MANUFACTURER + "_"
                + Build.MODEL;
        try {
            if (Build.VERSION.SDK_INT >= 11 && !MX2.equals(mark)) {
                //开启硬件加速
//                getWindow().addFlags(FLAG_HARDWARE_ACCELERATED);
                getWindow().setFlags(
                        WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                        WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            }
        } catch (Exception e) {
        }

        initWebView();
    }

    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    private void initWebView() {
        mWebView.clearCache(true);

        //下面4行 设置webview获取焦点
        mWebView.requestFocus();
        mWebView.requestFocusFromTouch();
        mWebView.setFocusable(true);
        mWebView.setFocusableInTouchMode(true);

        mWebView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebView.setVerticalScrollBarEnabled(false);    //取消纵向滚动条
        mWebView.setHorizontalScrollBarEnabled(false);  //取消横向滚动条
        mWebView.setDrawingCacheEnabled(true);
        // 屏蔽长按事件
        mWebView.setOnCreateContextMenuListener(null);
        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                try {
                    startActivity(intent);
                } catch (Exception e) {
                }
            }
        });

        WebSettings settings = mWebView.getSettings();
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSavePassword(false);
        settings.setSaveFormData(false);
        settings.setGeolocationEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);

        settings.setAllowContentAccess(true);

        settings.setAllowFileAccess(true);  // 允许访问文件
        settings.setLoadsImagesAutomatically(true); //自定加载图片资源
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);


        settings.setSupportZoom(false);      //支持缩放
//        // 开启本地存储
        settings.setDatabaseEnabled(true);
        String dbDir = this.getApplicationContext().getDir("storage", Context.MODE_WORLD_WRITEABLE)
                .getPath();
        settings.setDatabasePath(dbDir);
        // 开启应用程序缓存
        settings.setAppCacheEnabled(true);
        String cacheDir = this.getApplicationContext().getDir("cache", Context.MODE_WORLD_WRITEABLE)
                .getPath();
        settings.setAppCachePath(cacheDir);


//        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
//        settings.setBlockNetworkImage(true);
//        blockLoadingNetworkImage=true;
//        settings.setBlockNetworkImage(true);
//        settings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
//        if (VersionUtils.isHoneycomb()) {
//            mWebView.removeJavascriptInterface("searchBoxJavaBridge_");
//        }

        mWebView.setWebViewClient(mWebViewClient);  //在webView中打开点击的链接
        mWebView.setWebChromeClient(mWebChromeClient);  //显示加载进度
//        mWebView.setWebChromeClient(new WebChromeClient());

    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }


    protected abstract WebView getWebView();

    protected abstract EmptyView getErrorView();

    protected abstract ProgressBar getLoadingView();

    public boolean shouldOverrideUrlLoading(WebView view, String url) {


        return false;
    }

    public void onPageStarted(WebView view, String url, Bitmap favicon) {
    }

    public void onPageFinished(WebView view, String url) {
        if (mReceivedError) return;
        BaseBrowserActivity.this.onReceivedTitle(view, view.getTitle());
        mWebView.setVisibility(View.VISIBLE);
        mErrorView.setVisibility(View.GONE);
        mLoadingView.setVisibility(View.GONE);
    }

    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        if (!NetworkUtil.isNetworkActive() || errorCode == -2) {
            //-2, 找不到网页
            mReceivedError = true;
//            setWebTitle("");
        }
        mWebView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.VISIBLE);
    }

    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.cancel();
    }

    public void onProgressChanged(WebView view, int newProgress) {
        if (newProgress < 100) {
            mLoadingView.setProgress(newProgress);
            mLoadingView.setVisibility(View.VISIBLE);
        } else {
            mLoadingView.setVisibility(View.GONE);
            mWebView.setVisibility(View.VISIBLE);
        }
    }

    public void onReceivedTitle(WebView view, String title) {
        if (mReceivedError) return;
//        setWebTitle(title);
        setDefaultBack("");
        setTitleString("用户服务协议");
    }

    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
//        final NormalDialog normalDialog = DialogUtil.CreateDefaultNoTitleDialog(this, message, getString(R.string.update_no), getString(R.string.ok));
//        normalDialog.setOnBtnClickL(new BaseDialog.OnBtnClickL() {
//            @Override
//            public void onBtnClick() {  //忽略
//                normalDialog.dismiss();
//
//            }
//        }, new BaseDialog.OnBtnClickL() {
//            @Override
//            public void onBtnClick() {  //确定
//                normalDialog.dismiss();
//            }
//        });
//        normalDialog.show();


        result.cancel();
        return true;
    }


    protected class WebChromClientImpl extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            BaseBrowserActivity.this.onProgressChanged(view, newProgress);
//            if(blockLoadingNetworkImage)
//            {
//                mWebView.getSettings().setBlockNetworkImage(false);
//                blockLoadingNetworkImage=false;
//            }

        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            BaseBrowserActivity.this.onReceivedTitle(view, title);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            return BaseBrowserActivity.this.onJsAlert(view, url, message, result);
        }

    }

    protected class WebViewClientImpl extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // FIXME: 15/11/30 这里应该要跳进linkproxyactivity的
//            Uri uri = Uri.parse(url);
//            if (AppInterface.HOST_LZB.equals(uri.getScheme())) {//进入活动详情页
//                if (uri.getHost().equals(AppInterface.HOST_EVENT)) {
//                    String eventid = uri.getQueryParameter("event_id");
//                    JumpUtil.toEventDetails(BaseBrowserActivity.this, Integer.parseInt(eventid));
//                } else if (uri.getHost().equals(AppInterface.HOST_TRIBE)) {//进入指定部落
//                    String tribe_id = uri.getQueryParameter("tribe_id");
//                    JumpUtil.toTribeActivity(BaseBrowserActivity.this, Integer.valueOf(tribe_id));
//                }
//                return true;
//            }
            return BaseBrowserActivity.this.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            BaseBrowserActivity.this.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            BaseBrowserActivity.this.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            BaseBrowserActivity.this.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onReceivedSslError(WebView view, @NonNull SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
            BaseBrowserActivity.this.onReceivedSslError(view, handler, error);
        }
    }
}
