package com.push.PushMerchant.modules.person.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.push.PushMerchant.R;
import com.push.PushMerchant.base.BaseResp;
import com.push.PushMerchant.base.BaseToolbarActivity;
import com.push.PushMerchant.modules.person.adapter.CurrentAdapter;
import com.push.PushMerchant.modules.person.bean.ProjectDetailResp;
import com.push.PushMerchant.network.ThreadManager;
import com.push.PushMerchant.network.engine.UserEngine;
import com.push.PushMerchant.network.impl.UserCallback;
import com.push.PushMerchant.util.JumpUtil;
import com.push.PushMerchant.util.TimeUtil;
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
 * Created by Administrator on 2017/8/8 0008.
 * 项目详情
 */

public class ProjectDetailActivity extends BaseToolbarActivity {
    @BindView(R.id.id_stickynavlayout_innerscrollview)
    RecyclerView recyclerView;
    @BindView(R.id.fragment_rotate_header_with_view_group_frame)
    PtrClassicFrameLayout ptrFrame;
    private CurrentAdapter adapter;
    private HeaderAndFooterWrapper headerAndFooterWrapper;
    private int taskid;
    private UserEngine userEngine;
    private UserSub userSub;
    private List<ProjectDetailResp.ObjectBean.StoreListBean> list;
    private LoadmoreWrapper loadmoreWrapper;
    private View headview;
    private TextView tv_task_name;
    private TextView tv_task_push;
    private TextView tv_sup_num;
    private TextView tv_product_name;
    private TextView tv_time;
    private ProjectDetailResp.ObjectBean bean;
    private TextView tv_all_money;

    @Override
    protected int loadViewLayout() {
        return R.layout.ac_project_detail;
    }

    @Override
    protected void initDataBeforeView() {
        taskid = getIntent().getIntExtra("taskid", 0);
        list = new ArrayList<>();
    }

    @Override
    protected void initDataAfterView() {
        setTitleString("任务详情");
        setDefaultBack("");
        userEngine = new UserEngine();
        userSub = new UserSub();
        bindCallback(userSub, userEngine);
        onRefresh(page);

        adapter = new CurrentAdapter(this, list);
        adapter.setiOnItemClickListener(new IOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                JumpUtil.toSuperDetail(ProjectDetailActivity.this, list.get(position).getId(), bean.getId());
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        headerAndFooterWrapper = new HeaderAndFooterWrapper(adapter);
        loadmoreWrapper = new LoadmoreWrapper(headerAndFooterWrapper);
        headview = LayoutInflater.from(this).inflate(R.layout.current_head, new LinearLayout(this), false);
        tv_task_name = (TextView) headview.findViewById(R.id.tv_task_name);
        tv_task_push = (TextView) headview.findViewById(R.id.tv_task_push);
        tv_sup_num = (TextView) headview.findViewById(R.id.tv_sup_num);
        tv_product_name = (TextView) headview.findViewById(R.id.tv_product_name);
        tv_time = (TextView) headview.findViewById(R.id.tv_time);
        tv_all_money = (TextView) headview.findViewById(R.id.tv_all_money);

        headerAndFooterWrapper.addHeaderView(headview);
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
            userEngine.getTaskStoreByTaskId(taskid);
        }
    }

    private void initData(ProjectDetailResp.ObjectBean bean) {
        tv_all_money.setText("¥" + bean.getCostSum() / 100);
        tv_task_name.setText(bean.getTaskName());
        tv_task_push.setText(Double.valueOf(bean.getCoverCrowd()) / 10000 + "万");
        tv_sup_num.setText(bean.getStoreCount() + "家");
        //0 传统推广 1 地推 2  新媒体
        if (bean.getProductType() == 0) {
            tv_product_name.setText("传统推广");
        } else if (bean.getProductType() == 1) {
            tv_product_name.setText("地推");
        } else {
            tv_product_name.setText("新媒体");
        }
        tv_time.setText("投放时间：" + TimeUtil.getYYMMDD(bean.getStartDate()) + " 至 " + TimeUtil.getYYMMDD(bean.getEndDate()));
    }

    class UserSub extends UserCallback.Stub {
        @Override
        public void onGetStoreTaskSuccess(ProjectDetailResp rsp) {
            super.onGetStoreTaskSuccess(rsp);
            dissProgressDlg();
            isload = false;
            if (frame != null)
                frame.refreshComplete();
            if (page == 1) {
                list.clear();
            }
            if (rsp.getObject() != null) {
                bean = rsp.getObject();
                initData(bean);
                if (rsp.getObject().getStoreList() != null) {
                    list.addAll(rsp.getObject().getStoreList());
                }
                loadmoreWrapper.notifyDataSetChanged();
            }
        }

        @Override
        public void onGetStoreTaskFail(int errCode, String msg) {
            super.onGetStoreTaskFail(errCode, msg);
            dissProgressDlg();
            isload = false;
            if (frame != null)
                frame.refreshComplete();
        }
    }
}
