package com.push.PushMerchant.modules.map.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.push.PushMerchant.R;
import com.push.PushMerchant.modules.map.bean.CityResp;
import com.push.PushMerchant.modules.person.bean.CheckListResp;
import com.push.PushMerchant.util.TimeUtil;
import com.push.PushMerchant.view.impl.IOnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/20 0020.
 */

public class CityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContexy;
    private IOnItemClickListener iOnItemClickListener;
    private List<CityResp.ObjectBean.ListBean> list;

    public CityAdapter(Activity mActivity, List<CityResp.ObjectBean.ListBean> list) {
        this.mContexy = mActivity;
        this.list = list;
    }

    public void setiOnItemClickListener(IOnItemClickListener iOnItemClickListener) {
        this.iOnItemClickListener = iOnItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContentViewHolder(LayoutInflater.from(mContexy).inflate(R.layout.city, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ContentViewHolder hol = (ContentViewHolder) holder;
        CityResp.ObjectBean.ListBean bean = list.get(position);
        hol.tv_city.setText(bean.getCityName());
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
        @BindView(R.id.tv_city)
        TextView tv_city;

        ContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
