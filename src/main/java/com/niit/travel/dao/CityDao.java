/*********************************************************
 * 文件名: CityDao
 * 作者: 魏捷宇
 * 说明:
 *********************************************************/
package com.niit.travel.dao;

import com.niit.travel.entity.City;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CityDao {
    List<City> getCityList();

    City getCityById(int cityId);

    City getCityByName(String cityName);

    int insertCity(City city);

    int updateCity(City city);

    int deleteCity(int cityId);

    List<City> getCityListInOrder(String property);

    List<City> getCityListInOrderByHN();

}
