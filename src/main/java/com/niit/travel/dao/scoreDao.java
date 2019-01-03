package com.niit.travel.dao;

import com.niit.travel.entity.score;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface scoreDao {
    int insertScore(score score);
    Float queryAvgScoreByNote(Integer noteid);
    score queryScoreByUserAndTN(@Param("SCNote_id") Integer noteid,@Param("SCUser_id") Integer userid);
    List<score> queryScoreByUser(Integer userid);
}
