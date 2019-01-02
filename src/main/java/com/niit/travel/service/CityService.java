/*********************************************************
 * 文件名: CityService
 * 作者: 魏捷宇
 * 说明:
 *********************************************************/
package com.niit.travel.service;

import com.niit.travel.entity.City;

import java.util.List;

public interface CityService {
    List<City> getAllCity();

    City getCityById(int cityId);

    City getCityByName(String cityName);

    boolean addCity(City city);

    boolean deleteCity(int cityId);

    boolean modifyCity(City city);

    List<City> getOrderCity(String property);

    boolean deletePic(int cityId, String addr);
}
