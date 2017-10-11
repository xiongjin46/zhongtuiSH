package com.push.PushMerchant.modules.person.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.push.PushMerchant.R;
import com.push.PushMerchant.modules.person.bean.ProjectDetailResp;
import com.push.PushMerchant.util.ImageLoader;
import com.push.PushMerchant.view.impl.IOnItemClickListener;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/20 0020.
 */

public class CurrentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContexy;
    private IOnItemClickListener iOnItemClickListener;
    private List<ProjectDetailResp.ObjectBean.StoreListBean> list;

    public CurrentAdapter(Activity mActivity, List<ProjectDetailResp.ObjectBean.StoreListBean> list) {
        this.mContexy = mActivity;
        this.list = list;
    }

    public void setiOnItemClickListener(IOnItemClickListener iOnItemClickListener) {
        this.iOnItemClickListener = iOnItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContentViewHolder(LayoutInflater.from(mContexy).inflate(R.layout.current, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ContentViewHolder hol = (ContentViewHolder) holder;
        ProjectDetailResp.ObjectBean.StoreListBean bean = list.get(position);
        ImageLoader.dongdefaultImage(bean.getStoreUrl(), hol.iv_pic, R.mipmap.error_icon, 120, 120);
        hol.tv_store_name.setText(bean.getName());
        hol.tv_store_address.setText(bean.getFullAddress());
        hol.tv_price.setText("¥ " + bean.getPrice() / 100 + "/" + bean.getManFlow() + "人");
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
        @BindView(R.id.iv_pic)
        ImageView iv_pic;
        @BindView(R.id.tv_store_name)
        TextView tv_store_name;
        @BindView(R.id.tv_store_address)
        TextView tv_store_address;
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
