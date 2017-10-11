package com.push.PushMerchant.modules.map.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.push.PushMerchant.R;
import com.push.PushMerchant.base.BaseFragment;
import com.push.PushMerchant.base.BaseToolbarActivity;
import com.push.PushMerchant.modules.map.bean.CircleResp;
import com.push.PushMerchant.modules.map.bean.StoreByCityResp;
import com.push.PushMerchant.network.engine.ServerEngine;
import com.push.PushMerchant.network.impl.ServerCallback;
import com.push.PushMerchant.util.BitmapUtil;
import com.push.PushMerchant.util.DialogHelper;
import com.push.PushMerchant.util.JumpUtil;
import com.push.PushMerchant.util.SharePrefUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Administrator on 2017/6/19 0019.
 * 地图
 */

public class MapFragment extends BaseFragment implements View.OnClickListener{
    @BindView(R.id.mapview)
    MapView mapview;
    @BindView(R.id.tv_address)
    TextView tv_address;
    private AMap mAMap;
    private String latitude;
    private String longitude;
    private List<StoreByCityResp.ObjectBean> list;
    private List<CircleResp.ObjectBean> listCircle;
    private ServerEngine serverEngine;
    private ServerSub serverSub;
    private String city;
    private final int GETADDRESS = 0x0001;  //获取地址
    private ArrayList<String> citylist;
    private String cityName;
    private Marker circlemarker;
    private Marker storemarker;
    private List<Marker> list2;
    private List<Marker> list3;
    private List<StoreByCityResp.ObjectBean> listStore1;

    @Override
    public int getLayoutId() {
        return R.layout.fg_map;
    }

    @Override
    protected void initBeforeView() {
        latitude = SharePrefUtil.getStringSetting("latitude", "");
        longitude = SharePrefUtil.getStringSetting("longitude", "");
        city = SharePrefUtil.getStringSetting("city", "");
        citylist = new ArrayList<>();
        list = new ArrayList<>();
        listCircle = new ArrayList<>();
        listStore1 = new ArrayList<>();
        list2 = new ArrayList<>();
        list3 = new ArrayList<>();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mapview.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected void initAfterView() {
        ((BaseToolbarActivity) mActivity).hintBar1();
        serverEngine = new ServerEngine();
        serverSub = new ServerSub();
        ((BaseToolbarActivity) mActivity).bindCallback(serverSub, serverEngine);
        tv_address.setText(city);
        serverEngine.getBusinessAreaByCityId(tv_address.getText().toString());
    }

    /**
     * 初始化AMap对象
     */
    private void init() {
        if (mAMap == null) {
            mAMap = mapview.getMap();
        }
        setUpMap();
    }

    private void setUpMap() {
        if (!TextUtils.isEmpty(latitude)){
            LatLng lp = new LatLng(Double.valueOf(latitude), Double.valueOf(longitude));
            mAMap.moveCamera(CameraUpdateFactory.changeLatLng(lp));//初始化地图中心点
            mAMap.moveCamera(CameraUpdateFactory.zoomTo(11));// 初始化地图缩放比例
            UiSettings uiSettings = mAMap.getUiSettings();
            uiSettings.setZoomControlsEnabled(false);
            MarkerOptions markOptiopns = new MarkerOptions();
            markOptiopns.position(lp);
            markOptiopns.icon(
                    BitmapDescriptorFactory.fromBitmap(BitmapFactory
                            .decodeResource(getResources(),
                                    R.mipmap.cover_map_location_mine)));
            mAMap.addMarker(markOptiopns);
        }
        mAMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                if (cameraPosition.zoom > 13) {
                    if (list2 != null) {
                        for (int i = 0; i < list2.size(); i++) {
                            Marker marker = list2.get(i);
                            marker.remove();
                        }
                        list2.clear();
                        serverEngine.getStoreInfoByCityName(tv_address.getText().toString());
                    }
                } else {
                    if (list3 != null) {
                        for (int i = 0; i < list3.size(); i++) {
                            Marker marker = list3.get(i);
                            marker.remove();
                        }
                        list3.clear();
                        serverEngine.getBusinessAreaByCityId(tv_address.getText().toString());
                    }
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        //销毁资源
        mapview.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        mapview.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        mapview.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        mapview.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1001) {
                mAMap.clear();
                String cityName = data.getStringExtra("cityName");
                double latItude = data.getDoubleExtra("latItude", 0);
                double longItude = data.getDoubleExtra("longItude", 0);
                LatLng lp = new LatLng(latItude, longItude);
                mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lp, 11));
                tv_address.setText(cityName);
                serverEngine.getBusinessAreaByCityId(cityName);
            }
        }
    }

    @OnClick({R.id.tv_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_address:
                JumpUtil.toCity(MapFragment.this, 1001);
                break;
        }
    }

    /*
     *显示所有商圈
     */
    private void initCircle(final List<CircleResp.ObjectBean> beanList) {
        for (int i = 0; i < beanList.size(); i++) {
            final View view = View.inflate(getActivity(), R.layout.map_circle, null);
            TextView tv_circle = (TextView) view.findViewById(R.id.tv_circle);
            tv_circle.setText(beanList.get(i).getChannelName() + "\n" + beanList.get(i).getStoreCount());

            Bitmap bitmap = BitmapUtil.convertViewToBitmap(view);
            LatLng agent = new LatLng(Double.valueOf(beanList.get(i).getLatItude()),
                    Double.valueOf(beanList.get(i).getLongItude()));
            circlemarker = mAMap.addMarker(new MarkerOptions()
                    .position(agent)
                    .perspective(true)
                    .icon(BitmapDescriptorFactory.fromBitmap(bitmap)));
            list2.add(circlemarker);
        }

        mAMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                LatLng latLng = marker.getPosition();
                for (int i = 0; i < listCircle.size(); i++) {
                    LatLng latLngList = new LatLng(Double.valueOf(beanList.get(i).getLatItude()), Double.valueOf(beanList.get(i).getLongItude()));
                    if (latLng.equals(latLngList)) {
                        mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngList, 14));
                        break;
                    }
                }
                return true;
            }
        });
    }

    /*
    *显示所有门店
    */
    private void initStore(final List<StoreByCityResp.ObjectBean> bean) {
        for (int i = 0; i < bean.size(); i++) {
            LatLng agent = new LatLng(Double.valueOf(bean.get(i).getLatItude()),
                    Double.valueOf(bean.get(i).getLongItude()));
            storemarker = mAMap.addMarker(new MarkerOptions()
                    .position(agent)
                    .perspective(true)
                    .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                            .decodeResource(getResources(),
                                    R.mipmap.detail_location_icon))));
            list3.add(storemarker);

            mAMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    LatLng latLng = marker.getPosition();
                    for (int i = 0; i < list.size(); i++) {
                        LatLng latLngList = new LatLng(Double.valueOf(bean.get(i).getLatItude()), Double.valueOf(bean.get(i).getLongItude()));
                        if (latLng.equals(latLngList)) {
                            DialogHelper.showStoreDiaLog(mActivity, list.get(i), new DialogHelper.OnConfirmDiaLogListener1() {
                                @Override
                                public void onClick(int id) {
                                    JumpUtil.toAdvertDetail(mActivity, id);
                                }
                            });
                            break;
                        }
                    }
                    return true;
                }
            });
        }
    }

    /*
    *显示对应商圈下面的门店
    */

    private void initStore2(final List<StoreByCityResp.ObjectBean> bean) {
        for (int i = 0; i < bean.size(); i++) {
            LatLng agent = new LatLng(Double.valueOf(bean.get(i).getLatItude()),
                    Double.valueOf(bean.get(i).getLongItude()));
            storemarker = mAMap.addMarker(new MarkerOptions()
                    .position(agent)
                    .perspective(true)
                    .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                            .decodeResource(getResources(),
                                    R.mipmap.detail_location_icon))));
            list3.add(storemarker);

            mAMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    LatLng latLng = marker.getPosition();
                    for (int i = 0; i < listStore1.size(); i++) {
                        LatLng latLngList = new LatLng(Double.valueOf(bean.get(i).getLatItude()), Double.valueOf(bean.get(i).getLongItude()));
                        if (latLng.equals(latLngList)) {
                            ((BaseToolbarActivity) mActivity).showProgressDlg();
                            serverEngine.getStoreDetail(bean.get(i).getId());
                            break;
                        }
                    }
                    return true;
                }
            });
        }
    }

//    @Override
//    public void onRemoveCacheFinish(boolean b) {
//        mAMap.removecache();
//    }

    class ServerSub extends ServerCallback.Stub {

        @Override
        public void onGetCircleSuccess(CircleResp rsp) {
            super.onGetCircleSuccess(rsp);
            isload = false;
            ((BaseToolbarActivity) mActivity).dissProgressDlg();
            if (frame != null)
                frame.refreshComplete();
            if (rsp.getObject() != null) {
                listCircle.clear();
                listCircle.addAll(rsp.getObject());
                initCircle(rsp.getObject());
            }
        }

        @Override
        public void onGetCircleFail(int errCode, String msg) {
            super.onGetCircleFail(errCode, msg);
        }

        @Override
        public void onGetStoreByCitySuccess(StoreByCityResp rsp) {
            super.onGetStoreByCitySuccess(rsp);
            isload = false;
            ((BaseToolbarActivity) mActivity).dissProgressDlg();
            if (frame != null)
                frame.refreshComplete();
            if (rsp.getObject() != null) {
                list.clear();
                list.addAll(rsp.getObject());
                initStore(rsp.getObject());
            }
        }

        @Override
        public void onGetStoreByCityFail(int errCode, String msg) {
            super.onGetStoreByCityFail(errCode, msg);
            isload = false;
            ((BaseToolbarActivity) mActivity).dissProgressDlg();
        }

        @Override
        public void onGetStoreByCircleSuccess(StoreByCityResp rsp) {
            super.onGetStoreByCircleSuccess(rsp);
            isload = false;
            ((BaseToolbarActivity) mActivity).dissProgressDlg();
            if (frame != null)
                frame.refreshComplete();
            if (rsp.getObject() != null) {
                listStore1.clear();
                listStore1.addAll(rsp.getObject());
                initStore2(rsp.getObject());
            }
        }

        @Override
        public void onGetStoreByCircleFail(int errCode, String msg) {
            super.onGetStoreByCircleFail(errCode, msg);
            isload = false;
            ((BaseToolbarActivity) mActivity).dissProgressDlg();
        }
    }
}
