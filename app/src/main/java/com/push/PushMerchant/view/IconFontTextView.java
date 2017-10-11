package com.push.PushMerchant.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @ClassName: com.jiaoao.lzb.lzbApplication.view
 * @Description:
 * @author: YYL
 * create at 2017/2/22 0022 上午 11:01
 */
public class IconFontTextView extends TextView {

    public IconFontTextView(Context context) {
        super(context);
        init(context);
    }

    public IconFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public IconFontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        Typeface iconfont = Typeface.createFromAsset(context.getAssets(), "iconfont/iconfont.ttf");
        setTypeface(iconfont);
    }
}
