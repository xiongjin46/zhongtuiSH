package com.push.PushMerchant.constants;

/**
 * Created by admin on 2016/11/10.
 */

public interface GConstants {

    // 服务协议地址
    String AGREEMENT = "http://www.zhongtui168.com/xieyi.html";
    String EVENTURL = "https://www.laizhuangb.com/laiZhuangBApplication/activityDetail.html";

    /**
     * @Fields KEY_ENVIRONMENT : 环境
     */
    String KEY_ENVIRONMENT = "key_environment";

    String HTML_KEY = "<html><head><meta name=\"viewport\" content=\"width=device-width, " +
            "initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes\" />" +
            "<style>img{max-width:100% !important;height:auto !important;}</style>"
            + "<style>body{max-width:100% !important;}</style>" + "</head><body>";

    // 开发环境接口cgi
//    String CGI_DEV = "http://120.77.49.214:8080/massPromotionApp/service/";
    String CGI_DEV = "http://www.zhongtui168.com/massPromotionApp/service/";
    // 测试环境接口cgi
    String CGI_TEST = "http://120.77.49.214:8080/massPromotionApp/service/";
    // 正式环境接口cgi
    String CGI_RELEASE = "http://www.zhongtui168.com/massPromotionApp/service/";

//    String OSS_DEV = "test/";
//
//    String OSS_TEST = "test/";

    String OSS_DEV = "online/";

    String OSS_TEST = "online/";

    String OSS_ONLINE = "online/";

    String IS_OPENED = "is_opened_";

    String IMAGE_HOST = "https://zhongtui168.oss-cn-shenzhen.aliyuncs.com/";
    String KEY_ACCESSTOKEN = "key_userToken";//用户token
    String KEY_USERTYPE = "key_type";   //用户类型
    String KEY_HEADIMGURL = "key_headimgurl";   //用户头像地址
    String KEY_USERID = "ket_personid"; // 用户唯一id
    String KEY_PERSONCODE = "key_personcode";   //用户手机号
    String KEY_STATUS = "key_status";       //用户状态
    String KEY_SEX = "key_sex";  //sex
    String KEY_REALNAME = "key_realname";  //真实姓名
    String KEY_PERSONTYPE = "key_persontype";  //用户类型
    String KEY_PHONE = "key_phone";  //电话号码
    String KEY_ADDRESS = "key_address";  //地址
    String KEY_TRADE = "key_trade";  //地址


    interface API_MEMBER {
        String API_MEMBER_PROVINCE = "province/findProvince";    //获取所有省地址
        String API_MEMBER_CITY = "city/findCity";    //获取所有市地址
        String API_MEMBER_AREAS = "areas/findAreas";    //获取所有县地址
        String API_MEMBER_REGISTER = "person/regist";   //注册
        String API_MEMBER_LOGIN = "person/loginMerchant";   //      密码登录
        String API_MEMBER_FORGITPSD = "person/forgetPersonPassword";   //      忘记密码
        String API_MEMBER_LOGINNOPASSWORD = "person/loginNoPassword";   //免密码登录
        String API_MEMBER_CAPTCHA = "person/createCaptcha";   // 获取注册验证码
        String API_MEMBER_LOGOUT = "person/logout";   //退出登录
        String API_MEMBER_UPDATAUSER = "person/updatePerson";   //修改用户信息
        String API_MEMBER_CURRENTTASK = "taskInfo/findTaskInfoByMerchantId"; //获取商户当前项目
        String API_MEMBER_HISTORYTASK = "taskInfo/findTaskInfoByMerchantIdHistory"; //获取商户当前项目
        String API_MEMBER_MERCHANTCHECK = "merchant/findMerchantCheckPage"; //查询审核历史记录
        String API_MEMBER_SAVEMERCHANT = "merchant/saveMerchant"; //上传审核资料
        String API_MEMBER_GETMERCHANT = "merchant/updateMerchant"; //修改商户基本信息
        String API_MEMBER_GETTASKSTORE = "taskInfo/findTaskStoreByTaskId"; //修改商户基本信息
    }

    interface API_SERVER {
        String API_SERVER_TRIBEACTIVITY = "enum/findEnum";   //活动或者部落类型
        String API_SERVER_SHUFFLE = "merchant/findShuffleImage";   //获取轮播图
        String API_SERVER_SESSION = "merchant/findMerchanById";   //刷新用户session
        String API_SERVER_GETSTORE = "store/findStoreByMerchant";   //查询门店信息
        String API_SERVER_STOREDETAIL = "store/findStoreProduct";   //门店下的产品分页查询
        String API_SERVER_MERCHATSTORE = "store/findMerchantStoreById";   //根据门店ID查询门店投放信息
        String API_SERVER_PRODUCTLIST = "product/findProductList";   //查询所有可用的产品列表
        String API_SERVER_GETCIRCLE = "businessArea/findBusinessAreaByCityId";  //根据城市名查询商圈信息
        String API_SERVER_GETSTOREBYCITY = "store/findStoreInfoByCityName";  //根据城市名查询商圈下的所有门店信息
        String API_SERVER_GETSTOREBYCIRCLE = "store//findStoreByBusinessAreaId";  //根据商圈id查询商圈下的所有门店信息
        String API_SERVER_GETCITYPAGE = "city/findCityPage";  //查询有经纬度的城市
        String API_SERVER_SAVEMESSAGE = "person/saveAppointmentInfo";  //保存预约信息
    }
}
