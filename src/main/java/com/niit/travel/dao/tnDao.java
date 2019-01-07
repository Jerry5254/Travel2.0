package com.niit.travel.dao;

import com.niit.travel.entity.tn;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface tnDao {
        List<tn> queryTravelNote(String status);//根据用户收藏人数排列
        List<tn> queryTravelNoteByCity(@Param("TNCity")String TNCity,@Param("TN_Status")String status);
        int insertTravelNote(tn TN);
        int updateTravelNote(tn TN);
        int deleteTravelNote(int TNId);
        List<tn> queryTravelNoteByAuthor(Integer AuthorId);
        tn queryTravelNoteById(Integer tnId);
        List<tn> queryTravelNoteByStatus(String status);
        List<tn> queryTravelNoteAll();//所有游记
}
