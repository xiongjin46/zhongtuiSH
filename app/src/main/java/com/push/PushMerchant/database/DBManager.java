package com.push.PushMerchant.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.push.PushMerchant.modules.home.bean.City;
import com.push.PushMerchant.modules.home.bean.CityBaseResp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @ClassName: DBManager
 * @Description:地址选择数据库
 * @author: YYL
 * <p>
 * create at 2016/11/14 16:19
 */
public class DBManager {
    //    private static final String ASSETS_NAME = "china_cities.db";
//    private static final String DB_NAME = "china_cities.db";
//    private static final String TABLE_NAME = "city";
//    private static final String NAME = "name";
//    private static final String PINYIN = "pinyin";
//    private static final int BUFFER_SIZE = 1024;
//    private String DB_PATH;
    private Context mContext;
    private ArrayList<CityBaseResp.ObjectEntity> allCitys;
    private SQLiteDatabase db;
    private Cursor cursor;
    private ArrayList<CityBaseResp.ObjectEntity> searchCityLists;


    public DBManager(Context context) {
        this.mContext = context;
//        DB_PATH = File.separator + "data"
//                + Environment.getDataDirectory().getAbsolutePath() + File.separator
//                + context.getPackageName() + File.separator + "databases" + File.separator;
    }

    //    @SuppressWarnings("ResultOfMethodCallIgnored")
//    public void copyDBFile() {
//        File dir = new File(DB_PATH);
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }
//        File dbFile = new File(DB_PATH + DB_NAME);
//        if (!dbFile.exists()) {
//            InputStream is;
//            OutputStream os;
//            try {
//                is = mContext.getResources().getAssets().open(ASSETS_NAME);
//                os = new FileOutputStream(dbFile);
//                byte[] buffer = new byte[BUFFER_SIZE];
//                int length;
//                while ((length = is.read(buffer, 0, buffer.length)) > 0) {
//                    os.write(buffer, 0, length);
//                }
//                os.flush();
//                os.close();
//                is.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * 读取所有城市
//     *
//     * @return
//     */
//    public List<City> getAllCities() {
//        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
//        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);
//        List<City> result = new ArrayList<>();
//        City city;
//        StringBuffer str = new StringBuffer();
//        while (cursor.moveToNext()) {
//            String name = cursor.getString(cursor.getColumnIndex(NAME));
//            String pinyin = cursor.getString(cursor.getColumnIndex(PINYIN));
//            city = new City(name, pinyin);
//            result.add(city);
//            str.append("id=" + cursor.getString(0) + "  name=" + cursor.getString(1) + "  pinyin=" + cursor.getString(2) + ",  ");
//        }
//        cursor.close();
//        db.close();
//        Collections.sort(result, new CityComparator());
//
//        return result;
//    }
//
//    /**
//     * 通过名字或者拼音搜索
//     *
//     * @param keyword
//     * @return
//     */
    public List<CityBaseResp.ObjectEntity> searchCity(final String keyword) {
        db = CommonDatabaseHelper.getInstance().getReadableDatabase();
        try {
            searchCityLists = new ArrayList<>();
            db.beginTransaction();
            Cursor cursor = db.rawQuery("select * from " + CommonDatabaseHelper.TABLE_ADDRESS_CITY + " where cityName like \"%" + keyword + "%\" or firstPinyin like \"%" + keyword + "%\"", null);
            while (cursor.moveToNext()) {
                CityBaseResp.ObjectEntity cityBaseResp = new CityBaseResp.ObjectEntity();
                cityBaseResp.setCityName(cursor.getString(1));
                cityBaseResp.setCityCode(cursor.getString(2));
                cityBaseResp.setProvinceCode(cursor.getString(3));
                cityBaseResp.setHotFlag(cursor.getInt(4));
                cityBaseResp.setFirstPinyin(cursor.getString(5));
                searchCityLists.add(cityBaseResp);
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
//        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
//        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where name like \"%" + keyword
//                + "%\" or pinyin like \"%" + keyword + "%\"", null);
//        List<City> result = new ArrayList<>();
//        City city;
//        while (cursor.moveToNext()) {
//            String name = cursor.getString(cursor.getColumnIndex(NAME));
//            String pinyin = cursor.getString(cursor.getColumnIndex(PINYIN));
//            city = new City(name, pinyin);
//            result.add(city);
//        }
//        cursor.close();
//        db.close();
//        Collections.sort(result, new CityComparator());
        return searchCityLists;
    }

    public List<CityBaseResp.ObjectEntity> getAllCitys() {
        try {
            db = CommonDatabaseHelper.getInstance().getReadableDatabase();
            allCitys = new ArrayList<>();
            db.beginTransaction();
            cursor = db.rawQuery("select * from " + CommonDatabaseHelper.TABLE_ADDRESS_CITY, null);
            while (cursor.moveToNext()) {
                CityBaseResp.ObjectEntity cityBaseResp = new CityBaseResp.ObjectEntity();
                cityBaseResp.setCityName(cursor.getString(1));
                cityBaseResp.setCityCode(cursor.getString(2));
                cityBaseResp.setProvinceCode(cursor.getString(3));
                cityBaseResp.setHotFlag(cursor.getInt(4));
                cityBaseResp.setFirstPinyin(cursor.getString(5));
                allCitys.add(cityBaseResp);
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
        Collections.sort(allCitys, new CityComparator1());
        return allCitys;
    }


    public String getCityCode(String CityName) {
        String cityCode = "";
        db = CommonDatabaseHelper.getInstance().getReadableDatabase();
        try {
            db.beginTransaction();
            Cursor cursor = db.rawQuery("select * from " + CommonDatabaseHelper.TABLE_ADDRESS_CITY + " where cityName =" + CityName, null);
            if (cursor.moveToNext()) {
                cityCode = cursor.getString(cursor.getColumnIndex("cityCode"));
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
        return cityCode;

    }

    /**
     * a-z排序
     */
    private class CityComparator1 implements Comparator<CityBaseResp.ObjectEntity> {
        @Override
        public int compare(CityBaseResp.ObjectEntity lhs, CityBaseResp.ObjectEntity rhs) {
            String a = lhs.getFirstPinyin().substring(0, 1);
            String b = rhs.getFirstPinyin().substring(0, 1);
            return a.compareTo(b);
        }
    }

    /**
     * a-z排序
     */
    private class CityComparator implements Comparator<City> {
        @Override
        public int compare(City lhs, City rhs) {
            String a = lhs.getPinyin().substring(0, 1);
            String b = rhs.getPinyin().substring(0, 1);
            return a.compareTo(b);
        }
    }
}
