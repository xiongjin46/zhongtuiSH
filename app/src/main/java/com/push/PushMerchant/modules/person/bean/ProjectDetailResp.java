package com.push.PushMerchant.modules.person.bean;

import com.push.PushMerchant.base.BaseResp;

import java.util.List;

/**
 * Created by Administrator on 2017/8/8 0008.
 */

public class ProjectDetailResp extends BaseResp {

    /**
     * object : {"awardRule":"<p>每个门店做到20单<\/p>","brokerageRule":"<p>单笔佣金介绍<\/p>","costSum":26332000,"coverCrowd":"2200","endDate":1502207999000,"id":5,"inputDate":1501066982000,"merchantId":15,"productId":18,"productType":0,"startDate":1496246400000,"status":0,"storeCount":2,"storeList":[{"areaMan":3400,"businessId":1,"channelId":18,"cityId":200,"contactMan":"李","contactMobile":"12322222222","coverCrowd":"200","expenseHeartId":"61,62","fullAddress":"湖滨东路3号","hotelRatesRIm":12333,"id":64,"inputDate":1500977874000,"latItude":22.555219,"longItude":113.91171,"manFlow":12,"name":"测试门店","personId":79,"price":20,"provinceId":19,"radiateCrowdId":"64","regionId":1933,"scope":"沃尔玛","sort":0,"state":1,"status":0,"storeUrl":"test/201707/4412d08cedec42d48837fae310511b29.jpg","timeoutType":0},{"areaMan":99999,"businessId":2,"channelId":17,"cityId":202,"contactMan":"彭女士","contactMobile":"15098322134","coverCrowd":"2000","expenseHeartId":"63","fullAddress":"龙华新区龙华办事处油松路38号B栋、C栋","hotelRatesRIm":20000,"id":65,"inputDate":1500979286000,"latItude":22.641313,"longItude":114.044839,"manFlow":400,"name":"坂田丰客隆","personId":84,"price":220000,"provinceId":19,"radiateCrowdId":"64","regionId":1958,"scope":"沃尔玛，大润发","sort":1,"state":1,"status":0,"storeUrl":"test/201707/48c268dbfbd44ee4ad34f8ed64916111.jpg","timeoutType":1}],"strategy":"<p>攻略说明，。<span style=\"white-space: normal;\">。<\/span><\/p>","strategyUrl":"http://flashmedia.eastday.com/newdate/news/2016-11/shznews1125-19.mp4","taskName":"增加2000用户量","type":1}
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
         * storeList : [{"areaMan":3400,"businessId":1,"channelId":18,"cityId":200,"contactMan":"李","contactMobile":"12322222222","coverCrowd":"200","expenseHeartId":"61,62","fullAddress":"湖滨东路3号","hotelRatesRIm":12333,"id":64,"inputDate":1500977874000,"latItude":22.555219,"longItude":113.91171,"manFlow":12,"name":"测试门店","personId":79,"price":20,"provinceId":19,"radiateCrowdId":"64","regionId":1933,"scope":"沃尔玛","sort":0,"state":1,"status":0,"storeUrl":"test/201707/4412d08cedec42d48837fae310511b29.jpg","timeoutType":0},{"areaMan":99999,"businessId":2,"channelId":17,"cityId":202,"contactMan":"彭女士","contactMobile":"15098322134","coverCrowd":"2000","expenseHeartId":"63","fullAddress":"龙华新区龙华办事处油松路38号B栋、C栋","hotelRatesRIm":20000,"id":65,"inputDate":1500979286000,"latItude":22.641313,"longItude":114.044839,"manFlow":400,"name":"坂田丰客隆","personId":84,"price":220000,"provinceId":19,"radiateCrowdId":"64","regionId":1958,"scope":"沃尔玛，大润发","sort":1,"state":1,"status":0,"storeUrl":"test/201707/48c268dbfbd44ee4ad34f8ed64916111.jpg","timeoutType":1}]
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
        private List<StoreListBean> storeList;

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

        public List<StoreListBean> getStoreList() {
            return storeList;
        }

        public void setStoreList(List<StoreListBean> storeList) {
            this.storeList = storeList;
        }

        public static class StoreListBean extends BaseResp {
            /**
             * areaMan : 3400.0
             * businessId : 1
             * channelId : 18
             * cityId : 200
             * contactMan : 李
             * contactMobile : 12322222222
             * coverCrowd : 200
             * expenseHeartId : 61,62
             * fullAddress : 湖滨东路3号
             * hotelRatesRIm : 12333.0
             * id : 64
             * inputDate : 1500977874000
             * latItude : 22.555219
             * longItude : 113.91171
             * manFlow : 12
             * name : 测试门店
             * personId : 79
             * price : 20
             * provinceId : 19
             * radiateCrowdId : 64
             * regionId : 1933
             * scope : 沃尔玛
             * sort : 0
             * state : 1
             * status : 0
             * storeUrl : test/201707/4412d08cedec42d48837fae310511b29.jpg
             * timeoutType : 0
             */

            private double areaMan;
            private int businessId;
            private int channelId;
            private int cityId;
            private String contactMan;
            private String contactMobile;
            private String coverCrowd;
            private String expenseHeartId;
            private String fullAddress;
            private double hotelRatesRIm;
            private int id;
            private long inputDate;
            private double latItude;
            private double longItude;
            private int manFlow;
            private String name;
            private int personId;
            private double price;
            private int provinceId;
            private String radiateCrowdId;
            private int regionId;
            private String scope;
            private int sort;
            private int state;
            private int status;
            private String storeUrl;
            private Integer timeoutType;

            public double getAreaMan() {
                return areaMan;
            }

            public void setAreaMan(double areaMan) {
                this.areaMan = areaMan;
            }

            public int getBusinessId() {
                return businessId;
            }

            public void setBusinessId(int businessId) {
                this.businessId = businessId;
            }

            public int getChannelId() {
                return channelId;
            }

            public void setChannelId(int channelId) {
                this.channelId = channelId;
            }

            public int getCityId() {
                return cityId;
            }

            public void setCityId(int cityId) {
                this.cityId = cityId;
            }

            public String getContactMan() {
                return contactMan;
            }

            public void setContactMan(String contactMan) {
                this.contactMan = contactMan;
            }

            public String getContactMobile() {
                return contactMobile;
            }

            public void setContactMobile(String contactMobile) {
                this.contactMobile = contactMobile;
            }

            public String getCoverCrowd() {
                return coverCrowd;
            }

            public void setCoverCrowd(String coverCrowd) {
                this.coverCrowd = coverCrowd;
            }

            public String getExpenseHeartId() {
                return expenseHeartId;
            }

            public void setExpenseHeartId(String expenseHeartId) {
                this.expenseHeartId = expenseHeartId;
            }

            public String getFullAddress() {
                return fullAddress;
            }

            public void setFullAddress(String fullAddress) {
                this.fullAddress = fullAddress;
            }

            public double getHotelRatesRIm() {
                return hotelRatesRIm;
            }

            public void setHotelRatesRIm(double hotelRatesRIm) {
                this.hotelRatesRIm = hotelRatesRIm;
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

            public int getManFlow() {
                return manFlow;
            }

            public void setManFlow(int manFlow) {
                this.manFlow = manFlow;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getPersonId() {
                return personId;
            }

            public void setPersonId(int personId) {
                this.personId = personId;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public int getProvinceId() {
                return provinceId;
            }

            public void setProvinceId(int provinceId) {
                this.provinceId = provinceId;
            }

            public String getRadiateCrowdId() {
                return radiateCrowdId;
            }

            public void setRadiateCrowdId(String radiateCrowdId) {
                this.radiateCrowdId = radiateCrowdId;
            }

            public int getRegionId() {
                return regionId;
            }

            public void setRegionId(int regionId) {
                this.regionId = regionId;
            }

            public String getScope() {
                return scope;
            }

            public void setScope(String scope) {
                this.scope = scope;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getStoreUrl() {
                return storeUrl;
            }

            public void setStoreUrl(String storeUrl) {
                this.storeUrl = storeUrl;
            }

            public Integer getTimeoutType() {
                return timeoutType;
            }

            public void setTimeoutType(Integer timeoutType) {
                this.timeoutType = timeoutType;
            }
        }
    }
}
