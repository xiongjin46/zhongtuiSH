package com.push.PushMerchant.weight.selectImage;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.push.PushMerchant.R;
import com.push.PushMerchant.util.ImageLoader;
import com.push.PushMerchant.weight.photoview.PhotoView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 预览图片适配
 *
 * @author Administrator
 */
public class PhotoPagerAdapter extends PagerAdapter {

    private List<String> paths = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private int nowPosition;
    Uri uri;
    private View view;

    public PhotoPagerAdapter(Context mContext, List<String> paths) {
        this.mContext = mContext;
        this.paths = paths;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View itemView = mLayoutInflater.inflate(R.layout.picker_item_pager,
                container, false);

        PhotoView imageView = (PhotoView) itemView.findViewById(R.id.parent_img);
        imageView.enable();
        String path = paths.get(position);

        if (path.startsWith("http://") || path.startsWith("https://")) {
            uri = Uri.parse(path);
        } else {
            uri = Uri.fromFile(new File(path));
        }
        ImageLoader.dontanimationload(uri.toString(), imageView, mContext);


        /**
         * 	 ImageLoader.getInstance(mContext).displayImage(uri.toString())
         .setThumbnail(0.1f).setDontAnimate().setDontTransform()
         .setOverride(800, 800)
         .setPlaceholder(R.drawable.ic_picture_loading)
         .setError(R.drawable.ic_picture_loading).into(imageView);
         */

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mContext instanceof SelectImageActivity) {
                    if (!((Activity) mContext).isFinishing()) {
                        ((Activity) mContext).onBackPressed();
                    }
                }
            }
        });

        container.addView(itemView);

        return itemView;
    }

    @Override
    public int getCount() {
        return paths.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    /**
     * 当前选中项
     */
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        nowPosition = position;
        view = (View) object;
        Fragment fragment = ((SelectImageActivity) mContext).getFragment();
        ((ImagePagerFragment) fragment).setBtnBG(nowPosition);
    }

    /**
     * 获取当前选中图片的路径
     *
     * @return
     */
    public String getUri() {
        return paths.get(nowPosition);
    }

    /**
     * 初始化选中图片
     */
    public void setResetZoom() {
        ((PhotoView) view.findViewById(R.id.parent_img)).enable();

    }
}
