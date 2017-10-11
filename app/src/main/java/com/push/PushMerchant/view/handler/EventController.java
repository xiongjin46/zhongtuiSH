package com.push.PushMerchant.view.handler;

import android.os.Message;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: EventController
 * @Description:
 * @author: YYL
 * create at 2017-06-01 下午 4:38
 */
public class EventController implements EventListener {

    protected ConcurrentHashMap<Integer, List<WeakReference<UIEventListener>>> mEventListeners;
    protected ReferenceQueue<UIEventListener> mListenerReferenceQueue;

    private static EventController sInstance;

    public synchronized static EventController getsInstance() {
        if (sInstance == null) {
            sInstance = new EventController();
        }
        return sInstance;
    }

    private EventController() {
        mEventListeners = new ConcurrentHashMap<>();
        mListenerReferenceQueue = new ReferenceQueue<>();
    }

    public void addUIEventListener(int eventId, UIEventListener listener) {
        if (listener == null) return;

        synchronized (mEventListeners) {
            Reference<? extends UIEventListener> releaseListeners = null;
            List<WeakReference<UIEventListener>> list = mEventListeners.get(eventId);
            if (list != null && !list.isEmpty()) {
                while ((releaseListeners = mListenerReferenceQueue.poll()) != null) {
                    list.remove(releaseListeners);
                }
            }

            if (list == null) {
                list = new ArrayList<>();
                mEventListeners.put(eventId, list);
            }
            for (WeakReference<UIEventListener> wl : list) {
                UIEventListener l = wl.get();
                if (l == listener) {
                    return;
                }
            }

            WeakReference<UIEventListener> newListener = new WeakReference<>(listener, mListenerReferenceQueue);
            list.add(newListener);
        }
    }

    public void removeUIEventListener(int eventId, UIEventListener listener) {
        if (listener == null) return;

        synchronized (mEventListeners) {
            List<WeakReference<UIEventListener>> list = mEventListeners.get(eventId);
            if (list != null && !list.isEmpty()) {

                List<WeakReference<UIEventListener>> dataList = new ArrayList<>();
                for (WeakReference<UIEventListener> wl : list) {
                    if (wl.get() == listener) {
                        dataList.add(wl);
                    }
                }
                list.remove(dataList);
                if (list.isEmpty()) {
                    mEventListeners.remove(eventId);
                }
            }
        }
    }

    private void handleUIEvent(Message msg) {
        List<WeakReference<UIEventListener>> lt = mEventListeners.get(msg.what);
        if (lt != null) {
            List<WeakReference<UIEventListener>> list = new ArrayList<>(lt);
            for (Iterator<WeakReference<UIEventListener>> i = list.iterator(); i.hasNext(); ) {
                UIEventListener listener = i.next().get();
                if (listener != null) {
                    listener.handleUIEvent(msg);
                }
            }
        }
    }

    @Override
    public void handleEvent(Message msg) {
        handleUIEvent(msg);
    }
}
