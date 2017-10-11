package com.push.PushMerchant.weight.viewpage;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.push.PushMerchant.modules.home.bean.SthufflePageBaseResp;
import com.push.PushMerchant.util.ImageLoader;


/**
 * @ClassName: EventTopHolderView
 * @Description: 活动详情轮播
 * @author: YYL
 * <p>
 * create at 2016/11/17 14:42
 */
public class EventTopHolderView implements Holder<SthufflePageBaseResp.ObjectBean> {

    private ImageView imageView;

    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, SthufflePageBaseResp.ObjectBean data) {
        ImageLoader.load(data.getImg_url(), imageView, context);
    }

}
