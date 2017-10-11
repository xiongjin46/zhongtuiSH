package com.push.PushMerchant.network.impl;

/**
 * @ClassName: INetworkEngine
 * @Description: 网络返回接口
 * @author: YYL
 * <p/>
 * create at 2016/9/6
 */
public interface INetworkEngine {

    /**
     * 请求成功
     *
     * @param reqCode
     * @param response
     */
    void onNetworkRequestSuccess(int reqCode, String response);

    /**
     * 请求失败
     *
     * @param reqCode
     * @param result
     * @param response
     */
    void onNetworkRequestFail(int reqCode, int result, Exception response);

    /**
     * post数据时的进度
     *
     * @param reqCode
     * @param f
     */
    void inProgress(int reqCode, float f);
}
