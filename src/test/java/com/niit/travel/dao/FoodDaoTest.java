package com.niit.travel.dao;

import com.niit.travel.entity.Food;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FoodDaoTest {

    @Autowired
    private FoodDao foodDao;

    @Test
    public void getFoodList() {
        List<Food> foodList = foodDao.getFoodList();
        assertEquals(1, foodList.size());
    }

    @Test
    public void getFoodById() {
        Food food = foodDao.getFoodById(1);
        assertEquals("西安", food.getFCity());
    }

    @Test
    public void getFoodByCity() {
    }

    @Test
    public void insertFood() {
        Food food = new Food();
        food.setFCity("广州");
        food.setFName("肠粉");
        food.setFPic(null);
        int effectedNum = foodDao.insertFood(food);
        assertEquals(1, effectedNum);
    }

    @Test
    public void updateFood() {
        Food food = new Food();
        food.setFId(2);
        food.setFName("干蒸");
        int effectedNum = foodDao.updateFood(food);
        assertEquals(1, effectedNum);
    }

    @Test
    public void deleteFood() {
        int effectedNum = foodDao.deleteFood(2);
        assertEquals(1, effectedNum);
    }
}