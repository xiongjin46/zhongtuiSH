package com.push.PushMerchant.modules;

import android.content.Context;
import android.util.Log;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.push.PushMerchant.base.PushApplicationContext;
import com.push.PushMerchant.util.SharePrefUtil;


/**
 * @ClassName: LocationManager
 * @Description: 地图相关
 * @author: YYL
 * <p>
 * create at 2016/12/24 11:41
 */
public class LocationManager {

    private static LocationManager instance;
    private Context mContext;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener;

    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    public static final String LOCATCITY = "locatcity";
    private double latitude, longitude;


    public static LocationManager getInstance() {
        if (instance == null) {
            instance = new LocationManager();
        }
        return instance;
    }

    public void start() {
        mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        //可在其中解析amapLocation获取相应内容。
                        //为空的时候才会执行定位保存
                        if (SharePrefUtil.getStringSetting(LOCATCITY, "全部").equals("全部")) {
//                            DBManager dbManager = new DBManager(LzbApplicationContext.context);
//                            List<CityBaseResp.ObjectEntity> allCitys = dbManager.getAllCitys();
//                            if (!allCitys.isEmpty()) {      //数据库不为空则
                                SharePrefUtil.putStringSetting("city", aMapLocation.getCity());
                                SharePrefUtil.putStringSetting(LOCATCITY, aMapLocation.getCity());
                                SharePrefUtil.putStringSetting("province", aMapLocation.getProvince());
                                SharePrefUtil.putStringSetting("district", aMapLocation.getDistrict());
                                SharePrefUtil.putStringSetting("address", aMapLocation.getAddress());
                                SharePrefUtil.putStringSetting("latitude",aMapLocation.getLatitude()+"");
                                SharePrefUtil.putStringSetting("longitude",aMapLocation.getLongitude()+"");
                                SharePrefUtil.putStringSetting("street",aMapLocation.getStreet()
                                );
//                                for (CityBaseResp.ObjectEntity bean : allCitys) {
//                                    if (bean.getCityName().equals(aMapLocation.getCity())) {
//                                        String citycode = dbManager.getCityCode(aMapLocation.getCity());
//                                        SharePrefUtil.putStringSetting("cityCode", citycode);
//                                        break;
//                                    }
//                                }
//                            }
                        }
                        latitude = aMapLocation.getLatitude();//获取纬度
                        longitude = aMapLocation.getLongitude();//获取经度
                        if (mLocationClient != null)
                            mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
                    } else {
                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError", "location Error, ErrCode:"
                                + aMapLocation.getErrorCode() + ", errInfo:"
                                + aMapLocation.getErrorInfo());
                        mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
                    }
                }
            }
        };
        //初始化定位
        mLocationClient = new AMapLocationClient(PushApplicationContext.context);
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);

        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();

        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);

        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
