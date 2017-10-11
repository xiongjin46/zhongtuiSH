package com.push.PushMerchant.modules.map.bean;

import com.push.PushMerchant.base.BaseBean;
import com.push.PushMerchant.base.BaseResp;

import java.util.List;

/**
 * Created by Administrator on 2017/8/12 0012.
 */

public class CircleResp extends BaseResp {

    private List<ObjectBean> object;

    public List<ObjectBean> getObject() {
        return object;
    }

    public void setObject(List<ObjectBean> object) {
        this.object = object;
    }

    public static class ObjectBean extends BaseBean {
        /**
         * id : 1
         * inputDate : 1501041523000
         * channelName : 龙华商圈
         * scope : 500
         * longItude : 114.025909
         * latItude : 22.628658
         * cityId : 202
         * storeCount : 5
         * areaId : 1
         */

        private int id;
        private long inputDate;
        private String channelName;
        private int scope;
        private double longItude;
        private double latItude;
        private int cityId;
        private int storeCount;
        private int areaId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getInputDate() {
            return inputDate;
        }

        public void setInputDate(long inputDate) {
            this.inputDate = inputDate;
        }

        public String getChannelName() {
            return channelName;
        }

        public void setChannelName(String channelName) {
            this.channelName = channelName;
        }

        public int getScope() {
            return scope;
        }

        public void setScope(int scope) {
            this.scope = scope;
        }

        public double getLongItude() {
            return longItude;
        }

        public void setLongItude(double longItude) {
            this.longItude = longItude;
        }

        public double getLatItude() {
            return latItude;
        }

        public void setLatItude(double latItude) {
            this.latItude = latItude;
        }

        public int getCityId() {
            return cityId;
        }

        public void setCityId(int cityId) {
            this.cityId = cityId;
        }

        public int getStoreCount() {
            return storeCount;
        }

        public void setStoreCount(int storeCount) {
            this.storeCount = storeCount;
        }

        public int getAreaId() {
            return areaId;
        }

        public void setAreaId(int areaId) {
            this.areaId = areaId;
        }
    }
}
