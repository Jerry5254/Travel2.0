package com.niit.travel.dao;

import com.niit.travel.entity.City;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CityDaoTest {
    @Autowired
    private CityDao cityDao;

    @Test
    public void getCityList() {
        List<City> cityList = cityDao.getCityList();
        assertEquals(1, cityList.size());
    }

    @Test
    public void getCityById() {
        City city = cityDao.getCityById(1);
        assertEquals("Guangzhou", city.getCName());
    }

    @Test
    public void getCityByName() {
        City city = cityDao.getCityByName("Guangzhou");
        assertEquals(1, (int) city.getCId());
    }

    @Test
    public void insertCity() {
        City city = new City();
        city.setCName("Qingdao");
        city.setCDes("旅游养老圣地");
        int effectedNum = cityDao.insertCity(city);
        assertEquals(1, effectedNum);
    }

    @Test
    public void updateCity() {
        City city = new City();
        city.setCId(9);
        city.setCStatus("Y");
        int effectedNum = cityDao.updateCity(city);
        assertEquals(1, effectedNum);
    }

    @Test
    public void deleteCity() {
        int effectedNum = cityDao.deleteCity(2);
        assertEquals(1, effectedNum);
    }
}