package com.push.PushMerchant.modules.advert.bean;

import com.push.PushMerchant.base.BaseResp;

import java.util.List;

/**
 * Created by Administrator on 2017/8/3 0003.
 */

public class StoreResp extends BaseResp {

    /**
     * object : {"count":11,"list":[{"coverCrowd":"200","fullAddress":"湖滨东路3号","id":64,"latItude":0,"longItude":0,"name":"测试门店"},{"coverCrowd":"2000","fullAddress":"龙华新区龙华办事处油松路38号B栋、C栋","id":65,"latItude":0,"longItude":0,"name":"坂田丰客隆"},{"coverCrowd":"2000","fullAddress":"龙华新区龙华办事处油松路38号B栋、C栋","id":65,"latItude":0,"longItude":0,"name":"坂田丰客隆"},{"coverCrowd":"100","fullAddress":"龙华新区民治街道民治大道471-1号(嘉熙业广场大润发对面)","id":67,"latItude":0,"longItude":0,"name":"罗湖Hm"},{"coverCrowd":"100","fullAddress":"龙华新区民治街道民治大道471-1号(嘉熙业广场大润发对面)","id":67,"latItude":0,"longItude":0,"name":"罗湖Hm"},{"coverCrowd":"10","fullAddress":"龙华新区民治街道民治大道471-1号(嘉熙业广场大润发对面)","id":69,"latItude":0,"longItude":0,"name":"龙华大润发"},{"coverCrowd":"10","fullAddress":"龙华新区民治街道民治大道471-1号(嘉熙业广场大润发对面)","id":76,"latItude":0,"longItude":0,"name":"测试门店qjh"},{"coverCrowd":"10","fullAddress":"龙华新区民治街道民治大道471-1号(嘉熙业广场大润发对面)","id":76,"latItude":0,"longItude":0,"name":"测试门店qjh"},{"coverCrowd":"10","fullAddress":"龙华新区民治街道民治大道471-1号(嘉熙业广场大润发对面)","id":76,"latItude":0,"longItude":0,"name":"测试门店qjh"},{"coverCrowd":"10","fullAddress":"龙华新区民治街道民治大道471-1号(嘉熙业广场大润发对面)","id":76,"latItude":0,"longItude":0,"name":"测试门店qjh"}]}
     */

    private ObjectBean object;

    public ObjectBean getObject() {
        return object;
    }

    public void setObject(ObjectBean object) {
        this.object = object;
    }

    public static class ObjectBean extends BaseResp {
        /**
         * count : 11
         * list : [{"coverCrowd":"200","fullAddress":"湖滨东路3号","id":64,"latItude":0,"longItude":0,"name":"测试门店"},{"coverCrowd":"2000","fullAddress":"龙华新区龙华办事处油松路38号B栋、C栋","id":65,"latItude":0,"longItude":0,"name":"坂田丰客隆"},{"coverCrowd":"2000","fullAddress":"龙华新区龙华办事处油松路38号B栋、C栋","id":65,"latItude":0,"longItude":0,"name":"坂田丰客隆"},{"coverCrowd":"100","fullAddress":"龙华新区民治街道民治大道471-1号(嘉熙业广场大润发对面)","id":67,"latItude":0,"longItude":0,"name":"罗湖Hm"},{"coverCrowd":"100","fullAddress":"龙华新区民治街道民治大道471-1号(嘉熙业广场大润发对面)","id":67,"latItude":0,"longItude":0,"name":"罗湖Hm"},{"coverCrowd":"10","fullAddress":"龙华新区民治街道民治大道471-1号(嘉熙业广场大润发对面)","id":69,"latItude":0,"longItude":0,"name":"龙华大润发"},{"coverCrowd":"10","fullAddress":"龙华新区民治街道民治大道471-1号(嘉熙业广场大润发对面)","id":76,"latItude":0,"longItude":0,"name":"测试门店qjh"},{"coverCrowd":"10","fullAddress":"龙华新区民治街道民治大道471-1号(嘉熙业广场大润发对面)","id":76,"latItude":0,"longItude":0,"name":"测试门店qjh"},{"coverCrowd":"10","fullAddress":"龙华新区民治街道民治大道471-1号(嘉熙业广场大润发对面)","id":76,"latItude":0,"longItude":0,"name":"测试门店qjh"},{"coverCrowd":"10","fullAddress":"龙华新区民治街道民治大道471-1号(嘉熙业广场大润发对面)","id":76,"latItude":0,"longItude":0,"name":"测试门店qjh"}]
         */

        private int count;
        private List<ListBean> list;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * coverCrowd : 200
             * fullAddress : 湖滨东路3号
             * id : 64
             * latItude : 0.0
             * longItude : 0.0
             * name : 测试门店
             */

            private String coverCrowd;
            private String fullAddress;
            private int id;
            private double latItude;
            private double longItude;
            private String name;
            private double price;
            private String storeUrl;
            private Integer timeoutType;
            private String manFlow;

            public String getManFlow() {
                return manFlow;
            }

            public void setManFlow(String manFlow) {
                this.manFlow = manFlow;
            }

            public Integer getTimeoutType() {
                return timeoutType;
            }

            public void setTimeoutType(Integer timeoutType) {
                this.timeoutType = timeoutType;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public String getStoreUrl() {
                return storeUrl;
            }

            public void setStoreUrl(String storeUrl) {
                this.storeUrl = storeUrl;
            }

            public String getCoverCrowd() {
                return coverCrowd;
            }

            public void setCoverCrowd(String coverCrowd) {
                this.coverCrowd = coverCrowd;
            }

            public String getFullAddress() {
                return fullAddress;
            }

            public void setFullAddress(String fullAddress) {
                this.fullAddress = fullAddress;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public double getLatItude() {
                return latItude;
            }

            public void setLatItude(double latItude) {
                this.latItude = latItude;
            }

            public double getLongItude() {
                return longItude;
            }

            public void setLongItude(double longItude) {
                this.longItude = longItude;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
