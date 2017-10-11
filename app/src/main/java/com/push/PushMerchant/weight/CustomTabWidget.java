package com.push.PushMerchant.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TabWidget;


/**
 * Created by quan on 2015/11/9.
 */
public class CustomTabWidget extends TabWidget {
    private OnTabClickListener mListener;

    public interface OnTabClickListener {
        boolean onTabClick(int index);
    }

    public CustomTabWidget(Context context) {
        super(context);
    }

    public CustomTabWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTabWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setOnTabClickListener(OnTabClickListener listener) {
        mListener = listener;
    }

    @Override
    public void setCurrentTab(int index) {
        if (mListener != null && mListener.onTabClick(index)) {
            return;
        }
        super.setCurrentTab(index);
    }
}
