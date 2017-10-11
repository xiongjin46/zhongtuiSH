package com.push.PushMerchant.network.impl;


import com.push.PushMerchant.base.BaseResp;
import com.push.PushMerchant.modules.home.bean.AreasBaseResp;
import com.push.PushMerchant.modules.home.bean.CityBaseResp;
import com.push.PushMerchant.modules.home.bean.ProvinceListBaseResp;
import com.push.PushMerchant.modules.person.bean.PersonBeanResp;
import com.push.PushMerchant.modules.person.bean.ProjectDetailResp;
import com.push.PushMerchant.modules.person.bean.UserProfile;
import com.push.PushMerchant.modules.person.bean.CheckListResp;
import com.push.PushMerchant.modules.person.bean.HistoryTaskResp;

/**
 * @ClassName: UserCallback
 * @Description: 用户相关接口回调定义
 * @author: YYL
 * <p>
 * create at 2016/12/22 9:52
 */
public interface UserCallback extends ActionCallback {

    void onLoginSuccess(UserProfile rsp);

    void onLoginFail(int errCode, String msg);

    void onGetCurrentTaskSuccess(HistoryTaskResp rsp);

    void onGetCurrentTaskFail(int errCode, String msg);

    void onGetHistoryTaskSuccess(HistoryTaskResp rsp);

    void onGetHistoryTaskFail(int errCode, String msg);

    void onGetCheckListSuccess(CheckListResp rsp);

    void onGetCheckListFail(int errCode, String msg);

    void onGetSaveMerchatSuccess(BaseResp rsp);

    void onGetSaveMerchatFail(int errCode, String msg);

    void onLogOutSuccess(BaseResp rsp);

    void onLogOutFail(int errCode, String msg);

    void onUpdataUserSuccess(BaseResp rsp);

    void onUpdataUserFail(int errCode, String msg);

    void onUpdateSessionSuccess(PersonBeanResp rsp);

    void onUpdateSessionFail(int errCode, String msg);

    void onGetMerchantSuccess(BaseResp rsp);

    void onGetMerchantFail(int errCode, String msg);

    void onGetStoreTaskSuccess(ProjectDetailResp rsp);

    void onGetStoreTaskFail(int errCode, String msg);

    void onGetProvinceListSuccess(ProvinceListBaseResp rsp);

    void onGetProvinceListFail(int errCode, String msg);

    void onGetCityListSuccess(CityBaseResp rsp);

    void onGetAreasListFail(int errCode, String msg);

    void onGetAreasListSuccess(AreasBaseResp rsp);

    void onGetCityListFail(int errCode, String msg);

    void onGetForgitPasSuccess(BaseResp rsp);

    void onGetForgitPasFail(int errCode, String msg);

    void onGetCaptchaSuccess(BaseResp rsp);

    void onGetCaptchaFail(int errCode, String msg);


    class Stub implements UserCallback {

        @Override
        public void onLoginSuccess(UserProfile rsp) {

        }

        @Override
        public void onLoginFail(int errCode, String msg) {

        }

        @Override
        public void onGetCurrentTaskSuccess(HistoryTaskResp rsp) {

        }

        @Override
        public void onGetCurrentTaskFail(int errCode, String msg) {

        }

        @Override
        public void onGetHistoryTaskSuccess(HistoryTaskResp rsp) {

        }

        @Override
        public void onGetHistoryTaskFail(int errCode, String msg) {

        }

        @Override
        public void onGetCheckListSuccess(CheckListResp rsp) {

        }

        @Override
        public void onGetCheckListFail(int errCode, String msg) {

        }

        @Override
        public void onGetSaveMerchatSuccess(BaseResp rsp) {

        }

        @Override
        public void onGetSaveMerchatFail(int errCode, String msg) {

        }

        @Override
        public void onLogOutSuccess(BaseResp rsp) {

        }

        @Override
        public void onLogOutFail(int errCode, String msg) {

        }

        @Override
        public void onUpdataUserSuccess(BaseResp rsp) {

        }

        @Override
        public void onUpdataUserFail(int errCode, String msg) {

        }

        @Override
        public void onUpdateSessionSuccess(PersonBeanResp rsp) {

        }

        @Override
        public void onUpdateSessionFail(int errCode, String msg) {

        }

        @Override
        public void onGetMerchantSuccess(BaseResp rsp) {

        }

        @Override
        public void onGetMerchantFail(int errCode, String msg) {

        }

        @Override
        public void onGetStoreTaskSuccess(ProjectDetailResp rsp) {

        }

        @Override
        public void onGetStoreTaskFail(int errCode, String msg) {

        }

        @Override
        public void onGetProvinceListSuccess(ProvinceListBaseResp rsp) {

        }

        @Override
        public void onGetProvinceListFail(int errCode, String msg) {

        }

        @Override
        public void onGetCityListSuccess(CityBaseResp rsp) {

        }

        @Override
        public void onGetAreasListFail(int errCode, String msg) {

        }

        @Override
        public void onGetAreasListSuccess(AreasBaseResp rsp) {

        }

        @Override
        public void onGetCityListFail(int errCode, String msg) {

        }

        @Override
        public void onGetForgitPasSuccess(BaseResp rsp) {

        }

        @Override
        public void onGetForgitPasFail(int errCode, String msg) {

        }

        @Override
        public void onGetCaptchaSuccess(BaseResp rsp) {

        }

        @Override
        public void onGetCaptchaFail(int errCode, String msg) {

        }
    }
}
