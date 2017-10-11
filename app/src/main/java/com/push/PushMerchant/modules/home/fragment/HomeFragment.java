package com.push.PushMerchant.modules.home.fragment;


import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.push.PushMerchant.R;
import com.push.PushMerchant.base.BaseFragment;
import com.push.PushMerchant.base.BaseToolbarActivity;
import com.push.PushMerchant.base.PushApplicationContext;
import com.push.PushMerchant.database.AddressDBUtils;
import com.push.PushMerchant.modules.home.bean.AreasBaseResp;
import com.push.PushMerchant.modules.home.bean.CityBaseResp;
import com.push.PushMerchant.modules.home.bean.ProductListResp;
import com.push.PushMerchant.modules.home.bean.ProvinceListBaseResp;
import com.push.PushMerchant.modules.home.bean.SendAdvetResp;
import com.push.PushMerchant.modules.home.bean.SthufflePageBaseResp;
import com.push.PushMerchant.network.ThreadManager;
import com.push.PushMerchant.network.engine.ServerEngine;
import com.push.PushMerchant.network.impl.ServerCallback;
import com.push.PushMerchant.util.DeviceUtils;
import com.push.PushMerchant.util.ToastUtils;
import com.push.PushMerchant.view.RippleView;
import com.push.PushMerchant.view.handler.EventEnum;
import com.push.PushMerchant.view.impl.IOnItemClickListener;
import com.push.PushMerchant.weight.viewpage.CBViewHolderCreator;
import com.push.PushMerchant.weight.viewpage.ConvenientBanner;
import com.push.PushMerchant.weight.viewpage.EventTopHolderView;
import com.push.PushMerchant.weight.wheel.AddressOptionsWindowHelper;
import com.push.PushMerchant.weight.wheel.pickview.CharacterPickerWindow;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/19 0019.
 * 首页
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.home_banner_ripple)
    RippleView home_banner_ripple;
    @BindView(R.id.home_banner)
    ConvenientBanner homeBanner;
    @BindView(R.id.tv_provice)
    TextView tv_provice;
    @BindView(R.id.tv_time)
    TextView tv_time;
    private ArrayList<SthufflePageBaseResp.ObjectBean> topBanners;
    private ServerEngine serverEngine;
    private ServerSub serverSub;
    private final int GETADDRESS = 0x0002;  //获取地址
    private final int GETPRODUCT = 0x0001;  //获取产品类型
    private ArrayList<ArrayList<ArrayList<String>>> districtlist;
    private ArrayList<ArrayList<String>> cityStrlist;
    private ArrayList<ProvinceListBaseResp.ObjectEntity> provincelist;
    private ArrayList<String> provinceStrlist;
    private ArrayList<ArrayList<CityBaseResp.ObjectEntity>> citylist;
    private ArrayList<ArrayList<ArrayList<AreasBaseResp.ObjectEntity>>> Areaselist;
    private int areaId, cityId, proviceId;
    private ArrayList<String> productList;
    private ArrayList<ProductListResp.ObjectBean> productList2;
    private int productId;
    private int one, two, three, produt;


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GETADDRESS:
                    provinceStrlist.add(0, "区域不限");
                    ArrayList<String> list1 = new ArrayList<>();
                    list1.add(0, "");
                    ArrayList<ArrayList<String>> list2 = new ArrayList<>();
                    list2.add(0, list1);
                    cityStrlist.add(0, list1);
                    districtlist.add(0, list2);
                    OptionsPickerView pvOptions = new OptionsPickerView.Builder(mActivity, new OptionsPickerView.OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int options2, int options3, View v) {
                            //返回的分别是三个级别的选中位置
                            one = options1;
                            two = options2;
                            three = options3;

                            String province = provinceStrlist.get(options1);
                            String city = cityStrlist.get(options1).get(options2);
                            String district = districtlist.get(options1).get(options2).get(options3);
                            if (city.equals("不限")) {
                                tv_provice.setText(province);
                            } else if (province.equals(city)) {
                                if (district.equals("不限")) {
                                    tv_provice.setText(province);
                                } else {
                                    tv_provice.setText(district);
                                }
                            } else if (district.equals("不限")) {
                                tv_provice.setText(province + city);
                            } else {
                                tv_provice.setText(province + city + district);
                            }

                            if (province.equals("区域不限")) {
                                proviceId = 0;
                                cityId = 0;
                                areaId = 0;
                            } else if (province.equals("台湾省")) {
                                proviceId = provincelist.get(options1 - 1).getId();
                            } else if (province.equals("香港特别行政区")) {
                                proviceId = provincelist.get(options1 - 1).getId();
                            } else if (province.equals("澳门特别行政区")) {
                                proviceId = provincelist.get(options1 - 1).getId();
                            } else {
                                proviceId = provincelist.get(options1 - 1).getId();
                                cityId = citylist.get(options1 - 1).get(options2).getId();
                                areaId = Areaselist.get(options1 - 1).get(options2).get(options3).getId();
                            }
                        }
                    })
                            .setDividerColor(Color.BLACK)
                            .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                            .setContentTextSize(20)
                            .build();

                    pvOptions.setSelectOptions(one, two, three);
                    pvOptions.setPicker(provinceStrlist, cityStrlist, districtlist);//三级选择器
                    pvOptions.show();
                    break;
                case GETPRODUCT:
                    productList.add(0, "产品不限");
                    AddressOptionsWindowHelper.setOptions1Items(productList);
                    AddressOptionsWindowHelper.location(produt, 0, 0);
                    final CharacterPickerWindow window1 = AddressOptionsWindowHelper.builder(mActivity, new AddressOptionsWindowHelper.OnOptionsSelectListener() {

                        @Override
                        public void onOptionsSelect(String province, String city, String area,
                                                    int pro, int c, int a) {
                            produt = pro;
                            tv_time.setText(province);
                            if (province.equals("产品不限")) {
                                productId = 0;
                            } else {
                                productId = productList2.get(pro - 1).getId();
                            }
                        }
                    });
                    window1.showAtLocation(tv_time, Gravity.BOTTOM, 0, 0);
                    break;
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.fg_home;
    }

    @Override
    protected void initBeforeView() {
        productList = new ArrayList<>();
        productList2 = new ArrayList<>();
        topBanners = new ArrayList<>();
        provinceStrlist = new ArrayList<>();
        cityStrlist = new ArrayList<>();
        districtlist = new ArrayList<>();
    }

    @Override
    protected void initAfterView() {
        ((BaseToolbarActivity) mActivity).hintBar1();
        serverEngine = new ServerEngine();
        serverSub = new ServerSub();
        ((BaseToolbarActivity) mActivity).bindCallback(serverSub, serverEngine);
        ((BaseToolbarActivity) mActivity).showProgressDlg();
        serverEngine.getSthuffleList();
        initTopBanner();
    }

    private void initTopBanner() {
//        homeBanner.setArclayout(DeviceUtils.dp2px(mActivity, 20), false, 0);
        homeBanner.setPages(new CBViewHolderCreator<EventTopHolderView>() {
            @Override
            public EventTopHolderView createHolder() {
                return new EventTopHolderView();
            }
        }, topBanners).setPageIndicator(new int[]{R.drawable.shape_oval_white, R.drawable.shape_oval_black})
                .setOnItemClickListener(new IOnItemClickListener() {
                    @Override
                    public void onItemClick(final View view, final int position) {
                        home_banner_ripple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                            @Override
                            public void onComplete(RippleView rippleView) {

                            }
                        });

                    }

                });
        if (!homeBanner.isTurning()) {
            homeBanner.startTurning(3000);
        }
        homeBanner.setParentView(ptr);
    }

    @OnClick({R.id.rl_search, R.id.rl_provice, R.id.rl_time})
    public void onClick(View view) {
        String address = tv_provice.getText().toString();
        String time = tv_time.getText().toString();
        switch (view.getId()) {
            case R.id.rl_search:
                SendAdvetResp bean = new SendAdvetResp();
                bean.setAreaId(areaId);
                bean.setCityId(cityId);
                bean.setProviceId(proviceId);
                bean.setProductId(productId);
                bean.setAddress(address);
                bean.setTime(time);
                bean.setPruduct(produt);
                bean.setOne(one);
                bean.setTwo(two);
                bean.setThree(three);
                android.os.Message msg = android.os.Message.obtain();
                msg.what = EventEnum.EVENT_MESSAGE_PRIVATE;
                msg.obj = bean;
                PushApplicationContext.sEventDispatcher.handleMessage(msg);
                break;
            case R.id.rl_provice:
                productList.clear();
                provinceStrlist.clear();
                cityStrlist.clear();
                districtlist.clear();
                ThreadManager.executeOnSubThread(new Runnable() {
                    @Override
                    public void run() {
                        if (cityStrlist.isEmpty()) {
                            CreateDB();
                        } else {
                            handler.sendEmptyMessage(GETADDRESS);
                        }

                    }
                });
                break;
            case R.id.rl_time:
                provinceStrlist.clear();
                cityStrlist.clear();
                districtlist.clear();
                productList.clear();
                ThreadManager.executeOnSubThread(new Runnable() {
                    @Override
                    public void run() {
                        if (productList.isEmpty()) {
                            serverEngine.getProductList();
                        } else {
                            handler.sendEmptyMessage(GETPRODUCT);
                        }
                    }
                });
                break;
        }
    }

    private void CreateDB() {
        ThreadManager.executeOnFileThread(new Runnable() {
            @Override
            public void run() {
                provincelist = new AddressDBUtils().getProvincelist();
                if (provincelist.isEmpty()) {
                    return;
                } else {
                    citylist = new AddressDBUtils().getCitylist(provincelist);
                    Areaselist = new AddressDBUtils().getDistrictlist(citylist);
                    for (ProvinceListBaseResp.ObjectEntity prov : provincelist) {
                        provinceStrlist.add(prov.getProvinceName());
                    }
                    for (int i = 0; i < citylist.size(); i++) {
                        ArrayList<String> strings = new ArrayList<>();
                        for (CityBaseResp.ObjectEntity citystr : citylist.get(i)) {
                            strings.add(citystr.getCityName());
                        }
                        cityStrlist.add(strings);
                    }
                    for (ArrayList<ArrayList<AreasBaseResp.ObjectEntity>> list1 : Areaselist) {
                        ArrayList<ArrayList<String>> listStr1 = new ArrayList<>();
                        for (ArrayList<AreasBaseResp.ObjectEntity> list2 : list1) {
                            ArrayList<String> listStr2 = new ArrayList<>();
                            for (AreasBaseResp.ObjectEntity bean : list2) {
                                listStr2.add(bean.getAreasName());
                            }
                            listStr1.add(listStr2);
                        }
                        districtlist.add(listStr1);
                    }
                    handler.sendEmptyMessage(GETADDRESS);
                }

            }
        });
    }


    class ServerSub extends ServerCallback.Stub {

        @Override
        public void onGetSthuffleListSuccess(SthufflePageBaseResp rsp) {
            super.onGetSthuffleListSuccess(rsp);
            ((BaseToolbarActivity) mActivity).dissProgressDlg();
            if (frame != null)
                frame.refreshComplete();
            if (rsp.getObject() != null) {
                topBanners.clear();
                topBanners.addAll(rsp.getObject());
                homeBanner.notifyDataSetChanged();
            }
        }

        @Override
        public void onUpdateSessionFail(int errCode, String msg) {
            super.onUpdateSessionFail(errCode, msg);
            if (frame != null)
                frame.refreshComplete();
            ((BaseToolbarActivity) mActivity).dissProgressDlg();
        }

        @Override
        public void onGetProductListSuccess(ProductListResp rsp) {
            super.onGetProductListSuccess(rsp);
            ((BaseToolbarActivity) mActivity).dissProgressDlg();
            if (frame != null)
                frame.refreshComplete();
            if (rsp.getObject() != null) {
                productList.clear();
                productList2.clear();
                productList2.addAll(rsp.getObject());
                for (int i = 0; i < rsp.getObject().size(); i++) {
                    productList.add(rsp.getObject().get(i).getName());
                }
                handler.sendEmptyMessage(GETPRODUCT);
            }
        }

        @Override
        public void onGetProductListFail(int errCode, String msg) {
            super.onGetProductListFail(errCode, msg);
            ((BaseToolbarActivity) mActivity).dissProgressDlg();
            if (frame != null)
                frame.refreshComplete();
        }
    }
}
