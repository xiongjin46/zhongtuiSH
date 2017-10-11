package com.push.PushMerchant.modules.person.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.push.PushMerchant.R;
import com.push.PushMerchant.base.BaseToolbarActivity;
import com.push.PushMerchant.modules.person.adapter.AuditDataAdapter;
import com.push.PushMerchant.modules.person.bean.CheckListResp;
import com.push.PushMerchant.network.ThreadManager;
import com.push.PushMerchant.network.engine.UserEngine;
import com.push.PushMerchant.network.impl.UserCallback;
import com.push.PushMerchant.util.JumpUtil;
import com.push.PushMerchant.weight.recycleview.HeaderAndFooterWrapper;
import com.push.PushMerchant.weight.recycleview.LoadmoreWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by Administrator on 2017/7/24 0024.\
 * 审核资料
 */

public class AuditDataActivity extends BaseToolbarActivity implements View.OnClickListener {
    @BindView(R.id.id_stickynavlayout_innerscrollview)
    RecyclerView recyclerView;
    @BindView(R.id.fragment_rotate_header_with_view_group_frame)
    PtrClassicFrameLayout ptrFrame;
    private AuditDataAdapter adapter;
    private UserEngine userEngine;
    private UserSub userSub;
    private HeaderAndFooterWrapper headerAndFooterWrapper;
    private LoadmoreWrapper loadmoreWrapper;
    private List<CheckListResp.ObjectBean.ListBean> list;

    @Override
    protected int loadViewLayout() {
        return R.layout.ac_audit_data;
    }

    @Override
    protected void initDataBeforeView() {
        list = new ArrayList<>();
    }

    @Override
    protected void initDataAfterView() {
        setTitleString("资料审核");
        setDefaultBack("");
        userEngine = new UserEngine();
        userSub = new UserSub();
        bindCallback(userSub, userEngine);
        onRefresh(page);

        adapter = new AuditDataAdapter(this, list);
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
            userEngine.getMerchantCheckPage(page);
        }
    }

    @OnClick({R.id.rl_audit_data})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_audit_data:
                JumpUtil.toAuditData1(this);
                break;
        }
    }

    class UserSub extends UserCallback.Stub {
        @Override
        public void onGetCheckListSuccess(CheckListResp rsp) {
            super.onGetCheckListSuccess(rsp);
            dissProgressDlg();
            isload = false;
            if (frame != null)
                frame.refreshComplete();
            if (page == 1) {
                list.clear();
            }
            if (rsp.getObject() != null) {
                if (rsp.getObject().getList() != null) {
                    list.addAll(rsp.getObject().getList());
                    hasNext = rsp.getObject().getCount() > page * userEngine.rows;
                }
                loadmoreWrapper.notifyDataSetChanged();
            }
        }

        @Override
        public void onGetCheckListFail(int errCode, String msg) {
            super.onGetCheckListFail(errCode, msg);
            dissProgressDlg();
            isload = false;
            if (frame != null)
                frame.refreshComplete();
        }
    }
}
