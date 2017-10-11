package com.push.PushMerchant.modules.home.bean;


import com.push.PushMerchant.base.BaseBean;
import com.push.PushMerchant.base.BaseResp;

import java.util.List;

/**
 * @ClassName: com.jiaoao.lzb.lzbApplication.modules.home.entity
 * @Description:
 * @author: YYL
 * create at 2017/3/21 0021 上午 11:35
 */
public class InitBaseResp extends BaseResp {

    private List<ObjectBean> object;

    public List<ObjectBean> getObject() {
        return object;
    }

    public void setObject(List<ObjectBean> object) {
        this.object = object;
    }

    public static class ObjectBean extends BaseBean {
        /**
         * enumTypeCode : interfaceVersion
         * enumTypeId : 6
         * enumValue : VIP展示
         * enumValue1 : 0
         * enumValue2 : 0
         * id : 54
         * remark :
         * sortNo : 1
         * status : 0
         */

        private String enumTypeCode;
        private int enumTypeId;
        private String enumValue;
        private int enumValue1;
        private int enumValue2;
        private int id;
        private String remark;
        private int sortNo;
        private int status;

        public String getEnumTypeCode() {
            return enumTypeCode;
        }

        public void setEnumTypeCode(String enumTypeCode) {
            this.enumTypeCode = enumTypeCode;
        }

        public int getEnumTypeId() {
            return enumTypeId;
        }

        public void setEnumTypeId(int enumTypeId) {
            this.enumTypeId = enumTypeId;
        }

        public String getEnumValue() {
            return enumValue;
        }

        public void setEnumValue(String enumValue) {
            this.enumValue = enumValue;
        }

        public int getEnumValue1() {
            return enumValue1;
        }

        public void setEnumValue1(int enumValue1) {
            this.enumValue1 = enumValue1;
        }

        public int getEnumValue2() {
            return enumValue2;
        }

        public void setEnumValue2(int enumValue2) {
            this.enumValue2 = enumValue2;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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
