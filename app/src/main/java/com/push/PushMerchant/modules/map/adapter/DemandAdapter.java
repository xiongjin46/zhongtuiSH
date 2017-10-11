package com.push.PushMerchant.modules.map.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.push.PushMerchant.R;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/20 0020.
 */

public class DemandAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContexy;

    public DemandAdapter(Activity mActivity) {
        this.mContexy = mActivity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContentViewHolder(LayoutInflater.from(mContexy).inflate(R.layout.demand, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 30;
    }

    private class ContentViewHolder extends RecyclerView.ViewHolder {
        ContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
