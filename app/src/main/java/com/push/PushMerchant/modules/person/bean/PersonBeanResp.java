package com.push.PushMerchant.modules.person.bean;

import com.push.PushMerchant.base.BaseBean;
import com.push.PushMerchant.base.BaseResp;

/**
 * Created by Administrator on 2017/8/18 0018.
 */

public class PersonBeanResp extends BaseResp {

    /**
     * object : {"address":"深圳龙华公寓","id":15,"inputDate":1502073481000,"logoUrl":"onlineD2E94F98-8E84-4267-BDCE-FE7A1DB14691.jpg","mobile":"15093847563","name":"深圳市科瑞特有限公司","personId":85,"photoUrl":"test/201707/07951d38905d4c8cace1087d10d93a73.jpg","photoUrl3":"test/201707/924f6fbd93ef491cabb3911efe397943.jpg","state":1,"trades":"互联网"}
     */

    private ObjectBean object;

    public ObjectBean getObject() {
        return object;
    }

    public void setObject(ObjectBean object) {
        this.object = object;
    }

    public static class ObjectBean extends BaseBean{
        /**
         * address : 深圳龙华公寓
         * id : 15
         * inputDate : 1502073481000
         * logoUrl : onlineD2E94F98-8E84-4267-BDCE-FE7A1DB14691.jpg
         * mobile : 15093847563
         * name : 深圳市科瑞特有限公司
         * personId : 85
         * photoUrl : test/201707/07951d38905d4c8cace1087d10d93a73.jpg
         * photoUrl3 : test/201707/924f6fbd93ef491cabb3911efe397943.jpg
         * state : 1
         * trades : 互联网
         */

        private String address;
        private int id;
        private long inputDate;
        private String logoUrl;
        private String mobile;
        private String name;
        private int personId;
        private String photoUrl;
        private String photoUrl3;
        private int state;
        private String trades;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
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

        public String getPhotoUrl() {
            return photoUrl;
        }

        public void setPhotoUrl(String photoUrl) {
            this.photoUrl = photoUrl;
        }

        public String getPhotoUrl3() {
            return photoUrl3;
        }

        public void setPhotoUrl3(String photoUrl3) {
            this.photoUrl3 = photoUrl3;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getTrades() {
            return trades;
        }

        public void setTrades(String trades) {
            this.trades = trades;
        }
    }
}
