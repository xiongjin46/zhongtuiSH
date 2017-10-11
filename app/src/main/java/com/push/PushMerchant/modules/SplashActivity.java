package com.push.PushMerchant.modules;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.push.PushMerchant.R;
import com.push.PushMerchant.constants.IntentFlag;
import com.push.PushMerchant.network.ThreadManager;
import com.push.PushMerchant.util.FileUtil;
import com.push.PushMerchant.util.Global;
import com.push.PushMerchant.util.JumpUtil;
import com.push.PushMerchant.util.SharePrefUtil;

/**
 * Created by Administrator on 2017/6/23 0023.
 */

public class SplashActivity extends Activity {

    private ImageView splashimg;
    private long sleepTime = 1000;
    private String savePathName;
    public long startTime;
    private long endTime;
    private String mAccessToken;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_splash);
        initView();
        initData();
    }

    private void initData() {
        // 定位初始化
        LocationManager.getInstance().start();
        String sp = SharePrefUtil.getStringSetting(IntentFlag.SPLASHURL, "");

        if (TextUtils.isEmpty(sp)) {
            splashimg.setImageResource(R.mipmap.advert);
        } else {
            savePathName = FileUtil.getImagePath() + "/splash_" + Global.getMessageSuffix() + ".jpg";
//            ImageLoader.load("file://" + savePathName, splashimg, 0, this);
            Glide.with(SplashActivity.this).load("file://" + savePathName).asBitmap().placeholder(R.mipmap.advert).error(R.mipmap.advert)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            endTime = System.currentTimeMillis() - startTime;
                            if (sleepTime - endTime > 0) {
                                if (endTime < 1000) {
                                    try {
                                        Thread.sleep(1000 - endTime);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                splashimg.setImageBitmap(resource);
                            }
                        }
                    });
        }
        InitManager.getsInstance().loadAppVersion();
    }

    private void initView() {
        splashimg = (ImageView) findViewById(R.id.splashimg);
        splashimg.setImageResource(R.mipmap.advert);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ThreadManager.getMainThreadHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                JumpUtil.toHome(SplashActivity.this);
                finish();
            }
        },sleepTime);
    }
}
