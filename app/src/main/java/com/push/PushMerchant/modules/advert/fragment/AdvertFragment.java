package com.push.PushMerchant.modules.advert.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.push.PushMerchant.R;
import com.push.PushMerchant.base.BaseFragment;
import com.push.PushMerchant.base.BaseToolbarActivity;
import com.push.PushMerchant.base.PushApplicationContext;
import com.push.PushMerchant.database.AddressDBUtils;
import com.push.PushMerchant.modules.advert.adapter.AdvertAdapter;
import com.push.PushMerchant.modules.advert.bean.StoreResp;
import com.push.PushMerchant.modules.home.bean.AreasBaseResp;
import com.push.PushMerchant.modules.home.bean.CityBaseResp;
import com.push.PushMerchant.modules.home.bean.ProductListResp;
import com.push.PushMerchant.modules.home.bean.ProvinceListBaseResp;
import com.push.PushMerchant.modules.home.bean.SendAdvetResp;
import com.push.PushMerchant.network.ThreadManager;
import com.push.PushMerchant.network.engine.ServerEngine;
import com.push.PushMerchant.network.impl.ServerCallback;
import com.push.PushMerchant.util.JumpUtil;
import com.push.PushMerchant.util.ToastUtils;
import com.push.PushMerchant.view.handler.EventEnum;
import com.push.PushMerchant.view.handler.UIEventListener;
import com.push.PushMerchant.view.impl.IOnItemClickListener;
import com.push.PushMerchant.weight.recycleview.HeaderAndFooterWrapper;
import com.push.PushMerchant.weight.recycleview.LoadmoreWrapper;
import com.push.PushMerchant.weight.wheel.AddressOptionsWindowHelper;
import com.push.PushMerchant.weight.wheel.pickview.CharacterPickerWindow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Administrator on 2017/6/19 0019.
 * 广告源
 */

public class AdvertFragment extends BaseFragment implements View.OnClickListener, UIEventListener {
    @BindView(R.id.id_stickynavlayout_innerscrollview)
    RecyclerView recyclerView;
    @BindView(R.id.tv_area)
    TextView tv_area;
    @BindView(R.id.et_advert)
    EditText et_advert;
    @BindView(R.id.rl_price)
    RelativeLayout rl_price;
    @BindView(R.id.tv_productype)
    TextView tv_productype;
    @BindView(R.id.tv_price)
    TextView tv_price;
    private AdvertAdapter adapter;
    private HeaderAndFooterWrapper headerAndFooterWrapper;
    private LoadmoreWrapper loadmoreWrapper;
    private ServerEngine serverEngine;
    private ServerSub serverSub;
    private List<StoreResp.ObjectBean.ListBean> list;
    private final int GETADDRESS = 0x0002;  //获取地址
    private final int GETPRODUCT = 0x0001;  //获取产品类型
    private final int GETPPRICE = 0x0003;  //获取价格
    private ArrayList<ArrayList<ArrayList<String>>> districtlist;
    private ArrayList<ArrayList<String>> cityStrlist;
    private ArrayList<String> provinceStrlist;
    private ArrayList<ProvinceListBaseResp.ObjectEntity> provincelist;
    private ArrayList<ArrayList<CityBaseResp.ObjectEntity>> citylist;
    private ArrayList<ArrayList<ArrayList<AreasBaseResp.ObjectEntity>>> Areaselist;
    private int areaId, cityId, proviceId;
    private ArrayList<String> productList;
    private ArrayList<ProductListResp.ObjectBean> productList2;
    private ArrayList<String> pricelist;
    private String[] stringArray;
    private int productId, putbeginMoney, putbeginendMoney, putprice;
    private int one, two, three;
    private int produt, price;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GETADDRESS:
                    //初始化
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
                                tv_area.setText(province);
                            } else if (province.equals(city)) {
                                if (district.equals("不限")){
                                    tv_area.setText(province);
                                }else {
                                    tv_area.setText(district);
                                }
                            } else if (district.equals("不限")) {
                                tv_area.setText(province+city);
                            } else {
                                tv_area.setText(province + city + district);
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
                            ((BaseToolbarActivity) mActivity).showProgressDlg();
                            if (putprice != 0) {
                                serverEngine.upStoreByMerchart("", "", putprice, 0, proviceId, cityId, areaId, productId, page, "");
                            } else {
                                serverEngine.upStoreByMerchart("", "", putbeginMoney, putbeginendMoney, proviceId, cityId, areaId, productId, page, "");
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
                            page = 1;
                            produt = pro;
                            tv_productype.setText(province);
                            if (province.equals("产品不限")) {
                                productId = 0;
                            } else {
                                productId = productList2.get(pro - 1).getId();
                            }
                            ((BaseToolbarActivity) mActivity).showProgressDlg();
                            if (putprice != 0) {
                                serverEngine.upStoreByMerchart("", "", putprice, 0, proviceId, cityId, areaId, productId, page, "");
                            } else {
                                serverEngine.upStoreByMerchart("", "", putbeginMoney, putbeginendMoney, proviceId, cityId, areaId, productId, page, "");
                            }
                        }
                    });
                    window1.showAtLocation(rl_price, Gravity.BOTTOM, 0, 0);
                    break;
                case GETPPRICE:
                    AddressOptionsWindowHelper.setOptions1Items(pricelist);
                    AddressOptionsWindowHelper.location(price, 0, 0);
                    final CharacterPickerWindow window2 = AddressOptionsWindowHelper.builder(mActivity, new AddressOptionsWindowHelper.OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(String province, String city, String area,
                                                    int pro, int c, int a) {
                            price = pro;
                            String price = pricelist.get(pro);
                            page = 1;
                            tv_price.setText(price);
                            ((BaseToolbarActivity) mActivity).showProgressDlg();
                            if (price.equals("1000以上")) {
                                String substring = price.substring(0, 4);
                                putprice = Integer.valueOf(substring) * 100;
                                serverEngine.upStoreByMerchart("", "", putprice, 0, proviceId, cityId, areaId, productId, page, "");
                            } else if (price.equals("价格不限")) {
                                putbeginMoney = 0;
                                putbeginendMoney = 0;
                                putprice = 0;
                                serverEngine.upStoreByMerchart("", "", putbeginMoney, putbeginendMoney, proviceId, cityId, areaId, productId, page, "");
                            } else {
                                putprice = 0;
                                String beginMoney = price.substring(0, price.indexOf("-"));
                                String endMoney = price.substring(price.indexOf("-") + 1, price.length());
                                putbeginMoney = Integer.valueOf(beginMoney) * 100;
                                putbeginendMoney = Integer.valueOf(endMoney) * 100;
                                serverEngine.upStoreByMerchart("", "", putbeginMoney, putbeginendMoney, proviceId, cityId, areaId, productId, page, "");
                            }
                        }
                    });
                    window2.showAtLocation(rl_price, Gravity.BOTTOM, 0, 0);
                    break;
            }
        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fg_advert;
    }

    @Override
    protected void initBeforeView() {
        list = new ArrayList<>();
        productList = new ArrayList<>();
        productList2 = new ArrayList<>();
        provinceStrlist = new ArrayList<>();
        cityStrlist = new ArrayList<>();
        districtlist = new ArrayList<>();
        stringArray = getResources().getStringArray(R.array.price_list);
        pricelist = new ArrayList();
        Collections.addAll(pricelist, stringArray);
    }

    @Override
    protected void initAfterView() {
        ((BaseToolbarActivity) mActivity).hintBar1();
        serverEngine = new ServerEngine();
        serverSub = new ServerSub();
        ((BaseToolbarActivity) mActivity).bindCallback(serverSub, serverEngine);
        onRefresh(page);
        adapter = new AdvertAdapter(mActivity, list);
        adapter.setiOnItemClickListener(new IOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                JumpUtil.toAdvertDetail(mActivity, list.get(position).getId());
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        headerAndFooterWrapper = new HeaderAndFooterWrapper(adapter);
        loadmoreWrapper = new LoadmoreWrapper(headerAndFooterWrapper);
        final View loadview = LayoutInflater.from(mActivity).inflate(R.layout.view_loading, new LinearLayout(mActivity), false);
        loadmoreWrapper.setLoadMoreView(loadview);
        loadmoreWrapper.setOnLoadMoreListener(new LoadmoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (!isload) {
                    if (hasNext) {
                        page++;
                        onRefresh(page);
                        ((TextView) loadview.findViewById(R.id.tv_loading_tips)).setText("正在加载...");
                        loadview.findViewById(R.id.pb_loading).setVisibility(View.VISIBLE);
                    } else {
                        ((TextView) loadview.findViewById(R.id.tv_loading_tips)).setText("没有更多数据了");
                        loadview.findViewById(R.id.pb_loading).setVisibility(View.GONE);
                    }
                }
            }
        });
        recyclerView.setAdapter(loadmoreWrapper);
    }


    private void onRefresh(int page) {
        if (!isload) {
            isload = true;
            ((BaseToolbarActivity) mActivity).showProgressDlg();
            if (putprice != 0) {
                serverEngine.upStoreByMerchart("", "", putprice, 0, proviceId, cityId, areaId, productId, page, "");
            } else {
                serverEngine.upStoreByMerchart("", "", putbeginMoney, putbeginendMoney, proviceId, cityId, areaId, productId, page, "");
            }
        }
    }

    @Override
    public boolean canDoRefresh() {
        int firstCompletelyVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        if (firstCompletelyVisibleItemPosition == 0) {
            return true;
        }
        return false;
    }

    @Override
    public void refreshBegin(PtrFrameLayout frame) {
        ThreadManager.executeOnFileThread(new Runnable() {
            @Override
            public void run() {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        page = 1;
                        onRefresh(page);
                    }
                });
            }
        });
    }

    @OnClick({R.id.rl_area, R.id.rl_product, R.id.rl_price, R.id.tv_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_area:
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
            case R.id.rl_product:
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
            case R.id.rl_price:
                String productype = tv_productype.getText().toString();
                if (TextUtils.isEmpty(productype) || productype.equals("产品不限")) {
                    ToastUtils.showToast("请先选产品");
                    return;
                }
                provinceStrlist.clear();
                cityStrlist.clear();
                districtlist.clear();
                productList.clear();
                ThreadManager.executeOnSubThread(new Runnable() {
                    @Override
                    public void run() {
                        if (pricelist.isEmpty()) {
                            Collections.addAll(pricelist, stringArray);
                            handler.sendEmptyMessage(GETPPRICE);
                        } else {
                            handler.sendEmptyMessage(GETPPRICE);
                        }
                    }
                });
                break;
            case R.id.tv_search:
                page = 1;
                if (!TextUtils.isEmpty(et_advert.getText().toString())) {
                    ((BaseToolbarActivity) mActivity).showProgressDlg();
                    serverEngine.upStoreByMerchart("", "", 0, 0, 0, 0, 0, 0, page,
                            et_advert.getText().toString());
                } else {
                    ((BaseToolbarActivity) mActivity).showProgressDlg();
                    serverEngine.upStoreByMerchart("", "", 0, 0, 0, 0, 0, 0, page,
                            "");
                }
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


    @Override
    public void onResume() {
        super.onResume();
        PushApplicationContext.sEventController.addUIEventListener(EventEnum.EVENT_MESSAGE_PRIVATE, this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PushApplicationContext.sEventController.removeUIEventListener(EventEnum.EVENT_MESSAGE_PRIVATE, this);
    }

    @Override
    public void handleUIEvent(Message msg) {
        SendAdvetResp bean = (SendAdvetResp) msg.obj;
        areaId = bean.getAreaId();
        cityId = bean.getCityId();
        productId = bean.getProductId();
        proviceId = bean.getProviceId();
        one = bean.getOne();
        two = bean.getTwo();
        three = bean.getThree();
        produt = bean.getPruduct();
        String address = bean.getAddress();
        String time = bean.getTime();
        if (!TextUtils.isEmpty(address)) {
            tv_area.setText(address);
        }
        if (!TextUtils.isEmpty(time)) {
            tv_productype.setText(time);
        }
        isload = true;
        page = 1;
        ((BaseToolbarActivity) mActivity).showProgressDlg();
        serverEngine.upStoreByMerchart("", "", 0, 0, proviceId, cityId, areaId, productId, page, "");
    }

    class ServerSub extends ServerCallback.Stub {

        @Override
        public void onUpdateSessionSuccess(StoreResp rsp) {
            super.onUpdateSessionSuccess(rsp);
            isload = false;
            ((BaseToolbarActivity) mActivity).dissProgressDlg();
            if (frame != null)
                frame.refreshComplete();
            if (page == 1) {
                list.clear();
            }
            if (rsp.getObject() != null) {
                if (rsp.getObject().getList() != null) {
                    list.addAll(rsp.getObject().getList());
                    hasNext = rsp.getObject().getCount() > page * serverEngine.rows;
                }
                loadmoreWrapper.notifyDataSetChanged();
            }
        }

        @Override
        public void onUpdateSessionFail(int errCode, String msg) {
            super.onUpdateSessionFail(errCode, msg);
            isload = false;
            ((BaseToolbarActivity) mActivity).dissProgressDlg();
            if (frame != null)
                frame.refreshComplete();
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
