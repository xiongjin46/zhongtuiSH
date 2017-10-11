package com.push.PushMerchant.modules.home.bean;


import com.push.PushMerchant.base.BaseBean;

/**
 * @ClassName: com.jiaoao.lzb.lzbApplication.modules.home.entity
 * @Description:
 * @author: YYL
 * create at 2017/3/21 0021 上午 10:48
 */
public class AppVersionBean extends BaseBean {

    private boolean showVip;    //是否显示vip
    private int addressVersion; //地址版本
    private String appVersion;  //app版本
    private String downloadUrl; //下载地址
    private String appDescription;  //更新描述
    private int isForce;    //是否强制更新 1强制 0非
    private String splash;  //闪屏页图片
    private int versionCode;    //版本号
    private int shVersionCode; //商户版版本号
    private String shAppVersion;  //商户版app版本
    private String shDownloadUrl;   //商户版下载地址
    private String shAppDescription;    //商户版更新描述


    public AppVersionBean(boolean showVip, int addressVersion, String appVersion,
                          String downloadUrl, String appDescription,
                          int isForce, String splash, int versionCode,
                          int shVersionCode, String shAppVersion, String shDownloadUrl,
                          String shAppDescription) {
        this.showVip = showVip;
        this.addressVersion = addressVersion;
        this.appVersion = appVersion;
        this.downloadUrl = downloadUrl;
        this.appDescription = appDescription;
        this.isForce = isForce;
        this.splash = splash;
        this.versionCode = versionCode;
        this.shVersionCode = shVersionCode;
        this.shAppVersion = shAppVersion;
        this.shDownloadUrl = shDownloadUrl;
        this.shAppDescription = shAppDescription;
    }

    public int getShVersionCode() {
        return shVersionCode;
    }

    public void setShVersionCode(int shVersionCode) {
        this.shVersionCode = shVersionCode;
    }

    public String getShAppVersion() {
        return shAppVersion;
    }

    public void setShAppVersion(String shAppVersion) {
        this.shAppVersion = shAppVersion;
    }

    public String getShDownloadUrl() {
        return shDownloadUrl;
    }

    public void setShDownloadUrl(String shDownloadUrl) {
        this.shDownloadUrl = shDownloadUrl;
    }

    public String getShAppDescription() {
        return shAppDescription;
    }

    public void setShAppDescription(String shAppDescription) {
        this.shAppDescription = shAppDescription;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getSplash() {
        return splash;
    }

    public void setSplash(String splash) {
        this.splash = splash;
    }

    public boolean isShowVip() {
        return showVip;
    }

    public void setShowVip(boolean showVip) {
        this.showVip = showVip;
    }

    public int getAddressVersion() {
        return addressVersion;
    }

    public void setAddressVersion(int addressVersion) {
        this.addressVersion = addressVersion;
    }

    public int getIsForce() {
        return isForce;
    }

    public void setIsForce(int isForce) {
        this.isForce = isForce;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getAppDescription() {
        return appDescription;
    }

    public void setAppDescription(String appDescription) {
        this.appDescription = appDescription;
    }

}
