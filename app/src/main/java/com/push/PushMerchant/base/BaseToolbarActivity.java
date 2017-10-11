package com.push.PushMerchant.base;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.push.PushMerchant.R;
import com.push.PushMerchant.network.BaseEngine;
import com.push.PushMerchant.network.impl.ActionCallback;
import com.push.PushMerchant.util.DeviceUtils;
import com.push.PushMerchant.util.StatusBarUtil;
import com.push.PushMerchant.view.IconFontTextView;
import com.push.PushMerchant.view.theme.ColorView;
import com.push.PushMerchant.weight.ToolBarHelper;
import com.push.PushMerchant.weight.impl.ITitleBar;

import java.util.ArrayList;

import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Administrator on 2017/6/20 0020.
 */

public abstract class BaseToolbarActivity extends BaseActivity implements ITitleBar {
    private int statusResult;
    protected ColorView status_bar;
    private boolean isShow = true;  //是否显示Toolbar
    protected Toolbar toolBar;
    public ToolBarHelper toolBarHelper;
    private TextView toolbar_title;
    private LinearLayout home_search_ll;
    private Bundle saveInstanceState;
    private View view1;
    private RelativeLayout toolbar_back;
    public IconFontTextView toolbar_back_tv;
    public TextView toolbar_back_last_tv;
    private TextView toolbar_fun1;
    private ImageView toolbar_close;
    private RelativeLayout toolbar_fun2;
    private TextView toolbar_fun2_tv;
    private IconFontTextView toolbar_fun2_fonttv;
    private ArrayList<ActionCallback> callback;
    private ArrayList<BaseEngine> engine;
    public boolean hasNext;
    public boolean isload;
    public int page = 1;
    protected PtrFrameLayout frame;
    public TextView parent_page,tv_back,tv_save;
    public AlphaAnimation in = new AlphaAnimation(0, 1);
    public AlphaAnimation out = new AlphaAnimation(1, 0);
    public FrameLayout parent;
    public ImageView parentbg;
    public ViewPager parent_vp;

    public boolean isShowStatusBar() {
        return ShowStatusBar;
    }

    private boolean ShowStatusBar = true;

    public void setShowStatusBar(boolean showStatusBar) {
        ShowStatusBar = showStatusBar;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBeforeView();
        setStatusTextColor();
        if (loadViewLayout() > 0) {
            setContentView(loadViewLayout());
            ButterKnife.bind(this);
        }
        /*改变状态栏*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        this.saveInstanceState = savedInstanceState;
        callback = new ArrayList<>();
        engine = new ArrayList<>();
        initDataAfterView();
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        toolBarHelper = new ToolBarHelper(this, layoutResID, !isShow);
        if (isShow) {   //只有需要显示toolbar的界面将toolbar添加到主布局中
            toolBarHelper.initToolBar();
        }
        toolBar = toolBarHelper.getToolBar();
        setContentView(toolBarHelper.getContentView());
         /*把 toolbar 设置到Activity 中*/
        if (isShow) {
            setSupportActionBar(toolBar);
        }
        /*自定义的一些操作*/
        onCreateCustomToolBar(toolBar);
        initPhotoView();
    }

    public void onCreateCustomToolBar(Toolbar toolbar) {
        toolbar.setContentInsetsRelative(0, 0);
        toolbar.showOverflowMenu();
        ViewGroup.LayoutParams layoutParams = toolbar.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.height = DeviceUtils.dp2px(this, 48) + DeviceUtils.getStatusHeight(this);
            toolbar.setLayoutParams(layoutParams);
        }

        View inflate = getLayoutInflater().inflate(R.layout.toolbar_custom, toolbar);
        status_bar = (ColorView) inflate.findViewById(R.id.status_bar);
        toolbar_title = (TextView) inflate.findViewById(R.id.toolbar_title);
        view1 = inflate.findViewById(R.id.view1);
        toolbar_back = (RelativeLayout) inflate.findViewById(R.id.toolbar_back);
        toolbar_back_tv = (IconFontTextView) inflate.findViewById(R.id.toolbar_back_tv);
        toolbar_back_last_tv = (TextView) inflate.findViewById(R.id.toolbar_back_last_tv);
//        toolbar_fun1 = (TextView) inflate.findViewById(R.id.toolbar_fun1);
//        toolbar_close = (ImageView) inflate.findViewById(R.id.toolbar_close);
        toolbar_fun2 = (RelativeLayout) inflate.findViewById(R.id.toolbar_fun2);
        toolbar_fun2_tv = (TextView) inflate.findViewById(R.id.toolbar_fun2_tv);
        toolbar_fun2_fonttv = (IconFontTextView) inflate.findViewById(R.id.toolbar_fun2_fonttv);

        toolbar_back.setOnClickListener(onClickListener);
        toolbar_back.setOnClickListener(onClickListener);
        toolbar_back_last_tv.setOnClickListener(onClickListener);
        toolbar_fun2_tv.setOnClickListener(onClickListener);
        toolbar_fun2_fonttv.setOnClickListener(onClickListener);

        if (statusResult == 0) {
            status_bar.setBackgroundColor(getResources().getColor(R.color.colorC0));
        }
        ViewGroup.LayoutParams layoutParams1 = status_bar.getLayoutParams();
        layoutParams1.height = DeviceUtils.getStatusHeight(this);
        status_bar.setLayoutParams(layoutParams1);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    onBackButtonClick();
                    break;
                case R.id.toolbar_fun2_tv:
                    onFun2TextClick();
                    break;
                case R.id.toolbar_fun2_fonttv:
                    onFun2ButtonClick();
                    break;
            }
        }
    };

    /**
     * 设置状态栏文字颜色
     */
    public void setStatusTextColor() {
        if (isShowStatusBar()) {
            statusResult = StatusBarUtil.StatusBarLightMode(this);
            if (statusResult == 0 && status_bar != null) {
                status_bar.setBackgroundColor(getResources().getColor(R.color.colorC0));
            }
        } else {
            StatusBarUtil.StatusBarDarkMode(this, statusResult);
        }
    }

    private void initPhotoView() {
        toolBarHelper.initPhoto();
        parent = (FrameLayout) findViewById(R.id.parent);
        parentbg = (ImageView) findViewById(R.id.parent_bg);
        parent_page = (TextView) findViewById(R.id.parent_page);
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_save = (TextView) findViewById(R.id.tv_save);
        parent_vp = (ViewPager) findViewById(R.id.parent_vp);

//        parentimg = (PhotoView) findViewById(R.id.parent_img);

//        ArrayList<String> imgs = new ArrayList<>();
//        imgs.add("http://upload-images.jianshu.io/upload_images/281665-5fbe2ab9a70766a3.jpeg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240");
//        baseResp.setImgurl(imgs);
//        ArrayList<Info> infos = new ArrayList<>();
//        infos.add(parentimg.getInfo());
//        baseResp.setInfos(infos);

        in.setDuration(300);
        out.setDuration(300);
        out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                parentbg.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
//        photoStart(0);

    }


    public void hintBar1() {
        toolBarHelper.setHide(true, false);
    }
    public void hintBar() {
        toolBarHelper.setHide(true, true);
    }

    public void setTitleString(String msg) {
        toolbar_title.setVisibility(View.VISIBLE);
        toolbar_title.setText(msg);
    }

    public void setDefaultBack(String lastTv) {
        toolbar_back.setVisibility(View.VISIBLE);
        toolbar_back_tv.setVisibility(View.VISIBLE);
        toolbar_back_last_tv.setVisibility(View.VISIBLE);
        toolbar_back_last_tv.setText(lastTv);
    }

    public void setFun2(String tv, int tvResult) {
        toolbar_fun2.setVisibility(View.VISIBLE);
        if (TextUtils.isEmpty(tv)) {
            toolbar_fun2_tv.setVisibility(View.GONE);
        } else {
            toolbar_fun2_tv.setVisibility(View.VISIBLE);
            toolbar_fun2_tv.setText(tv);
        }
        if (tvResult == 0) {
            toolbar_fun2_fonttv.setVisibility(View.GONE);
        } else {
            toolbar_fun2_fonttv.setVisibility(View.VISIBLE);
            toolbar_fun2_fonttv.setText(tvResult);
        }
    }

    protected Bundle getSavedInstanceState() {
        return saveInstanceState;
    }

    public void setTitleString(int txtResource) {
        setTitleString(getString(txtResource));
    }

    public void setTitleBackground(float alpha) {
        setTitleBackground(alpha, getResources().getColor(R.color.colorCC), getResources().getColor(R.color.colorC4));
    }

    public void setTitleBackground(float alpha, int... c) {
        int color = c[0];
        int r = (color & 0x00ff0000) >> 16;
        int g = (color & 0x0000ff00) >> 8;
        int b = (color & 0x000000ff);
        toolBarHelper.getToolBar().setBackgroundColor(Color.argb((int) alpha, r, g, b));
        if (c.length > 1) {
            int color1 = c[1];
            int r1 = (color1 & 0x00ff0000) >> 16;
            int g1 = (color1 & 0x0000ff00) >> 8;
            int b1 = (color1 & 0x000000ff);
            view1.setBackgroundColor(Color.argb((int) alpha, r1, g1, b1));
        }
        hintBar();
    }

    public void setTitleTextColor(int alpha) {
        int color = getResources().getColor(R.color.colorC1);
//        int a = (color & 0xff000000) >> 32;
        int r = (color & 0x00ff0000) >> 16;
        int g = (color & 0x0000ff00) >> 8;
        int b = (color & 0x000000ff);
        toolbar_title.setTextColor(Color.argb(alpha, r, g, b));

    }

    public void bindCallback(ActionCallback callback, BaseEngine engine) {
        this.callback.add(callback);
        this.engine.add(engine);
        engine.register(callback);
    }

    /* 返回事件*/
    @Override
    public void onBackButtonClick() {
        if (getCurrentFrame() != null) {
            getCurrentFrame().onBackButtonClick();
        } else {
            onBackPressed();
        }
    }

    /*右边功能键的按钮事件*/
    @Override
    public void onFun2ButtonClick() {
        if (getCurrentFrame() != null) {
            getCurrentFrame().onFun2ButtonClick();
        }
    }

    @Override
    public void onFun3ButtonClick() {

    }

    /* 右边按钮资源*/
    @Override
    public int getFun2ButtonResource() {
        return 0;
    }

    @Override
    public int getFun3ButtonResource() {
        return 0;
    }

    /* 右边文字*/
    @Override
    public String getFun2TextString() {
        return null;
    }

    /* 右边文字点击事件*/
    @Override
    public void onFun2TextClick() {
        if (getCurrentFrame() != null) {
            getCurrentFrame().onFun2TextClick();
        }
    }

    /*title显示*/
    @Override
    public String getTitleString() {
        return null;
    }

    @Override
    public int getTitleResource() {
        return 0;
    }

    /*输入框回调*/
    @Override
    public void onEditorActionListener(TextView v) {

    }

    /*添加Vierw至标题栏*/
    @Override
    public void addViewToTitleBar(View view, ViewGroup.LayoutParams params) {

    }
}
