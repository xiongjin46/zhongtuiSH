package com.push.PushMerchant.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * @ClassName: BitmapUtil
 * @Description: 图片操作
 * @author: YYL
 * <p>
 * create at 2016/11/22 14:13
 */
public class BitmapUtil {
    private static final String TAG = "BitmapUtil";

    public final static double SCALE_THRESHOLD = 2;
    public final static double SCALE_HORIZONTAL = 1.2;
    public static final int MAX_DECODE_PICTURE_SIZE = 1920 * 1440;
    private static final int ANDROID_API_LEVEL_11 = 11;

    public static final int TYPE_WH = 1;
    public static final int TYPE_HW = 2;
    public static final int LIMITE_SIZE = 50;

    private static final int BIGMEDIA_HIGH_QUALITY = 100;
    private static final int BIGMEDIA_LONG_LIMIT = 960;
    private static final int BIGMEDIA_SHORT_LIMIT = 640;
    private static final int BIGMEDIA_QUALITY = 50;
    private static final int MINUM_SIZE = 70 * 1024; // 100K;

    public static boolean hasNoInNativeField;


    public static boolean isLongVertical(int width, int height) {
        return height > width * SCALE_THRESHOLD;
    }

    public static boolean isLongHorizontal(int width, int height) {
        return width > height * SCALE_THRESHOLD;
    }

    public static BitmapFactory.Options getImageOptions(final String path) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        bindlowMemeryOption(options);
        try {
            Bitmap tmp = BitmapFactory.decodeFile(path, options);
            if (tmp != null) {
                tmp.recycle();
                tmp = null;
            }
        } catch (final OutOfMemoryError e) {
        }
        return options;
    }

    /**
     * @param options
     */
    public static void bindlowMemeryOption(BitmapFactory.Options options) {
        if (android.os.Build.VERSION.SDK_INT < 14 && !hasNoInNativeField) {
            try {
                // 4. inNativeAlloc 属性设置为true，可以不把使用的内存算到VM里 4.x
                // 以上rom该api被废弃
                BitmapFactory.Options.class.getField("inNativeAlloc").setBoolean(options, true);
            } catch (Exception e) {
                e.printStackTrace();
                hasNoInNativeField = true;
            }
        }
    }

    public static boolean createPic(final String dstPath, final String origPath) {
        BitmapFactory.Options option = getImageOptions(origPath);
        if (option.outWidth >= option.outHeight * 2 || option.outHeight >= option.outWidth * 2) {
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inPreferredConfig = Bitmap.Config.RGB_565;
            BitmapUtil.bindlowMemeryOption(opt);
            Bitmap bm = BitmapFactory.decodeFile(origPath, opt);
            try {
                if (bm == null) {
                    return false;
                }
                int degree = getExifRotation(origPath);
                bm = BitmapUtil.rotate(bm, degree);
                saveBitmapToImage(bm, BIGMEDIA_QUALITY, Bitmap.CompressFormat.JPEG, dstPath, true);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        if ((option.outWidth <= BIGMEDIA_LONG_LIMIT && option.outHeight <= BIGMEDIA_SHORT_LIMIT) || (option.outHeight <= BIGMEDIA_LONG_LIMIT && option.outWidth <= BIGMEDIA_SHORT_LIMIT)) {
            int fileLen = readFileLength(origPath);
            if (fileLen < MINUM_SIZE) {
                return createThumbNail(origPath, option.outHeight, option.outWidth, Bitmap.CompressFormat.PNG,
                        BIGMEDIA_QUALITY, dstPath, true);
            }
            return createThumbNail(origPath, option.outHeight, option.outWidth, Bitmap.CompressFormat.JPEG, BIGMEDIA_QUALITY, dstPath, true);
        }
        int with = option.outWidth >= option.outHeight ? BIGMEDIA_LONG_LIMIT : BIGMEDIA_SHORT_LIMIT;
        int height = option.outWidth >= option.outHeight ? BIGMEDIA_SHORT_LIMIT : BIGMEDIA_LONG_LIMIT;

        return createThumbNail(origPath, height, with, Bitmap.CompressFormat.JPEG, BIGMEDIA_QUALITY, dstPath, true);
    }

    public static boolean createThumbNail(final String origPath, final int height, final int width,
                                          final Bitmap.CompressFormat format, final int quality,
                                          final String thumbFullPath, boolean checkDegree) {
        Bitmap bm = extractThumbNail(origPath, height, width, false);
        if (bm == null) {
            return false;
        }

        if (checkDegree) {
            int degree = getExifRotation(origPath);
            bm = BitmapUtil.rotate(bm, degree);
        }
        try {
            saveBitmapToImage(bm, quality, format, thumbFullPath, true);
        } catch (final IOException e) {
            return false;
        }

        return true;
    }

    public static byte[] getBitmapByte(final String path, final int height, final int width, final boolean crop) {
        Bitmap bitmap = extractThumbNail(path, height, width, crop);
        if (bitmap == null) return null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();

    }

    /**
     * 按比例压缩图片
     *
     * @param path
     * @return
     */
    public static byte[] getBitmapByte(String path, int width, int height) {
        if (TextUtils.isEmpty(path)) return null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = null;
//        do {
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        float ratioX = options.outWidth * 1.0f / width;
        float ratioY = options.outHeight * 1.0f / height;
        //按比例压缩  取比例小值
        options.inSampleSize = ratioX > ratioY ? (int) ratioY : (int) ratioX;
        if (options.inSampleSize <= 1) options.inSampleSize = 1;
        options.inJustDecodeBounds = false;

        bitmap = BitmapFactory.decodeFile(path, options);
//        } while (bitmap.getRowBytes() * bitmap.getHeight() > 300 * 1024); //图片小于300K

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    public static Bitmap extractThumbNail(final String path, final int height, final int width, final boolean crop) {
        if (height <= 0 || width <= 0) {
            return null;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();

        try {
            options.inJustDecodeBounds = true;
            Bitmap tmp = BitmapFactory.decodeFile(path, options);
            if (tmp != null) {
                tmp.recycle();
                tmp = null;
            }
            if (options.outHeight <= 0 || options.outWidth <= 0) {
                return null;
            }

            final double beY = options.outHeight * 1.0 / height;
            final double beX = options.outWidth * 1.0 / width;
            options.inSampleSize = (int) (crop ? (beY > beX ? beX : beY) : (beY < beX ? beX : beY));
            if (options.inSampleSize <= 1) {
                options.inSampleSize = 1;
            }

            // NOTE: out of memory error
            while (options.outHeight * options.outWidth / options.inSampleSize / options.inSampleSize > MAX_DECODE_PICTURE_SIZE) {
                options.inSampleSize++;
            }

            int newHeight = height;
            int newWidth = width;
            if (crop) {
                if (beY > beX) {
                    newHeight = (int) Math.ceil(newWidth * 1.0 * options.outHeight / options.outWidth);
                } else {
                    newWidth = (int) Math.ceil(newHeight * 1.0 * options.outWidth / options.outHeight);
                }
            } else {
                if (beY < beX) {
                    newHeight = (int) Math.ceil(newWidth * 1.0 * options.outHeight / options.outWidth);
                } else {
                    newWidth = (int) Math.ceil(newHeight * 1.0 * options.outWidth / options.outHeight);
                }
            }

            newHeight = newHeight > 0 ? newHeight : 1;
            newWidth = newWidth > 0 ? newWidth : 1;

            options.inJustDecodeBounds = false;

            // if not set true, setPixels method may throw
            // IllegalStateException
            if (android.os.Build.VERSION.SDK_INT >= ANDROID_API_LEVEL_11) {
                options.inMutable = true;
            }

            Bitmap bm = BitmapFactory.decodeFile(path, options);
            bindlowMemeryOption(options);
            if (bm == null) {
                return null;
            }

            final Bitmap scale = Bitmap.createScaledBitmap(bm, newWidth, newHeight, true);
            if (bm != scale && scale != null) {
                bm.recycle();
                bm = scale;
            }

            if (crop) {
                int x = (bm.getWidth() - width) >> 1;
                int y = (bm.getHeight() - height) >> 1;
                if (x < 0 || y < 0) {
                    x = x < 0 ? 0 : x;
                    y = y < 0 ? 0 : y;
                }
                final Bitmap cropped = Bitmap.createBitmap(bm, x, y, width, height);
                if (cropped == null) {
                    return bm;
                }

                if (cropped != bm) {
                    bm.recycle();
                    bm = cropped;
                }

            }
            return bm;

        } catch (final OutOfMemoryError e) {
            options = null;
        }

        return null;
    }

    public static Bitmap rotate(Bitmap temBmp, float degree) {
        if (degree % 360 == 0) {
            return temBmp;
        }

        Matrix m = new Matrix();
        m.reset();
        m.setRotate(degree, temBmp.getWidth() / 2, temBmp.getHeight() / 2);
        Bitmap resultBmp = Bitmap.createBitmap(temBmp, 0, 0, temBmp.getWidth(), temBmp.getHeight(), m, true);
        if (temBmp != resultBmp) {
            temBmp.recycle();
        }
        return resultBmp;
    }

    public static void saveBitmapToImage(final Bitmap bitmap, final int quality, final Bitmap.CompressFormat format, final String pathName, final boolean recycle) throws IOException {
        final File file = new File(pathName);
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            FileOutputStream fOut = null;
            fOut = new FileOutputStream(file);
            bitmap.compress(format, quality, fOut);
            fOut.flush();
            fOut.close();
            if (recycle) {
                bitmap.recycle();
            }
        } catch (final FileNotFoundException e) {
        } catch (final IOException e) {
        } catch (final Exception e) {
        }
    }

    public static int readFileLength(String fileName) {
        if (fileName == null) {
            return 0;
        }

        File file = new File(fileName);
        if (!file.exists()) {
            return 0;
        }
        return (int) file.length();
    }

    public static int getExifRotation(String path) {
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(path);
        } catch (IOException e) {
        }
        if (exif == null) {
            return 0;
        }
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);
        int rotation = 0;
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                rotation = 90;
                break;

            case ExifInterface.ORIENTATION_ROTATE_180:
                rotation = 180;
                break;

            case ExifInterface.ORIENTATION_ROTATE_270:
                rotation = 270;
                break;
        }
        return rotation;
    }

    final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {

        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    public static Bitmap downloadBitmap(String url, int size) {
        Bitmap bitmap = null;
        InputStream is = null;
        HttpURLConnection con = null;

        try {
            trustAllHosts();
            URL myFileUrl = new URL(url);

            if (myFileUrl.getProtocol().toLowerCase().equals("https")) {
                HttpsURLConnection https = (HttpsURLConnection) myFileUrl
                        .openConnection();
                https.setHostnameVerifier(DO_NOT_VERIFY);
                con = (HttpsURLConnection) myFileUrl
                        .openConnection();
            } else {
                con = (HttpURLConnection) myFileUrl.openConnection();
            }
            con.setDoInput(true);
            con.connect();
            is = con.getInputStream();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(is, null, options);
            BitmapFactory.Options options1 = calculateInSampleSize(options, size, size);
            options1.inPreferredConfig = Bitmap.Config.RGB_565;  //修改图片的颜色模式
            bitmap = BitmapFactory.decodeStream(is, null, options1);
        } catch (MalformedURLException e) {

        } catch (IOException e) {
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        } finally {
            Closer.close(is);
        }
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap = bitmap.createScaledBitmap(bitmap, size, size, false);
        }

        return bitmap;
    }

    private static void trustAllHosts() {
        final String TAG = "trustAllHosts";
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {

            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[]{};
            }

            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }
        }};

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    public static Bitmap okDownLoadBitmap(String url, int size) {
//        OkHttpClient client = new OkHttpClient();
//        Bitmap bm = null;
//        try {
//            Request request = new Request.Builder().url(url).build();
//            Response response = client.newCall(request).execute();
//            InputStream is = response.body().byteStream();
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inJustDecodeBounds = true;
//            options.inPreferredConfig = Bitmap.Config.RGB_565;
//            options.inPurgeable = true;
//            options.inInputShareable = true;
//            BitmapFactory.decodeStream(is, null, options);
//
//            BitmapFactory.Options options1 = calculateInSampleSize(options, size, size);
//            options1.inPreferredConfig = Bitmap.Config.RGB_565;  //修改图片的颜色模式
//            bm = BitmapFactory.decodeStream(is, null, options1);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return bm;
//    }


    /**
     * 图片压缩处理（使用Options的方法）
     * 通过控制JustDecodeBounds的设置获取原图的大小
     * 并根据最后需要生成的图片大小进行压缩处理
     */
    public static BitmapFactory.Options calculateInSampleSize(
            final BitmapFactory.Options options, final int reqWidth,
            final int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > 400 || width > 450) {
            if (height > reqHeight || width > reqWidth) {
                // 计算出实际宽高和目标宽高的比率
                final int heightRatio = Math.round((float) height
                        / (float) reqHeight);
                final int widthRatio = Math.round((float) width
                        / (float) reqWidth);
                // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
                // 一定都会大于等于目标的宽和高。
                inSampleSize = heightRatio < widthRatio ? heightRatio
                        : widthRatio;
            }
        }
        // 设置压缩比例
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        return options;
    }


    /**
     * 得到指定路径图片的options
     *
     * @return Options {@link BitmapFactory.Options}
     */
    public static BitmapFactory.Options getBitmapOptions(String url) {
        URL m;
        InputStream is = null;
        try {
            m = new URL(url);
            is = (InputStream) m.getContent();
            BufferedInputStream bis = new BufferedInputStream(is);
            //标记其实位置，供reset参考
            bis.mark(0);

            BitmapFactory.Options opts = new BitmapFactory.Options();
            //true,只是读图片大小，不申请bitmap内存
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(bis, null, opts);

            is.close();
            bis.close();
            return opts;
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取一个指定大小的bitmap
     *
     * @param reqWidth  目标宽度
     * @param reqHeight 目标高度
     */
    public static Bitmap getBitmapFromFile(String pathName, int reqWidth,
                                           int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        options = calculateInSampleSize(options, reqWidth, reqHeight);
        return BitmapFactory.decodeFile(pathName, options);
    }

    /**
     * 已售罄,已下架等状态,商品图片颜色变暗
     *
     * @param img
     */
    public static void shadeImage(ImageView img) {
        // 生成色彩矩阵
        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
                0.5F, 0, 0, 0, 0,
                0, 0.5F, 0, 0, 0,
                0, 0, 0.5F, 0, 0,
                0, 0, 0, 1, 0,
        });
        img.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
    }

    /**
     * 以最省内存的方式读取本地资源的图片
     *
     * @param context
     * @param resId
     * @return
     */

    public static BitmapDrawable readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        //获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, opt);
        BitmapDrawable bd = new BitmapDrawable(context.getResources(), bitmap);
        return bd;

    }


    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565);

        Canvas canvas = new Canvas(bitmap);
        //canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;

    }

    //view 转bitmap
    public static Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }
}
