package com.push.PushMerchant.weight.webview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.push.PushMerchant.base.PushApplicationContext;

/**
 * @ClassName: com.jiaoao.lzb.lzbApplication.modules.webview
 * @Description:
 * @author: YYL
 * create at 2017-04-15 下午 7:58
 */
@SuppressLint("JavascriptInterface")
public class BaseWebView extends WebView {

    protected static final String MX2 = "Meizu_M040";


    public BaseWebView(Context context) {
        this(context, null);
    }

    public BaseWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {

        String mark = Build.MANUFACTURER + "_"
                + Build.MODEL;
        try {
            if (Build.VERSION.SDK_INT >= 11 && !MX2.equals(mark)) {
                //开启硬件加速
//                getWindow().addFlags(FLAG_HARDWARE_ACCELERATED);
                ((Activity)getContext()).getWindow().setFlags(
                        WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                        WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            }
        } catch (Exception e) {
        }

        initWebView();
    }

    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    private void initWebView() {
        clearCache(true);

        //下面4行 设置webview获取焦点
        requestFocus();
        requestFocusFromTouch();
        setFocusable(true);
        setFocusableInTouchMode(true);

        setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        setVerticalScrollBarEnabled(false);    //取消纵向滚动条
        setHorizontalScrollBarEnabled(false);  //取消横向滚动条
        setDrawingCacheEnabled(true);
        // 屏蔽长按事件
        setOnCreateContextMenuListener(null);

//        setDownloadListener(new DownloadListener() {
//            @Override
//            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse(url));
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                try {
//                    startActivity(intent);
//                } catch (Exception e) {
//                }
//            }
//        });

        WebSettings settings = getSettings();
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setUseWideViewPort(false);
        settings.setLoadWithOverviewMode(true);
        settings.setSavePassword(false);
        settings.setSaveFormData(false);
        settings.setGeolocationEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);

        settings.setAllowContentAccess(true);

        settings.setAllowFileAccess(true);  // 允许访问文件
        settings.setLoadsImagesAutomatically(true); //自定加载图片资源
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);  //就是这句


        settings.setSupportZoom(false);      //支持缩放
//        // 开启本地存储
        settings.setDatabaseEnabled(true);
        String dbDir = PushApplicationContext.context.getDir("storage", Context.MODE_WORLD_WRITEABLE)
                .getPath();
        settings.setDatabasePath(dbDir);
        // 开启应用程序缓存
        settings.setAppCacheEnabled(true);
        String cacheDir = PushApplicationContext.context.getDir("cache", Context.MODE_WORLD_WRITEABLE)
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

//        mWebView.setWebChromeClient(new WebChromeClient());

    }

}
