package com.push.PushMerchant.weight.impl;

import android.view.View;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * @ClassName: IRefreshListener
 * @Description:
 * @author: YYL
 * <p/>
 * create at 2016/9/19
 */
public interface IRefreshListener {

    //    执行刷新的view
    View initRefresh(View v);

    //是否执行下拉
    boolean canDoRefresh();

    //刷新操作
    void refreshBegin(PtrFrameLayout frame);
}
