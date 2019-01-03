/*********************************************************
 * 文件名: scenicDao
 * 作者: 林夏媚
 * 说明:
 *********************************************************/
package com.niit.travel.dao;

import com.niit.travel.entity.scenic;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface scenicDao {
    List<scenic> queryScenic();

    List<scenic> queryScenicByCity(String SCity);

    int insertScenic(scenic scenic);

    int updateScenic(scenic scenic);

    scenic getScenicById(int SId);

    scenic getScenicByName(String SName);

    int deleteScenic(int SId);
}

