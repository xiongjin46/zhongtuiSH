package com.push.PushMerchant.modules.person.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.push.PushMerchant.R;
import com.push.PushMerchant.modules.person.bean.MerchantStoreResp;
import com.push.PushMerchant.util.ImageLoader;
import com.push.PushMerchant.view.impl.IOnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/30 0030.
 */

public class MaterielnfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private IOnItemClickListener iOnItemClickListener;
    private List<MerchantStoreResp.ObjectBean.StoreMaterialListBean> list;

    public MaterielnfoAdapter(Context mContext, List<MerchantStoreResp.ObjectBean.StoreMaterialListBean> list) {
        this.mContext = mContext;
        this.list=list;
    }

    public void setiOnItemClickListener(IOnItemClickListener iOnItemClickListener) {
        this.iOnItemClickListener = iOnItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContentViewHolder(LayoutInflater.from(mContext).inflate(R.layout.materiel_info, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ContentViewHolder hol = (ContentViewHolder) holder;
        MerchantStoreResp.ObjectBean.StoreMaterialListBean bean = list.get(position);
        ImageLoader.loadSmallRoundImage(bean.getMaterialUrl(), hol.materiel_icon, mContext);
        hol.tv_materiel_name.setText(bean.getName());
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
        @BindView(R.id.materiel_icon)
        ImageView materiel_icon;
        @BindView(R.id.tv_materiel_name)
        TextView tv_materiel_name;

        ContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
