package com.push.PushMerchant.database;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


import com.push.PushMerchant.modules.home.bean.AreasBaseResp;
import com.push.PushMerchant.modules.home.bean.CityBaseResp;
import com.push.PushMerchant.modules.home.bean.ProvinceListBaseResp;

import java.util.ArrayList;

/**
 * Created by admin on 2016/11/23.
 */

public class AddressDBUtils {
    private SQLiteDatabase db = null;
    private Cursor cursor = null;

    private ArrayList<ProvinceListBaseResp.ObjectEntity> provincelist;
    private ArrayList<ArrayList<CityBaseResp.ObjectEntity>> citylist;
    private ArrayList<ArrayList<ArrayList<AreasBaseResp.ObjectEntity>>> districtlist;
    private ArrayList<CityBaseResp.ObjectEntity> itemlist;
    private ArrayList<AreasBaseResp.ObjectEntity> disitemlist;

    /**
     * 获取省份信息
     *
     * @return
     */
    public ArrayList<ProvinceListBaseResp.ObjectEntity> getProvincelist() {
        try {
            db = CommonDatabaseHelper.getInstance().getReadableDatabase();
            provincelist = new ArrayList<>();
            cursor = db.rawQuery("select * from " + CommonDatabaseHelper.TABLE_ADDRESS_PROVINCE, null); //查询所有数据
            while (cursor.moveToNext()) {
                ProvinceListBaseResp.ObjectEntity objectEntity = new ProvinceListBaseResp.ObjectEntity();
                String provinceName = cursor.getString(cursor.getColumnIndex("provinceName"));
                String provinceCode = cursor.getString(cursor.getColumnIndex("provinceCode"));
                int id = cursor.getInt(cursor.getColumnIndex("addressId"));
                objectEntity.setProvinceName(provinceName);
                objectEntity.setProvinceCode(provinceCode);
                objectEntity.setId(id);
                provincelist.add(objectEntity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return provincelist;
    }

    /**
     * 获取城市信息
     *
     * @param provincelist
     * @return
     */
    public ArrayList<ArrayList<CityBaseResp.ObjectEntity>> getCitylist(ArrayList<ProvinceListBaseResp.ObjectEntity> provincelist) {
        try {
//            dbHelper.createDataBase();
            db = CommonDatabaseHelper.getInstance().getReadableDatabase();
            citylist = new ArrayList<>();
            for (ProvinceListBaseResp.ObjectEntity str : provincelist) {
                cursor = db.rawQuery("select * from " + CommonDatabaseHelper.TABLE_ADDRESS_CITY + " where provinceCode = '" + str.getProvinceCode() + "'", null); //查询指定省的数据
                itemlist = new ArrayList<>();
                CityBaseResp.ObjectEntity cityBean1 = new CityBaseResp.ObjectEntity();
                cityBean1.setCityName("不限");
                itemlist.add(cityBean1);
//                itemStrlist = new ArrayList<>();
                while (cursor.moveToNext()) {
//                    if (itemStrlist.contains(city)) {
////                        if (city.equals("重庆市")) {
////                            itemlist.add(city);
////                        }
//                    } else {
                    CityBaseResp.ObjectEntity cityBean = new CityBaseResp.ObjectEntity();
                    cityBean.setCityName(cursor.getString(cursor.getColumnIndex("cityName")));
                    cityBean.setCityCode(cursor.getString(cursor.getColumnIndex("cityCode")));
                    cityBean.setProvinceCode(cursor.getString(cursor.getColumnIndex("provinceCode")));
                    cityBean.setHotFlag(cursor.getInt(cursor.getColumnIndex("hotFlag")));
                    cityBean.setFirstPinyin(cursor.getString(cursor.getColumnIndex("firstPinyin")));
                    cityBean.setId(cursor.getInt(cursor.getColumnIndex("addressId")));

                    itemlist.add(cityBean);
//                    itemStrlist.add(city);
//                    }
                }
                if (itemlist.isEmpty()) {
                    CityBaseResp.ObjectEntity cityBean = new CityBaseResp.ObjectEntity();
                    itemlist.add(cityBean);
                }
                citylist.add(itemlist);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return citylist;
    }

    /**
     * 获取县区信息
     *
     * @param citylist
     * @return
     */
    public ArrayList<ArrayList<ArrayList<AreasBaseResp.ObjectEntity>>> getDistrictlist(ArrayList<ArrayList<CityBaseResp.ObjectEntity>> citylist) {
        ArrayList<ArrayList<AreasBaseResp.ObjectEntity>> arrcity = null;
//        ArrayList<ArrayList<String>> arrcitystr = null;
        try {
//            dbHelper.createDataBase();
            db = CommonDatabaseHelper.getInstance().getReadableDatabase();

            districtlist = new ArrayList<>();
//            districtStrlist = new ArrayList<>();
            for (ArrayList<CityBaseResp.ObjectEntity> arr : citylist) {
                arrcity = new ArrayList<>();
//                arrcitystr = new ArrayList<>();
                for (CityBaseResp.ObjectEntity Citystr : arr) { //  每个城市信息

                    cursor = db.rawQuery("select * from " + CommonDatabaseHelper.TABLE_ADDRESS_AREAS + " where cityCode = '" + Citystr.getCityCode() + "'", null); //查询指定市的数据
                    disitemlist = new ArrayList<>();
                    AreasBaseResp.ObjectEntity districtBean1 = new AreasBaseResp.ObjectEntity();
                    districtBean1.setAreasName("不限");
                    disitemlist.add(0, districtBean1);
//                    disitemStrlist = new ArrayList<>();
                    while (cursor.moveToNext()) {
//                        String District = cursor.getString(5);
//                        if (disitemStrlist.contains(District)) {
////                            if (District.equals("重庆市")) {
////                                disitemlist.add(District);
////                            }
//                        } else if (cursor.getString(1).equals(Citystr.getValue())) {
                        AreasBaseResp.ObjectEntity districtBean = new AreasBaseResp.ObjectEntity();
                        districtBean.setAreasName(cursor.getString(cursor.getColumnIndex("areasName")));
                        districtBean.setAreasCode(cursor.getString(cursor.getColumnIndex("areasCode")));
                        districtBean.setCityCode(cursor.getString(cursor.getColumnIndex("cityCode")));
                        districtBean.setId(cursor.getInt(cursor.getColumnIndex("addressId")));
                        disitemlist.add(districtBean);
//                            disitemStrlist.add(District);
//                        }
                    }

                    arrcity.add(disitemlist);
//                    arrcitystr.add(disitemStrlist);
                    cursor.close();

                }
                districtlist.add(arrcity);
//                districtStrlist.add(arrcitystr);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
        if (disitemlist.isEmpty()) {
            arrcity = new ArrayList<>();
            disitemlist = new ArrayList<>();
            AreasBaseResp.ObjectEntity districtBean = new AreasBaseResp.ObjectEntity();
            disitemlist.add(districtBean);
            arrcity.add(disitemlist);
            districtlist.add(arrcity);
        }
        return districtlist;
    }

//    /**
//     * 获取省市县的id
//     *
//     * @param ProvinceName
//     * @param CityName
//     * @param DistrictName
//     * @return
//     */
//    public AddressBean getAddress(String ProvinceName, String CityName, String DistrictName) {
//        AddressBean addressBean = null;
//        try {
//            db = CommonDatabaseHelper.getInstance().getReadableDatabase();
//            cursor = db.rawQuery("select * from " + CommonDatabaseHelper.TABLE_ADDRESS + " where District = '" + DistrictName + "'", null); //查询指定市的数据
//            addressBean = new AddressBean();
//            while (cursor.moveToNext()) {
//                if (cursor.getString(3).equals(CityName) && cursor.getString(1).equals(ProvinceName)) {
//                    addressBean.setProvince_id(Integer.valueOf(cursor.getString(2)));
//                    addressBean.setCity_id(Integer.valueOf(cursor.getString(4)));
//                    addressBean.setCounty_id(Integer.valueOf(cursor.getString(6)));
//                }
//            }
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//        } finally {
//            if (cursor != null)
//                cursor.close();
//            if (db != null)
//                db.close();
//        }
//        return addressBean;
//    }

}
