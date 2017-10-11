package com.push.PushMerchant.network;


/**
 * @ClassName: ProgressRequestListener
 * @Description:
 * @author: YYL
 * 
 * create at 2016/11/10 12:08
 */
public interface ProgressRequestListener {
        void onRequestProgress(long bytesWritten, long contentLength, boolean done);
}