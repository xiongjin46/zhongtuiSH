package com.push.PushMerchant.network.okhttp;

import android.util.Pair;

import com.push.PushMerchant.network.impl.INetworkEngine;

import java.io.File;
import java.io.IOException;
import java.util.IdentityHashMap;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


public abstract class OkHttpRequest {

    protected OkHttpClientManager mOkHttpClientManager = OkHttpClientManager.getInstance();
    protected OkHttpClient mOkHttpClient;

    protected RequestBody requestBody;
    protected Request request;

    protected String url;
    protected String tag;
    protected Map<String, String> params;
    protected Map<String, String> headers;

    protected OkHttpRequest(String url, String tag, Map<String, String> params, Map<String, String> headers) {
        mOkHttpClient = mOkHttpClientManager.getOkHttpClient();
        this.url = url;
        this.tag = tag;
        this.params = params;
        this.headers = headers;
    }

    protected abstract Request buildRequest();

    protected abstract RequestBody buildRequestBody();

    protected void prepareInvoked(int seqCode, INetworkEngine callback) {
        requestBody = buildRequestBody();
        requestBody = wrapRequestBody(seqCode, requestBody, callback);
        request = buildRequest();
    }

    public void invokeAsyn(int seqCode, INetworkEngine callback) {
        prepareInvoked(seqCode, callback);
        mOkHttpClientManager.execute(seqCode, request, callback);
    }

    protected RequestBody wrapRequestBody(int seqCode, RequestBody requestBody, final INetworkEngine callback) {
        return requestBody;
    }

    public <T> T invoke(int reqCode, Class<T> clazz) throws IOException {
        requestBody = buildRequestBody();
        Request request = buildRequest();
        return mOkHttpClientManager.execute(reqCode, request, clazz);
    }

    protected void appendHeaders(Request.Builder builder, Map<String, String> headers) {
        if (builder == null) {
            throw new IllegalArgumentException("builder can not be empty!");
        }

        Headers.Builder headerBuilder = new Headers.Builder();
        if (headers == null || headers.isEmpty())
            return;

        for (String key : headers.keySet()) {
            headerBuilder.add(key, headers.get(key));
        }
        builder.headers(headerBuilder.build());
    }

//    public void cancel() {
//        if (!TextUtils.isEmpty(tag))
//            mOkHttpClientManager.cancelTag(tag);
//    }

    public static class Builder {
        private String url;
        private String tag;
        private Map<String, String> headers;
        private Map<String, String> params;
        private Pair<String, File>[] files;

        private String destFileDir;
        private String destFileName;

        // for post
        private String content;
        private byte[] bytes;
        private File file;

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder tag(String tag) {
            this.tag = tag;
            return this;
        }

        public Builder params(Map<String, String> params) {
            this.params = params;
            return this;
        }

        public Builder addParams(String key, String val) {
            if (this.params == null) {
                params = new IdentityHashMap<>();
            }
            params.put(key, val);
            return this;
        }

        public Builder headers(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public Builder addHeader(String key, String val) {
            if (this.headers == null) {
                headers = new IdentityHashMap<>();
            }
            headers.put(key, val);
            return this;
        }

        public Builder files(Pair<String, File>... files) {
            this.files = files;
            return this;
        }

        public Builder destFileName(String destFileName) {
            this.destFileName = destFileName;
            return this;
        }

        public Builder destFileDir(String destFileDir) {
            this.destFileDir = destFileDir;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public <T> T get(int reqCode, Class<T> clazz) throws IOException {
            OkHttpRequest request = new OkHttpGetRequest(url, tag, params, headers);
            return request.invoke(reqCode, clazz);
        }

        public OkHttpRequest get(int req, INetworkEngine callback) {
            OkHttpRequest request = new OkHttpGetRequest(url, tag, params, headers);
            request.invokeAsyn(req, callback);
            return request;
        }

        public <T> T post(int reqCode, Class<T> clazz) throws IOException {
            OkHttpRequest request = new OkHttpPostRequest(url, tag, params, headers, content, bytes, file);
            return request.invoke(reqCode, clazz);
        }

        public OkHttpRequest post(int req, INetworkEngine callback) {
            OkHttpRequest request = new OkHttpPostRequest(url, tag, params, headers, content, bytes, file);
            request.invokeAsyn(req, callback);
            return request;
        }

        public OkHttpRequest upload(int req, INetworkEngine callback) {
            OkHttpRequest request = new OkHttpUploadRequest(url, tag, params, headers, files);
            request.invokeAsyn(req, callback);
            return request;
        }

        public <T> T upload(int reqCode, Class<T> clazz) throws IOException {
            OkHttpRequest request = new OkHttpUploadRequest(url, tag, params, headers, files);
            return request.invoke(reqCode, clazz);
        }

        public OkHttpRequest download(int req, INetworkEngine callback) {
            OkHttpRequest request = new OkHttpDownloadRequest(url, tag, params, headers, destFileName, destFileDir);
            request.invokeAsyn(req, callback);
            return request;
        }

        public String download(int reqCode) throws IOException {
            OkHttpRequest request = new OkHttpDownloadRequest(url, tag, params, headers, destFileName, destFileDir);
            return request.invoke(reqCode, String.class);
        }

    }

}
