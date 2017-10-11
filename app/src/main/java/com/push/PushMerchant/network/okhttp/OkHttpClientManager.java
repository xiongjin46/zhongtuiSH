package com.push.PushMerchant.network.okhttp;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.$Gson$Types;
import com.push.PushMerchant.network.impl.INetworkEngine;
import com.push.PushMerchant.network.resp.ResultCode;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * @ClassName: OkHttpClientManager
 * @Description:
 * @author: YYL
 * <p>
 * create at 2016/9/6
 */
public class OkHttpClientManager {

    public static final String TAG = "OkHttpClientManager";
    private static OkHttpClientManager mInstance;
    private final OkHttpClient.Builder builder;
    private OkHttpClient mOkHttpClient;
    private Gson mGson;
    private Handler mDelivery;

    private OkHttpClientManager() {
        mOkHttpClient = new OkHttpClient();
        // cookie enabled
        builder = mOkHttpClient.newBuilder();
//        mOkHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
        builder.cookieJar(new CookieJar() {
            private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();

            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                cookieStore.put(url, cookies);
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookieStore.get(url);
                return cookies != null ? cookies : new ArrayList<Cookie>();
            }
        });
        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.writeTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(30, TimeUnit.SECONDS);

        final int sdk = Build.VERSION.SDK_INT;
        if (sdk >= 23) {
            GsonBuilder gsonBuilder = new GsonBuilder().excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT,
                    Modifier.STATIC);
            mGson = gsonBuilder.create();
        } else {
            mGson = new Gson();
        }
        mDelivery = new Handler(Looper.getMainLooper());
    }

    public static OkHttpClientManager getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpClientManager.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpClientManager();
                    mInstance.setCertificates();
                }
            }
        }
        return mInstance;
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public void execute(final int seqCode, final Request request, INetworkEngine callback) {
        if (callback == null) {
            callback = new INetworkEngine() {

                @Override
                public void onNetworkRequestSuccess(int requestId, String response) {

                }

                @Override
                public void onNetworkRequestFail(int requestId, int result, Exception response) {

                }

                @Override
                public void inProgress(int reqCode, float f) {

                }
            };
        }
        final INetworkEngine resCallBack = callback;
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFailResultCallback(seqCode, ResultCode.Code_Response_Empty, e, resCallBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() >= 400 && response.code() <= 599) {
                    try {
                        sendFailResultCallback(seqCode, response.code(), new RuntimeException(response.body().string()),
                                resCallBack);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                try {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        sendSuccessResultCallback(seqCode, string, resCallBack);
                    } else {
                        sendFailResultCallback(seqCode, ResultCode.Code_Http_EntityNull, null, resCallBack);
                    }
                } catch (Exception e) {
                    sendFailResultCallback(seqCode, response.code(), e, resCallBack);
                }

            }
        });
    }

    public <T> T execute(int reqCode, Request request, Class<T> clazz) throws IOException {
        Call call = mOkHttpClient.newCall(request);
        Response execute = call.execute();
        String respStr = execute.body().string();
        return mGson.fromJson(respStr, clazz);
    }

    public void sendFailResultCallback(final int reqCode, final int respCode, final Exception e,
                                       final INetworkEngine callback) {
        if (callback == null)
            return;

        callback.onNetworkRequestFail(reqCode, respCode, e);
    }

    public void sendSuccessResultCallback(final int reqCode, final String object, final INetworkEngine callback) {
        if (callback == null)
            return;
        callback.onNetworkRequestSuccess(reqCode, object);
    }

//    public void cancelTag(Object tag) {
//        mOkHttpClient.cancel(tag);
//    }

    /**
     * for https-way authentication
     *
     * @param certificates
     */
    public void setCertificates(InputStream... certificates) {
        SSLSocketFactory sslSocketFactory = getSslSocketFactory(certificates, null, null);

        builder.sslSocketFactory(sslSocketFactory);


    }

    public SSLSocketFactory getSslSocketFactory(InputStream[] certificates, InputStream bksFile, String password) {
        try {
            TrustManager[] trustManagers = prepareTrustManager(certificates);
            KeyManager[] keyManagers = prepareKeyManager(bksFile, password);
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManager trustManager = null;
            if (trustManagers != null) {
                trustManager = new MyTrustManager(chooseTrustManager(trustManagers));
            } else {
                trustManager = new UnSafeTrustManager();
            }
            sslContext.init(keyManagers, new TrustManager[]{trustManager}, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        } catch (KeyManagementException e) {
            throw new AssertionError(e);
        } catch (KeyStoreException e) {
            throw new AssertionError(e);
        }
    }

//    public void setCertificates(InputStream... certificates) {
//        setCertificates(certificates, null, null);
//    }

    private TrustManager[] prepareTrustManager(InputStream... certificates) {
        if (certificates == null || certificates.length <= 0)
            return null;
        try {

            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));
                try {
                    if (certificate != null)
                        certificate.close();
                } catch (IOException e)

                {
                }
            }
            TrustManagerFactory trustManagerFactory = null;

            trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);

            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();

            return trustManagers;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    private KeyManager[] prepareKeyManager(InputStream bksFile, String password) {
        try {
            if (bksFile == null || password == null)
                return null;

            KeyStore clientKeyStore = KeyStore.getInstance("BKS");
            clientKeyStore.load(bksFile, password.toCharArray());
            KeyManagerFactory keyManagerFactory = KeyManagerFactory
                    .getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(clientKeyStore, password.toCharArray());
            return keyManagerFactory.getKeyManagers();

        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setCertificates(InputStream[] certificates, InputStream bksFile, String password) {
        try {
            TrustManager[] trustManagers = prepareTrustManager(certificates);
            KeyManager[] keyManagers = prepareKeyManager(bksFile, password);
            SSLContext sslContext = SSLContext.getInstance("TLS");

            sslContext.init(keyManagers, new TrustManager[]{new MyTrustManager(chooseTrustManager(trustManagers))},
                    new SecureRandom());
            builder.sslSocketFactory(sslContext.getSocketFactory());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
    }

    private static class UnSafeTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }

    private X509TrustManager chooseTrustManager(TrustManager[] trustManagers) {
        for (TrustManager trustManager : trustManagers) {
            if (trustManager instanceof X509TrustManager) {
                return (X509TrustManager) trustManager;
            }
        }
        return null;
    }

    private class MyTrustManager implements X509TrustManager {
        private X509TrustManager defaultTrustManager;
        private X509TrustManager localTrustManager;

        public MyTrustManager(X509TrustManager localTrustManager) throws NoSuchAlgorithmException, KeyStoreException {
            TrustManagerFactory var4 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            var4.init((KeyStore) null);
            defaultTrustManager = chooseTrustManager(var4.getTrustManagers());
            this.localTrustManager = localTrustManager;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            try {
                defaultTrustManager.checkServerTrusted(chain, authType);
            } catch (CertificateException ce) {
                localTrustManager.checkServerTrusted(chain, authType);
            }
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }


    private String getFileName(String path) {
        int separatorIndex = path.lastIndexOf("/");
        return (separatorIndex < 0) ? path : path.substring(separatorIndex + 1, path.length());
    }

    public static abstract class ResultCallback<T> {
        Type mType;

        public ResultCallback() {
            mType = getSuperclassTypeParameter(getClass());
        }

        static Type getSuperclassTypeParameter(Class<?> subclass) {
            Type superclass = subclass.getGenericSuperclass();
            if (superclass instanceof Class) {
                throw new RuntimeException("Missing type parameter.");
            }
            ParameterizedType parameterized = (ParameterizedType) superclass;
            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
        }

        public abstract void onError(Request request, Exception e);

        public abstract void onResponse(T response);

    }

    private void sendFailedStringCallback(final Request request, final Exception e, final ResultCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null)
                    callback.onError(request, e);
            }
        });
    }

    private void sendSuccessResultCallback(final Object object, final ResultCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onResponse(object);
                }
            }
        });
    }
}
