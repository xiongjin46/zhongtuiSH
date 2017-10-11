package com.push.PushMerchant.modules.person.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.push.PushMerchant.R;
import com.push.PushMerchant.modules.person.bean.CheckListResp;
import com.push.PushMerchant.util.TimeUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/20 0020.
 */

public class AuditDataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContexy;
    private List<CheckListResp.ObjectBean.ListBean> list;

    public AuditDataAdapter(Activity mActivity, List<CheckListResp.ObjectBean.ListBean> list) {
        this.mContexy = mActivity;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContentViewHolder(LayoutInflater.from(mContexy).inflate(R.layout.audit_data, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ContentViewHolder hol = (ContentViewHolder) holder;
        CheckListResp.ObjectBean.ListBean bean = list.get(position);
        int state = bean.getState();
        if (state == 0) {
            hol.tv_aduit_data.setText("审核不通过");
            hol.tv_aduit_data.setTextColor(Color.parseColor("#f93911"));
        } else {
            hol.tv_aduit_data.setText("审核通过");
            hol.tv_aduit_data.setTextColor(Color.parseColor("#00e676"));
        }
        hol.tv_time.setText(TimeUtil.getYYMMDD(bean.getInputDate()));
        hol.tv_reason.setText("原因：" + bean.getReason());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ContentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_aduit_data)
        TextView tv_aduit_data;
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.tv_reason)
        TextView tv_reason;

        ContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
