package com.push.PushMerchant.modules.home.bean;


import com.push.PushMerchant.base.BaseBean;
import com.push.PushMerchant.base.BaseResp;

import java.util.List;

/**
 * Created by admin on 2016/12/22.
 */

public class ProvinceListBaseResp extends BaseResp {


    /**
     * object : [{"id":1,"provinceCode":"110000","provinceName":"北京市"},{"id":2,"provinceCode":"120000","provinceName":"天津市"},{"id":3,"provinceCode":"130000","provinceName":"河北省"},{"id":4,"provinceCode":"140000","provinceName":"山西省"},{"id":5,"provinceCode":"150000","provinceName":"内蒙古自治区"},{"id":6,"provinceCode":"210000","provinceName":"辽宁省"},{"id":7,"provinceCode":"220000","provinceName":"吉林省"},{"id":8,"provinceCode":"230000","provinceName":"黑龙江省"},{"id":9,"provinceCode":"310000","provinceName":"上海市"},{"id":10,"provinceCode":"320000","provinceName":"江苏省"},{"id":11,"provinceCode":"330000","provinceName":"浙江省"},{"id":12,"provinceCode":"340000","provinceName":"安徽省"},{"id":13,"provinceCode":"350000","provinceName":"福建省"},{"id":14,"provinceCode":"360000","provinceName":"江西省"},{"id":15,"provinceCode":"370000","provinceName":"山东省"},{"id":16,"provinceCode":"410000","provinceName":"河南省"},{"id":17,"provinceCode":"420000","provinceName":"湖北省"},{"id":18,"provinceCode":"430000","provinceName":"湖南省"},{"id":19,"provinceCode":"440000","provinceName":"广东省"},{"id":20,"provinceCode":"450000","provinceName":"广西壮族自治区"},{"id":21,"provinceCode":"460000","provinceName":"海南省"},{"id":22,"provinceCode":"500000","provinceName":"重庆市"},{"id":23,"provinceCode":"510000","provinceName":"四川省"},{"id":24,"provinceCode":"520000","provinceName":"贵州省"},{"id":25,"provinceCode":"530000","provinceName":"云南省"},{"id":26,"provinceCode":"540000","provinceName":"西藏自治区"},{"id":27,"provinceCode":"610000","provinceName":"陕西省"},{"id":28,"provinceCode":"620000","provinceName":"甘肃省"},{"id":29,"provinceCode":"630000","provinceName":"青海省"},{"id":30,"provinceCode":"640000","provinceName":"宁夏回族自治区"},{"id":31,"provinceCode":"650000","provinceName":"新疆维吾尔自治区"},{"id":32,"provinceCode":"710000","provinceName":"台湾省"},{"id":33,"provinceCode":"810000","provinceName":"香港特别行政区"},{"id":34,"provinceCode":"820000","provinceName":"澳门特别行政区"}]
     */

    private List<ObjectEntity> object;

    public void setObject(List<ObjectEntity> object) {
        this.object = object;
    }

    public List<ObjectEntity> getObject() {
        return object;
    }

    public static class ObjectEntity extends BaseBean {
        /**
         * id : 1
         * provinceCode : 110000
         * provinceName : 北京市
         */

        private int id;
        private String provinceCode;
        private String provinceName;

        public void setId(int id) {
            this.id = id;
        }

        public void setProvinceCode(String provinceCode) {
            this.provinceCode = provinceCode;
        }

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public int getId() {
            return id;
        }

        public String getProvinceCode() {
            return provinceCode;
        }

        public String getProvinceName() {
            return provinceName;
        }
    }
}
