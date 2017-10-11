package com.push.PushMerchant.modules.person.bean;

import com.push.PushMerchant.base.BaseResp;

import java.util.List;

/**
 * Created by Administrator on 2017/8/10 0010.
 */

public class MerchantStoreResp extends BaseResp {

    /**
     * object : {"areaMan":3400,"businessId":1,"channelId":18,"cityId":200,"contactMan":"李","contactMobile":"12322222222","content":"测试","coverCrowd":"200","endDate":1502207999000,"expenseHeartArray":["从众","求实"],"expenseHeartId":"61,62","fullAddress":"湖滨东路3号","hotelRatesRIm":12333,"id":64,"inputDate":1500977874000,"latItude":22.555219,"longItude":113.91171,"manFlow":12,"name":"测试门店","personId":79,"price":20,"productType":0,"provinceId":19,"radiateCrowdId":"64","radiateCrowdIdArray":["民房"],"regionId":1933,"scope":"沃尔玛","sort":0,"startDate":1496246400000,"state":1,"status":0,"storeMaterialList":[{"count":40,"id":19,"inputDate":1500981388000,"introduce":"一些发布给用户的小礼品<p><\/p>","materialId":6,"materialUrl":"test/201707/da32b8c9024648428e665ba02454737c.jpg","name":"小饰品","residueCount":10,"storeId":64,"taskId":5}],"storeUrl":"test/201707/4412d08cedec42d48837fae310511b29.jpg","timeoutType":0}
     */

    private ObjectBean object;

    public ObjectBean getObject() {
        return object;
    }

    public void setObject(ObjectBean object) {
        this.object = object;
    }

    public static class ObjectBean extends BaseResp{
        /**
         * areaMan : 3400.0
         * businessId : 1
         * channelId : 18
         * cityId : 200
         * contactMan : 李
         * contactMobile : 12322222222
         * content : 测试
         * coverCrowd : 200
         * endDate : 1502207999000
         * expenseHeartArray : ["从众","求实"]
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
         * productType : 0
         * provinceId : 19
         * radiateCrowdId : 64
         * radiateCrowdIdArray : ["民房"]
         * regionId : 1933
         * scope : 沃尔玛
         * sort : 0
         * startDate : 1496246400000
         * state : 1
         * status : 0
         * storeMaterialList : [{"count":40,"id":19,"inputDate":1500981388000,"introduce":"一些发布给用户的小礼品<p><\/p>","materialId":6,"materialUrl":"test/201707/da32b8c9024648428e665ba02454737c.jpg","name":"小饰品","residueCount":10,"storeId":64,"taskId":5}]
         * storeUrl : test/201707/4412d08cedec42d48837fae310511b29.jpg
         * timeoutType : 0
         */

        private int areaMan;
        private int businessId;
        private int channelId;
        private int cityId;
        private String contactMan;
        private String contactMobile;
        private String content;
        private String coverCrowd;
        private long endDate;
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
        private int productType;
        private int provinceId;
        private String radiateCrowdId;
        private int regionId;
        private String scope;
        private int sort;
        private long startDate;
        private int state;
        private int status;
        private String storeUrl;
        private Integer timeoutType;
        private List<String> expenseHeartArray;
        private List<String> radiateCrowdIdArray;
        private List<StoreMaterialListBean> storeMaterialList;

        public int getAreaMan() {
            return areaMan;
        }

        public void setAreaMan(int areaMan) {
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public int getProductType() {
            return productType;
        }

        public void setProductType(int productType) {
            this.productType = productType;
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

        public long getStartDate() {
            return startDate;
        }

        public void setStartDate(long startDate) {
            this.startDate = startDate;
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

        public List<String> getExpenseHeartArray() {
            return expenseHeartArray;
        }

        public void setExpenseHeartArray(List<String> expenseHeartArray) {
            this.expenseHeartArray = expenseHeartArray;
        }

        public List<String> getRadiateCrowdIdArray() {
            return radiateCrowdIdArray;
        }

        public void setRadiateCrowdIdArray(List<String> radiateCrowdIdArray) {
            this.radiateCrowdIdArray = radiateCrowdIdArray;
        }

        public List<StoreMaterialListBean> getStoreMaterialList() {
            return storeMaterialList;
        }

        public void setStoreMaterialList(List<StoreMaterialListBean> storeMaterialList) {
            this.storeMaterialList = storeMaterialList;
        }

        public static class StoreMaterialListBean extends BaseResp {
            /**
             * count : 40
             * id : 19
             * inputDate : 1500981388000
             * introduce : 一些发布给用户的小礼品<p></p>
             * materialId : 6
             * materialUrl : test/201707/da32b8c9024648428e665ba02454737c.jpg
             * name : 小饰品
             * residueCount : 10
             * storeId : 64
             * taskId : 5
             */

            private int count;
            private int id;
            private long inputDate;
            private String introduce;
            private int materialId;
            private String materialUrl;
            private String name;
            private int residueCount;
            private int storeId;
            private int taskId;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
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

            public String getIntroduce() {
                return introduce;
            }

            public void setIntroduce(String introduce) {
                this.introduce = introduce;
            }

            public int getMaterialId() {
                return materialId;
            }

            public void setMaterialId(int materialId) {
                this.materialId = materialId;
            }

            public String getMaterialUrl() {
                return materialUrl;
            }

            public void setMaterialUrl(String materialUrl) {
                this.materialUrl = materialUrl;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getResidueCount() {
                return residueCount;
            }

            public void setResidueCount(int residueCount) {
                this.residueCount = residueCount;
            }

            public int getStoreId() {
                return storeId;
            }

            public void setStoreId(int storeId) {
                this.storeId = storeId;
            }

            public int getTaskId() {
                return taskId;
            }

            public void setTaskId(int taskId) {
                this.taskId = taskId;
            }
        }
    }
}
