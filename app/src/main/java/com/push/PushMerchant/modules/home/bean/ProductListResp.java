package com.push.PushMerchant.modules.home.bean;

import com.push.PushMerchant.base.BaseBean;
import com.push.PushMerchant.base.BaseResp;

import java.util.List;

/**
 * Created by Administrator on 2017/8/11 0011.
 */

public class ProductListResp extends BaseResp {

    private List<ObjectBean> object;

    public List<ObjectBean> getObject() {
        return object;
    }

    public void setObject(List<ObjectBean> object) {
        this.object = object;
    }

    public static class ObjectBean extends BaseBean{
        /**
         * channelRatio : 0.1
         * content : 测试
         * id : 18
         * inputDate : 1500978024000
         * name : 传统推广
         * productType : 0
         * status : 0
         * storeRatio : 0.2
         * teamRatio : 0.3
         */

        private double channelRatio;
        private String content;
        private int id;
        private long inputDate;
        private String name;
        private int productType;
        private int status;
        private double storeRatio;
        private double teamRatio;

        public double getChannelRatio() {
            return channelRatio;
        }

        public void setChannelRatio(double channelRatio) {
            this.channelRatio = channelRatio;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getProductType() {
            return productType;
        }

        public void setProductType(int productType) {
            this.productType = productType;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public double getStoreRatio() {
            return storeRatio;
        }

        public void setStoreRatio(double storeRatio) {
            this.storeRatio = storeRatio;
        }

        public double getTeamRatio() {
            return teamRatio;
        }

        public void setTeamRatio(double teamRatio) {
            this.teamRatio = teamRatio;
        }
    }
}
