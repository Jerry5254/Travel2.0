/*********************************************************
 * 文件名: scenicServiceImpl
 * 作者:林夏媚
 * 说明:
 *********************************************************/
package com.niit.travel.service.Impl;

import com.niit.travel.dao.scenicDao;
import com.niit.travel.entity.scenic;
import com.niit.travel.service.scenicService;
import com.niit.travel.util.DeleteFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

@Service
public class scenicServiceImpl implements scenicService {
    @Autowired
    private scenicDao dao;

    @Override
    public List<scenic> queryScenic() {
        return dao.queryScenic();
    }

    @Override
    public List<scenic> queryScenicByCity(String SCity) {
        return dao.queryScenicByCity(SCity);
    }

    @Transactional
    @Override
    public boolean insertScenic(scenic scenic) {
        if (scenic.getSDes() != null && !"".equals(scenic.getSDes())) {
            try {
                int effectedNum = dao.insertScenic(scenic);
                if (effectedNum > 0) {
                    return true;
                } else {
                    throw new RuntimeException("插入信息失败！");
                }
            } catch (Exception e) {
                throw new RuntimeException("插入信息失败：" + e.getMessage());
            }
        } else {
            throw new RuntimeException("区域信息不能为空！");
        }
    }

    @Override
    public boolean updateScenic(scenic scenic) {
        if (scenic.getSId() > 0) {
            try {
                int effectedNum = dao.updateScenic(scenic);
                if (effectedNum > 0) {
                    return true;
                } else {
                    throw new RuntimeException("更新信息失败！");
                }
            } catch (Exception e) {
                throw new RuntimeException("更新信息失败：" + e.toString());
            }
        } else {
            throw new RuntimeException("景点Id不能为空！");
        }
    }

    @Override
    public scenic getScenicById(int scenicId) {
        return dao.getScenicById(scenicId);
    }

    @Override
    public scenic getScenicByName(String scenicName) {
        return dao.getScenicByName(scenicName);
    }

    @Transactional
    @Override
    public boolean deleteScenic(int scenicId) {
        if (scenicId > 0) {
            try {
                int effectedNum = dao.deleteScenic(scenicId);

                if (effectedNum > 0) {
                    return true;
                } else {
                    throw new RuntimeException("删除景点失败");
                }
            } catch (Exception e) {
                throw new RuntimeException("删除景点失败");
            }

        } else {
            throw new RuntimeException("景点ID有误");
        }
    }

    @Override
    public boolean deletePic(int scenicId, String addr) {
        scenic scenic = getScenicById(scenicId);
        String path = "E:/idea/image/" + addr;
        String separator = System.getProperty("file.separator");
        path.replace("/", separator);
        DeleteFileUtil.deleteFile(new File(path));
        String scenicPicPath = scenic.getSPic();
        String newScenicPicPath = "";
        String[] PicsPath = scenicPicPath.split(";");
        for (String picPath : PicsPath) {
            if (!picPath.equals(addr)) {
                newScenicPicPath += picPath + ";";
            }
        }
        com.niit.travel.entity.scenic updateScenic = new scenic();
        updateScenic.setSId(scenicId);
        updateScenic.setSPic(newScenicPicPath);
        try {
            int effectedNum = dao.updateScenic(updateScenic);
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
