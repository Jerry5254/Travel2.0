/*********************************************************
 * 文件名: CityServiceImpl
 * 作者: 魏捷宇
 * 说明:
 *********************************************************/
package com.niit.travel.service.Impl;

import com.niit.travel.dao.CityDao;
import com.niit.travel.entity.City;
import com.niit.travel.service.CityService;
import com.niit.travel.util.DeleteFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

@Service
public class CityServiceImpl implements CityService {
    @Autowired
    private CityDao cityDao;

    @Override
    public List<City> getAllCity() {
        return cityDao.getCityList();
    }

    @Override
    public City getCityById(int cityId) {
        return cityDao.getCityById(cityId);
    }

    @Override
    public City getCityByName(String cityName) {
        if (cityDao.getCityByName(cityName) != null) {
            return cityDao.getCityByName(cityName);
        } else {
            return null;
        }
    }

    @Transactional
    @Override
    public boolean addCity(City city) {
        if (city.getCName() != null && !"".equals(city.getCName())) {
            try {
                int effectedNum = cityDao.insertCity(city);
                if (effectedNum > 0) {
                    return true;
                } else {
                    throw new RuntimeException("城市添加失败");
                }
            } catch (Exception e) {
                throw new RuntimeException("城市添加失败" + e);
            }
        } else {
            throw new RuntimeException("城市信息不能为空");
        }
    }

    @Transactional
    @Override
    public boolean deleteCity(int cityId) {
        if (cityId > 0) {
            try {
                String path = "E:/idea/image/upload/item/city/" + cityId;
                DeleteFileUtil.deleteFile(new File(path));
                int effectedNum = cityDao.deleteCity(cityId);

                if (effectedNum > 0) {
                    return true;
                } else {
                    throw new RuntimeException("删除城市失败");
                }
            } catch (Exception e) {
                throw new RuntimeException("删除城市失败");
            }

        } else {
            throw new RuntimeException("输入ID有误");
        }
    }

    @Transactional
    @Override
    public boolean modifyCity(City city) {
        if (city.getCId() != null && city.getCId() > 0) {
            try {
                int effectedNum = cityDao.updateCity(city);
                if (effectedNum > 0) {
                    return true;
                } else {
                    throw new RuntimeException("更新城市失败");
                }

            } catch (Exception e) {
                throw new RuntimeException("更新城市失败" + e);
            }

        } else {
            throw new RuntimeException("输入ID有误");
        }
    }

    @Override
    public List<City> getOrderCity(String property) {
        return cityDao.getCityListInOrder(property);
    }

    @Override
    public boolean deletePic(int cityId, String addr) {
        City city = getCityById(cityId);
        String path = "E:/idea/image/" + addr;
        String separator = System.getProperty("file.separator");
        path.replace("/", separator);
        DeleteFileUtil.deleteFile(new File(path));
        String cityPicPath = city.getCPic();
        String newCityPicPath = "";
        String[] PicsPath = cityPicPath.split(";");
        for (String picPath : PicsPath) {
            if (!picPath.equals(addr)) {
                newCityPicPath += picPath + ";";
            }
        }

        City updateCity = new City();
        updateCity.setCId(cityId);
        updateCity.setCPic(newCityPicPath);
        try {
            int effectedNum = cityDao.updateCity(updateCity);
            if (effectedNum > 0) {
                return true;
            } else {
                throw new RuntimeException("删除图片失败");
            }

        } catch (Exception e) {
            throw new RuntimeException("删除图片失败" + e);
        }

    }
}
