package com.push.PushMerchant.modules.person.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.push.PushMerchant.R;
import com.push.PushMerchant.base.BaseToolbarActivity;
import com.push.PushMerchant.constants.GConstants;
import com.push.PushMerchant.modules.person.adapter.MaterielnfoAdapter;
import com.push.PushMerchant.modules.person.bean.MerchantStoreResp;
import com.push.PushMerchant.network.engine.ServerEngine;
import com.push.PushMerchant.network.impl.ServerCallback;
import com.push.PushMerchant.util.ImageLoader;
import com.push.PushMerchant.util.JumpUtil;
import com.push.PushMerchant.util.TimeUtil;
import com.push.PushMerchant.view.FlowLayout;
import com.push.PushMerchant.view.impl.IOnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/7/25 0025.
 * 超市详情
 */

public class SuperDetailActivity extends BaseToolbarActivity {
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
    @BindView(R.id.tv_content)
    WebView tv_content;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    @BindView(R.id.tv_sup_size)
    TextView tv_sup_size;
    @BindView(R.id.tv_productype)
    TextView tv_productype;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_environment)
    TextView tv_environment;
    @BindView(R.id.tv_house_pepple)
    TextView tv_house_pepple;
    @BindView(R.id.tv_house_price)
    TextView tv_house_price;
    @BindView(R.id.flowlayout)
    FlowLayout flowlayout;
    @BindView(R.id.flowlayout_psychology)
    FlowLayout flowlayout_psychology;
    private ServerEngine serverEngine;
    private ServerSub serverSub;
    private List<MerchantStoreResp.ObjectBean.StoreMaterialListBean> list;
    private MaterielnfoAdapter adapter;
    private int storeId;
    private int taskId;

    @Override
    protected int loadViewLayout() {
        return R.layout.ac_super_detail;
    }

    @Override
    protected void initDataBeforeView() {
        list = new ArrayList<>();
        storeId = getIntent().getIntExtra("storeId", 0);
        taskId = getIntent().getIntExtra("taskId", 0);
    }

    @Override
    protected void initDataAfterView() {
        setTitleString("超市详情");
        setDefaultBack("");
        serverEngine = new ServerEngine();
        serverSub = new ServerSub();
        bindCallback(serverSub, serverEngine);
        showProgressDlg();
        serverEngine.getMerchantStoreById(storeId, taskId);
        adapter = new MaterielnfoAdapter(this, list);
        adapter.setiOnItemClickListener(new IOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                JumpUtil.toMaterielDetail(SuperDetailActivity.this, list.get(position).getName(),
                        list.get(position).getMaterialUrl(), list.get(position).getIntroduce());
            }
        });
        recycleview.setAdapter(adapter);
        recycleview.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initData(MerchantStoreResp.ObjectBean bean) {
        tv_store_name.setText(bean.getName());//名字
        tv_store_address.setText(bean.getFullAddress());//地址
        tv_task_push.setText(bean.getManFlow() + "人");//覆盖人群
        tv_price.setText("¥" + bean.getPrice() / 100);//价格
        tv_content.loadDataWithBaseURL(null, GConstants.HTML_KEY + bean.getContent() +
                "</body></html>", "text/html", "UTF-8", null);//产品详细说明
        ImageLoader.load(bean.getStoreUrl(), store_pic, this);//门店图片
        tv_time.setText(TimeUtil.getYYMMDD(bean.getStartDate()) + " 至 " +
                TimeUtil.getYYMMDD(bean.getEndDate()));//开始时间和结束时间
        tv_environment.setText(bean.getScope());//周围三公里
        tv_house_pepple.setText(bean.getAreaMan() + "万人");//片区人口
        tv_house_price.setText(bean.getHotelRatesRIm() + "元/平");//周边房价
        if (bean.getProductType() == 0) {//推广类型
            tv_productype.setText("传统推广");
        } else if (bean.getProductType() == 1) {
            tv_productype.setText("地推");
        } else {
            tv_productype.setText("新媒体");
        }
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

    class ServerSub extends ServerCallback.Stub {

        @Override
        public void onGetMerchantStoreSuccess(MerchantStoreResp rsp) {
            super.onGetMerchantStoreSuccess(rsp);
            dissProgressDlg();
            if (rsp.getObject() != null) {
                initData(rsp.getObject());
                if (rsp.getObject().getStoreMaterialList() != null) {
                    list.addAll(rsp.getObject().getStoreMaterialList());
                }
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onGetMerchantStoreFail(int errCode, String msg) {
            super.onGetMerchantStoreFail(errCode, msg);
            dissProgressDlg();
        }
    }
}
