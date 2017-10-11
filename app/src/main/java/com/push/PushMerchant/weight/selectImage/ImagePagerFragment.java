package com.push.PushMerchant.weight.selectImage;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.push.PushMerchant.R;
import com.push.PushMerchant.base.BaseFragment;
import com.push.PushMerchant.util.ToastUtils;
import com.push.PushMerchant.util.VersionUtils;
import com.push.PushMerchant.weight.viewpage.HackyViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;


/**
 * 图片预览
 * Created by donglua on 15/6/21.
 */
public class ImagePagerFragment extends BaseFragment implements OnClickListener, ViewPager.OnPageChangeListener {

    public final static String ARG_PATH = "PATHS";
    public final static String ARG_CURRENT_ITEM = "ARG_CURRENT_ITEM";
    @BindView(R.id.vp_photos)
    HackyViewPager mViewPager;
    @BindView(R.id.btn_reduction)
    CheckBox btn_reduction;
    @BindView(R.id.tv_reduction)
    TextView tv_reduction;
    @BindView(R.id.tv_preview)
    TextView tv_preview;
    @BindView(R.id.btn_check)
    CheckBox btn_check;
    @BindView(R.id.bottom)
    RelativeLayout bottom;

    private ArrayList<String> paths;
    private PhotoPagerAdapter mPagerAdapter;

    public final static long ANIM_DURATION = 200L;

    public final static String ARG_THUMBNAIL_TOP = "THUMBNAIL_TOP";
    public final static String ARG_THUMBNAIL_LEFT = "THUMBNAIL_LEFT";
    public final static String ARG_THUMBNAIL_WIDTH = "THUMBNAIL_WIDTH";
    public final static String ARG_THUMBNAIL_HEIGHT = "THUMBNAIL_HEIGHT";
    public final static String ARG_HAS_ANIM = "HAS_ANIM";

    private int thumbnailTop = 0;
    private int thumbnailLeft = 0;
    private int thumbnailWidth = 0;
    private int thumbnailHeight = 0;

    private boolean hasAnim = false;

    private final ColorMatrix colorizerMatrix = new ColorMatrix();

    private int currentItem = 0;

    public static ImagePagerFragment newInstance(List<String> paths, int currentItem) {
        ImagePagerFragment f = new ImagePagerFragment();
        Bundle args = new Bundle();
        args.putStringArray(ARG_PATH, paths.toArray(new String[paths.size()]));
        args.putInt(ARG_CURRENT_ITEM, currentItem);
        args.putBoolean(ARG_HAS_ANIM, false);
        f.setArguments(args);
        return f;
    }

    public static ImagePagerFragment newInstance(List<String> paths, int currentItem, int[] screenLocation, int thumbnailWidth, int thumbnailHeight) {
        ImagePagerFragment f = newInstance(paths, currentItem);
        f.getArguments().putInt(ARG_THUMBNAIL_LEFT, screenLocation[0]);
        f.getArguments().putInt(ARG_THUMBNAIL_TOP, screenLocation[1]);
        f.getArguments().putInt(ARG_THUMBNAIL_WIDTH, thumbnailWidth);
        f.getArguments().putInt(ARG_THUMBNAIL_HEIGHT, thumbnailHeight);
        f.getArguments().putBoolean(ARG_HAS_ANIM, true);
        return f;
    }

    public void setPhotos(List<String> paths, int currentItem) {
        this.paths.clear();
        this.paths.addAll(paths);
        this.currentItem = currentItem;
        mViewPager.setCurrentItem(currentItem);
        mViewPager.getAdapter().notifyDataSetChanged();
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_image_pager;
    }

    @Override
    protected void initBeforeView() {
        paths = new ArrayList<>();

        Bundle bundle = getArguments();

        if (bundle != null) {
            String[] pathArr = bundle.getStringArray(ARG_PATH);
            paths.clear();
            if (pathArr != null) {

                paths = new ArrayList<>(Arrays.asList(pathArr));
            }

            hasAnim = bundle.getBoolean(ARG_HAS_ANIM);
            currentItem = bundle.getInt(ARG_CURRENT_ITEM);
            thumbnailTop = bundle.getInt(ARG_THUMBNAIL_TOP);
            thumbnailLeft = bundle.getInt(ARG_THUMBNAIL_LEFT);
            thumbnailWidth = bundle.getInt(ARG_THUMBNAIL_WIDTH);
            thumbnailHeight = bundle.getInt(ARG_THUMBNAIL_HEIGHT);
        }

        mPagerAdapter = new PhotoPagerAdapter(getActivity(), paths);
    }


    protected void setListener() {
        btn_reduction.setOnClickListener(this);
        tv_reduction.setOnClickListener(this);
        tv_preview.setOnClickListener(this);
        btn_check.setOnClickListener(this);
        mViewPager.addOnPageChangeListener(this);
    }

    @Override
    protected void initAfterView() {
        setListener();

        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(currentItem);
        mViewPager.setOffscreenPageLimit(5);
        if (!VersionUtils.isLOLLIPOP()) {
            btn_reduction.setButtonDrawable(R.drawable.btn_checkbox_selector);
            btn_check.setButtonDrawable(R.drawable.btn_checkbox_selector);
        }
        ViewTreeObserver observer = mViewPager.getViewTreeObserver();
        observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {

                mViewPager.getViewTreeObserver().removeOnPreDrawListener(
                        this);

                // Figure out where the thumbnail and full size versions
                // are, relative
                // to the screen and each other
                int[] screenLocation = new int[2];
                mViewPager.getLocationOnScreen(screenLocation);
                thumbnailLeft = thumbnailLeft - screenLocation[0];
                thumbnailTop = thumbnailTop - screenLocation[1];

                runEnterAnimation();

                return true;
            }
        });
    }


    /**
     * The enter animation scales the picture in from its previous thumbnail
     * size/location, colorizing it in parallel. In parallel, the background of
     * the activity is fading in. When the pictue is in place, the text
     * description drops down.
     */
    private void runEnterAnimation() {
        final long duration = ANIM_DURATION;

        // Set starting values for properties we're going to animate. These
        // values scale and position the full size version down to the thumbnail
        // size/location, from which we'll animate it back up
        ViewHelper.setPivotX(mViewPager, 0);
        ViewHelper.setPivotY(mViewPager, 0);
        ViewHelper.setScaleX(mViewPager,
                (float) thumbnailWidth / mViewPager.getWidth());// 236/720
        ViewHelper.setScaleY(mViewPager,
                (float) thumbnailHeight / mViewPager.getHeight());// 240/1134
        ViewHelper.setTranslationX(mViewPager, thumbnailLeft); // 482
        ViewHelper.setTranslationY(mViewPager, thumbnailTop); // 978

        // Animate scale and translation to go from thumbnail to full size
        ViewPropertyAnimator.animate(mViewPager).setDuration(duration)
                .scaleX(1).scaleY(1).translationX(0).translationY(0)
                .setInterpolator(new DecelerateInterpolator());

        // Fade in the black background
        ObjectAnimator bgAnim = ObjectAnimator.ofInt(
                mViewPager.getBackground(), "alpha", 0, 255);
        bgAnim.setDuration(duration);
        bgAnim.start();

        // Animate a color filter to take the image from grayscale to full
        // color.
        // This happens in parallel with the image scaling and moving into
        // place.
        ObjectAnimator colorizer = ObjectAnimator.ofFloat(
                ImagePagerFragment.this, "saturation", 0, 1);
        colorizer.setDuration(duration);
        colorizer.start();

    }

    /**
     * The exit animation is basically a reverse of the enter animation, except
     * that if the orientation has changed we simply scale the picture back into
     * the center of the screen.
     *
     * @param endAction This action gets run after the animation completes (this is
     *                  when we actually switch activities)
     */
    public void runExitAnimation(final Runnable endAction) {

        if (!getArguments().getBoolean(ARG_HAS_ANIM, false) || !hasAnim) {
            endAction.run();
            return;
        }

        final long duration = ANIM_DURATION;

        // Animate image back to thumbnail size/location
        ViewPropertyAnimator.animate(mViewPager).setDuration(duration)
                .setInterpolator(new AccelerateInterpolator())
                .scaleX((float) thumbnailWidth / mViewPager.getWidth())
                .scaleY((float) thumbnailHeight / mViewPager.getHeight())
                .translationX(thumbnailLeft).translationY(thumbnailTop)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        endAction.run();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                });

        // Fade out background
        ObjectAnimator bgAnim = ObjectAnimator.ofInt(
                mViewPager.getBackground(), "alpha", 0);
        bgAnim.setDuration(duration);
        bgAnim.start();

        // Animate a color filter to take the image back to grayscale,
        // in parallel with the image scaling and moving into place.
        ObjectAnimator colorizer = ObjectAnimator.ofFloat(
                ImagePagerFragment.this, "saturation", 1, 0);
        colorizer.setDuration(duration);
        colorizer.start();
    }

    /**
     * This is called by the colorizing animator. It sets a saturation factor
     * that is then passed onto a filter on the picture's drawable.
     *
     * @param value saturation
     */
    public void setSaturation(float value) {
        colorizerMatrix.setSaturation(value);
        ColorMatrixColorFilter colorizerFilter = new ColorMatrixColorFilter(
                colorizerMatrix);
        mViewPager.getBackground().setColorFilter(colorizerFilter);
    }

    public ViewPager getViewPager() {
        return mViewPager;
    }

    public ArrayList<String> getPaths() {
        return paths;
    }

    public int getCurrentItem() {
        return mViewPager.getCurrentItem();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_reduction:
            case R.id.btn_reduction:    //原图
                mPagerAdapter.setResetZoom();
                btn_reduction.setChecked(true);
                if (!btn_check.isChecked() && ((SelectImageActivity) getActivity())
                        .getSelectedPicture().size() + 1 > SelectImageActivity.MAX_NUM) {
                    return;

                }
                ArrayList<ImageItem> selectedPicture = ((SelectImageActivity) getActivity())
                        .getSelectedPicture();
                if (((SelectImageActivity) getActivity()).ImageItemToString(
                        selectedPicture).contains(mPagerAdapter.getUri())) {

                } else {
                    ((SelectImageActivity) getActivity())
                            .addSelectedPicture(mPagerAdapter.getUri());
                    btn_check.setChecked(true);
                }

                break;
            case R.id.tv_preview: // 选中
                btn_check.setChecked(!btn_check.isChecked());
            case R.id.btn_check:  //选中
                IsBtnCheck();
                break;
        }

    }

    private void IsBtnCheck() {
        if (btn_check.isChecked()) {
            if (((SelectImageActivity) getActivity())
                    .getSelectedPicture().size() + 1 > SelectImageActivity.MAX_NUM) {
                ToastUtils.showToast("最多选择" + SelectImageActivity.MAX_NUM + "张");
                btn_check.setChecked(false);
                return;
            }
        }
        ArrayList<ImageItem> selectedPicture = ((SelectImageActivity) getActivity())
                .getSelectedPicture();
        if (((SelectImageActivity) getActivity()).ImageItemToString(
                selectedPicture).contains(mPagerAdapter.getUri())) {
            ((SelectImageActivity) getActivity())
                    .removeSelectedPicture(mPagerAdapter.getUri());
            btn_check.setChecked(false);
        } else {
            ((SelectImageActivity) getActivity())
                    .addSelectedPicture(mPagerAdapter.getUri());
            btn_check.setChecked(true);
        }
    }

    public void setBtnBG(int pos) {
        if (isAdded()) {
            if (((SelectImageActivity) getActivity()).isSelectedPicture(paths.get(pos))) {
                btn_check.setChecked(true);
            } else {
                btn_check.setChecked(false);
            }
        }
    }

    public PagerAdapter getPagerAdapter() {
        return mPagerAdapter;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        hasAnim = currentItem == position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


//    @Override
//    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//        switch (compoundButton.getId()) {
//            case R.id.btn_reduction:    //原图
//                mPagerAdapter.setResetZoom();
//                btn_reduction.setChecked(true);
//
//                if (!btn_check.isChecked() && ((SelectImageActivity) getActivity())
//                        .getSelectedPicture().size() + 1 > SelectImageActivity.MAX_NUM) {
//                    return;
//
//                }
//                ArrayList<ImageItem> selectedPicture = ((SelectImageActivity) getActivity())
//                        .getSelectedPicture();
//                if (((SelectImageActivity) getActivity()).ImageItemToString(
//                        selectedPicture).contains(mPagerAdapter.getUri())) {
//
//                } else {
//                    ((SelectImageActivity) getActivity())
//                            .addSelectedPicture(mPagerAdapter.getUri());
//                    btn_check.setChecked(true);
//                }
//                break;
//            case R.id.btn_check:  //选中
//                IsBtnCheck();
//                break;
//        }
//    }
}
