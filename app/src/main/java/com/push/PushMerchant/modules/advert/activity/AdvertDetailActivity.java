package com.push.PushMerchant.modules.advert.activity;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.push.PushMerchant.R;
import com.push.PushMerchant.base.BaseResp;
import com.push.PushMerchant.base.BaseToolbarActivity;
import com.push.PushMerchant.constants.GConstants;
import com.push.PushMerchant.modules.advert.adapter.ProducetAdapter;
import com.push.PushMerchant.modules.advert.bean.StoreDetailResp;
import com.push.PushMerchant.network.engine.ServerEngine;
import com.push.PushMerchant.network.impl.ServerCallback;
import com.push.PushMerchant.util.CommonUtil;
import com.push.PushMerchant.util.DialogHelper;
import com.push.PushMerchant.util.ImageLoader;
import com.push.PushMerchant.util.ToastUtils;
import com.push.PushMerchant.view.FlowLayout;
import com.push.PushMerchant.view.impl.IOnItemClickListener;
import com.push.PushMerchant.weight.dialog.DialogPlus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/25 0025.
 * 广告源详情
 */

public class AdvertDetailActivity extends BaseToolbarActivity implements View.OnClickListener {
    @BindView(R.id.store_pic)
    ImageView store_pic;
    @BindView(R.id.tv_store_name)
    TextView tv_store_name;
    @BindView(R.id.tv_store_address)
    TextView tv_store_address;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.tv_task_push)
    TextView tv_task_push;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    @BindView(R.id.tv_content)
    WebView tv_content;
    @BindView(R.id.tv_sup_size)
    TextView tv_sup_size;
    @BindView(R.id.tv_house_pepple)
    TextView tv_house_pepple;
    @BindView(R.id.tv_house_price)
    TextView tv_house_price;
    @BindView(R.id.tv_environment)
    TextView tv_environment;
    @BindView(R.id.flowlayout)
    FlowLayout flowlayout;
    @BindView(R.id.flowlayout_psychology)
    FlowLayout flowlayout_psychology;
    @BindView(R.id.tv_people)
    TextView tv_people;
    @BindView(R.id.tv_day)
    TextView tv_day;
    @BindView(R.id.ll_near)
    LinearLayout ll_near;
    private int id;
    private ServerEngine serverEngine;
    private ServerSub serverSub;
    private List<StoreDetailResp.ObjectBean.ProductListBean> list;
    private ProducetAdapter adapter;
    private DialogPlus dialogPlus1;

    @Override
    protected int loadViewLayout() {
        return R.layout.ac_advert_detail;
    }

    @Override
    protected void initDataBeforeView() {
        id = getIntent().getIntExtra("id", 0);
        list = new ArrayList<>();
    }

    @Override
    protected void initDataAfterView() {
        setTitleString("广告源详情");
        setDefaultBack("");
        serverEngine = new ServerEngine();
        serverSub = new ServerSub();
        bindCallback(serverSub, serverEngine);
        showProgressDlg();
        serverEngine.getStoreDetail(id);

        adapter = new ProducetAdapter(this, list);
        adapter.setiOnItemClickListener(new IOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                adapter.setDefSelect(position);
                tv_price.setText("¥" + list.get(position).getPrice() / 100);
                tv_content.loadDataWithBaseURL(null, GConstants.HTML_KEY + list.get(position).getContent() +
                        "</body></html>", "text/html", "UTF-8", null);
            }
        });
        recycleview.setAdapter(adapter);
        recycleview.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
    }

    private void initData(StoreDetailResp.ObjectBean bean) {
        tv_store_name.setText(bean.getName());//名字
        tv_store_address.setText(bean.getFullAddress());//地址
        tv_price.setText("¥" + bean.getProductList().get(0).getPrice() / 100);//价钱
        if ((bean.getManFlow() == null)) {
            tv_task_push.setVisibility(View.GONE);
            tv_day.setVisibility(View.GONE);
            tv_people.setVisibility(View.GONE);
        } else {
            tv_task_push.setText(bean.getManFlow() + "人");//覆盖人群
        }
        tv_content.loadDataWithBaseURL(null, GConstants.HTML_KEY + bean.getProductList().get(0).getContent() +
                "</body></html>", "text/html", "UTF-8", null);//产品详细说明
        if (bean.getAreaMan()==null){
            ll_near.setVisibility(View.GONE);
        }else {
            tv_house_pepple.setText(bean.getAreaMan() + "万人");//片区人口
            tv_house_price.setText(bean.getHotelRatesRIm() + "元/平");//周边房价
        }
        tv_environment.setText(bean.getScope());//3公里环境

        if (bean.getTimeoutType() == null) {
            tv_sup_size.setVisibility(View.GONE);
        } else if (bean.getTimeoutType() == 0) {
            tv_sup_size.setText("小");
        } else if (bean.getTimeoutType() == 1) {
            tv_sup_size.setText("中小");
        } else if (bean.getTimeoutType() == 2) {
            tv_sup_size.setText("中");
        } else if (bean.getTimeoutType() == 3) {
            tv_sup_size.setText("中大");
        } else {
            tv_sup_size.setText("大");
        }
        ImageLoader.load(!TextUtils.isEmpty(bean.getStoreUrl()) ? bean.getStoreUrl() : "", store_pic, this);
        //周边人群类型
        flowlayout.removeAllViews();
        List<String> radiateCrowdIdArray = bean.getRadiateCrowdIdArray();
        for (int i = 0; i < radiateCrowdIdArray.size(); i++) {
            TextView text = (TextView) LayoutInflater.from(this).inflate(
                    R.layout.topic_tag_text, flowlayout, false);
            text.setText("#" + radiateCrowdIdArray.get(i) + "#");
            flowlayout.addView(text);
        }
        //人群消费心理
        flowlayout_psychology.removeAllViews();
        List<String> expenseHeartArray = bean.getExpenseHeartArray();
        for (int i = 0; i < expenseHeartArray.size(); i++) {
            TextView text = (TextView) LayoutInflater.from(this).inflate(
                    R.layout.topic_tag_text, flowlayout, false);
            text.setText("#" + expenseHeartArray.get(i) + "#");
            flowlayout_psychology.addView(text);
        }
    }

    @OnClick({R.id.tv_call, R.id.tv_order})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_call:
                DialogHelper.showConfirmDiaLog(this, getString(R.string.call), new DialogHelper.OnConfirmDiaLogListener() {
                    @Override
                    public void onCancel(DialogPlus dialogPlus) {

                    }

                    @Override
                    public void onAccept(DialogPlus dialogPlus) {
                        TelephonyManager mTelephonyManager = (TelephonyManager) getSystemService(Service.TELEPHONY_SERVICE);
                        if (mTelephonyManager.getSimState() != TelephonyManager.SIM_STATE_READY) {//SIM卡没有就绪
                            ToastUtils.showToast("没有SIM卡");
                        } else {
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "0755-33941515"));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            if (ActivityCompat.checkSelfPermission(AdvertDetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }
                            startActivity(intent);
                        }
                    }
                });
                break;
            case R.id.tv_order:
                DialogHelper.showDiaLog(this, new DialogHelper.OnConfirmDiaLogListener2() {
                    @Override
                    public void onCancel(DialogPlus dialogPlus) {

                    }

                    @Override
                    public void onAccept(DialogPlus dialogPlus, String phone, String name) {
                        if (TextUtils.isEmpty(phone)) {
                            ToastUtils.showToast("电话号码不能为空");
                            return;
                        }
                        if (TextUtils.isEmpty(name)) {
                            ToastUtils.showToast("姓名不能为空");
                            return;
                        }
                        boolean noContainsEmoji = CommonUtil.noContainsEmoji(name);
                        if (noContainsEmoji) {
                            ToastUtils.showToast("不能含有表情");
                            return;
                        }
                        dialogPlus1 = dialogPlus;
                        showProgressDlg();
                        serverEngine.saveAppointmentInfo(name, phone);
                    }
                });
                break;
        }
    }

    class ServerSub extends ServerCallback.Stub {
        @Override
        public void onGetStoreDetailSuccess(StoreDetailResp rsp) {
            super.onGetStoreDetailSuccess(rsp);
            dissProgressDlg();
            if (rsp.getObject() != null) {
                initData(rsp.getObject());
                if (rsp.getObject().getProductList() != null) {
                    list.addAll(rsp.getObject().getProductList());
                }
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onGetStoreDetailFail(int errCode, String msg) {
            super.onGetStoreDetailFail(errCode, msg);
            dissProgressDlg();
        }

        @Override
        public void onGetSaveMessageSuccess(BaseResp rsp) {
            super.onGetSaveMessageSuccess(rsp);
            dissProgressDlg();
            dialogPlus1.dismiss();
            ToastUtils.showToast("保存成功");
        }

        @Override
        public void onGetSaveMessageFail(int errCode, String msg) {
            super.onGetSaveMessageFail(errCode, msg);
            dissProgressDlg();
        }
    }
}
