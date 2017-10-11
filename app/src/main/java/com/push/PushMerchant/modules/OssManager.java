package com.push.PushMerchant.modules;


import android.text.TextUtils;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.push.PushMerchant.base.PushApplicationContext;
import com.push.PushMerchant.constants.KeyContants;
import com.push.PushMerchant.modules.person.bean.CommentImageBean;
import com.push.PushMerchant.util.BitmapUtil;
import com.push.PushMerchant.util.CommonUtil;
import com.push.PushMerchant.util.Global;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: OssManager
 * @Description: Oss 管理
 * @author: YYL
 * <p>
 * create at 2016/11/30 17:32
 */

public class OssManager {

    //    public static final String KEYACCESSKEY = "key-accesskey";
//    public static final String KEYSCRECTKEY = "key-screctkey";
    public static final String KEYACCESSKEY = KeyContants.OSS_AccessKey;
    public static final String KEYSCRECTKEY = KeyContants.OSS_AccessServer;

    public interface OnCompletedCallback {

        void onSuccess(int sort, String path);

        void onFailure(int sort, String errMsg);
    }

    private static final String OSSBUCKET = "zhongtui168";//https图片组需要用到的
    private static final String AVATAR = "avatar/";  //用户
    private static final String COMMENT = "comment/";   //评论
    private static final String TOPIC = "topic/";   //帖子

    private static final String CDNHOST = "http://oss-cn-shenzhen.aliyuncs.com";

    private static OssManager sInstance;
    private OSSClient mOSSClient;

    private List<WeakReference<OnCompletedCallback>> mCallbacks;
    private List<WeakReference<OSSAsyncTask<PutObjectResult>>> mTasks;

    public static OssManager getInstance() {

        if (sInstance == null) {
            sInstance = new OssManager();
        }
        return sInstance;
    }

    /**
     * 是否连接oss
     *
     * @return
     */
    public static boolean isOssAvailable() {
        String accessKey = KEYACCESSKEY;
        String screctKey = KEYSCRECTKEY;
        if (TextUtils.isEmpty(accessKey) || TextUtils.isEmpty(screctKey)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 注册oss 服务
     *
     * @param callback
     */
    public void registerCallback(OnCompletedCallback callback) {
        if (callback != null) {
            for (WeakReference<OnCompletedCallback> wr : mCallbacks) {
                if (wr.get() == callback) {
                    return;
                }
            }
            WeakReference<OnCompletedCallback> weakReference = new WeakReference<OnCompletedCallback>(callback);
            mCallbacks.add(weakReference);
        }
    }

    /**
     * 解绑oss 服务
     *
     * @param callback
     */
    public void unregisterCallback(OnCompletedCallback callback) {
        if (callback != null) {
            for (WeakReference<OnCompletedCallback> wr : mCallbacks) {
                if (wr.get() == callback) {
                    mCallbacks.remove(wr);
                    break;
                }
            }
        }
    }

    private OssManager() {
        String accessKey = KEYACCESSKEY;
        String screctKey = KEYSCRECTKEY;
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKey, screctKey);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次

        mOSSClient = new OSSClient(PushApplicationContext.context, CDNHOST, credentialProvider, conf);
        mCallbacks = new ArrayList<>();
    }

    public void uploadCommentPic(CommentImageBean cib) {
        String ossObjectKey = Global.getOSSPath() + CommonUtil.getImgName();
//        String ossObjectKey = Global.getOSSPath() + COMMENT + CommonUtil.getImgName();
        uploadSort(cib, ossObjectKey);
    }

    public void uploadHead(String uploadFile) {
        String ossObjectKey = Global.getOSSPath() + CommonUtil.getImgName();
//        String ossObjectKey = Global.getOSSPath() + AVATAR + CommonUtil.getImgName();
        upload(uploadFile, ossObjectKey);
    }

    public void uploadTopic(CommentImageBean cib) {
        String ossObjectKey = Global.getOSSPath() + CommonUtil.getImgName();
//        String ossObjectKey = Global.getOSSPath() + TOPIC + CommonUtil.getImgName();
        uploadSort(cib, ossObjectKey);
    }

    private void upload(String uploadFile, final String ossObjectKey) {
        if (mTasks == null) {
            mTasks = new ArrayList<>();
        }
//        byte[] uploadByte = BitmapUtil.getBitmapByte(uploadFile, 280, 280, true);
        byte[] uploadByte = BitmapUtil.getBitmapByte(uploadFile, 280, 280);
        if (uploadByte == null) return;
        PutObjectRequest put = new PutObjectRequest(OSSBUCKET, ossObjectKey, uploadByte);
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {

                Log.e("put object", "currentSize: " + currentSize + "----totalSize: " + totalSize);
            }
        });
        OSSAsyncTask<PutObjectResult> task = mOSSClient.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                for (WeakReference<OnCompletedCallback> wr : mCallbacks) {
                    if (wr.get() != null) {
                        wr.get().onSuccess(0, ossObjectKey);
                    }
                }
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                for (WeakReference<OnCompletedCallback> wr : mCallbacks) {
                    if (wr.get() != null) {
                        wr.get().onFailure(0, clientExcepion.getMessage());
                    }
                }
            }
        });
        WeakReference<OSSAsyncTask<PutObjectResult>> weakReference = new WeakReference<>(task);
        mTasks.add(weakReference);
    }

    private void uploadSort(final CommentImageBean cib, final String ossObjectKey) {
        if (mTasks == null) {
            mTasks = new ArrayList<>();
        }
//        byte[] uploadByte = BitmapUtil.getBitmapByte(cib.getImagePath(), 500, 500, true);
        byte[] uploadByte = BitmapUtil.getBitmapByte(cib.getImagePath(), 500, 500);
        if (uploadByte == null) return;
        PutObjectRequest put = new PutObjectRequest(OSSBUCKET, ossObjectKey, uploadByte);
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {

                Log.e("put object", "currentSize: " + currentSize + "----totalSize: " + totalSize);
            }
        });
        OSSAsyncTask<PutObjectResult> task = mOSSClient.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                for (WeakReference<OnCompletedCallback> wr : mCallbacks) {
                    if (wr.get() != null) {
                        wr.get().onSuccess(cib.getSort(), ossObjectKey);
                    }
                }
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                for (WeakReference<OnCompletedCallback> wr : mCallbacks) {
                    if (wr.get() != null) {
                        wr.get().onFailure(cib.getSort(), clientExcepion.getMessage());
                    }
                }
            }
        });
        WeakReference<OSSAsyncTask<PutObjectResult>> weakReference = new WeakReference<>(task);
        mTasks.add(weakReference);
    }

    /**
     * 清除所有上传任务
     */
    public void clearAllTask() {
        if (mTasks == null) {
            return;
        }
        for (WeakReference<OSSAsyncTask<PutObjectResult>> wr : mTasks) {
            if (wr.get() != null) {
                wr.get().cancel();
            }
        }
    }

}
