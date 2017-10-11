package com.push.PushMerchant.modules;

import android.text.TextUtils;

import com.push.PushMerchant.constants.GConstants;
import com.push.PushMerchant.modules.person.bean.UserProfile;
import com.push.PushMerchant.util.SharePrefUtil;


/**
 * @ClassName: AccountManager
 * @Description: 用户信息管理
 * @author: YYL
 * <p>
 * create at 2016/11/17 10:13
 */
public class AccountManager {

    private static AccountManager sInstance;
    private int userId;
    private int userStatus;
    private String headimgurl;
    private int mUserType;
    private String mAccessToken = "";
    private UserProfile mUserEntity;
    private String personCode;
    private String realName;
    private int personType;
    private String mobile;
    private String address;
    private String trade;


    private AccountManager() {
        mAccessToken = SharePrefUtil.getStringUser(GConstants.KEY_ACCESSTOKEN, "");
        mUserType = SharePrefUtil.getIntUser(GConstants.KEY_USERTYPE, -1);
        headimgurl = SharePrefUtil.getStringUser(GConstants.KEY_HEADIMGURL, "");
        personCode = SharePrefUtil.getStringUser(GConstants.KEY_PERSONCODE, "");
        userId = SharePrefUtil.getIntUser(GConstants.KEY_USERID, 0);
        userStatus = SharePrefUtil.getIntUser(GConstants.KEY_STATUS, 0);
        realName = SharePrefUtil.getStringUser(GConstants.KEY_REALNAME, "");
        mobile = SharePrefUtil.getStringUser(GConstants.KEY_PHONE, "");
        address = SharePrefUtil.getStringUser(GConstants.KEY_ADDRESS, "");
        trade = SharePrefUtil.getStringUser(GConstants.KEY_TRADE, "");
    }

    public static AccountManager getInstance() {
        if (sInstance == null) {
            sInstance = new AccountManager();
        }
        return sInstance;
    }

    public UserProfile getUserEntity() {
        if (mUserEntity == null) {
            mUserEntity = new UserProfile();
            UserProfile.ObjectEntity objectEntity = new UserProfile.ObjectEntity();
            objectEntity.setAuthToken(mAccessToken);
            objectEntity.setPersonType(mUserType);
            objectEntity.setPersonCode(personCode);
            objectEntity.setLogoUrl(headimgurl);
            objectEntity.setState(userStatus);
            objectEntity.setRealName(realName);
            objectEntity.setPersonType(personType);
            objectEntity.setMobile(mobile);
            objectEntity.setMerchantId(userId);
            objectEntity.setAddress(address);
            objectEntity.setTrades(trade);
            mUserEntity.setObject(objectEntity);
            return mUserEntity;
        }
        return mUserEntity;
    }

    public void setUserEntity(UserProfile mUserEntity) {
        UserProfile.ObjectEntity object = mUserEntity.getObject();
        if (object == null) return;
        SharePrefUtil.putStringUser(GConstants.KEY_ACCESSTOKEN, object.getAuthToken());
        SharePrefUtil.putIntUser(GConstants.KEY_USERTYPE, object.getPersonType());
        SharePrefUtil.putStringUser(GConstants.KEY_HEADIMGURL, object.getLogoUrl());
        SharePrefUtil.putStringUser(GConstants.KEY_PERSONCODE, object.getPersonCode());
        SharePrefUtil.putIntUser(GConstants.KEY_STATUS, object.getState());
        SharePrefUtil.putStringUser(GConstants.KEY_REALNAME, object.getRealName());
        SharePrefUtil.putStringUser(GConstants.KEY_PHONE, object.getMobile());
        SharePrefUtil.putIntUser(GConstants.KEY_PERSONTYPE, object.getPersonType());
        SharePrefUtil.putIntUser(GConstants.KEY_USERID, object.getMerchantId());
        SharePrefUtil.putStringUser(GConstants.KEY_ADDRESS, object.getAddress());
        SharePrefUtil.putStringUser(GConstants.KEY_TRADE, object.getTrades());

        mAccessToken = object.getAuthToken();
        mUserType = object.getPersonType();
        userStatus = object.getState();
        personCode = object.getPersonCode();
        headimgurl = object.getLogoUrl();
        realName = object.getRealName();
        personType = object.getPersonType();
        mobile = object.getMobile();
        userId = object.getMerchantId();
        trade = object.getTrades();
        address = object.getAddress();
        this.mUserEntity = mUserEntity;
    }


    /**
     * 是否已经登录
     *
     * @return
     */
    public boolean isLogin() {
        return !TextUtils.isEmpty(mAccessToken);
    }

    /**
     * 退出登录
     */
    public void logout() {
        mAccessToken = "";
        mUserType = -1;
        personCode = "";
        headimgurl = "";
        userStatus = 0;
        userId = 0;
        realName = "";
        personType = 0;
        mobile = "";
        SharePrefUtil.clearUser();
    }

}
