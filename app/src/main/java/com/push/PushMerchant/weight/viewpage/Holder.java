package com.push.PushMerchant.weight.viewpage;

/**
 * @ClassName: Holder
 *
 * @Description: holder基类
 *
 * @author: YYL
 *
 * create at 2016/3/9 15:22
 *
 */
import android.content.Context;
import android.view.View;

public interface Holder<T>{
    View createView(Context context);
    void UpdateUI(Context context, int position, T data);
}