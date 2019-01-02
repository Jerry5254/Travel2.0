/*********************************************************
 * 文件名: scenicService
 * 作者: 林夏媚
 * 说明:
 *********************************************************/
package com.niit.travel.service;

import com.niit.travel.entity.scenic;

import java.util.List;

public interface scenicService {
    List<scenic> queryScenic();

    List<scenic> queryScenicByCity(String SCity);

    boolean insertScenic(scenic scenic);

    boolean updateScenic(scenic scenic);

    scenic getScenicById(int scenicId);

    scenic getScenicByName(String scenicName);

    boolean deleteScenic(int scenicId);

    boolean deletePic(int scenicId, String addr);
}
