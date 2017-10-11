package com.push.PushMerchant.modules.map.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.push.PushMerchant.R;
import com.push.PushMerchant.base.BaseToolbarActivity;
import com.push.PushMerchant.modules.map.adapter.CityAdapter;
import com.push.PushMerchant.modules.map.bean.CityResp;
import com.push.PushMerchant.network.ThreadManager;
import com.push.PushMerchant.network.engine.ServerEngine;
import com.push.PushMerchant.network.impl.ServerCallback;
import com.push.PushMerchant.view.impl.IOnItemClickListener;
import com.push.PushMerchant.weight.recycleview.HeaderAndFooterWrapper;
import com.push.PushMerchant.weight.recycleview.LoadmoreWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by Administrator on 2017/8/15 0015.
 * 城市列表
 */

public class CityActivty extends BaseToolbarActivity {
    @BindView(R.id.id_stickynavlayout_innerscrollview)
    RecyclerView recyclerView;
    @BindView(R.id.fragment_rotate_header_with_view_group_frame)
    PtrClassicFrameLayout ptrFrame;
    private HeaderAndFooterWrapper headerAndFooterWrapper;
    private LoadmoreWrapper loadmoreWrapper;
    private ServerSub serverSub;
    private ServerEngine serverEngine;
    private CityAdapter adapter;
    private List<CityResp.ObjectBean.ListBean> list;

    @Override
    protected int loadViewLayout() {
        return R.layout.ac_city;
    }

    @Override
    protected void initDataBeforeView() {
        list = new ArrayList<>();
    }

    @Override
    protected void initDataAfterView() {
        setDefaultBack("");
        setTitleString("请选择城市");
        serverEngine = new ServerEngine();
        serverSub = new ServerSub();
        bindCallback(serverSub, serverEngine);
        onRefresh(page);
        adapter = new CityAdapter(this,list);
        adapter.setiOnItemClickListener(new IOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent();
                intent.putExtra("cityName",list.get(position).getCityName());
                intent.putExtra("latItude",list.get(position).getLatItude());
                intent.putExtra("longItude",list.get(position).getLongItude());
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        headerAndFooterWrapper = new HeaderAndFooterWrapper(adapter);
        loadmoreWrapper = new LoadmoreWrapper(headerAndFooterWrapper);
        final View loadview = LayoutInflater.from(this).inflate(R.layout.view_loading, new LinearLayout(this), false);
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
        ptrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                int firstCompletelyVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
                //达到这个条件就说明滑到了顶部
                if (firstCompletelyVisibleItemPosition == 0) {
                    return true;
                }
                return false;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout fram) {
                frame = fram;
                ThreadManager.executeOnFileThread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                page = 1;
                                onRefresh(page);
                            }
                        });

                    }
                });
            }
        });
    }

    private void onRefresh(int page) {
        if (!isload) {
            isload = true;
            showProgressDlg();
            serverEngine.getCityPage(page);
        }
    }

    class ServerSub extends ServerCallback.Stub {

        @Override
        public void onGetCityListSuccess(CityResp rsp) {
            super.onGetCityListSuccess(rsp);
            isload = false;
            dissProgressDlg();
            if (page == 1) {
                list.clear();
            }
            if (frame != null)
                frame.refreshComplete();
            if (rsp.getObject() != null) {
                if (rsp.getObject().getList() != null) {
                    list.addAll(rsp.getObject().getList());
                    hasNext = rsp.getObject().getCount() > page * serverEngine.rows;
                }
                loadmoreWrapper.notifyDataSetChanged();
            }
        }

        @Override
        public void onGetCityListFail(int errCode, String msg) {
            super.onGetCityListFail(errCode, msg);
            isload = false;
            dissProgressDlg();
            if (frame != null)
                frame.refreshComplete();
        }
    }
}
