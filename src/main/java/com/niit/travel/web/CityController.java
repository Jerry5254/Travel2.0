/*********************************************************
 * 文件名: CityController
 * 作者: 魏捷宇
 * 说明:
 *********************************************************/
package com.niit.travel.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.niit.travel.entity.City;
import com.niit.travel.service.CityService;
import com.niit.travel.util.ImageUtil;
import com.niit.travel.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/city")
public class CityController {
    @Autowired
    private CityService cityService;

    @RequestMapping(value = "/citylist", method = RequestMethod.GET)
    public Map<String, Object> getCityList() {
        Map<String, Object> modelMap = new HashMap<>();
        try {
            List<City> cityList = cityService.getAllCity();
            List<City> effecedList = new ArrayList<>();
            for (City city : cityList) {
                if (city.getCStatus().equals("Y")) {
                    city.setCStatus("审核通过");
                    effecedList.add(city);
                }
            }
            modelMap.put("cityList", effecedList);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }

    @RequestMapping(value = "/ncitylist", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getNCityList() {
        Map<String, Object> modelMap = new HashMap<>();
        try {
            List<City> cityList = cityService.getAllCity();
            List<City> effecedList = new ArrayList<>();
            for (City city : cityList) {
                if (city.getCStatus().equals("N")) {
                    city.setCStatus("待审核");
                    effecedList.add(city);
                }
            }
            modelMap.put("cityList", effecedList);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }

    @RequestMapping(value = "/allcitylist", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getAllCityList() {
        Map<String, Object> modelMap = new HashMap<>();
        try {
            List<City> cityList = cityService.getAllCity();
            for (City city : cityList) {
                if (city.getCStatus().equals("Y")) {
                    city.setCStatus("审核通过");
                } else {
                    city.setCStatus("待审核");
                }
            }
            modelMap.put("cityList", cityList);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }


    @RequestMapping(value = "/citylistinorder", method = RequestMethod.GET)
    public Map<String, Object> getCityListInOrder() {
        Map<String, Object> modelMap = new HashMap<>();
        List<City> cityList = cityService.getOrderCity("CHit_Number");
        modelMap.put("cityList", cityList);
        return modelMap;
    }

    @RequestMapping(value = "/city_id", method = RequestMethod.GET)
    public Map<String, Object> getCityById(HttpServletRequest request) {

        Map<String, Object> modelMap = new HashMap<>();
        String cityIdString = request.getParameter("cityId");
        if (cityIdString != null && !"".equals(cityIdString)) {
            int cityId = Integer.parseInt(cityIdString);
            City city = cityService.getCityById(cityId);
            if (city != null && city.getCId() > 0) {
                if (city.getCPic() == null || city.getCPic().equals("")) {
                    modelMap.put("picAmount", 0);
                    modelMap.put("city", city);
                    modelMap.put("success", true);
                } else {
                    String[] cityPics = city.getCPic().split(";");
                    int cityPicAmount = cityPics.length;
                    modelMap.put("picAmount", cityPicAmount);
                    modelMap.put("picList", cityPics);
                    modelMap.put("city", city);
                    modelMap.put("success", true);
                }
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "城市获取失败");
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "城市获取失败");
        }
        return modelMap;
    }

    @RequestMapping(value = "/getcitybyname", method = RequestMethod.GET)
    public Map<String, Object> getCityByName(HttpServletRequest request) {
        String cityName = request.getParameter("cityName");
        Map<String, Object> modelMap = new HashMap<>();
        if (cityService.getCityByName(cityName) != null && cityService.getCityByName(cityName).getCId() > 0) {
            City city = cityService.getCityByName(cityName);
            modelMap.put("city", city);
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "不存在这个城市");
        }
        return modelMap;
    }

    @RequestMapping(value = "/addcity", method = RequestMethod.POST)
    public Map<String, Object> addCity(HttpServletRequest request) {
        City city = null;
        Map<String, Object> modelMap = new HashMap<>();

        String newCityInfo = request.getParameter("newCityInfo");
        ObjectMapper mapper = new ObjectMapper();
        try {
            city = mapper.readValue(newCityInfo, City.class);
        } catch (IOException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }

        if (cityService.getCityByName(city.getCName()) != null) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "已经存在该城市了");
        } else {
            modelMap.put("success", cityService.addCity(city));
        }
        return modelMap;
    }

    @RequestMapping(value = "/addcitybyadmin", method = RequestMethod.POST)
    public Map<String, Object> insertCity(HttpServletRequest request) {
        City city = null;
        Map<String, Object> modelMap = new HashMap<>();

        String newCityInfo = request.getParameter("newCityInfo");
        ObjectMapper mapper = new ObjectMapper();
        try {
            city = mapper.readValue(newCityInfo, City.class);
        } catch (IOException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }

        if (cityService.getCityByName(city.getCName()) != null) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "已经存在该城市了");
        } else {
            boolean result = cityService.addCity(city);
            int imgAmount = Integer.parseInt(request.getParameter("imgAmount"));
            if (result) {
                int cityId = cityService.getCityByName(city.getCName()).getCId();
                String cityImagesAddr = "";
                for (int i = 0; i < imgAmount; i++) {
                    MultipartFile file = ((MultipartHttpServletRequest) request).getFile("cityImg[" + i + "]");
                    String dest = PathUtil.getCityImagePath(cityId);
                    String cityImgAddr = ImageUtil.addPicture(file, file.getOriginalFilename(), dest);
                    cityImagesAddr += cityImgAddr + ";";
                }

                City updataCity = new City();
                updataCity.setCId(cityId);
                updataCity.setCStatus("Y");
                if (cityImagesAddr.equals("")) {
                    updataCity.setCPic(null);
                } else {

                    updataCity.setCPic(cityImagesAddr);
                }
                modelMap.put("success", cityService.modifyCity(updataCity));
            }
        }
        return modelMap;
    }

    @RequestMapping(value = "/modifycity", method = RequestMethod.POST)
    public Map<String, Object> modifyCity(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        City city = new City();
        int cityId = Integer.parseInt(request.getParameter("cityId"));
        String cityName = request.getParameter("cityName");
        String cityDesc = request.getParameter("cityDesc");
        city.setCId(cityId);
        city.setCName(cityName);
        city.setCDes(cityDesc);
        modelMap.put("success", cityService.modifyCity(city));
        return modelMap;
    }

    @RequestMapping(value = "/changecitystatus", method = RequestMethod.POST)
    public Map<String, Object> changeCityStatus(HttpServletRequest request) {
        City city = new City();
        Map<String, Object> modelMap = new HashMap<>();
        int cityId = 0;
        try {
            String cityIdString = request.getParameter("cityId");
            cityId = Integer.parseInt(cityIdString);
            city.setCId(cityId);
            city.setCStatus("Y");
            modelMap.put("success", cityService.modifyCity(city));
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }

    @RequestMapping(value = "/deletecity", method = RequestMethod.POST)
    public Map<String, Object> deleteCity(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        int cityId = 0;
        try {
            String cityIdString = request.getParameter("cityId");
            cityId = Integer.parseInt(cityIdString);
            modelMap.put("success", cityService.deleteCity(cityId));
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "城市ID有误");
        }
        return modelMap;
    }

    @RequestMapping(value = "/addcitypic", method = RequestMethod.POST)
    public Map<String, Object> addCityPic(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        int imgAmount = Integer.parseInt(request.getParameter("imgAmount"));
        int cityId = Integer.parseInt(request.getParameter("cityId"));
        String cityImagesAddr;
        if (cityService.getCityById(cityId).getCPic() == null || "".equals(cityService.getCityById(cityId).getCPic())) {
            cityImagesAddr = "";
        } else {
            cityImagesAddr = cityService.getCityById(cityId).getCPic();
        }
        for (int i = 0; i < imgAmount; i++) {
            MultipartFile file = ((MultipartHttpServletRequest) request).getFile("cityImg[" + i + "]");
            String dest = PathUtil.getCityImagePath(cityId);
            String cityImgAddr = ImageUtil.addPicture(file, file.getOriginalFilename(), dest);
            cityImagesAddr += cityImgAddr + ";";
        }
        City city = new City();
        city.setCId(cityId);
        city.setCPic(cityImagesAddr);
        modelMap.put("success", cityService.modifyCity(city));
        return modelMap;
    }

    @RequestMapping(value = "/getcitypic", method = RequestMethod.GET)
    public Map<String, Object> getCityPic(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        int cityId = Integer.parseInt(request.getParameter("cityId"));
        City city = cityService.getCityById(cityId);
        if (city.getCPic() != null && !city.getCPic().equals("")) {
            String cityPics = city.getCPic();
            String pic[] = cityPics.split(";");
            List<String> picList = new ArrayList<>();
            for (String citypic : pic) {
                picList.add(citypic);
            }
            modelMap.put("success", true);
            modelMap.put("havePic", true);
            modelMap.put("picList", picList);
        } else {
            modelMap.put("success", true);
            modelMap.put("havePic", false);
        }
        return modelMap;
    }

    @RequestMapping(value = "/deletepic", method = RequestMethod.POST)
    public Map<String, Object> deletePic(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        String path = request.getParameter("cityPath");
        int cityId = Integer.parseInt(request.getParameter("cityId"));
        boolean result = cityService.deletePic(cityId, path);
        if (result) {
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "删除图片失败");
        }
        return modelMap;
    }

    @RequestMapping(value = "/addpoint", method = RequestMethod.GET)
    public Map<String, Object> addPoint(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        int cityId = Integer.parseInt(request.getParameter("cityId"));
        City city = cityService.getCityById(cityId);
        City updateCity = new City();
        updateCity.setCId(cityId);
        int cityHN = city.getCHitNumber();
        updateCity.setCHitNumber(cityHN + 1);
        boolean result = cityService.modifyCity(updateCity);
        if (result) {
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "增加点击量失败");

        }

        return modelMap;

    }

    @RequestMapping(value = "/indexcitylist", method = RequestMethod.GET)
    public Map<String, Object> getIndexCityList() {
        Map<String, Object> modelMap = new HashMap<>();
        try {
            List<City> cityList = cityService.getCityInOrderByHn();
            List<City> effecedList = new ArrayList<>();
            String cityFirstPic;
            int flag = 0;
            for (City city : cityList) {
                if (flag > 3) {
                    break;
                }
                if (city.getCStatus().equals("Y")) {
                    city.setCStatus("审核通过");
                    effecedList.add(city);
                    if (city.getCPic() != null && !city.getCPic().equals("")) {
                        String cityPics = city.getCPic();
                        String pic[] = cityPics.split(";");
                        city.setCPic(pic[0]);
                    }
                }
                flag++;
            }
            modelMap.put("cityList", effecedList);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }


}
