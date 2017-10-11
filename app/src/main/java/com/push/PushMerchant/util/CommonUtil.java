package com.push.PushMerchant.util;


import android.content.Context;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: CommonUtil
 * @Description:
 * @author: YYL
 * <p>
 * create at 2016/11/10 14:29
 */
public class CommonUtil {

    protected static int mUniqueId = 0;

    public synchronized static int getUniqueId() {
        return mUniqueId++;
    }

    private static CountDownTimer countDownTimer;

    /**
     * @return String
     * @throws @Title: getImgName
     * @Description: 产生随机图片名字 20位码
     */
    public static String getImgName() {
        String str = UUID.randomUUID().toString();
        String[] strArr = str.split("-");
        StringBuffer sb = new StringBuffer();
        for (String string : strArr) {
            sb.append(string);
        }
        return sb.toString() + ".jpg";
    }


    public static CountDownTimer countDownTimer(final TextView registereSendText) {
        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int i = (int) (millisUntilFinished / 1000);
                registereSendText.setText(i + "秒后可重发");
                //LogUtil.d(TAG,i+"");
            }

            @Override
            public void onFinish() {
                registereSendText.setText("获取验证码");
                registereSendText.setEnabled(true);
            }
        };

        return countDownTimer;
    }

    public static List<String> StringToList(String content) {
        if (TextUtils.isEmpty(content)) return new ArrayList<String>();
        String[] split = content.split(",");
        List<String> photoUrlLists = Arrays.asList(split);
        return photoUrlLists;
    }

    public static String setText(Context context, int strRes, Object... value) {
        String format = context.getResources().getString(strRes);
        String result = String.format(format, value);
        return result;
    }

    /**
     * 判断当前是否为手机号
     *
     * @param str
     * @return
     */
    public static boolean isMobile(final String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    /**
     * 判断输入框中是否含有表情
     *
     * @param str
     * @return
     */
    public static boolean noContainsEmoji(String str) {
        int len = str.length();
        for (int i = 0; i < len; i++) {
            if (isEmojiCharacter(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEmojiCharacter(char codePoint) {
        return !((codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)));
    }
}
