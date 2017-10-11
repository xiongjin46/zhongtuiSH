package com.push.PushMerchant.network.okhttp;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;

import java.io.InputStream;

/**
 * Created by admin on 2017/2/12.
 */

public class SimpleGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //设置图片的显示格式ARGB_8888(指图片大小为32bit)
        builder.setDecodeFormat(DecodeFormat.PREFER_RGB_565);
        //设置磁盘缓存目录（和创建的缓存目录相同）
//        File storageDirectory = Environment.getExternalStorageDirectory();
//        String downloadDirectoryPath = storageDirectory + "/GlideCache";
        //设置缓存的大小为100M
        int cacheSize = 100 * 1000 * 1000;
//        builder.setDiskCache(new DiskLruCacheFactory(downloadDirectoryPath, cacheSize));
        builder.setBitmapPool(new LruBitmapPool(cacheSize));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        glide.register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
    }
}
