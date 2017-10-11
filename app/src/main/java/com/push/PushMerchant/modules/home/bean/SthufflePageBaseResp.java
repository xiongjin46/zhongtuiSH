package com.push.PushMerchant.modules.home.bean;


import com.push.PushMerchant.base.BaseBean;
import com.push.PushMerchant.base.BaseResp;

import java.util.List;

/**
 * @ClassName: com.jiaoao.lzb.lzbApplication.modules.home.entity
 * @Description:
 * @author: YYL
 * create at 2017/2/28 0028 下午 5:55
 */
public class SthufflePageBaseResp extends BaseResp {

    private List<ObjectBean> object;

    public List<ObjectBean> getObject() {
        return object;
    }

    public void setObject(List<ObjectBean> object) {
        this.object = object;
    }

    public static class ObjectBean extends BaseBean{
        /**
         * id : 9
         * img_url : test/201708/b2b195a38f0e473f9a169973dc52409a.JPG
         * inputDate : 1501576664000
         * remark : bb
         * sortNo : 1
         * status : 0
         */

        private int id;
        private String img_url;
        private long inputDate;
        private String remark;
        private int sortNo;
        private int status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public long getInputDate() {
            return inputDate;
        }

        public void setInputDate(long inputDate) {
            this.inputDate = inputDate;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getSortNo() {
            return sortNo;
        }

        public void setSortNo(int sortNo) {
            this.sortNo = sortNo;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
