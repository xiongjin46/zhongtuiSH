package com.push.PushMerchant.weight;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.push.PushMerchant.R;
import com.push.PushMerchant.util.DeviceUtils;


/**
 * @ClassName: ToolBarHelper
 * @Description: Toolbar封装工具类
 * @author: YYL
 * <p>
 * create at 2016/9/5
 */
public class ToolBarHelper {
    /*上下文，创建view的时候需要用到*/
    private Context mContext;

    /*base view*/
    private ViewGroup mContentView;

    /*用户定义的view*/
    private View mUserView;

    /*toolbar*/
    private Toolbar mToolBar;

    /*视图构造器*/
    private LayoutInflater mInflater;

    /*toolbar是否支持透明*/
    private boolean toolBarAlpha = false;
    /*
   * 两个属性
   * 1、toolbar是否悬浮在窗口之上
   * 2、toolbar的高度获取
   * */
//    private static int[] ATTRS = {
//            android.R.attr.windowActionBarOverlay,
//            android.R.attr.actionBarSize
//    };

    private int toolBarSize;
    private View toolbar;
    private ViewGroup mPtr;
    private ViewGroup mPhoto;
//    private PtrClassicFrameLayout mPtr;
//    private LinearLayout view_group_ll;

    public ToolBarHelper(Context context, int layoutId, boolean toolbarAlpha) {
        this.toolBarAlpha = toolbarAlpha;
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
        /*初始化整个内容*/
        initContentView();
        /*初始化用户定义的布局*/
        initUserView(layoutId);

              /*初始化toolbar*/
//        initToolBar();
//        initEmpty();
    }

    private void initContentView() {
        /*直接创建一个帧布局，作为视图容器的父容器*/
        mContentView = (ViewGroup) mInflater.inflate(R.layout.toolbar_activity_content, null);
        mPtr = (ViewGroup) mInflater.inflate(R.layout.toolbar_activity_ptr, null);
        mPhoto = (ViewGroup) mInflater.inflate(R.layout.toolbar_activity_photo, null);
//        view_group_ll = (LinearLayout) mPtr.findViewById(R.id.view_group_ll);

//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT);
//        mContentView.setLayoutParams(params);

    }

    @SuppressWarnings("ResourceType")
    private void initUserView(int id) {
        mUserView = mInflater.inflate(id, null);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        TypedArray typedArray = mContext.getTheme().obtainStyledAttributes(ATTRS);
        /*获取主题中定义的悬浮标志*/
//        boolean overly = typedArray.getBoolean(0, false);
        /*获取主题中定义的toolbar的高度*/
//        toolBarSize = (int) typedArray.getDimension(1, 0);
        toolBarSize = (int) mContext.getResources().getDimension(R.dimen.title_view_height) + DeviceUtils.getStatusHeight(mContext);
//        typedArray.recycle();
        /*如果是悬浮状态，则不需要设置间距*/
//        params.topMargin = overly ? 0 : toolBarSize;//overly 为false 获取不到值 暂不用
        params.topMargin = toolBarAlpha ? 0 : toolBarSize;//构造传参设置
//        view_group_ll.addView(mUserView);
        mContentView.addView(mUserView, params);
        /*通过inflater获取toolbar的布局文件*/
        toolbar = mInflater.inflate(R.layout.toolbar, null);
        mToolBar = (Toolbar) toolbar.findViewById(R.id.id_tool_bar);
    }


    /**
     * fragment中是否隐藏toolbar
     *
     * @param hide
     */
    public void setHide(boolean hide, boolean toolbarHide) {
        setHide(hide, toolbarHide, toolBarSize);
    }

    /**
     * fragment中是否隐藏toolbar
     *
     * @param hide
     */
    public void setHide(boolean hide, boolean toolbarHide, int toolBarHeight) {

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mUserView.getLayoutParams();
        if (hide) {
            params.topMargin = 0;
            if (!toolbarHide)
                mToolBar.setVisibility(View.GONE);
        } else {
            params.topMargin = toolBarHeight;
            if (!toolbarHide)
                mToolBar.setVisibility(View.VISIBLE);
        }
        mUserView.setLayoutParams(params);
    }

    public void setHide(boolean hide) {
        setHide(hide, false);
    }


    public void initToolBar() {
        mContentView.addView(toolbar);
    }

    public ViewGroup getContentView() {
        return mContentView;
    }

    public Toolbar getToolBar() {
        return mToolBar;
    }

    public int getToolBarSize() {
        return toolBarSize;
    }

    public ViewGroup getmPtr() {
        return mPtr;
    }

    public ViewGroup getmPhoto() {
        return mPhoto;
    }

    public void initPhoto() {
        mContentView.addView(mPhoto);
    }

}
