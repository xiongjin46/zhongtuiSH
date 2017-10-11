package com.push.PushMerchant.network.impl;


/**
 * Created by admin on 2017/1/10.
 */

public interface IUserEngine {

    /**
     * 登录
     *
     * @param personCode     手机号码
     * @param personPassword 密码
     */
    void login(String personCode, String personPassword);

    /**
     * 获取当前任务
     *
     * @param id 商户id
     */
    void getCurrentTaskInfo(int id, int page);

    /**
     * 获取历史任务
     *
     * @param id 商户id
     */
    void getHistoryTaskInfo(int id, int page);

    /**
     * 查询审核历史记录
     */
    void getMerchantCheckPage(int page);

    /**
     * 上传审核资料
     *
     * @param photoUrl  商户认证正面图片
     * @param photoUrl3 商户认证背面图片
     */
    void SaveMerchantInfo(String photoUrl, String photoUrl3);

    /**
     * 退出登录
     */
    void logout();

    /**
     * 修改用户信息
     *
     * @param nickName  昵称
     * @param photoUrl  头像
     * @param photoUrl2 背景图片
     * @param realName  真实姓名
     */
    void updataUser(String nickName, String photoUrl, String photoUrl2, String realName);

    /**
     * 刷新用户session
     */
    void updateSession();

    /**
     * 修改商户基本信息
     * address 地址
     * logoUrl 商户Logo
     * name 商户名称
     * trades 行业
     */
    void updateMerchant(String address, String logoUrl, String name, String trades);

    /**
     * 根据任务ID查询任务信息和任务门店信息
     * id  任务id
     */
    void getTaskStoreByTaskId(int id);

    /**
     * 获取省集合
     */
    void getRrovinceList();

    /**
     * 获取所有市集合
     * provinceCode 省份代码
     */
    void getCityList(String provinceCode);


    /**
     * 获取所有县集合
     * cityCode 城市代码
     */
    void getAreasList(String cityCode);

    /**
     * 忘记密码
     *
     * @param personCode
     * @param captcha
     * @param newPassword
     */
    void getForgitPsd(String personCode, String captcha, String newPassword);

    /**
     * 注册获取验证码
     *
     * @param personCode
     * @param type       0注册 1免密码登录 2忘记密码
     */
    void getCapcha(String personCode, int type);
}
