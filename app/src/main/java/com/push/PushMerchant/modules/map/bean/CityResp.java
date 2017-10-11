package com.push.PushMerchant.modules.map.bean;

import com.push.PushMerchant.base.BaseBean;
import com.push.PushMerchant.base.BaseResp;

import java.util.List;

/**
 * Created by Administrator on 2017/8/15 0015.
 */

public class CityResp extends BaseResp {

    /**
     * object : {"count":4,"list":[{"cityCode":"110100","cityName":"北京","firstPinyin":"b","hotFlag":1,"id":1,"latItude":33.3,"longItude":22.2,"provinceCode":"110000"},{"cityCode":"120100","cityName":"天津","firstPinyin":"t","hotFlag":0,"id":3,"latItude":11.36,"longItude":25.2,"provinceCode":"120000"},{"cityCode":"130200","cityName":"唐山市","firstPinyin":"t","hotFlag":0,"id":6,"latItude":66.66,"longItude":66.6,"provinceCode":"130000"},{"cityCode":"130600","cityName":"保定市","firstPinyin":"b","hotFlag":0,"id":10,"latItude":1,"longItude":123.111111,"provinceCode":"130000"}]}
     */

    private ObjectBean object;

    public ObjectBean getObject() {
        return object;
    }

    public void setObject(ObjectBean object) {
        this.object = object;
    }

    public static class ObjectBean extends BaseBean {
        /**
         * count : 4
         * list : [{"cityCode":"110100","cityName":"北京","firstPinyin":"b","hotFlag":1,"id":1,"latItude":33.3,"longItude":22.2,"provinceCode":"110000"},{"cityCode":"120100","cityName":"天津","firstPinyin":"t","hotFlag":0,"id":3,"latItude":11.36,"longItude":25.2,"provinceCode":"120000"},{"cityCode":"130200","cityName":"唐山市","firstPinyin":"t","hotFlag":0,"id":6,"latItude":66.66,"longItude":66.6,"provinceCode":"130000"},{"cityCode":"130600","cityName":"保定市","firstPinyin":"b","hotFlag":0,"id":10,"latItude":1,"longItude":123.111111,"provinceCode":"130000"}]
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

        public static class ListBean extends BaseBean{
            /**
             * cityCode : 110100
             * cityName : 北京
             * firstPinyin : b
             * hotFlag : 1
             * id : 1
             * latItude : 33.3
             * longItude : 22.2
             * provinceCode : 110000
             */

            private String cityCode;
            private String cityName;
            private String firstPinyin;
            private int hotFlag;
            private int id;
            private double latItude;
            private double longItude;
            private String provinceCode;

            public String getCityCode() {
                return cityCode;
            }

            public void setCityCode(String cityCode) {
                this.cityCode = cityCode;
            }

            public String getCityName() {
                return cityName;
            }

            public void setCityName(String cityName) {
                this.cityName = cityName;
            }

            public String getFirstPinyin() {
                return firstPinyin;
            }

            public void setFirstPinyin(String firstPinyin) {
                this.firstPinyin = firstPinyin;
            }

            public int getHotFlag() {
                return hotFlag;
            }

            public void setHotFlag(int hotFlag) {
                this.hotFlag = hotFlag;
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

            public String getProvinceCode() {
                return provinceCode;
            }

            public void setProvinceCode(String provinceCode) {
                this.provinceCode = provinceCode;
            }
        }
    }
}
