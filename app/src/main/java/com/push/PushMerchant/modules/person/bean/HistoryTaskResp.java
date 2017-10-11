package com.push.PushMerchant.modules.person.bean;

import com.push.PushMerchant.base.BaseResp;

import java.util.List;

/**
 * Created by Administrator on 2017/8/3 0003.
 */

public class HistoryTaskResp extends BaseResp {

    /**
     * object : {"count":1,"list":[{"awardRule":"<p>每个门店做到20单<\/p>","brokerageRule":"<p>单笔佣金介绍<\/p>","costSum":26332000,"coverCrowd":"2200","endDate":1502207999000,"id":5,"inputDate":1501066982000,"merchantId":15,"productId":18,"productType":0,"startDate":1496246400000,"status":0,"storeCount":2,"strategy":"<p>攻略说明，。<span style=\"white-space: normal;\">。<\/span><\/p>","strategyUrl":"http://flashmedia.eastday.com/newdate/news/2016-11/shznews1125-19.mp4","taskName":"增加2000用户量","type":1}]}
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
         * count : 1
         * list : [{"awardRule":"<p>每个门店做到20单<\/p>","brokerageRule":"<p>单笔佣金介绍<\/p>","costSum":26332000,"coverCrowd":"2200","endDate":1502207999000,"id":5,"inputDate":1501066982000,"merchantId":15,"productId":18,"productType":0,"startDate":1496246400000,"status":0,"storeCount":2,"strategy":"<p>攻略说明，。<span style=\"white-space: normal;\">。<\/span><\/p>","strategyUrl":"http://flashmedia.eastday.com/newdate/news/2016-11/shznews1125-19.mp4","taskName":"增加2000用户量","type":1}]
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

        public static class ListBean extends BaseResp {
            /**
             * awardRule : <p>每个门店做到20单</p>
             * brokerageRule : <p>单笔佣金介绍</p>
             * costSum : 26332000
             * coverCrowd : 2200
             * endDate : 1502207999000
             * id : 5
             * inputDate : 1501066982000
             * merchantId : 15
             * productId : 18
             * productType : 0
             * startDate : 1496246400000
             * status : 0
             * storeCount : 2
             * strategy : <p>攻略说明，。<span style="white-space: normal;">。</span></p>
             * strategyUrl : http://flashmedia.eastday.com/newdate/news/2016-11/shznews1125-19.mp4
             * taskName : 增加2000用户量
             * type : 1
             */

            private String awardRule;
            private String brokerageRule;
            private double costSum;
            private String coverCrowd;
            private long endDate;
            private int id;
            private long inputDate;
            private int merchantId;
            private int productId;
            private int productType;
            private long startDate;
            private int status;
            private int storeCount;
            private String strategy;
            private String strategyUrl;
            private String taskName;
            private int type;

            public String getAwardRule() {
                return awardRule;
            }

            public void setAwardRule(String awardRule) {
                this.awardRule = awardRule;
            }

            public String getBrokerageRule() {
                return brokerageRule;
            }

            public void setBrokerageRule(String brokerageRule) {
                this.brokerageRule = brokerageRule;
            }

            public double getCostSum() {
                return costSum;
            }

            public void setCostSum(double costSum) {
                this.costSum = costSum;
            }

            public String getCoverCrowd() {
                return coverCrowd;
            }

            public void setCoverCrowd(String coverCrowd) {
                this.coverCrowd = coverCrowd;
            }

            public long getEndDate() {
                return endDate;
            }

            public void setEndDate(long endDate) {
                this.endDate = endDate;
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

            public int getMerchantId() {
                return merchantId;
            }

            public void setMerchantId(int merchantId) {
                this.merchantId = merchantId;
            }

            public int getProductId() {
                return productId;
            }

            public void setProductId(int productId) {
                this.productId = productId;
            }

            public int getProductType() {
                return productType;
            }

            public void setProductType(int productType) {
                this.productType = productType;
            }

            public long getStartDate() {
                return startDate;
            }

            public void setStartDate(long startDate) {
                this.startDate = startDate;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getStoreCount() {
                return storeCount;
            }

            public void setStoreCount(int storeCount) {
                this.storeCount = storeCount;
            }

            public String getStrategy() {
                return strategy;
            }

            public void setStrategy(String strategy) {
                this.strategy = strategy;
            }

            public String getStrategyUrl() {
                return strategyUrl;
            }

            public void setStrategyUrl(String strategyUrl) {
                this.strategyUrl = strategyUrl;
            }

            public String getTaskName() {
                return taskName;
            }

            public void setTaskName(String taskName) {
                this.taskName = taskName;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}
