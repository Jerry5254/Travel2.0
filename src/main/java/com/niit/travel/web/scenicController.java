/*********************************************************
 * 文件名: scenicController
 * 作者: 林夏媚
 * 说明:
 *********************************************************/
package com.niit.travel.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.niit.travel.entity.scenic;
import com.niit.travel.service.scenicService;
import com.niit.travel.util.ImageUtil;
import com.niit.travel.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
@RequestMapping("/scenic")
public class scenicController {
    @Autowired
    private scenicService scenicservice;

    @RequestMapping(value = "/sceniclist", method = RequestMethod.GET)
    private Map<String, Object> listtn() {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<scenic> list = scenicservice.queryScenic();
        modelMap.put("sceniclist", list);
        modelMap.put("success", true);
        return modelMap;
    }

    @RequestMapping(value = "/getscenicbycity", method = RequestMethod.GET)
    private Map<String, Object> scn(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        String city = request.getParameter("cityName");
        List<scenic> list = null;
        try {
            list = scenicservice.queryScenicByCity(city);
            if (list != null) {
                for (scenic scenic : list) {
                    String[] picList = scenic.getSPic().split(";");
                    scenic.setSPic(picList[0]);
                }
                modelMap.put("scenicList", list);
                modelMap.put("success", true);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "暂时没有景区");
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }

    @RequestMapping(value = "/addscenic", method = RequestMethod.POST)
    private Map<String, Object> addtn(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        scenic scenic = null;
        String scenicStr = request.getParameter("scenic");
        ObjectMapper mapper = new ObjectMapper();
        try {
            scenic = mapper.readValue(scenicStr, scenic.class);
        } catch (IOException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        int imgAmount = 0;
        imgAmount = Integer.parseInt(request.getParameter("imgAmount"));
        if (scenic.getSName() != null && !scenic.getSName().equals("")) {
            if (scenicservice.getScenicByName(scenic.getSName()) != null) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "已经存在该景点了");
            } else {
                scenicservice.insertScenic(scenic);
                int scenicId = 0;
                try {
                    scenicId = scenicservice.getScenicByName(scenic.getSName()).getSId();
                } catch (Exception e) {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "景点ID出错啦");
                }
                String scenicImagesAddr = "";
                if (scenicId > 0 && imgAmount > 0) {
                    for (int i = 0; i < imgAmount; i++) {
                        MultipartFile file = ((MultipartHttpServletRequest) request).getFile("scenicImg[" + i + "]");
                        String dest = PathUtil.getScenicImagePath(scenicId);
                        String scenicImgAddr = ImageUtil.addPicture(file, file.getOriginalFilename(), dest);
                        scenicImagesAddr += scenicImgAddr + ";";
                    }

                    scenic updateScenic = new scenic();
                    updateScenic.setSId(scenicId);
                    updateScenic.setSPic(scenicImagesAddr);
                    modelMap.put("success", scenicservice.updateScenic(updateScenic));
                } else {
                    modelMap.put("success", true);
                }
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "景点名字不能为空");
        }


        return modelMap;
    }

    @RequestMapping(value = "/modifyscenic", method = RequestMethod.POST)
    private Map<String, Object> modifysc(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        scenic scenic = new scenic();
        int scenicId = Integer.parseInt(request.getParameter("scenicId"));
        String scenicName = request.getParameter("scenicName");
        String scenicDesc = request.getParameter("scenicDesc");
        String scenicCity = request.getParameter("scenicCity");
        scenic.setSId(scenicId);
        scenic.setSCity(scenicCity);
        scenic.setSDes(scenicDesc);
        scenic.setSName(scenicName);
        modelMap.put("success", scenicservice.updateScenic(scenic));
        return modelMap;
    }

    @RequestMapping(value = "/deletescenic", method = RequestMethod.POST)
    private Map<String, Object> deleteScenic(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        int scenicId = Integer.parseInt(request.getParameter("scenicId"));
        modelMap.put("success", scenicservice.deleteScenic(scenicId));
        return modelMap;
    }

    @RequestMapping(value = "/scenic_id", method = RequestMethod.GET)
    private Map<String, Object> getScenicById(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        String scenicIdString = request.getParameter("scenicId");
        if (scenicIdString != null && !"".equals(scenicIdString)) {
            int scenicId = Integer.parseInt(scenicIdString);
            scenic scenic = scenicservice.getScenicById(scenicId);
            if (scenic != null && scenic.getSId() > 0) {
                modelMap.put("scenic", scenic);
                modelMap.put("success", true);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "景点获取失败");
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "景点获取失败");
        }
        return modelMap;
    }

    @RequestMapping(value = "/getscenicpic", method = RequestMethod.GET)
    private Map<String, Object> getScenicPic(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        int scenicId = Integer.parseInt(request.getParameter("scenicId"));
        scenic scenic = scenicservice.getScenicById(scenicId);
        if (scenic.getSPic() != null && !scenic.getSPic().equals("")) {
            String scenicPics = scenic.getSPic();
            String pic[] = scenicPics.split(";");
            List<String> picList = new ArrayList<>();
            for (String scenicpic : pic) {
                picList.add(scenicpic);
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
        String path = request.getParameter("scenicPath");
        int scenicId = Integer.parseInt(request.getParameter("scenicId"));
        boolean result = scenicservice.deletePic(scenicId, path);
        if (result) {
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "删除图片失败");
        }
        return modelMap;
    }

    @RequestMapping(value = "/addscenicpic", method = RequestMethod.POST)
    public Map<String, Object> addCityPic(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        int imgAmount = Integer.parseInt(request.getParameter("imgAmount"));
        int scenicId = Integer.parseInt(request.getParameter("scenicId"));
        String scenicImagesAddr;
        if (scenicservice.getScenicById(scenicId).getSPic() == null || "".equals(scenicservice.getScenicById(scenicId).getSPic())) {
            scenicImagesAddr = "";
        } else {
            scenicImagesAddr = scenicservice.getScenicById(scenicId).getSPic();
        }
        for (int i = 0; i < imgAmount; i++) {
            MultipartFile file = ((MultipartHttpServletRequest) request).getFile("scenicImg[" + i + "]");
            String dest = PathUtil.getScenicImagePath(scenicId);
            String scenicImgAddr = ImageUtil.addPicture(file, file.getOriginalFilename(), dest);
            scenicImagesAddr += scenicImgAddr + ";";
        }
        scenic scenic = new scenic();
        scenic.setSId(scenicId);
        scenic.setSPic(scenicImagesAddr);
        modelMap.put("success", scenicservice.updateScenic(scenic));
        return modelMap;
    }

    @RequestMapping(value = "/indexsceniclist", method = RequestMethod.GET)
    public Map<String, Object> getIndexScenicList() {
        Map<String, Object> modelMap = new HashMap<>();
        try {
            List<scenic> scenicList = scenicservice.queryScenic();
            List<scenic> effecedList = new ArrayList<>();
            int flag = 0;
            for (scenic showScenic : scenicList) {
                if (flag > 2) {
                    break;
                }
                String[] pics = showScenic.getSPic().split(";");
                showScenic.setSPic(pics[0]);
                effecedList.add(showScenic);
                flag++;
            }
            modelMap.put("scenicList", effecedList);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }


}
