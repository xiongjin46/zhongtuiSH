package com.push.PushMerchant.modules.person.bean;

import com.push.PushMerchant.base.BaseResp;

import java.util.List;

/**
 * Created by Administrator on 2017/8/4 0004.
 */

public class CheckListResp extends BaseResp {

    /**
     * object : {"count":1,"list":[{"id":1,"inputDate":1501814669000,"merchantId":15,"reason":"不清楚","state":0}]}
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
         * list : [{"id":1,"inputDate":1501814669000,"merchantId":15,"reason":"不清楚","state":0}]
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

        public static class ListBean extends BaseResp{
            /**
             * id : 1
             * inputDate : 1501814669000
             * merchantId : 15
             * reason : 不清楚
             * state : 0
             */

            private int id;
            private long inputDate;
            private int merchantId;
            private String reason;
            private int state;

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

            public String getReason() {
                return reason;
            }

            public void setReason(String reason) {
                this.reason = reason;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }
        }
    }
}
