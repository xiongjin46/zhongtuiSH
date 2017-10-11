package com.push.PushMerchant.network.engine;

import android.text.TextUtils;

import com.push.PushMerchant.base.BaseResp;
import com.push.PushMerchant.constants.GConstants;
import com.push.PushMerchant.modules.home.bean.AreasBaseResp;
import com.push.PushMerchant.modules.home.bean.CityBaseResp;
import com.push.PushMerchant.modules.home.bean.ProvinceListBaseResp;
import com.push.PushMerchant.modules.person.bean.PersonBeanResp;
import com.push.PushMerchant.modules.person.bean.ProjectDetailResp;
import com.push.PushMerchant.modules.person.bean.UserProfile;
import com.push.PushMerchant.modules.person.bean.CheckListResp;
import com.push.PushMerchant.modules.person.bean.HistoryTaskResp;
import com.push.PushMerchant.network.BaseEngine;
import com.push.PushMerchant.network.impl.IUserEngine;
import com.push.PushMerchant.network.impl.UserCallback;
import com.push.PushMerchant.network.req.RequestCode;
import com.push.PushMerchant.network.resp.CallbackHelper;
import com.push.PushMerchant.util.Md5Utils;
import com.push.PushMerchant.util.ToastUtils;

import java.util.HashMap;

/**
 * @ClassName: ServerEngine
 * @Description:
 * @author: YYL
 * <p>
 * create at 2017/1/10 11:00
 */

public class UserEngine extends BaseEngine<UserCallback> implements IUserEngine {

    public static int rows = 10;
    public static final int CapchaType_Regist = 0;
    public static final int CapchaType_NoPsd = 1;
    public static final int CapchaType_Uppsd = 2;


    @Override
    public void login(String personCode, String personPassword) {
        HashMap<String, String> defaultHashMap = new HashMap<>();
        defaultHashMap.put("personCode", personCode);
        defaultHashMap.put("personPassword", Md5Utils.encode(personPassword));
        sendPost(RequestCode.User.CODE_MEMBER_LOGIN, GConstants.API_MEMBER.API_MEMBER_LOGIN, defaultHashMap, UserProfile.class);
    }

    @Override
    public void getCurrentTaskInfo(int id, int page) {
        HashMap<String, String> defaultHashMap = getDefaultHashMap();
        defaultHashMap.put("id", id + "");
        defaultHashMap.put("page", page + "");
        defaultHashMap.put("rows", rows + "");
        sendPost(RequestCode.User.CODE_MEMBER_CURRENTTASK, GConstants.API_MEMBER.API_MEMBER_CURRENTTASK, defaultHashMap, HistoryTaskResp.class);
    }

    @Override
    public void getHistoryTaskInfo(int id, int page) {
        HashMap<String, String> defaultHashMap = getDefaultHashMap();
        defaultHashMap.put("id", id + "");
        defaultHashMap.put("page", page + "");
        defaultHashMap.put("rows", rows + "");
        sendPost(RequestCode.User.CODE_MEMBER_HISTORYTASK, GConstants.API_MEMBER.API_MEMBER_HISTORYTASK, defaultHashMap, HistoryTaskResp.class);
    }

    @Override
    public void getMerchantCheckPage(int page) {
        HashMap<String, String> defaultHashMap = getDefaultHashMap();
        defaultHashMap.put("page", page + "");
        defaultHashMap.put("rows", rows + "");
        sendPost(RequestCode.User.CODE_MEMBER_MARCHANTCHECK, GConstants.API_MEMBER.API_MEMBER_MERCHANTCHECK, defaultHashMap, CheckListResp.class);
    }

    @Override
    public void SaveMerchantInfo(String photoUrl, String photoUrl3) {
        HashMap<String, String> defaultHashMap = getDefaultHashMap();
        defaultHashMap.put("photoUrl", photoUrl);
        defaultHashMap.put("photoUrl3", photoUrl3);
        sendPost(RequestCode.User.CODE_MEMBER_SAVEMERCHANT, GConstants.API_MEMBER.API_MEMBER_SAVEMERCHANT, defaultHashMap, BaseResp.class);
    }

    @Override
    public void logout() {
        HashMap<String, String> defaultHashMap = getDefaultHashMap();
        sendPost(RequestCode.User.CODE_MEMBER_LOGOUT, GConstants.API_MEMBER.API_MEMBER_LOGOUT, defaultHashMap, BaseResp.class);
    }

    @Override
    public void updataUser(String nickName, String photoUrl, String photoUrl2, String realName) {
        HashMap<String, String> defaultHashMap = getDefaultHashMap();
        if (!TextUtils.isEmpty(nickName)) {
            defaultHashMap.put("nickName", nickName);
        }
        if (!TextUtils.isEmpty(photoUrl)) {
            defaultHashMap.put("photoUrl", photoUrl);
        }
        if (!TextUtils.isEmpty(photoUrl2)) {
            defaultHashMap.put("photoUrl2", photoUrl2);
        }
        if (!TextUtils.isEmpty(realName)) {
            defaultHashMap.put("realName", realName);
        }
        sendPost(RequestCode.User.CODE_MEMBER_UPDATAUSER, GConstants.API_MEMBER.API_MEMBER_UPDATAUSER, defaultHashMap, BaseResp.class);
    }

    @Override
    public void updateSession() {
        HashMap<String, String> defaultHashMap = getDefaultHashMap();
        sendPost(RequestCode.User.CODE_MEMBER_SESSION, GConstants.API_SERVER.API_SERVER_SESSION, defaultHashMap, PersonBeanResp.class);
    }

    @Override
    public void updateMerchant(String address, String logoUrl, String name, String trades) {
        HashMap<String, String> defaultHashMap = getDefaultHashMap();
        if (!TextUtils.isEmpty(address)) {
            defaultHashMap.put("address", address);
        }
        if (!TextUtils.isEmpty(logoUrl)) {
            defaultHashMap.put("logoUrl", logoUrl);
        }
        if (!TextUtils.isEmpty(name)) {
            defaultHashMap.put("name", name);
        }
        if (!TextUtils.isEmpty(trades)) {
            defaultHashMap.put("trades", trades);
        }
        sendPost(RequestCode.User.CODE_MEMBER_GETMERCHANT, GConstants.API_MEMBER.API_MEMBER_GETMERCHANT, defaultHashMap, BaseResp.class);

    }

    @Override
    public void getTaskStoreByTaskId(int id) {
        HashMap<String, String> defaultHashMap = getDefaultHashMap();
        defaultHashMap.put("id", id + "");
        sendPost(RequestCode.User.CODE_MEMBER_GETTASKSTORE, GConstants.API_MEMBER.API_MEMBER_GETTASKSTORE, defaultHashMap, ProjectDetailResp.class);
    }

    @Override
    public void getRrovinceList() {
        HashMap<String, String> defaultHashMap = new HashMap<>();
        defaultHashMap.put("", "");
        sendPost(RequestCode.User.CODE_MEMBER_PROVINCELIST, GConstants.API_MEMBER.API_MEMBER_PROVINCE, defaultHashMap, ProvinceListBaseResp.class);
    }

    @Override
    public void getCityList(String provinceCode) {
        HashMap<String, String> defaultHashMap = new HashMap<>();
        defaultHashMap.put("provinceCode", provinceCode);
        sendPost(RequestCode.User.CODE_MEMBER_CITYLIST, GConstants.API_MEMBER.API_MEMBER_CITY, defaultHashMap, CityBaseResp.class);
    }

    @Override
    public void getAreasList(String cityCode) {
        HashMap<String, String> defaultHashMap = new HashMap<>();
        defaultHashMap.put("cityCode", cityCode);
        sendPost(RequestCode.User.CODE_MEMBER_AREASLIST, GConstants.API_MEMBER.API_MEMBER_AREAS, defaultHashMap, AreasBaseResp.class);
    }


    @Override
    public void getForgitPsd(String personCode, String captcha, String newPassword) {
        HashMap<String, String> defaultHashMap = new HashMap<>();
        defaultHashMap.put("personCode", personCode);
        defaultHashMap.put("captcha", captcha);
        defaultHashMap.put("newPassword", newPassword);
        sendPost(RequestCode.User.CODE_MEMBER_FORGITPSD, GConstants.API_MEMBER.API_MEMBER_FORGITPSD, defaultHashMap, BaseResp.class);
    }

    @Override
    public void getCapcha(String personCode, int type) {
        HashMap<String, String> defaultHashMap = new HashMap<>();
        defaultHashMap.put("personCode", personCode);
        defaultHashMap.put("type", type + "");
        sendPost(RequestCode.User.CODE_MEMBER_GETCAPTCHA, GConstants.API_MEMBER.API_MEMBER_CAPTCHA, defaultHashMap, BaseResp.class);
    }

    @Override
    protected void onRequestSuccess(final int seq, final BaseResp rsp) {
        super.onRequestSuccess(seq, rsp);
        notifyDataChangedInMainThread(new CallbackHelper.Caller<UserCallback>() {
            @Override
            public void call(UserCallback cb) {
                switch (seq) {
                    case RequestCode.User.CODE_MEMBER_LOGIN:
                        cb.onLoginSuccess((UserProfile) rsp);
                        break;
                    case RequestCode.User.CODE_MEMBER_CURRENTTASK:
                        cb.onGetCurrentTaskSuccess((HistoryTaskResp) rsp);
                        break;
                    case RequestCode.User.CODE_MEMBER_HISTORYTASK:
                        cb.onGetHistoryTaskSuccess((HistoryTaskResp) rsp);
                        break;
                    case RequestCode.User.CODE_MEMBER_MARCHANTCHECK:
                        cb.onGetCheckListSuccess((CheckListResp) rsp);
                        break;
                    case RequestCode.User.CODE_MEMBER_SAVEMERCHANT:
                        cb.onGetSaveMerchatSuccess(rsp);
                        break;
                    case RequestCode.User.CODE_MEMBER_LOGOUT:
                        cb.onLogOutSuccess(rsp);
                        break;
                    case RequestCode.User.CODE_MEMBER_UPDATAUSER:
                        cb.onUpdataUserSuccess(rsp);
                        break;
                    case RequestCode.User.CODE_MEMBER_SESSION:
                        cb.onUpdateSessionSuccess((PersonBeanResp) rsp);
                        break;
                    case RequestCode.User.CODE_MEMBER_GETMERCHANT:
                        cb.onGetMerchantSuccess(rsp);
                        break;
                    case RequestCode.User.CODE_MEMBER_GETTASKSTORE:
                        cb.onGetStoreTaskSuccess((ProjectDetailResp) rsp);
                        break;
                    case RequestCode.User.CODE_MEMBER_PROVINCELIST:
                        cb.onGetProvinceListSuccess((ProvinceListBaseResp) rsp);
                        break;
                    case RequestCode.User.CODE_MEMBER_CITYLIST:
                        cb.onGetCityListSuccess((CityBaseResp) rsp);
                        break;
                    case RequestCode.User.CODE_MEMBER_AREASLIST:
                        cb.onGetAreasListSuccess((AreasBaseResp) rsp);
                        break;
                    case RequestCode.User.CODE_MEMBER_FORGITPSD:
                        cb.onGetForgitPasSuccess(rsp);
                        break;
                    case RequestCode.User.CODE_MEMBER_GETCAPTCHA:
                        cb.onGetCaptchaSuccess(rsp);
                        break;

                }
            }
        });
    }

    @Override
    protected void onRequestFailed(final int seq, final int errCode, final String msg) {
        super.onRequestFailed(seq, errCode, msg);
        notifyDataChangedInMainThread(new CallbackHelper.Caller<UserCallback>() {
            @Override
            public void call(UserCallback cb) {
                switch (seq) {
                    case RequestCode.User.CODE_MEMBER_LOGIN:
                        cb.onLoginFail(errCode, msg);
                        ToastUtils.showToast(msg);
                        break;
                    case RequestCode.User.CODE_MEMBER_CURRENTTASK:
                        cb.onGetCurrentTaskFail(errCode, msg);
                        break;
                    case RequestCode.User.CODE_MEMBER_HISTORYTASK:
                        cb.onGetHistoryTaskFail(errCode, msg);
                        break;
                    case RequestCode.User.CODE_MEMBER_MARCHANTCHECK:
                        cb.onGetCheckListFail(errCode, msg);
                        break;
                    case RequestCode.User.CODE_MEMBER_SAVEMERCHANT:
                        cb.onGetSaveMerchatFail(errCode, msg);
                        break;
                    case RequestCode.User.CODE_MEMBER_LOGOUT:
                        cb.onLogOutFail(errCode, msg);
                        break;
                    case RequestCode.User.CODE_MEMBER_UPDATAUSER:
                        cb.onUpdataUserFail(errCode, msg);
                        break;
                    case RequestCode.User.CODE_MEMBER_SESSION:
                        cb.onUpdateSessionFail(errCode, msg);
                        break;
                    case RequestCode.User.CODE_MEMBER_GETMERCHANT:
                        cb.onGetMerchantFail(errCode, msg);
                        break;
                    case RequestCode.User.CODE_MEMBER_GETTASKSTORE:
                        cb.onGetStoreTaskFail(errCode, msg);
                        break;
                    case RequestCode.User.CODE_MEMBER_PROVINCELIST:
                        cb.onGetProvinceListFail(errCode, msg);
                        break;
                    case RequestCode.User.CODE_MEMBER_CITYLIST:
                        cb.onGetCityListFail(errCode, msg);
                        break;
                    case RequestCode.User.CODE_MEMBER_AREASLIST:
                        cb.onGetAreasListFail(errCode, msg);
                        break;
                    case RequestCode.User.CODE_MEMBER_FORGITPSD:
                        cb.onGetForgitPasFail(errCode, msg);
                        break;
                    case RequestCode.User.CODE_MEMBER_GETCAPTCHA:
                        cb.onGetCaptchaFail(errCode, msg);
                        break;
                }
            }
        });
    }
}
