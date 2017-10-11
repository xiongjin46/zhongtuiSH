package com.push.PushMerchant.modules.person.bean;

import com.push.PushMerchant.base.BaseResp;

/**
 * Created by Administrator on 2017/7/5 0005.
 */

public class UserProfile extends BaseResp {

    /**
     * object : {"accountBalance":0,"authToken":"7f412d0f3aca6a7f4b4474ad3984e701","id":85,"inputDate":1500980155000,"merchantId":15,"mobileNo":"15093847563","personCode":"15093847563","personId":85,"personType":1,"photoUrl":"test/201702/438f7c154d33444b9861ec2231d6360f.png","realName":"深圳骄傲科技有限公司","sex":0,"state":1,"status":0}
     */

    private ObjectEntity object;

    public ObjectEntity getObject() {
        return object;
    }

    public void setObject(ObjectEntity object) {
        this.object = object;
    }

    public static class ObjectEntity extends BaseResp {
        /**
         * accountBalance : 0
         * authToken : 7f412d0f3aca6a7f4b4474ad3984e701
         * id : 85
         * inputDate : 1500980155000
         * merchantId : 15
         * mobileNo : 15093847563
         * personCode : 15093847563
         * personId : 85
         * personType : 1
         * photoUrl : test/201702/438f7c154d33444b9861ec2231d6360f.png
         * realName : 深圳骄傲科技有限公司
         * sex : 0
         * state : 1
         * status : 0
         */

        private int accountBalance;
        private String authToken;
        private int id;
        private long inputDate;
        private int merchantId;
        private String mobileNo;
        private String personCode;
        private int personId;
        private int personType;
        private String photoUrl;
        private String realName;
        private int sex;
        private int state;
        private int status;
        private String address;
        private String mobile;
        private String logoUrl;

        public String getLogoUrl() {
            return logoUrl;
        }

        public void setLogoUrl(String logoUrl) {
            this.logoUrl = logoUrl;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTrades() {
            return trades;
        }

        public void setTrades(String trades) {
            this.trades = trades;
        }

        private String trades;

        public int getAccountBalance() {
            return accountBalance;
        }

        public void setAccountBalance(int accountBalance) {
            this.accountBalance = accountBalance;
        }

        public String getAuthToken() {
            return authToken;
        }

        public void setAuthToken(String authToken) {
            this.authToken = authToken;
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

        public String getMobileNo() {
            return mobileNo;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }

        public String getPersonCode() {
            return personCode;
        }

        public void setPersonCode(String personCode) {
            this.personCode = personCode;
        }

        public int getPersonId() {
            return personId;
        }

        public void setPersonId(int personId) {
            this.personId = personId;
        }

        public int getPersonType() {
            return personType;
        }

        public void setPersonType(int personType) {
            this.personType = personType;
        }

        public String getPhotoUrl() {
            return photoUrl;
        }

        public void setPhotoUrl(String photoUrl) {
            this.photoUrl = photoUrl;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
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
    }
}
