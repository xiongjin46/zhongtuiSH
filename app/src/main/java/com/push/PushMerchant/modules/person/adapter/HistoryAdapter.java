package com.push.PushMerchant.modules.person.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.push.PushMerchant.R;
import com.push.PushMerchant.modules.person.bean.HistoryTaskResp;
import com.push.PushMerchant.util.TimeUtil;
import com.push.PushMerchant.view.impl.IOnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/20 0020.
 */

public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContexy;
    private List<HistoryTaskResp.ObjectBean.ListBean> list;
    private IOnItemClickListener iOnItemClickListener;

    public HistoryAdapter(Activity mActivity, List<HistoryTaskResp.ObjectBean.ListBean> list) {
        this.mContexy = mActivity;
        this.list = list;
    }

    public void setiOnItemClickListener(IOnItemClickListener iOnItemClickListener) {
        this.iOnItemClickListener = iOnItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContentViewHolder(LayoutInflater.from(mContexy).inflate(R.layout.history, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ContentViewHolder hol = (ContentViewHolder) holder;
        HistoryTaskResp.ObjectBean.ListBean bean = list.get(position);
        hol.tv_task_name.setText(bean.getTaskName());
        hol.tv_task_push.setText(Double.valueOf(bean.getCoverCrowd())/10000 + "万");
        hol.tv_sup_num.setText(bean.getStoreCount() + "家");
        hol.tv_all_money.setText("¥" + bean.getCostSum() / 100);
        if (bean.getProductType() == 0) {
            hol.tv_product_name.setText("传统推广");
        } else if (bean.getProductType() == 1) {
            hol.tv_product_name.setText("地推");
        } else {
            hol.tv_product_name.setText("新媒体");
        }
        hol.tv_time.setText("投放时间：" + TimeUtil.getYYMMDD(bean.getStartDate()) + " 至 " + TimeUtil.getYYMMDD(bean.getEndDate()));
        hol.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != iOnItemClickListener) {
                    iOnItemClickListener.onItemClick(view, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ContentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_task_name)
        TextView tv_task_name;
        @BindView(R.id.tv_task_push)
        TextView tv_task_push;
        @BindView(R.id.tv_sup_num)
        TextView tv_sup_num;
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.tv_product_name)
        TextView tv_product_name;
        @BindView(R.id.tv_all_money)
        TextView tv_all_money;

        ContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
