package com.push.PushMerchant.network.resp;


import com.push.PushMerchant.network.impl.ActionCallback;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * @ClassName: CallbackHelper
 * @Description: 回调帮助类
 * @author: YYL
 * <p>
 * create at 2016/11/10 14:10
 */
public class CallbackHelper<ICallback extends ActionCallback> {
    protected ReferenceQueue<ICallback> mCallbackReferenceQueue;
    protected ArrayList<WeakReference<ICallback>> mWeakCallbackArrayList;

    public CallbackHelper() {
        mCallbackReferenceQueue = new ReferenceQueue<ICallback>();
        mWeakCallbackArrayList = new ArrayList<WeakReference<ICallback>>();
    }

    public void register(ICallback cb) {
        if (cb == null) {
            return;
        }

        synchronized (mWeakCallbackArrayList) {
            // 每次注册的时候清理已经被系统回收的对象
            Reference<? extends ICallback> releaseListener = null;
            while ((releaseListener = mCallbackReferenceQueue.poll()) != null) {
                mWeakCallbackArrayList.remove(releaseListener);
            }

            // 弱引用处理
            for (WeakReference<ICallback> weakListener : mWeakCallbackArrayList) {
                ICallback listenerItem = weakListener.get();
                if (listenerItem == cb) {
                    return;
                }
            }
            WeakReference<ICallback> weakListener = new WeakReference<ICallback>(cb, mCallbackReferenceQueue);
            this.mWeakCallbackArrayList.add(weakListener);
        }
    }

    public void unregister(ICallback cb) {
        if (cb == null) {
            return;
        }

        synchronized (mWeakCallbackArrayList) {
            // 弱引用处理
            for (WeakReference<ICallback> weakListener : mWeakCallbackArrayList) {
                ICallback listenerItem = weakListener.get();
                if (listenerItem == cb) {
                    mWeakCallbackArrayList.remove(weakListener);
                    return;
                }
            }
        }
    }

    public void unregisterAll() {
        synchronized (mWeakCallbackArrayList) {
            mWeakCallbackArrayList.clear();
        }
    }

    public void broadcast(final Caller<ICallback> caller) {
        synchronized (mWeakCallbackArrayList) {
            for (WeakReference<ICallback> weakListener : mWeakCallbackArrayList) {
                ICallback listener = weakListener.get();
                if (listener != null) {
                    try {
                        caller.call(listener);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public interface Caller<T> {
        public void call(T cb);
    }
}