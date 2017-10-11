package com.push.PushMerchant.modules.map.bean;

import com.push.PushMerchant.base.BaseBean;
import com.push.PushMerchant.base.BaseResp;

import java.util.List;

/**
 * Created by Administrator on 2017/8/12 0012.
 */

public class StoreByCityResp extends BaseResp {

    private List<ObjectBean> object;

    public List<ObjectBean> getObject() {
        return object;
    }

    public void setObject(List<ObjectBean> object) {
        this.object = object;
    }

    public static class ObjectBean extends BaseBean {
        /**
         * areaMan : 99999.0
         * businessId : 2
         * channelId : 17
         * cityId : 202
         * contactMan : 彭女士
         * contactMobile : 15098322134
         * coverCrowd : 2000
         * expenseHeartId : 63
         * fullAddress : 龙华新区龙华办事处油松路38号B栋、C栋
         * hotelRatesRIm : 20000.0
         * id : 65
         * inputDate : 1500979286000
         * latItude : 22.641313
         * longItude : 114.044839
         * manFlow : 400
         * name : 坂田丰客隆
         * personId : 84
         * provinceId : 19
         * radiateCrowdId : 64
         * regionId : 1958
         * scope : 沃尔玛，大润发
         * sort : 1
         * state : 1
         * status : 0
         * storeUrl : test/201707/48c268dbfbd44ee4ad34f8ed64916111.jpg
         * timeoutType : 1
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
        private int provinceId;
        private String radiateCrowdId;
        private int regionId;
        private String scope;
        private int sort;
        private int state;
        private int status;
        private String storeUrl;
        private Integer timeoutType;
        private String channelName;
        private double price;

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getChannelName() {
            return channelName;
        }

        public void setChannelName(String channelName) {
            this.channelName = channelName;
        }

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
