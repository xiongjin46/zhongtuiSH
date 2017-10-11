package com.push.PushMerchant.view.handler;

import android.os.Handler;
import android.os.Message;

/**
 * @ClassName: EventDispatcher
 * @Description:
 * @author: YYL
 * create at 2017-06-01 下午 4:39
 */
public class EventDispatcher extends Handler {

    private static EventDispatcher sInstance;

    private EventListener mListener;

    public static EventDispatcher getsInstance(EventListener listener) {
        if (sInstance == null) {
            sInstance = new EventDispatcher(listener);
        }

        return sInstance;
    }

    private EventDispatcher(EventListener listener) {
        mListener = listener;
    }

    public void setListener(EventListener listener) {
        mListener = listener;
    }

    @Override
    public void handleMessage(Message msg) {
        if (mListener != null) {
            mListener.handleEvent(msg);
        } else {
            super.handleMessage(msg);
        }
    }
}
