package com.push.PushMerchant.util;

import android.widget.Toast;
import com.push.PushMerchant.base.PushApplicationContext;

/**
 * @ClassName: ToastUtils
 * @Description: Toast工具类
 * @author: YYL
 * <p/>
 * create at 2016/9/5
 */
public class ToastUtils {


    private static Toast toast = null;

    public static void showToast(String msg) {
        if (null == toast) {
            toast = Toast.makeText(PushApplicationContext.context, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    public static void showToast(int StringId) {
        showToast(PushApplicationContext.context.getString(StringId));
    }
}
