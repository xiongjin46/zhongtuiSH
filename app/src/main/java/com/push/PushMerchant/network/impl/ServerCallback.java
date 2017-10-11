package com.push.PushMerchant.network.impl;


import com.push.PushMerchant.base.BaseResp;
import com.push.PushMerchant.modules.advert.bean.StoreDetailResp;
import com.push.PushMerchant.modules.advert.bean.StoreResp;
import com.push.PushMerchant.modules.home.bean.InitBaseResp;
import com.push.PushMerchant.modules.home.bean.ProductListResp;
import com.push.PushMerchant.modules.home.bean.SthufflePageBaseResp;
import com.push.PushMerchant.modules.map.bean.CircleResp;
import com.push.PushMerchant.modules.map.bean.CityResp;
import com.push.PushMerchant.modules.map.bean.StoreByCityResp;
import com.push.PushMerchant.modules.person.bean.MerchantStoreResp;

/**
 * Created by admin on 2017/1/10.
 */

public interface ServerCallback extends ActionCallback {

    void onUpdateSessionSuccess(StoreResp rsp);

    void onUpdateSessionFail(int errCode, String msg);

    void onGetSthuffleListSuccess(SthufflePageBaseResp rsp);

    void onGetSthuffleListFail(int errCode, String msg);

    void onGetStoreDetailSuccess(StoreDetailResp rsp);

    void onGetStoreDetailFail(int errCode, String msg);

    void onGetMerchantStoreSuccess(MerchantStoreResp rsp);

    void onGetMerchantStoreFail(int errCode, String msg);

    void onGetVersionSuccess(InitBaseResp rsp);

    void onGetVersionFail(int errCode, String msg);

    void onGetProductListSuccess(ProductListResp rsp);

    void onGetProductListFail(int errCode, String msg);

    void onGetCircleSuccess(CircleResp rsp);

    void onGetCircleFail(int errCode, String msg);

    void onGetStoreByCitySuccess(StoreByCityResp rsp);

    void onGetStoreByCityFail(int errCode, String msg);

    void onGetStoreByCircleSuccess(StoreByCityResp rsp);

    void onGetStoreByCircleFail(int errCode, String msg);

    void onGetCityListSuccess(CityResp rsp);

    void onGetCityListFail(int errCode, String msg);

    void onGetSaveMessageSuccess(BaseResp rsp);

    void onGetSaveMessageFail(int errCode, String msg);

    class Stub implements ServerCallback {

        @Override
        public void onUpdateSessionSuccess(StoreResp rsp) {

        }

        @Override
        public void onUpdateSessionFail(int errCode, String msg) {

        }

        @Override
        public void onGetSthuffleListSuccess(SthufflePageBaseResp rsp) {

        }

        @Override
        public void onGetSthuffleListFail(int errCode, String msg) {

        }

        @Override
        public void onGetStoreDetailSuccess(StoreDetailResp rsp) {

        }

        @Override
        public void onGetStoreDetailFail(int errCode, String msg) {

        }

        @Override
        public void onGetMerchantStoreSuccess(MerchantStoreResp rsp) {

        }

        @Override
        public void onGetMerchantStoreFail(int errCode, String msg) {

        }

        @Override
        public void onGetVersionSuccess(InitBaseResp rsp) {

        }

        @Override
        public void onGetVersionFail(int errCode, String msg) {

        }

        @Override
        public void onGetProductListSuccess(ProductListResp rsp) {

        }

        @Override
        public void onGetProductListFail(int errCode, String msg) {

        }

        @Override
        public void onGetCircleSuccess(CircleResp rsp) {

        }

        @Override
        public void onGetCircleFail(int errCode, String msg) {

        }

        @Override
        public void onGetStoreByCitySuccess(StoreByCityResp rsp) {

        }

        @Override
        public void onGetStoreByCityFail(int errCode, String msg) {

        }

        @Override
        public void onGetStoreByCircleSuccess(StoreByCityResp rsp) {

        }

        @Override
        public void onGetStoreByCircleFail(int errCode, String msg) {

        }

        @Override
        public void onGetCityListSuccess(CityResp rsp) {

        }

        @Override
        public void onGetCityListFail(int errCode, String msg) {

        }

        @Override
        public void onGetSaveMessageSuccess(BaseResp rsp) {

        }

        @Override
        public void onGetSaveMessageFail(int errCode, String msg) {

        }
    }
}
