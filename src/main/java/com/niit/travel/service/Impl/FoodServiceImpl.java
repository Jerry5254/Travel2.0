/*********************************************************
 * 文件名: FoodServiceImpl
 * 作者: 魏捷宇
 * 说明:
 *********************************************************/
package com.niit.travel.service.Impl;

import com.niit.travel.dao.FoodDao;
import com.niit.travel.entity.Food;
import com.niit.travel.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodDao foodDao;

    @Override
    public List<Food> getFoodList() {
        return foodDao.getFoodList();
    }

    @Override
    public Food getFoodById(Integer foodId) {
        return foodDao.getFoodById(foodId);
    }

    @Override
    public Food getFoodByName(String foodName) {
        return foodDao.getFoodByName(foodName);
    }

    @Override
    public List<Food> getFoodByCity(String cityName) {
        return foodDao.getFoodByCity(cityName);
    }

    @Transactional
    @Override
    public boolean addFood(Food food) {
        if (food.getFName() != null && !"".equals(food.getFName())) {
            try {
                int effectedNum = foodDao.insertFood(food);
                if (effectedNum > 0) {
                    return true;
                } else {
                    throw new RuntimeException("添加美食失败");
                }
            } catch (Exception e) {
                throw new RuntimeException("添加美食失败" + e);
            }
        } else {
            throw new RuntimeException("美食信息不能为空");
        }
    }

    @Transactional
    @Override
    public boolean modifyFood(Food food) {
        if (food.getFId() != null && food.getFId() > 0) {
            try {
                int effectedNum = foodDao.updateFood(food);
                if (effectedNum > 0) {
                    return true;
                } else {
                    throw new RuntimeException("更新美食信息失败");
                }

            } catch (Exception e) {
                throw new RuntimeException("更新美食信息" + e);
            }

        } else {
            throw new RuntimeException("美食ID有误");
        }
    }

    @Transactional
    @Override
    public boolean deleteFood(Integer foodId) {
        if (foodId > 0) {
            try {
                int effectedNum = foodDao.deleteFood(foodId);
                if (effectedNum > 0) {
                    return true;
                } else {
                    throw new RuntimeException("删除美食失败");
                }
            } catch (Exception e) {
                throw new RuntimeException("删除美食失败" + e);
            }

        } else {
            throw new RuntimeException("美食ID有误");
        }
    }
}
