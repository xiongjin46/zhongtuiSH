package com.push.PushMerchant.network.req;

/**
 * @ClassName: RequestCode
 * @Description: 请求码
 * @author: YYL
 * <p>
 * create at 2016/11/10 15:00
 */
public interface RequestCode {

    interface User {
        int CODE_MEMBER_LOGIN = 0X001;
        int CODE_MEMBER_CURRENTTASK = 0X002;
        int CODE_MEMBER_HISTORYTASK = 0X003;
        int CODE_MEMBER_MARCHANTCHECK = 0X004;
        int CODE_MEMBER_SAVEMERCHANT = 0X005;
        int CODE_MEMBER_LOGOUT = 0X006;
        int CODE_MEMBER_UPDATAUSER = 0X007;
        int CODE_MEMBER_SESSION = 0X008;
        int CODE_MEMBER_GETMERCHANT = 0X009;
        int CODE_MEMBER_GETTASKSTORE = 0X010;
        int CODE_MEMBER_PROVINCELIST = 0X011;
        int CODE_MEMBER_CITYLIST = 0X012;
        int CODE_MEMBER_AREASLIST = 0X013;
        int CODE_MEMBER_FORGITPSD = 0X0014;
        int CODE_MEMBER_GETCAPTCHA = 0X015;
    }

    interface Server {
        int CODE_SERVER_GETSTORE = 0X2001;
        int CODE_SERVER_SHUFFLE = 0X2002;
        int CODE_SERVER_STOREDETAIL = 0X2003;
        int CODE_SERVER_MERCHANTSTORE = 0X2004;
        int CODE_SERVER_VERSION = 0X2005;
        int CODE_SERVER_PRODUCTLIST = 0X2006;
        int CODE_SERVER_GETCIRCLE = 0X2007;
        int CODE_SERVER_GETSTOREBYCITY = 0X2008;
        int CODE_SERVER_GETSTOREBYCIRCLE = 0X2009;
        int CODE_SERVER_GETCITYPAGE = 0X2010;
        int CODE_SERVER_SAVEMESSAGE = 0X2011;
    }
}
