package com.push.PushMerchant.weight.photoview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.push.PushMerchant.R;
import com.push.PushMerchant.base.BaseFragment;
import com.push.PushMerchant.weight.selectImage.PhotoPagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @ClassName: PhotoBgFragment
 * @Description: 点击后放大的图片viewpager
 * @author: YYL
 * <p>
 * create at 2016/11/29 17:15
 */
public class PhotoBgFragment extends BaseFragment {

    private static String SELECTIMGINFO = "select_img";
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.pager_tv)
    TextView pagerTv;
    PhotoBaseResp resp;
    private ArrayList<Fragment> fragments;

    private static PhotoBgFragment newInstance(PhotoBaseResp resp) {
        PhotoBgFragment f = new PhotoBgFragment();
        Bundle args = new Bundle();
        args.putSerializable(SELECTIMGINFO, resp);
        f.setArguments(args);
        return f;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_photo_bg;
    }

    @Override
    protected void initBeforeView() {
        resp = (PhotoBaseResp) getArguments().getSerializable(SELECTIMGINFO);
        fragments = new ArrayList<>();
    }

    @Override
    protected void initAfterView() {

        PhotoPagerAdapter adapter = new PhotoPagerAdapter(mActivity, resp.getImgurl());
        viewpager.setAdapter(adapter);
    }

}
