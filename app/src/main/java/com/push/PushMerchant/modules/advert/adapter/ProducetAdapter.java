package com.push.PushMerchant.modules.advert.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.push.PushMerchant.R;
import com.push.PushMerchant.modules.advert.bean.StoreDetailResp;
import com.push.PushMerchant.modules.person.bean.ProjectDetailResp;
import com.push.PushMerchant.util.ImageLoader;
import com.push.PushMerchant.view.impl.IOnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/20 0020.
 */

public class ProducetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContexy;
    private List<StoreDetailResp.ObjectBean.ProductListBean> list;
    private IOnItemClickListener iOnItemClickListener;
    private int defItem = -1;

    public void setiOnItemClickListener(IOnItemClickListener iOnItemClickListener) {
        this.iOnItemClickListener = iOnItemClickListener;
    }

    public void setDefSelect(int position) {
        this.defItem = position;
        notifyDataSetChanged();
    }

    public ProducetAdapter(Activity mActivity, List<StoreDetailResp.ObjectBean.ProductListBean> list) {
        this.mContexy = mActivity;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContentViewHolder(LayoutInflater.from(mContexy).inflate(R.layout.producet, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ContentViewHolder hol = (ContentViewHolder) holder;
        StoreDetailResp.ObjectBean.ProductListBean bean = list.get(position);
        if (list.size() > 1) {
            if (position==0){
                hol.tv_type.setTextColor(Color.parseColor("#ffffff"));
                hol.tv_type.setBackgroundResource(R.drawable.btn_advet_choosetype_press);
                hol.tv_type.setEnabled(true);
            }else {
                hol.tv_type.setTextColor(Color.parseColor("#111111"));
                hol.tv_type.setBackgroundResource(R.drawable.btn_advet_choosetype_nor);
                hol.tv_type.setEnabled(true);
            }
        } else {
            hol.tv_type.setTextColor(Color.parseColor("#ffffff"));
            hol.tv_type.setBackgroundResource(R.drawable.btn_advet_choosetype_press);
            hol.tv_type.setEnabled(false);
        }
        if (defItem != -1) {
            if (defItem == position) {
                hol.tv_type.setTextColor(Color.parseColor("#ffffff"));
                hol.tv_type.setBackgroundResource(R.drawable.btn_advet_choosetype_press);
            } else {
                hol.tv_type.setTextColor(Color.parseColor("#111111"));
                hol.tv_type.setBackgroundResource(R.drawable.btn_advet_choosetype_nor);
            }
        }
        hol.tv_type.setText(bean.getName());
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
        @BindView(R.id.tv_type)
        TextView tv_type;

        ContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
