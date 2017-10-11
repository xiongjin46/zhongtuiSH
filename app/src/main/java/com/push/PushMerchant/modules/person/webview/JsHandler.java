package com.push.PushMerchant.modules.person.webview;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public abstract class JsHandler {
    public JsBridgeListener jsBridgeListener;

    public void release() {

    }
	/**
     * 如需要自己处理
     * @param methodName
     * @param args
     * @param listener
     */
    @SuppressWarnings("rawtypes")
	public void invoke(String methodName, Map<String, String> args,final JsBridgeListener listener) {
        Method[] methods = this.getClass().getDeclaredMethods();
        Method targetMethod = null;
        for(Method method : methods) {
            if(method.getName().equalsIgnoreCase(methodName)) {
//                Class[] types = method.getParameterTypes();
//                if(types.length == args.size()){//暂时只判断长度
                    targetMethod = method;
                    break;
//                }
            }
        }

        if(targetMethod != null) {
            Object result = null;
            try {
                if (args.size() == 0) {
                    result = targetMethod.invoke(this);
                } else {
                    result = targetMethod.invoke(this, args);
                }
                if (targetMethod.getReturnType() == Void.class) {
                    // 暂时不回调
//                    if(listener != null) {
//                        listener.onComplete(null);
//                    }
                } else {
                    if (listener != null) {
                        if (customCallback()) {// 自定义callback，即返回的是需执行的js脚本
                            listener.onCustomCallback(result.toString());
                        } else {//非自定义callback,返回值为参数
                            listener.onComplete((String) result);
                        }
                    }
                    return;
                }
            } catch (IllegalAccessException e) {// TODO 提供更明确的错误信息
                if(listener != null)listener.onNoMatchMethod();
            } catch (InvocationTargetException e) {
                if(listener != null)listener.onNoMatchMethod();
            } catch(Exception e) {// catch 捕获所有exception
                if(listener != null)listener.onNoMatchMethod();
            }

        }

        if(listener != null) listener.onNoMatchMethod();
    }

    /**
     * 需要自定义callback，修改此函数返回值，并在具体接口里返回回调的js串，满足需在回调js时有多个参数或有number/string/int之外参数的情况
     * @return
     */
    public boolean customCallback() {
        return false;
    }

    protected String generateResponse(HashMap<String, String> map) {
        JSONObject obj = new JSONObject();
        for (String key : map.keySet()) {
            try {
                obj.put(key, map.get(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return obj.toString();
    }
    
    public abstract String getInterfaceName();
}
