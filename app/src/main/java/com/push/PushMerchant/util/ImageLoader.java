package com.push.PushMerchant.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.target.ViewTarget;
import com.push.PushMerchant.R;
import com.push.PushMerchant.constants.GConstants;

import net.qiujuer.genius.graphics.Blur;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * @ClassName: ImageLoader
 * @Description: 图片加载工具类
 * @author: YYL
 * <p>
 * create at 2016/11/16 11:15
 */
public class ImageLoader {
    private static final int defaultDrawable = R.mipmap.error_icon;

    /**
     * 加载图片
     *
     * @param url
     * @param imageView
     */
    public static void load(String url, ImageView imageView, Context context) {
        load(url, imageView, defaultDrawable, context);
    }

    /**
     * 加载图片
     *
     * @param result
     * @param imageView
     */
    public static void load(int result, ImageView imageView, Context context) {
        Glide.with(context).load(result).centerCrop().dontAnimate().thumbnail(0.1f).placeholder(defaultDrawable).error(defaultDrawable).into(imageView);
    }

    /**
     * 加载图片
     *
     * @param url
     * @param imageView
     * @param placeResId
     */
    public static void load(String url, ImageView imageView, int placeResId, Context context) {
        if (url == null || url.isEmpty()) {
            return;
        }
        DrawableRequestBuilder<String> thumbnail = Glide.with(context).load(getStartImageResult(url)).centerCrop();
        if (placeResId == 0) {
            thumbnail.into(imageView);
        } else {
            thumbnail.placeholder(placeResId).error(placeResId).into(imageView);
        }

    }

    /**
     * 根据图片宽高加载图片
     *
     * @param url
     * @param imageview
     * @param widht
     * @param height
     */
    public static void dongdefaultImage(String url, ImageView imageview, int defaultImg, int widht, int height) {
        Glide.with(imageview.getContext()).load(getStartImageResult(url)).error(defaultImg).override(widht, height).centerCrop().into(imageview);
    }


    public static void loadImage(final String url, final ImageView imageView) {
        Glide.with(imageView.getContext()).load(getStartImageResult(url)).asBitmap().into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                int imageWidth = resource.getWidth();
                int imageHeight = resource.getHeight();
                int height = (DeviceUtils.getScreenWidth(imageView.getContext()) / 2) * imageHeight / imageWidth;
                ViewGroup.LayoutParams para = imageView.getLayoutParams();
                para.height = height;
                imageView.setLayoutParams(para);
                Glide.with(imageView.getContext()).load(getStartImageResult(url)).asBitmap().into(imageView);
            }
        });
    }

    /**
     * 加载圆形图片
     *
     * @param url
     * @param imageView
     * @error 加载圆形图片或者其他自定义图片，需要作一些转化，详细请参考：
     * <a herf="http://www.jianshu.com/p/4a3177b57949">
     */

    public static void loadSmallRoundImage(String url, final ImageView imageView, final Context context) {
        loadSmallRoundImage(url, imageView, context, false);
//        Glide.with(context).load(getBigImageRealUrl(url)).asBitmap().centerCrop().dontAnimate().thumbnail(0.1f).placeholder(defaultDrawable)
//                .error(defaultDrawable).into(imageView);
    }

    public static void loadSmallRoundImage(String url, final ImageView imageView, final Context context, boolean isShowPlaceholder) {
        loadSmallRoundImage(url, imageView, context, isShowPlaceholder, defaultDrawable);
//        Glide.with(context).load(getBigImageRealUrl(url)).asBitmap().centerCrop().dontAnimate().thumbnail(0.1f)
//                .error(defaultDrawable).into(imageView);
    }

    public static void loadSmallRoundImage(String url, final ImageView imageView, final Context context, boolean isShowPlaceholder, int errorid) {
        BitmapRequestBuilder<String, Bitmap> stringBitmapBitmapRequestBuilder = Glide.with(context).load(getStartImageResult(url)).asBitmap().centerCrop();
        if (isShowPlaceholder)
            stringBitmapBitmapRequestBuilder.placeholder(errorid);

        stringBitmapBitmapRequestBuilder.thumbnail(0.1f)
                .error(errorid).into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                try {
                    RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory
                            .create(context.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    imageView.setImageDrawable(circularBitmapDrawable);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public static void loadBlurImage(String url, final ImageView imageView, Context context) {
        Glide.with(context).load(ImageLoader.getStartImageResult(url)).asBitmap().error(R.mipmap.error_icon)
                .into(new ViewTarget<ImageView, Bitmap>(imageView) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        Bitmap bt = Blur.onStackBlurClip(resource, 50);
                        imageView.setImageBitmap(bt);
                    }
                });

    }

    /**
     * 根据图片宽高加载图片
     *
     * @param view
     * @param widht
     * @param height
     */
    public static void dongdefaultImage(int result, final View view, int widht, int height) {

        Glide.with(view.getContext()).load(result).asBitmap().override(widht, height).centerCrop().into(new ViewTarget<View, Bitmap>(view) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Bitmap bt = Blur.onStackBlurClip(resource, 50);
                view.setBackground(new BitmapDrawable(bt));
            }
        });
    }

    public static void loadBlurImage(String url, final View view, Context context) {
        Glide.with(context).load(ImageLoader.getStartImageResult(url)).asBitmap().error(R.mipmap.error_icon)
                .into(new ViewTarget<View, Bitmap>(view) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        Bitmap bt = Blur.onStackBlurClip(resource, 0);
                        view.setBackground(new BitmapDrawable(bt));
                    }
                });

    }

    /**
     * 取消加载动画
     *
     * @param url
     * @param imageview
     */
    public static void dontanimationload(String url, ImageView imageview, Context context) {
        Glide.with(context).load(url).dontAnimate().dontTransform().placeholder(defaultDrawable).thumbnail(0.1f).error(defaultDrawable).into(imageview);
    }

    /**
     * 加载高斯模糊图片
     *
     * @param url
     * @param imageview
     */
    public static void donTransload(String url, ImageView imageview, Context context) {
        Glide.with(context).load(url).dontAnimate().bitmapTransform(new BlurTransformation(context, 14, 3)).placeholder(defaultDrawable).thumbnail(0.1f).error(defaultDrawable).into(imageview);
    }

    /**
     * @desc 获取图片本地缓存大小
     * @date 2016/9/22 10:15
     * @author zzh
     */
//    public static long getCacheSize(Context c) {
//        File photoCacheDir = Glide.getPhotoCacheDir(c);
//        long photoCacheDirSize = FileUtil.getDirSize(photoCacheDir);
//        return photoCacheDirSize;
//    }

    /**
     * 非UI线程
     *
     * @param context
     */
    public static void clearDiskCache(Context context) {
        Glide.get(context).clearDiskCache();
    }


    public static String getStartImageResult(String url) {
        if (TextUtils.isEmpty(url)) {
            url = "";
        } else {
            if (url.startsWith("http://") || url.startsWith("https://") || url.startsWith("file://")) {

            } else {
                url = GConstants.IMAGE_HOST + url;
            }
        }
        return url;
    }
}
