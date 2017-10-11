package com.push.PushMerchant.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;


import com.push.PushMerchant.base.PushApplicationContext;
import com.push.PushMerchant.modules.home.bean.AreasBaseResp;
import com.push.PushMerchant.modules.home.bean.CityBaseResp;
import com.push.PushMerchant.modules.home.bean.ProvinceListBaseResp;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: CommonDatabaseHelper
 * @Description: 数据库缓存
 * @author: YYL
 * <p>
 * create at 2016/11/16 16:32
 */
public class CommonDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "base.db";
    //活动
    public static final String TABLE_HOT_EVENT = "event";
    //部落
    public static final String TABLE_HOT_TRIBE = "tribe";
    private static final int DATABASE_VERSION = 4;

    //用户信息表
    private static final String TABLE_NAME_USERINFO = "userinfo";

    //地址信息表
    public static final String TABLE_ADDRESS_PROVINCE = "address_province";
    public static final String TABLE_ADDRESS_CITY = "address_city";
    public static final String TABLE_ADDRESS_AREAS = "address_areas";

    private static CommonDatabaseHelper sInstance;
    private ArrayList<CityBaseResp.ObjectEntity> hotCitys;

    public static synchronized CommonDatabaseHelper getInstance() {
        if (sInstance == null) {
            sInstance = new CommonDatabaseHelper(PushApplicationContext.context);
        }
        return sInstance;
    }

    public CommonDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建融云用户表，保存用户信息数据
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME_USERINFO + "(" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'personCode' TEXT NOT NULL UNIQUE ," + // 1: userid
                "'nickName' TEXT ," + // 2: username
                "'nickNameRemark' TEXT ," + // 2: username
                "'photoUrl' TEXT ," +
                " 'environment' TEXT );"); // 3: portrait
//                "'STATUS' TEXT NOT NULL );"); // 4: status


        //搜索活动历史
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_HOT_EVENT + "( ConfirID INTEGER PRIMARY KEY AUTOINCREMENT,Name nvarchar(50),Type nvarchar(50),Time nvarchar(50))");
        //搜索部落历史
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_HOT_TRIBE + "( ConfirID INTEGER PRIMARY KEY AUTOINCREMENT,Name nvarchar(50),Type nvarchar(50),Time nvarchar(50))");

        //地址表
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_ADDRESS_PROVINCE + "(id INTEGER PRIMARY KEY AUTOINCREMENT,provinceName nvarchar(50),provinceCode nvarchar(50),addressId INTEGER )");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_ADDRESS_CITY + "(id INTEGER PRIMARY KEY AUTOINCREMENT,cityName nvarchar(50),cityCode nvarchar(50),provinceCode nvarchar(50),hotFlag INTEGER,firstPinyin nvarchar(50),addressId INTEGER )");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_ADDRESS_AREAS + "(id INTEGER PRIMARY KEY AUTOINCREMENT,areasName nvarchar(50),areasCode nvarchar(50),cityCode nvarchar(50),addressId INTEGER )");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_USERINFO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOT_EVENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOT_TRIBE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADDRESS_PROVINCE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADDRESS_CITY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADDRESS_AREAS);
        onCreate(db);
    }

    /**
     * 更新省地址数据库
     *
     * @param rsp
     */
    public void updataAddressProvince(ProvinceListBaseResp rsp) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            List<ProvinceListBaseResp.ObjectEntity> lists = rsp.getObject();
            db.beginTransaction();
            for (ProvinceListBaseResp.ObjectEntity bean : lists) {
                SQLiteStatement province = db.compileStatement("insert into " + TABLE_ADDRESS_PROVINCE + " values(null,?,?,?)");
                province.bindString(1, bean.getProvinceName());
                province.bindString(2, bean.getProvinceCode());
                province.bindLong(3, bean.getId());
                province.executeInsert();
//                for (CityBean citybean : bean.getChildren()) {
//                    for (DistrictBean disbean : citybean.getChildren()) {
//                        SQLiteStatement hisCity = db.compileStatement("insert into " + TABLE_ADDRESS + " values(null,?,?,?,?,?,?)");
//                        hisCity.bindString(1, bean.getText());
//                        hisCity.bindString(2, bean.getValue());
//                        hisCity.bindString(3, citybean.getText());
//                        hisCity.bindString(4, citybean.getValue());
//                        hisCity.bindString(5, disbean.getText());
//                        hisCity.bindString(6, disbean.getValue());
//                        hisCity.executeInsert();
//                        LogUtil.enableLog();
//                        LogUtil.e("onGetRegionSuccess",disbean.getText());
//                    }
//                }
            }
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (db.inTransaction()) {
                db.endTransaction();
                db.close();
            }
        }
    }

    /**
     * 更新市地址
     *
     * @param rsp
     */
    public void updataAddressCity(CityBaseResp rsp) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            List<CityBaseResp.ObjectEntity> lists = rsp.getObject();
            db.beginTransaction();
            for (CityBaseResp.ObjectEntity bean : lists) {
                SQLiteStatement province = db.compileStatement("insert into " + TABLE_ADDRESS_CITY + " values(null,?,?,?,?,?,?)");
                province.bindString(1, bean.getCityName());
                province.bindString(2, bean.getCityCode());
                province.bindString(3, bean.getProvinceCode());
                province.bindLong(4, bean.getHotFlag());
                province.bindLong(6, bean.getId());    //保存id
//                province.bindString(5, PinyinUtils.getPinyin(PushApplicationContext.context, bean.getCityName()));
                province.executeInsert();
            }
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (db.inTransaction()) {
                db.endTransaction();
                db.close();
            }
        }
    }

    /**
     * 更新区县地址
     *
     * @param rsp
     */
    public void updataAddressAreas(AreasBaseResp rsp) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            List<AreasBaseResp.ObjectEntity> lists = rsp.getObject();
            db.beginTransaction();
            for (AreasBaseResp.ObjectEntity bean : lists) {
                SQLiteStatement province = db.compileStatement("insert into " + TABLE_ADDRESS_AREAS + " values(null,?,?,?,?)");
                province.bindString(1, bean.getAreasName());
                province.bindString(2, bean.getAreasCode());
                province.bindString(3, bean.getCityCode());
                province.bindLong(4, bean.getId());
                province.executeInsert();
            }
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (db.inTransaction()) {
                db.endTransaction();
                db.close();
            }
        }
    }

    /**
     * 删除指定表
     *
     * @param tableName
     */
    public void delTableName(String tableName) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();
            db.delete(tableName, null, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db.inTransaction()) {
                db.endTransaction();
                db.close();
            }
        }

    }

    /**
     * 根据城市名称获取区县信息
     *
     * @param city 城市
     */
    public ArrayList<AreasBaseResp.ObjectEntity> getAddressAreas(String city) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        Cursor cursor1 = null;
        Cursor cursor2 = null;
        ArrayList<AreasBaseResp.ObjectEntity> lists = new ArrayList<>();
//        cursor = db.rawQuery(" select * from " + TABLE_NAME_USERINFO + "  where personCode = ? and  environment = ?", new String[]{personCode, Global.isRelease() ? "1" : "2"});
        try {
            db.beginTransaction();
//            cursor = db.rawQuery(" select * from " + TABLE_ADDRESS_PROVINCE + " where provinceName = ?", new String[]{province});
//            while (cursor.moveToNext()) {
//            String provinceCode = cursor.getString(cursor.getColumnIndex("provinceCode"));
            cursor1 = db.rawQuery(" select * from " + TABLE_ADDRESS_CITY + " where cityName = ?", new String[]{city});
            while (cursor1.moveToNext()) {
                String cityCode = cursor1.getString(cursor1.getColumnIndex("cityCode"));
                cursor2 = db.rawQuery(" select * from " + TABLE_ADDRESS_AREAS + " where cityCode = ?", new String[]{cityCode});
                while (cursor2.moveToNext()) {
                    AreasBaseResp.ObjectEntity objectEntity = new AreasBaseResp.ObjectEntity();
                    objectEntity.setAreasName(cursor2.getString(cursor2.getColumnIndex("areasName")));
                    objectEntity.setAreasCode(cursor2.getString(cursor2.getColumnIndex("areasCode")));
                    objectEntity.setCityCode(cursor2.getString(cursor2.getColumnIndex("cityCode")));
                    objectEntity.setId(cursor2.getInt(cursor2.getColumnIndex("addressId")));    //获取是新增获取id
                    lists.add(objectEntity);
                }
            }
//            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (cursor1 != null) {
                cursor1.close();
            }
            if (cursor2 != null) {
                cursor2.close();
            }
            if (db.inTransaction()) {
                db.endTransaction();
                db.close();
            }
        }
        return lists;
    }

    public ArrayList<CityBaseResp.ObjectEntity> getHotCity() {
        hotCitys = new ArrayList<>();
        Cursor cursor = null;
        SQLiteDatabase db = getReadableDatabase();
        try {
//            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_ADDRESS_CITY + "(id INTEGER PRIMARY KEY AUTOINCREMENT,cityName nvarchar(50),cityCode nvarchar(50),provinceCode nvarchar(50),hotFlag INTEGER,firstPinyin nvarchar(50))");
            db.beginTransaction();
            cursor = db.rawQuery(" select * from " + TABLE_ADDRESS_CITY + " where hotFlag = ? ", new String[]{"1"});
            while (cursor.moveToNext()) {
                CityBaseResp.ObjectEntity objectEntity = new CityBaseResp.ObjectEntity();
                objectEntity.setCityName(cursor.getString(cursor.getColumnIndex("cityName")));
                objectEntity.setCityCode(cursor.getString(cursor.getColumnIndex("cityCode")));
                objectEntity.setProvinceCode(cursor.getString(cursor.getColumnIndex("provinceCode")));
                objectEntity.setHotFlag(cursor.getInt(cursor.getColumnIndex("hotFlag")));
                objectEntity.setFirstPinyin(cursor.getString(cursor.getColumnIndex("firstPinyin")));
                hotCitys.add(objectEntity);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db.inTransaction()) {
                db.endTransaction();
                db.close();
            }
        }
        return hotCitys;
    }


//
//    public void updataAddress(GetRegionResp rsp) {
//        SQLiteDatabase db = getWritableDatabase();
//        try {
//            List<ProvinceBean> data = rsp.getData();
//            db.beginTransaction();
//            for (ProvinceBean bean : data) {
//                for (CityBean citybean : bean.getChildren()) {
//                    for (DistrictBean disbean : citybean.getChildren()) {
//                        SQLiteStatement hisCity = db.compileStatement("insert into " + TABLE_ADDRESS + " values(null,?,?,?,?,?,?)");
//                        hisCity.bindString(1, bean.getText());
//                        hisCity.bindString(2, bean.getValue());
//                        hisCity.bindString(3, citybean.getText());
//                        hisCity.bindString(4, citybean.getValue());
//                        hisCity.bindString(5, disbean.getText());
//                        hisCity.bindString(6, disbean.getValue());
//                        hisCity.executeInsert();
////                        LogUtil.enableLog();
////                        LogUtil.e("onGetRegionSuccess",disbean.getText());
//                    }
//                }
//            }
//            db.setTransactionSuccessful();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            if (db.inTransaction()) {
//                db.endTransaction();
//                db.close();
//            }
//        }
//    }
}
