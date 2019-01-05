package com.niit.travel.dao;

import com.niit.travel.entity.collect;
import com.niit.travel.entity.tn;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface collectDao {
    List<collect> queryCollectByUser(Integer userid);
    int insertCollect(collect collect);
    collect queryCollectByUserTn(@Param("tn") tn tn, @Param("Collect_Userid") Integer userid);
    int deleteCollect(Integer collectId);
    collect queryCollectByid(Integer collectid);
}
