package com.push.PushMerchant.network;


import com.push.PushMerchant.network.impl.ActionCallback;
import com.push.PushMerchant.network.resp.CallbackHelper;
import com.push.PushMerchant.network.resp.CallbackHelper.Caller;



/**
 * 业务基类，与BaseModuleEngine的区别是，继承这个类的可以有回调方法通知UI。基础模块，请勿随便修改
 */
public abstract class BaseEngine<T extends ActionCallback> extends BaseModuleEngine {
    public static final int ENCRYPT_YES = 2;
    public static final int ENCRYPT_NO = 1;


    protected CallbackHelper<T> mCallbacks = new CallbackHelper<T>();

    public BaseEngine() {
    }

    public void register(T cb) {
        mCallbacks.register(cb);
    }

    public void unregister(T cb) {
        mCallbacks.unregister(cb);
    }

    public void unregisterAll() {
        mCallbacks.unregisterAll();
    }

    /**
     * 通知UI数据改变了。在主线程通知
     *
     * @param caller
     */
    protected void notifyDataChangedInMainThread(final Caller<T> caller) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                notifyDataChanged(caller);
            }
        });
    }

    /**
     * 延迟一些时间通知UI数据改变了。在主线程通知
     *
     * @param caller
     * @param delayMillis
     */
    protected void delayNotifyDataChangedInMainThread(final Caller<T> caller, long delayMillis) {
        ThreadManager.getMainThreadHandler().postDelayed(new Runnable() {

            @Override
            public void run() {
                notifyDataChanged(caller);
            }
        }, delayMillis);
    }

    /**
     * 通知UI数据改变了。这个方法跑在哪个线程取决于调用方。如果需要通知UI，而没有在主线程调用，那么请使用notifyDataChangedInMainThread
     *
     * @param caller
     */
    protected void notifyDataChanged(Caller<T> caller) {
        mCallbacks.broadcast(caller);
    }

    /**
     * 在主线程执行一些事情。UI数据变更跟通知一定要在同一个runnable里执行完毕。通知的话，一定要使用notifyDataChanged，而不是notifyDataChangedInMainThread。保证数据变更跟通知是一个原子操作
     *
     * @param action
     */
    protected void runOnUiThread(Runnable action) {
        ThreadManager.getMainThreadHandler().post(action);
    }
}