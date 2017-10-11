package com.push.PushMerchant.base;

import java.io.Serializable;

/**
 * @ClassName: BaseResp
 * @Description: 返回基类
 * @author: YYL
 * <p/>
 * create at 2016/9/6
 */
public class BaseResp implements Serializable {

    private static final long serialVersionUID = -3424883911810440779L;

    private int success;
    private String message;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
