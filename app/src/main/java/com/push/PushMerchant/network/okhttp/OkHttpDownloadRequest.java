package com.push.PushMerchant.network.okhttp;


import com.push.PushMerchant.network.impl.INetworkEngine;
import com.push.PushMerchant.network.resp.NetworkContanst;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @ClassName: OkHttpDownloadRequest
 * @Description: 下载请求
 * @author: weinong
 * @date: 2015年11月16日
 */
public class OkHttpDownloadRequest extends OkHttpGetRequest {
    private String destFileDir;
    private String destFileName;

    protected OkHttpDownloadRequest(String url, String tag, Map<String, String> params, Map<String, String> headers,
                                    String destFileName, String destFileDir) {
        super(url, tag, params, headers);
        this.destFileName = destFileName;
        this.destFileDir = destFileDir;
    }

    @Override
    public void invokeAsyn(final int reqCode, final INetworkEngine callback) {
        prepareInvoked(reqCode, callback);

        final Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mOkHttpClientManager.sendFailResultCallback(reqCode, NetworkContanst.RESPONSE_SEND_FAIL, e, callback);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String filePath = saveFile(reqCode, response, callback);
                    OkHttpClientManager.getInstance().sendSuccessResultCallback(reqCode, filePath, callback);
                } catch (IOException e) {
                    e.printStackTrace();
                    OkHttpClientManager.getInstance().sendFailResultCallback(reqCode, response.code(), e, callback);
                }
            }
        });

    }

    private String getFileName(String path) {
        int separatorIndex = path.lastIndexOf("/");
        return (separatorIndex < 0) ? path : path.substring(separatorIndex + 1, path.length());
    }

    @Override
    public <T> T invoke(int reqCode, Class<T> clazz) throws IOException {
        final Call call = mOkHttpClient.newCall(request);
        Response response = call.execute();
        return (T) saveFile(reqCode, response, null);
    }

    public String saveFile(final int reqCode, Response response, final INetworkEngine callback) throws IOException {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        try {
            is = response.body().byteStream();
            final long total = response.body().contentLength();
            long sum = 0;

            File dir = new File(destFileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, destFileName);
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                sum += len;
                fos.write(buf, 0, len);

                if (callback != null) {
                    final long finalSum = sum;
                    callback.inProgress(reqCode, finalSum * 1.0f / total);
                }
            }
            fos.flush();

            return file.getAbsolutePath();

        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
            }
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
            }

        }
    }

}
