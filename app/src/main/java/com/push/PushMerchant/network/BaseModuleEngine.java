package com.push.PushMerchant.network;

import android.content.Intent;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.push.PushMerchant.base.BaseResp;
import com.push.PushMerchant.base.PushApplicationContext;
import com.push.PushMerchant.constants.IntentFlag;
import com.push.PushMerchant.modules.AccountManager;
import com.push.PushMerchant.modules.person.activity.LoginActivity;
import com.push.PushMerchant.network.impl.INetworkEngine;
import com.push.PushMerchant.network.okhttp.OkHttpRequest;
import com.push.PushMerchant.network.parser.BaseParse;
import com.push.PushMerchant.network.resp.NetworkContanst;
import com.push.PushMerchant.network.resp.ResultCode;
import com.push.PushMerchant.util.CommonUtil;
import com.push.PushMerchant.util.Global;
import com.push.PushMerchant.util.SharePrefUtil;
import com.push.PushMerchant.util.ToastUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 类说明：业务基类，不需要ui回调的使用这个类
 *
 * @author Quanshao
 * @version 1.0
 * @date 2015年11月16日
 */
public abstract class BaseModuleEngine implements INetworkEngine {
    private static final String TAG = BaseModuleEngine.class.getName();
    protected static final int PAGESIZE = 20;
    public static final String X_ACCESSTOKEN = "X-AccessToken";
    public static final String GUID = "WN-Guid";
    public static final String USERAGENT = "User-Agent";
    public static final String WNUSERAGENT = "WUA";
    private Map<Integer, RequestContext> seqMap = new ConcurrentHashMap<Integer, RequestContext>();
    protected static final int HTTP_POST = 0;
    protected static final int HTTP_GET = 1;

    /**
     * @throws
     * @Description: Post
     * @param: @param urlPath @param: @param request @param: @param
     * clazz @param: @return
     */
    @Deprecated
    protected int sendPost(String urlPath, String request, Class<?> clazz) {
        if (request == null) {
            return NetworkContanst.REQUEST_SEND_FAIL;
        }
        return send(HTTP_POST, urlPath, request, clazz);
    }

    /**
     * @throws
     * @Description: Post
     * @param: @param urlPath @param: @param request @param: @param
     * clazz @param: @return
     */
    protected int sendPost(int reqCode, String urlPath, String request, Class<?> clazz) {
        if (request == null) {
            return NetworkContanst.REQUEST_SEND_FAIL;
        }
        return sent(reqCode, HTTP_POST, urlPath, request, clazz);
    }

    /**
     * @throws
     * @Description: Post
     * @param: @param urlPath @param: @param request @param: @param
     * clazz @param: @return
     */
    protected int sendPost(int reqCode, String urlPath, HashMap request, Class<?> clazz) {
        if (request == null) {
            return NetworkContanst.REQUEST_SEND_FAIL;
        }
        return sent(reqCode, HTTP_POST, urlPath, request, clazz);
    }


    /**
     * @throws
     * @Description: send request
     * @param: @param method @param: @param urlPath @param: @param
     * request @param: @param clazz @param: @return
     */
    protected int send(int method, String urlPath, String request, Class<?> clazz) {
        if (request == null) {
            return NetworkContanst.REQUEST_SEND_FAIL;
        }
        return sent(getUniqueId(), method, urlPath, request, clazz);
    }

    private int getUniqueId() {
        return CommonUtil.getUniqueId();
    }

    /**
     * @throws
     * @Description: request
     * @param: @param seq @param: @param method @param: @param
     * urlPath @param: @param request @param: @param clazz @param: @return
     */
    protected int sent(int seq, int method, String urlPath, String request, Class<?> clazz) {
        if (seq <= 0) {
            seq = CommonUtil.getUniqueId();
        }
        OkHttpRequest.Builder requestBuilder = new OkHttpRequest.Builder().url(Global.getCommonUrl() + urlPath);
        switch (method) {
            case HTTP_POST:
//                requestBuilder.addHeader(USERAGENT, Global.getWUA() + AccountManager.getInstance().getMemberId());//用以下的自定义header字段代替
              /*  requestBuilder.addHeader(WNUSERAGENT, Global.getWUA() + AccountManager.getInstance().getMemberId());
                if (Global.getAndroidid() != null) {
                    requestBuilder.addHeader(GUID, Global.getAndroidid());
                }*/
                if (Global.getAndroidid() != null) {
                    requestBuilder.addHeader(GUID, Global.getAndroidid());
                }
                requestBuilder.addHeader("Accept-Language", Locale.getDefault().toString());
                requestBuilder.addHeader("Connection", "Keep-Alive");
//                if (AccountManager.getInstance().getAccessToken() != null) {
//                    requestBuilder.addHeader(X_ACCESSTOKEN, AccountManager.getInstance().getAccessToken());
//                }
                requestBuilder.content(request).post(seq, this);
                break;
            case HTTP_GET:
                new OkHttpRequest.Builder().url(Global.getCommonUrl() + urlPath).get(seq, this);
                break;
            default:
                break;
        }
        RequestContext context = new RequestContext();
        context.seq = seq;
        context.clazz = clazz;
        context.mRequests = request;

        seqMap.put(seq, context);

        return seq;
    }

    /**
     * @throws
     * @Description: request
     * @param: @param seq @param: @param method @param: @param
     * urlPath @param: @param request @param: @param clazz @param: @return
     */
    protected int sent(int seq, int method, String urlPath, HashMap request, Class<?> clazz) {
        if (seq <= 0) {
            seq = CommonUtil.getUniqueId();
        }
        OkHttpRequest.Builder requestBuilder = new OkHttpRequest.Builder().url(Global.getCommonUrl() + urlPath);
        switch (method) {
            case HTTP_POST:
//                requestBuilder.addHeader(USERAGENT, Global.getWUA() + AccountManager.getInstance().getMemberId());//用以下的自定义header字段代替
              /*  requestBuilder.addHeader(WNUSERAGENT, Global.getWUA() + AccountManager.getInstance().getMemberId());
                if (Global.getAndroidid() != null) {
                    requestBuilder.addHeader(GUID, Global.getAndroidid());
                }*/
                if (Global.getAndroidid() != null) {
                    requestBuilder.addHeader(GUID, Global.getAndroidid());
                }
                requestBuilder.addHeader("Accept-Language", Locale.getDefault().toString());
                requestBuilder.addHeader("Connection", "Keep-Alive");

//                if (AccountManager.getInstance().getAccessToken() != null) {
//                    requestBuilder.addHeader(X_ACCESSTOKEN, AccountManager.getInstance().getAccessToken());
//                }
                requestBuilder.params(request).post(seq, this);
                break;
            case HTTP_GET:
                new OkHttpRequest.Builder().url(Global.getCommonUrl() + urlPath).get(seq, this);
                break;
            default:
                break;
        }
        RequestContext context = new RequestContext();
        context.seq = seq;
        context.clazz = clazz;
        context.mRequests = request.toString();

        seqMap.put(seq, context);

        return seq;
    }


    protected void download(int seq, String urlPath, String filePath, String dirFilePath) {
        if (seq <= 0) {
            seq = CommonUtil.getUniqueId();
        }
        OkHttpRequest.Builder requestBuilder = new OkHttpRequest.Builder().url(Global.getCommonUrl() + urlPath);
        if (Global.getAndroidid() != null) {
            requestBuilder.addHeader(GUID, Global.getAndroidid());
        }
        requestBuilder.addHeader("Accept-Language", Locale.getDefault().toString());
        requestBuilder.addHeader("Connection", "Keep-Alive");

        requestBuilder.destFileDir(dirFilePath).destFileName(filePath).download(seq, this);
        RequestContext context = new RequestContext();
        context.seq = seq;
        seqMap.put(seq, context);
    }

    @Override
    public void onNetworkRequestSuccess(int reqÇode, String response) {
        RequestContext context = seqMap.remove(reqÇode);
        if (context == null) {
            return;
        }
        BaseResp baseResp = null;
        try {
            baseResp = new BaseParse<>().parse(response, context.clazz);
//            Log.e(TAG, "onNetworkRequestSuccess: " +response);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        }
        if (baseResp != null) {
            if (baseResp.getSuccess() == ResultCode.CODE_EXPIRED_ACCESS_TOKEN || baseResp.getSuccess() == ResultCode.CODE_ACCESS_TOKEN_ERROR) {
                onRequestFailed(reqÇode, baseResp.getSuccess(), baseResp.getMessage());
                //token失效
                AccountManager.getInstance().logout();
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                SharePrefUtil.putBooleanSetting(IntentFlag.OUTTOKEN, true);
                intent.setClass(PushApplicationContext.context, LoginActivity.class);
                PushApplicationContext.context.startActivity(intent);
                PushApplicationContext.closeAllActivitys();
                return;
            }

            if (baseResp.getSuccess() == 1) {
                onRequestSuccess(reqÇode, baseResp);
            } else {
                if (baseResp.getSuccess() == 9003) {
                    onRequestSuccess(reqÇode, baseResp);
                } else if (baseResp.getSuccess() == 108) {
//                    JumpUtil.jumpCommonBrowser(BaseActivity.sTopActivity, baseResp.getMessage());
//                    if (BaseActivity.sTopActivity.getProgressDlg() != null && BaseActivity.sTopActivity.getProgressDlg().isShowing())
//                        BaseActivity.sTopActivity.getProgressDlg().dismiss();
                } else {
                    onRequestFailed(reqÇode, baseResp.getSuccess(), baseResp.getMessage());
                }

            }
        } else {
            onRequestFailed(reqÇode, ResultCode.Code_Response_Empty, null);
        }

    }

    @Override
    public void onNetworkRequestFail(int reqÇode, int errorCode, Exception response) {
        onRequestFailed(reqÇode, ResultCode.Code_Response_Empty, "网络异常，请设置网络");
    }

    @Override
    public void inProgress(int reqÇode, float f) {
        // TODO Auto-generated method stub

    }

    protected JsonObject getDefaultJsonObject() {
        JsonObject json = new JsonObject();
        return json;
    }

    protected HashMap<String, String> getDefaultHashMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("authToken", AccountManager.getInstance().getUserEntity().getObject().getAuthToken());
        return map;
    }


    /**
     * 请求成功
     *
     * @param reqCode
     * @param rsp
     */
    protected void onRequestSuccess(final int reqCode, final BaseResp rsp) {
        // TODO 请求监控

        try {
            Properties map = new Properties();
            map.put("reqCode", String.valueOf(reqCode));
            if (rsp != null) {
                map.put("errMsg", rsp.getMessage());
            }
        } catch (Exception e) {

        }
    }


    /**
     * 请求失败
     *
     * @param reqCode
     * @param errCode
     * @param msg
     */
    protected void onRequestFailed(final int reqCode, final int errCode, final String msg) {
        try {
            Properties map = new Properties();
            map.put("reqCode", String.valueOf(reqCode));
            map.put("errCode", String.valueOf(errCode));
            if (msg != null) {
                map.put("errMsg", msg);
                ThreadManager.getMainThreadHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast(msg);
                    }
                });
            }
        } catch (Exception e) {

        }
        // TODO 失败监控
    }

    private class RequestContext {
        public int seq;// 请求码
        public String mRequests;// 我的请求协议
        public Class<?> clazz;// 传进来的需要gson解析的类
    }

}
