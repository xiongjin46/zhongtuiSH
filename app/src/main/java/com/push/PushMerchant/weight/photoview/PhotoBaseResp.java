package com.push.PushMerchant.weight.photoview;



import com.push.PushMerchant.base.BaseBean;

import java.util.ArrayList;

/**
 * @ClassName: PhotoBaseResp
 * @Description: 缩放图片实体类
 * @author: YYL
 * <p>
 * create at 2016/11/29 17:40
 */
public class PhotoBaseResp extends BaseBean {

    private ArrayList<String> imgurl = new ArrayList<>();

    private ArrayList<Info> infos = new ArrayList<>();

    public ArrayList<String> getImgurl() {
        return imgurl;
    }

    public void setImgurl(ArrayList<String> imgurl) {
        this.imgurl = imgurl;
    }

    public ArrayList<Info> getInfos() {
        return infos;
    }

    public void setInfos(ArrayList<Info> infos) {
        this.infos = infos;
    }
}
