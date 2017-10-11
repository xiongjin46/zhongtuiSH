package com.push.PushMerchant.modules.home.bean;

/**
 * @ClassName: City
 * @Description: 城市
 * @author: YYL
 * <p>
 * create at 2016/11/14 16:19
 */
public class City {
    private String name;
    private String pinyin;

    public City() {
    }

    public City(String name, String pinyin) {
        this.name = name;
        this.pinyin = pinyin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
}
