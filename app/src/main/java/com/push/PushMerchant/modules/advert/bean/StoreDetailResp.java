package com.push.PushMerchant.modules.advert.bean;

import com.push.PushMerchant.base.BaseBean;
import com.push.PushMerchant.base.BaseResp;

import java.util.List;

/**
 * Created by Administrator on 2017/8/8 0008.
 */

public class StoreDetailResp extends BaseResp {


    /**
     * object : {"areaMan":55.05,"coverCrowd":"550500","expenseHeartArray":["从众","求实"],"fullAddress":"横岗六约社区圣德保酒店后面","hotelRatesRIm":36106,"latItude":0,"longItude":0,"manFlow":1115,"name":"横岗天天乐","productList":[{"content":"传统推广","name":"传统推广","price":2000,"productType":0}],"radiateCrowdIdArray":["工厂","行政单位"],"scope":"紧邻主干道深惠路，塘坑地铁站与六约地铁站之间。","storeUrl":"online/201708/f1d4f2e4fbbd4cdd94734501bfc7d68d.png","timeoutType":2}
     */

    private ObjectBean object;

    public ObjectBean getObject() {
        return object;
    }

    public void setObject(ObjectBean object) {
        this.object = object;
    }

    public static class ObjectBean extends BaseBean {

        private Double areaMan;
        private String coverCrowd;
        private String fullAddress;
        private Double hotelRatesRIm;
        private Double latItude;
        private Double longItude;
        private Integer manFlow;
        private String name;
        private String scope;
        private String storeUrl;
        private Integer timeoutType;
        private List<String> expenseHeartArray;
        private List<ProductListBean> productList;
        private List<String> radiateCrowdIdArray;


        public Double getAreaMan() {
            return areaMan;
        }

        public void setAreaMan(Double areaMan) {
            this.areaMan = areaMan;
        }

        public String getCoverCrowd() {
            return coverCrowd;
        }

        public void setCoverCrowd(String coverCrowd) {
            this.coverCrowd = coverCrowd;
        }

        public String getFullAddress() {
            return fullAddress;
        }

        public void setFullAddress(String fullAddress) {
            this.fullAddress = fullAddress;
        }

        public Double getHotelRatesRIm() {
            return hotelRatesRIm;
        }

        public void setHotelRatesRIm(Double hotelRatesRIm) {
            this.hotelRatesRIm = hotelRatesRIm;
        }

        public Double getLatItude() {
            return latItude;
        }

        public void setLatItude(Double latItude) {
            this.latItude = latItude;
        }

        public Double getLongItude() {
            return longItude;
        }

        public void setLongItude(Double longItude) {
            this.longItude = longItude;
        }

        public Integer getManFlow() {
            return manFlow;
        }

        public void setManFlow(Integer manFlow) {
            this.manFlow = manFlow;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
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

        public List<ProductListBean> getProductList() {
            return productList;
        }

        public void setProductList(List<ProductListBean> productList) {
            this.productList = productList;
        }

        public List<String> getRadiateCrowdIdArray() {
            return radiateCrowdIdArray;
        }

        public void setRadiateCrowdIdArray(List<String> radiateCrowdIdArray) {
            this.radiateCrowdIdArray = radiateCrowdIdArray;
        }

        /**
         * areaMan : 55.05
         * coverCrowd : 550500
         * expenseHeartArray : ["从众","求实"]
         * fullAddress : 横岗六约社区圣德保酒店后面
         * hotelRatesRIm : 36106.0
         * latItude : 0.0
         * longItude : 0.0
         * manFlow : 1115
         * name : 横岗天天乐
         * productList : [{"content":"传统推广","name":"传统推广","price":2000,"productType":0}]
         * radiateCrowdIdArray : ["工厂","行政单位"]
         * scope : 紧邻主干道深惠路，塘坑地铁站与六约地铁站之间。
         * storeUrl : online/201708/f1d4f2e4fbbd4cdd94734501bfc7d68d.png
         * timeoutType : 2
         */

        public static class ProductListBean extends BaseBean {
            /**
             * content : 传统推广
             * name : 传统推广
             * price : 2000
             * productType : 0
             */

            private String content;
            private String name;
            private int price;
            private int productType;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public int getProductType() {
                return productType;
            }

            public void setProductType(int productType) {
                this.productType = productType;
            }
        }
    }
}
