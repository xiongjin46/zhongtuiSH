package com.push.PushMerchant.network.impl;


/**
 * @ClassName: IServerEngine
 * @Description:
 * @author: YYL
 * <p>
 * create at 2017/1/10 10:58
 */
public interface IServerEngine {

    String INITVERSION = "interfaceVersion";

    /**
     * 查询门店信息
     *
     * @param beginDate  起始时间
     * @param beginMoney 起始价格
     * @param cityId     城市id
     * @param endDate    结束时间
     * @param endMoney   结束价格
     * @param productId  产品id
     * @param provinceId 省份id
     * @param regionId   区域id
     * @param name       门店名称
     * @param page
     */

    void upStoreByMerchart(String beginDate, String endDate, int beginMoney, int endMoney,
                           int provinceId, int cityId, int regionId, int productId, int page,String name);

    /**
     * 获取轮播图
     */
    void getSthuffleList();

    /**
     * 门店下的产品分页查询
     * id 门店id
     */
    void getStoreDetail(int id);
    /**
     * 根据门店ID查询门店投放信息
     * id     门店id
     * taskId 任务ID
     */
    void getMerchantStoreById(int id,int taskId);

    /**
     * 获取信息
     */
    void init(String enumTypeCode);

    /**
     * 查询所有可用的产品列表
     */
    void getProductList();

    /**
     * 根据城市id查询商圈信息
     * cityName 城市名称
     */
    void getBusinessAreaByCityId(String cityName);

    /**
     * 根据城市名称查询所有的门店信息
     * cityName 城市名称
     */
    void getStoreInfoByCityName(String  cityName);

    /**
     * 根据商圈id查询商圈下的所有门店信息
     * businessId 商圈ID
     */
    void getStoreByBusinessAreaId(int  businessId);

    /**
     * 查询有经纬度的城市
     * page
     */
    void getCityPage(int page);

    /**
     * 保存预约信息
     * name  姓名
     * phone 电话
     */
    void saveAppointmentInfo(String name,String phone);

}
