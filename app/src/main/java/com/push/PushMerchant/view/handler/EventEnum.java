package com.push.PushMerchant.view.handler;

/**
 * @ClassName: com.jiaoao.lzb.lzbApplication.modules.message.handler
 * @Description:
 * @author: YYL
 * create at 2017/2/27 0027 下午 7:55
 */
public class EventEnum {

    public static final int EVENT_BASE = 3000;

    public static final int EVENT_MESSAGE_PRIVATE = EVENT_BASE + 1;
    public static final int EVENT_HANDLE_SYSTEM = EVENT_MESSAGE_PRIVATE + 1;
    public static final int EVENT_PRICE_SYSTEM = EVENT_HANDLE_SYSTEM + 1;
    public static final int EVENT_PRICE_ToPOC_DETAIL = EVENT_PRICE_SYSTEM + 1;
}
