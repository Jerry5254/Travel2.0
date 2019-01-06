/*********************************************************
 * 文件名: FoodDao
 * 作者: 魏捷宇
 * 说明:
 *********************************************************/
package com.niit.travel.dao;

import com.niit.travel.entity.Food;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface FoodDao {
    List<Food> getFoodList();

    Food getFoodById(int foodId);

    Food getFoodByName(String foodName);

    List<Food> getFoodByCity(String cityName);

    int insertFood(Food food);

    int updateFood(Food food);

    int deleteFood(int foodId);
}
