package com.push.PushMerchant.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.push.PushMerchant.R;


/**
 * Created by Administrator on 2016/12/3.
 */
public class MyRadioButton extends RadioButton {

    public MyRadioButton(Context context) {
        super(context);
        init(null, 0);
    }

    public MyRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public MyRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyle) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TypeView);
        int tag = a.getInt(R.styleable.TypeView_indicator_tag, 0);
        a.recycle();
//        setTypeface(ZajaApplication.getInstance().getTypeface(tag));
    }
}
