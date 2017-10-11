package com.push.PushMerchant.modules.person.bean;

/**
 * Created by admin on 2016/12/1.
 */

public class CommentImageBean {
    private String imagePath;
    private int sort;

    public CommentImageBean(String imagePath, int sort) {
        this.imagePath = imagePath;
        this.sort = sort;
    }

    public CommentImageBean() {
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
