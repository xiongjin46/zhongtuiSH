package com.push.PushMerchant.modules.person.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.push.PushMerchant.R;
import com.push.PushMerchant.base.BaseFragment;
import com.push.PushMerchant.base.BaseToolbarActivity;
import com.push.PushMerchant.constants.GConstants;
import com.push.PushMerchant.modules.person.adapter.HistoryAdapter;
import com.push.PushMerchant.modules.person.bean.HistoryTaskResp;
import com.push.PushMerchant.network.ThreadManager;
import com.push.PushMerchant.network.engine.UserEngine;
import com.push.PushMerchant.network.impl.UserCallback;
import com.push.PushMerchant.util.JumpUtil;
import com.push.PushMerchant.util.SharePrefUtil;
import com.push.PushMerchant.view.impl.IOnItemClickListener;
import com.push.PushMerchant.weight.recycleview.HeaderAndFooterWrapper;
import com.push.PushMerchant.weight.recycleview.LoadmoreWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Administrator on 2017/7/21 0021.
 */

public class HistoryProjectFragment extends BaseFragment {
    @BindView(R.id.id_stickynavlayout_innerscrollview)
    RecyclerView recyclerView;
    @BindView(R.id.fragment_rotate_header_with_view_group_frame)
    PtrClassicFrameLayout ptrFrame;
    private int id;
    private HistoryAdapter adapter;
    private LoadmoreWrapper loadmoreWrapper;
    private HeaderAndFooterWrapper headerAndFooterWrapper;
    private UserEngine userEngine;
    private UserSub userSub;
    private List<HistoryTaskResp.ObjectBean.ListBean> list;

    public static HistoryProjectFragment newInstance(int type) {
        HistoryProjectFragment fragment = new HistoryProjectFragment();
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fg_history;
    }

    @Override
    protected void initBeforeView() {
        id = SharePrefUtil.getIntUser(GConstants.KEY_USERID, 0);
        list = new ArrayList<>();
    }

    @Override
    protected void initAfterView() {
        userEngine = new UserEngine();
        userSub = new UserSub();
        ((BaseToolbarActivity) mActivity).bindCallback(userSub, userEngine);
        onRefresh(page);
        adapter = new HistoryAdapter(mActivity, list);
        adapter.setiOnItemClickListener(new IOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                JumpUtil.toProjectDetail(mActivity, list.get(position).getId());
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
            userEngine.getHistoryTaskInfo(id, page);
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

    class UserSub extends UserCallback.Stub {

        @Override
        public void onGetHistoryTaskSuccess(HistoryTaskResp rsp) {
            super.onGetHistoryTaskSuccess(rsp);
            isload = false;
            if (frame != null)
                frame.refreshComplete();
            ((BaseToolbarActivity) mActivity).dissProgressDlg();
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
        public void onGetHistoryTaskFail(int errCode, String msg) {
            super.onGetHistoryTaskFail(errCode, msg);
            ((BaseToolbarActivity) mActivity).dissProgressDlg();
            isload = false;
            if (frame != null)
                frame.refreshComplete();
        }
    }
}
