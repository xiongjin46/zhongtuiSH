package com.push.PushMerchant.constants;

/**
 * Created by admin on 2016/11/17.
 */

public class IntentFlag {

    /**
     * 类型
     */
    public static final String INTENT_FLAG_TYPE = "INTENT_FLAG_TYPE";
    public static final String DEFAULT = "DEFAULT";

    /**
     * 对象
     */
    public static final String INTENT_FLAG_OBJ = "INTENT_FLAG_OBJ";

    /**
     * 活动id
     */
    public static final String EVENT_ID = "event_id";

    /**
     * WEBView
     */
    public static final String KEY_URL = "key-url";

    /**
     * 数据类型
     */
    public static final String CODE_TYPE = "code_type";

    public static final String USERID = "user_id";

    public static final String INTENT_NUM = "integer_num";
    public static final String INTENT_SELECTED_PICTURE = "intent_selected_picture";

    /**
     * 顶部标题
     */
    public static final String TITLE_STRING = "title_string";
    /**
     * 数量
     */
    public static final String INTENT_FLAG_COUNT = "INTENT_FLAG_COUNT";
    /**
     * 编辑内容
     */
    public static final String REMARK = "intent_remark";
    /*历史记录*/
    public static final String HISTORY = "history";

    public static final String INTENT_ID = "intent_id";
    public static final int COMMENT = 0x0001;   //评论
    public static final int SENDCARD = 0x0002;   //发帖

    public static String ADDRESSVERSION = "adderssversion"; //地址版本
    public static String SPLASHURL = "splashurl"; //闪屏页图片地址
    public static String OUTTOKEN = "outtoken"; //token过期
    public static String MESSAGEBLACK = "messageblack"; //聊天室屏蔽
    public static String USERINFO_MASK = "userinfo_mask"; //用户信息遮罩层
}
