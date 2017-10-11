package com.push.PushMerchant.network.engine;


import android.text.TextUtils;
import com.push.PushMerchant.base.BaseResp;
import com.push.PushMerchant.constants.GConstants;
import com.push.PushMerchant.modules.advert.bean.StoreDetailResp;
import com.push.PushMerchant.modules.advert.bean.StoreResp;
import com.push.PushMerchant.modules.home.bean.InitBaseResp;
import com.push.PushMerchant.modules.home.bean.ProductListResp;
import com.push.PushMerchant.modules.home.bean.SthufflePageBaseResp;
import com.push.PushMerchant.modules.map.bean.CircleResp;
import com.push.PushMerchant.modules.map.bean.CityResp;
import com.push.PushMerchant.modules.map.bean.StoreByCityResp;
import com.push.PushMerchant.modules.person.bean.MerchantStoreResp;
import com.push.PushMerchant.network.BaseEngine;
import com.push.PushMerchant.network.impl.IServerEngine;
import com.push.PushMerchant.network.impl.ServerCallback;
import com.push.PushMerchant.network.req.RequestCode;
import com.push.PushMerchant.network.resp.CallbackHelper;
import java.util.HashMap;

/**
 * @ClassName: ServerEngine
 * @Description:
 * @author: YYL
 * <p>
 * create at 2017/1/10 11:00
 */

public class ServerEngine extends BaseEngine<ServerCallback> implements IServerEngine {

    public static int rows = 10;

    @Override
    public void upStoreByMerchart(String beginDate, String endDate, int beginMoney, int endMoney,
                                  int provinceId, int cityId, int regionId, int productId, int page, String name) {
        HashMap<String, String> defaultHashMap = new HashMap<>();
        if (!TextUtils.isEmpty(beginDate)) {
            defaultHashMap.put("beginDate", beginDate);
        }
        if (!TextUtils.isEmpty(endDate)) {
            defaultHashMap.put("endDate", endDate);
        }
        if (beginMoney != 0) {
            defaultHashMap.put("beginMoney", beginMoney + "");
        }
        if (endMoney != 0) {
            defaultHashMap.put("endMoney", endMoney + "");
        }
        if (provinceId != 0) {
            defaultHashMap.put("provinceId", provinceId + "");
        }
        if (cityId != 0) {
            defaultHashMap.put("cityId", cityId + "");
        }
        if (regionId != 0) {
            defaultHashMap.put("regionId", regionId + "");
        }
        if (productId != 0) {
            defaultHashMap.put("productId", productId + "");
        }
        if (!TextUtils.isEmpty(name)) {
            defaultHashMap.put("name", name);
        }
        defaultHashMap.put("page", page + "");
        defaultHashMap.put("rows", rows + "");
        sendPost(RequestCode.Server.CODE_SERVER_GETSTORE, GConstants.API_SERVER.API_SERVER_GETSTORE, defaultHashMap, StoreResp.class);

    }

    @Override
    public void getSthuffleList() {
        HashMap<String, String> defaultHashMap = getDefaultHashMap();
        sendPost(RequestCode.Server.CODE_SERVER_SHUFFLE, GConstants.API_SERVER.API_SERVER_SHUFFLE, defaultHashMap, SthufflePageBaseResp.class);
    }

    @Override
    public void getStoreDetail(int id) {
        HashMap<String, String> defaultHashMap = new HashMap<>();
        defaultHashMap.put("id", id + "");
        sendPost(RequestCode.Server.CODE_SERVER_STOREDETAIL, GConstants.API_SERVER.API_SERVER_STOREDETAIL, defaultHashMap, StoreDetailResp.class);
    }

    @Override
    public void getMerchantStoreById(int id, int taskId) {
        HashMap<String, String> defaultHashMap = getDefaultHashMap();
        defaultHashMap.put("id", id + "");
        defaultHashMap.put("taskId", taskId + "");
        sendPost(RequestCode.Server.CODE_SERVER_MERCHANTSTORE, GConstants.API_SERVER.API_SERVER_MERCHATSTORE, defaultHashMap, MerchantStoreResp.class);
    }

    @Override
    public void init(String enumTypeCode) {
        HashMap<String, String> defaultHashMap = getDefaultHashMap();
        defaultHashMap.put("enumTypeCode", enumTypeCode);
        sendPost(RequestCode.Server.CODE_SERVER_VERSION, GConstants.API_SERVER.API_SERVER_TRIBEACTIVITY, defaultHashMap, InitBaseResp.class);
    }

    @Override
    public void getProductList() {
        HashMap<String, String> defaultHashMap = new HashMap<>();
        defaultHashMap.put("", "");
        sendPost(RequestCode.Server.CODE_SERVER_PRODUCTLIST, GConstants.API_SERVER.API_SERVER_PRODUCTLIST, defaultHashMap, ProductListResp.class);
    }

    @Override
    public void getBusinessAreaByCityId(String cityName) {
        HashMap<String, String> defaultHashMap = getDefaultHashMap();
        defaultHashMap.put("cityName", cityName);
        sendPost(RequestCode.Server.CODE_SERVER_GETCIRCLE, GConstants.API_SERVER.API_SERVER_GETCIRCLE, defaultHashMap, CircleResp.class);
    }

    @Override
    public void getStoreInfoByCityName(String  cityName) {
        HashMap<String, String> defaultHashMap = getDefaultHashMap();
        defaultHashMap.put("cityName", cityName);
        sendPost(RequestCode.Server.CODE_SERVER_GETSTOREBYCITY, GConstants.API_SERVER.API_SERVER_GETSTOREBYCITY, defaultHashMap, StoreByCityResp.class);
    }

    @Override
    public void getStoreByBusinessAreaId(int businessId) {
        HashMap<String, String> defaultHashMap = getDefaultHashMap();
        defaultHashMap.put("businessId", businessId+"");
        sendPost(RequestCode.Server.CODE_SERVER_GETSTOREBYCIRCLE, GConstants.API_SERVER.API_SERVER_GETSTOREBYCIRCLE, defaultHashMap, StoreByCityResp.class);
    }

    @Override
    public void getCityPage(int page) {
        HashMap<String, String> defaultHashMap = new HashMap<>();
        defaultHashMap.put("page", page + "");
        defaultHashMap.put("rows", rows + "");
        sendPost(RequestCode.Server.CODE_SERVER_GETCITYPAGE, GConstants.API_SERVER.API_SERVER_GETCITYPAGE, defaultHashMap, CityResp.class);
    }

    @Override
    public void saveAppointmentInfo(String name, String phone) {
        HashMap<String, String> defaultHashMap = new HashMap<>();
        defaultHashMap.put("name", name);
        defaultHashMap.put("phone", phone);
        sendPost(RequestCode.Server.CODE_SERVER_SAVEMESSAGE, GConstants.API_SERVER.API_SERVER_SAVEMESSAGE, defaultHashMap, BaseResp.class);
    }

    @Override
    protected void onRequestSuccess(final int seq, final BaseResp rsp) {
        super.onRequestSuccess(seq, rsp);
        notifyDataChangedInMainThread(new CallbackHelper.Caller<ServerCallback>() {
            @Override
            public void call(ServerCallback cb) {
                switch (seq) {
                    case RequestCode.Server.CODE_SERVER_GETSTORE:
                        cb.onUpdateSessionSuccess((StoreResp) rsp);
                        break;
                    case RequestCode.Server.CODE_SERVER_SHUFFLE:
                        cb.onGetSthuffleListSuccess((SthufflePageBaseResp) rsp);
                        break;
                    case RequestCode.Server.CODE_SERVER_STOREDETAIL:
                        cb.onGetStoreDetailSuccess((StoreDetailResp) rsp);
                        break;
                    case RequestCode.Server.CODE_SERVER_MERCHANTSTORE:
                        cb.onGetMerchantStoreSuccess((MerchantStoreResp) rsp);
                        break;
                    case RequestCode.Server.CODE_SERVER_VERSION:
                        cb.onGetVersionSuccess((InitBaseResp) rsp);
                        break;
                    case RequestCode.Server.CODE_SERVER_PRODUCTLIST:
                        cb.onGetProductListSuccess((ProductListResp) rsp);
                        break;
                    case RequestCode.Server.CODE_SERVER_GETCIRCLE:
                        cb.onGetCircleSuccess((CircleResp) rsp);
                        break;
                    case RequestCode.Server.CODE_SERVER_GETSTOREBYCITY:
                        cb.onGetStoreByCitySuccess((StoreByCityResp) rsp);
                        break;
                    case RequestCode.Server.CODE_SERVER_GETSTOREBYCIRCLE:
                        cb.onGetStoreByCircleSuccess((StoreByCityResp) rsp);
                        break;
                    case RequestCode.Server.CODE_SERVER_GETCITYPAGE:
                        cb.onGetCityListSuccess((CityResp) rsp);
                        break;
                    case RequestCode.Server.CODE_SERVER_SAVEMESSAGE:
                        cb.onGetSaveMessageSuccess(rsp);
                        break;

                }
            }
        });
    }

    @Override
    protected void onRequestFailed(final int seq, final int errCode, final String msg) {
        super.onRequestFailed(seq, errCode, msg);
        notifyDataChangedInMainThread(new CallbackHelper.Caller<ServerCallback>() {

            @Override
            public void call(ServerCallback cb) {
                switch (seq) {
                    case RequestCode.Server.CODE_SERVER_GETSTORE:
                        cb.onUpdateSessionFail(errCode, msg);
                        break;
                    case RequestCode.Server.CODE_SERVER_SHUFFLE:
                        cb.onGetSthuffleListFail(errCode, msg);
                        break;
                    case RequestCode.Server.CODE_SERVER_STOREDETAIL:
                        cb.onGetStoreDetailFail(errCode, msg);
                        break;
                    case RequestCode.Server.CODE_SERVER_MERCHANTSTORE:
                        cb.onGetMerchantStoreFail(errCode, msg);
                        break;
                    case RequestCode.Server.CODE_SERVER_VERSION:
                        cb.onGetVersionFail(errCode, msg);
                        break;
                    case RequestCode.Server.CODE_SERVER_PRODUCTLIST:
                        cb.onGetProductListFail(errCode, msg);
                        break;
                    case RequestCode.Server.CODE_SERVER_GETCIRCLE:
                        cb.onGetCircleFail(errCode, msg);
                        break;
                    case RequestCode.Server.CODE_SERVER_GETSTOREBYCITY:
                        cb.onGetStoreByCityFail(errCode, msg);
                        break;
                    case RequestCode.Server.CODE_SERVER_GETSTOREBYCIRCLE:
                        cb.onGetStoreByCircleFail(errCode, msg);
                        break;
                    case RequestCode.Server.CODE_SERVER_GETCITYPAGE:
                        cb.onGetCityListFail(errCode, msg);
                        break;
                    case RequestCode.Server.CODE_SERVER_SAVEMESSAGE:
                        cb.onGetSaveMessageFail(errCode, msg);
                        break;
                }
            }
        });
    }

}
