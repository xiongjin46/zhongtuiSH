package com.push.PushMerchant.modules;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.push.PushMerchant.base.PushApplicationContext;
import com.push.PushMerchant.constants.IntentFlag;
import com.push.PushMerchant.database.CommonDatabaseHelper;
import com.push.PushMerchant.modules.home.bean.AppVersionBean;
import com.push.PushMerchant.modules.home.bean.AreasBaseResp;
import com.push.PushMerchant.modules.home.bean.CityBaseResp;
import com.push.PushMerchant.modules.home.bean.InitBaseResp;
import com.push.PushMerchant.modules.home.bean.ProvinceListBaseResp;
import com.push.PushMerchant.network.engine.ServerEngine;
import com.push.PushMerchant.network.engine.UserEngine;
import com.push.PushMerchant.network.impl.ServerCallback;
import com.push.PushMerchant.network.impl.UserCallback;
import com.push.PushMerchant.util.FileUtil;
import com.push.PushMerchant.util.Global;
import com.push.PushMerchant.util.ImageLoader;
import com.push.PushMerchant.util.SharePrefUtil;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

/**
 * @ClassName: com.jiaoao.lzb.lzbApplication
 * @Description:
 * @author: YYL
 * create at 2017/3/21 0021 上午 10:34
 */
public class InitManager {
    private static InitManager sInitManager;
    //    private final Application context;
//    private final ServerEngine mServerEngine;
//    private final ServerSub mServerSub;
    private final UserEngine userEngine;
    private final UserSub userSub;
    private ProvinceListBaseResp provincelist;
    private CityBaseResp cityList;
    private AreasBaseResp areasList;
    private final ServerEngine mServerEngine;
    private final ServerSub mServerSub;
    private Context mContext;


    public synchronized static InitManager getsInstance() {
        if (sInitManager == null) {
            sInitManager = new InitManager();
        }
        return sInitManager;
    }

    private InitManager() {
        userEngine = new UserEngine();
        userSub = new UserSub();

        mServerEngine = new ServerEngine();
        mServerSub = new ServerSub();

    }

    public void loadAppVersion() {
        mServerEngine.register(mServerSub);
//        updateAddress();
        mServerEngine.init(ServerEngine.INITVERSION);
    }

    public void updateAddress() {
        userEngine.register(userSub);
        userEngine.getRrovinceList();
    }

    class ServerSub extends ServerCallback.Stub {

        /**
         * 获取最新版本信息
         *
         * @param rsp
         */
        @Override
        public void onGetVersionSuccess(InitBaseResp rsp) {
            super.onGetVersionSuccess(rsp);
            mServerEngine.unregisterAll();
            if (rsp.getObject() != null) {
                List<InitBaseResp.ObjectBean> lists = rsp.getObject();
                if (lists.size() > 7) {
                    if (!Global.getVersionName(PushApplicationContext.context).equals(lists.get(9).getRemark())) {
                        PushApplicationContext.appVersion = new AppVersionBean(lists.get(0).getEnumValue2() == 0,
                                lists.get(1).getEnumValue2(), lists.get(2).getRemark(), lists.get(3).getRemark(),
                                lists.get(4).getRemark(), Integer.parseInt(lists.get(5).getRemark()),
                                lists.get(6).getRemark(), Integer.parseInt(lists.get(7).getRemark()),
                                Integer.parseInt(lists.get(8).getRemark()), lists.get(9).getRemark(),
                                lists.get(10).getRemark(),lists.get(11).getRemark());
                    }
                    final String splashUrl = lists.get(12).getRemark();
                    String splash = SharePrefUtil.getStringSetting(IntentFlag.SPLASHURL, "");

                    int intSetting = SharePrefUtil.getIntSetting(IntentFlag.ADDRESSVERSION, -1);
                    if (intSetting != lists.get(1).getEnumValue2()) {
                        updateAddress();
                        SharePrefUtil.putIntSetting(IntentFlag.ADDRESSVERSION, lists.get(1).getEnumValue2());
                    }
                    if (TextUtils.isEmpty(splashUrl)) {
                        SharePrefUtil.putStringSetting(IntentFlag.SPLASHURL, "");
                        return;
                    } else {
                        //修改闪屏页
                        if (!splashUrl.equals(splash)) {
                            Glide.with(PushApplicationContext.context).load(splashUrl).asBitmap()
                                    .into(new SimpleTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                            String savePathName = FileUtil.getImagePath() + "/splash_" + Global.getMessageSuffix() + ".jpg";
                                            FileUtil.isFileExists(savePathName);
                                            try {
                                                resource.compress(Bitmap.CompressFormat.JPEG, 70, new FileOutputStream(savePathName));
                                                SharePrefUtil.putStringSetting(IntentFlag.SPLASHURL, ImageLoader.getStartImageResult(splashUrl));
                                                PushApplicationContext.context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + savePathName)));
                                            } catch (FileNotFoundException e) {
                                                e.printStackTrace();

                                            }

                                        }
                                    });
                        }
                    }
                }
            }
        }

        @Override
        public void onGetVersionFail(int errCode, String msg) {
            super.onGetVersionFail(errCode, msg);
        }
    }

    class UserSub extends UserCallback.Stub {

        @Override
        public void onGetProvinceListSuccess(ProvinceListBaseResp rsp) {
            super.onGetProvinceListSuccess(rsp);
            userEngine.getCityList(rsp.getObject().get(0).getProvinceCode());
            provincelist = rsp;
            //删除旧库
            CommonDatabaseHelper.getInstance().delTableName(CommonDatabaseHelper.TABLE_ADDRESS_PROVINCE);
            //添加新库
            CommonDatabaseHelper.getInstance().updataAddressProvince(rsp);
            upUserEngine();
        }

        @Override
        public void onGetProvinceListFail(int errCode, String msg) {
            super.onGetProvinceListFail(errCode, msg);
            provincelist = new ProvinceListBaseResp();
            upUserEngine();
        }

        @Override
        public void onGetCityListSuccess(CityBaseResp rsp) {
            super.onGetCityListSuccess(rsp);
            userEngine.getAreasList(rsp.getObject().get(0).getCityCode());
            cityList = rsp;
            //删除旧库
            CommonDatabaseHelper.getInstance().delTableName(CommonDatabaseHelper.TABLE_ADDRESS_CITY);
            //添加新库
            CommonDatabaseHelper.getInstance().updataAddressCity(rsp);
            upUserEngine();
        }

        @Override
        public void onGetCityListFail(int errCode, String msg) {
            super.onGetCityListFail(errCode, msg);
            cityList = new CityBaseResp();
            upUserEngine();
        }

        @Override
        public void onGetAreasListSuccess(AreasBaseResp rsp) {
            super.onGetAreasListSuccess(rsp);
            areasList = rsp;
            //删除旧库
            CommonDatabaseHelper.getInstance().delTableName(CommonDatabaseHelper.TABLE_ADDRESS_AREAS);
            //添加新库
            CommonDatabaseHelper.getInstance().updataAddressAreas(rsp);
            upUserEngine();
        }

        @Override
        public void onGetAreasListFail(int errCode, String msg) {
            super.onGetAreasListFail(errCode, msg);
            areasList = new AreasBaseResp();
            upUserEngine();
        }

    }

    private void upUserEngine() {
        if (provincelist != null && cityList != null && areasList != null) {
            userEngine.unregisterAll();

        }
    }
}
