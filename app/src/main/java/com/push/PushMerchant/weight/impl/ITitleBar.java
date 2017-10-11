package com.push.PushMerchant.weight.impl;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @ClassName: ITitleBar
 * @Description: 自定义Toolbar封装
 * @author: YYL
 * <p>
 * create at 2016/9/5
 */
public interface ITitleBar {


    /**
     * 返回事件按钮
     */
    void onBackButtonClick();

    /**
     * 右边功能键的按钮事件
     */
    void onFun2ButtonClick();

    void onFun3ButtonClick();

    /**
     * 右边按钮资源
     *
     * @return
     */
    int getFun2ButtonResource();

    int getFun3ButtonResource();

    /**
     * 右边文字
     *
     * @return
     */
    String getFun2TextString();

    /**
     * 右边文字的点击事件
     */
    void onFun2TextClick();

    /**
     * title显示
     *
     * @return
     */
    String getTitleString();

    int getTitleResource();


    /**
     * 输入框回调
     *
     * @param v
     */
    void onEditorActionListener(TextView v);

    /**
     * 添加Vierw至标题栏
     *
     * @param view
     * @param params
     */
    void addViewToTitleBar(View view, ViewGroup.LayoutParams params);

}
