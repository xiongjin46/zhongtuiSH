package com.push.PushMerchant.weight.photoview;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.push.PushMerchant.R;
import com.push.PushMerchant.base.BaseToolbarActivity;
import com.push.PushMerchant.view.impl.IOnItemClickListener;


/**
 * Created by admin on 2016/11/30.
 */

public class ParentPageAdapter extends PagerAdapter {

    private Context mContext;
    private PhotoBaseResp bases;
    private LayoutInflater mLayoutInflater;
    private int nowPosition;
    private PhotoView view;
    private IOnItemClickListener iOnItemClickListener;

    public void setiOnItemClickListener(IOnItemClickListener iOnItemClickListener) {
        this.iOnItemClickListener = iOnItemClickListener;
    }

    public ParentPageAdapter(Context mContext, PhotoBaseResp bases) {
        this.mContext = mContext;
        this.bases = bases;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.picker_item_pager,
                container, false);

        final PhotoView parentimg = (PhotoView) itemView.findViewById(R.id.parent_img);

        parentimg.setUrl(bases.getImgurl().get(position));
        parentimg.enable();
        parentimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iOnItemClickListener != null) {
                    iOnItemClickListener.onItemClick(v, position);
                }
            }
        });
        container.addView(itemView);
        return itemView;
    }

    @Override
    public int getCount() {
        return bases.getImgurl().size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    /**
     * 当前选中项
     */
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        nowPosition = position;
        view = (PhotoView) ((View) object).findViewById(R.id.parent_img);
        ((BaseToolbarActivity) mContext).parent_page.setText(position + 1 + "/" + bases.getImgurl().size());
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    /**
     * 获取当前选中图片的路径
     *
     * @return
     */
    public String getUri() {
        return bases.getImgurl().get(nowPosition);
    }

    /**
     * 获取当前选中图片的信息
     *
     * @return
     */
    public Info getInfo() {
        return bases.getInfos().get(nowPosition);
    }

    /**
     * 当前view
     *
     * @return
     */
    public PhotoView getView() {
        return view;
    }


}
