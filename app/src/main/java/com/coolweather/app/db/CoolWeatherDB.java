package com.coolweather.app.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaof_000 on 2015/4/6.
 */
public class CoolWeatherDB {
    /**
     * 数据库名
     */
    public static final String DB_NAME = "cool_weather";
    /**
     * 数据库版本
     */
    public static final int VERSION = 1;

    private static CoolWeatherDB coolWeatherDB;

    private SQLiteDatabase db;

    private CoolWeatherDB(Context context) {
        CoolWeatherOpenHelper dbHepler = new CoolWeatherOpenHelper(context, DB_NAME, null, VERSION);
        db = dbHepler.getWritableDatabase();
    }

    /**
     * 获取CoolWeatherDB的实例。
     * @param context
     * @return
     */
    public synchronized static CoolWeatherDB getInstance(Context context) {
        if (coolWeatherDB == null) {
            coolWeatherDB = new CoolWeatherDB(context);
        }
        return coolWeatherDB;
    }

    /**
     * 保存省份数据
     * @param province 省份数据实例
     */
    public void saveProvince(Province province) {
        if (province != null) {
            ContentValues values = new ContentValues();
            values.put("province_name", province.getName());
            values.put("province_code", province.getCode());
            db.insert("Province", null, values);
        }
    }

    /**
     * 读取全国所有省份的数据
     * @return
     */
    public List<Province> loadProvinces() {
        List<Province> provinceList = new ArrayList<Province>();
        Cursor cursor = db.query("Province", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Province province = new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setCode(cursor.getString(cursor.getColumnIndex("province_code")));
                provinceList.add(province);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return provinceList;
    }

    /**
     * 保存城市数据实例
     * @param city
     */
    public void saveCity (City city) {
        if (city != null) {
            ContentValues values = new ContentValues();
            values.put("city_name", city.getName());
            values.put("city_code", city.getCode());
            values.put("province_id", city.getProvinceId());
            db.insert("City", null, values);
        }
    }

    /**
     * 获取指定省份下的所有城市数据
     * @param provinceId 省份编号
     * @return
     */
    public List<City> loadCities(int provinceId) {
        List<City> cities = new ArrayList<City>();
        Cursor cursor = db.query("City", null, "province_id = ?", new String[] { String.valueOf(provinceId) }, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setProvinceId(cursor.getInt(cursor.getColumnIndex("province_id")));
                cities.add(city);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return cities;
    }

    /**
     * 将县的数据保存到数据库
     * @param county
     */
    public void saveCounty(County county) {
        if (county != null) {
            ContentValues values = new ContentValues();
            values.put("county_name", county.getName());
            values.put("county_code", county.getCode());
            values.put("city_id", county.getCityId());
            db.insert("County", null, values);
        }
    }

    /**
     * 获取指定城市下的所有县的数据
     * @param cityId 城市编号
     * @return
     */
    public List<County> loadCounties(int cityId) {
        List<County> list = new ArrayList<County>();
        Cursor cursor = db.query("County", null, "city_id = ?", new String[] { String.valueOf(cityId) }, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                County county = new County();
                county.setId(cursor.getInt(cursor.getColumnIndex("id")));
                county.setName(cursor.getString(cursor.getColumnIndex("county_name")));
                county.setCode(cursor.getString(cursor.getColumnIndex("county_code")));
                county.setCityId(cursor.getInt(cursor.getColumnIndex("city_id")));
                list.add(county);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }
}
