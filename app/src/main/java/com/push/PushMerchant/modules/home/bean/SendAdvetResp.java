package com.push.PushMerchant.modules.home.bean;

import com.push.PushMerchant.base.BaseResp;

/**
 * Created by Administrator on 2017/8/11 0011.
 */

public class SendAdvetResp extends BaseResp {
    private int proviceId;
    private int cityId;
    private int areaId;
    private int productId;
    private String address;
    private String time;
    private int pruduct;
    private int one;
    private int two;
    private int three;

    public int getPruduct() {
        return pruduct;
    }

    public void setPruduct(int pruduct) {
        this.pruduct = pruduct;
    }

    public int getOne() {
        return one;
    }

    public void setOne(int one) {
        this.one = one;
    }

    public int getTwo() {
        return two;
    }

    public void setTwo(int two) {
        this.two = two;
    }

    public int getThree() {
        return three;
    }

    public void setThree(int three) {
        this.three = three;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getProviceId() {
        return proviceId;
    }

    public void setProviceId(int proviceId) {
        this.proviceId = proviceId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

}
