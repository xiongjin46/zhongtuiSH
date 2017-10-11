package com.push.PushMerchant.modules.advert.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.push.PushMerchant.R;
import com.push.PushMerchant.modules.advert.bean.StoreResp;
import com.push.PushMerchant.util.ImageLoader;
import com.push.PushMerchant.view.impl.IOnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/28 0028.
 */

public class AdvertAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<StoreResp.ObjectBean.ListBean> list;

    private IOnItemClickListener iOnItemClickListener;

    public void setiOnItemClickListener(IOnItemClickListener iOnItemClickListener) {
        this.iOnItemClickListener = iOnItemClickListener;
    }

    public AdvertAdapter(Context context, List<StoreResp.ObjectBean.ListBean> list) {
        this.mContext = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContentViewHolder(LayoutInflater.from(mContext).inflate(R.layout.advert, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ContentViewHolder hol = (ContentViewHolder) holder;
        StoreResp.ObjectBean.ListBean bean = list.get(position);
        hol.tv_store_address.setText(bean.getFullAddress());
        hol.tv_store_name.setText(bean.getName());
        if (!TextUtils.isEmpty(bean.getManFlow())) {
            hol.tv_price.setText("¥ " + bean.getPrice() / 100 + "/" + bean.getManFlow() + "人次");
        } else {
            hol.tv_price.setText("¥ " + bean.getPrice() / 100);
        }
        ImageLoader.dongdefaultImage(bean.getStoreUrl(), hol.iv_store, R.mipmap.error_icon, 160, 120);
        if (bean.getTimeoutType() == null) {
            hol.tv_sup_size.setVisibility(View.GONE);
        } else if (bean.getTimeoutType() == 0) {
            hol.tv_sup_size.setText("小");
            hol.tv_sup_size.setVisibility(View.VISIBLE);
        } else if (bean.getTimeoutType() == 1) {
            hol.tv_sup_size.setText("中小");
            hol.tv_sup_size.setVisibility(View.VISIBLE);
        } else if (bean.getTimeoutType() == 2) {
            hol.tv_sup_size.setText("中");
            hol.tv_sup_size.setVisibility(View.VISIBLE);
        } else if (bean.getTimeoutType() == 3) {
            hol.tv_sup_size.setText("中大");
            hol.tv_sup_size.setVisibility(View.VISIBLE);
        } else {
            hol.tv_sup_size.setText("大");
            hol.tv_sup_size.setVisibility(View.VISIBLE);
        }
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
        @BindView(R.id.iv_store)
        ImageView iv_store;
        @BindView(R.id.tv_store_address)
        TextView tv_store_address;
        @BindView(R.id.tv_store_name)
        TextView tv_store_name;
        @BindView(R.id.tv_price)
        TextView tv_price;
        @BindView(R.id.tv_sup_size)
        TextView tv_sup_size;

        ContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
