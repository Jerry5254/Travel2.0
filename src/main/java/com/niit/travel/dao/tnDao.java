package com.niit.travel.dao;

import com.niit.travel.entity.tn;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface tnDao {
        List<tn> queryTravelNote();
        List<tn> queryTravelNoteByCity(String TNCity);
        int insertTravelNote(tn TN);
        int updateTravelNote(tn TN);
        int deleteTravelNote(int TNId);
        List<tn> queryTravelNoteByAuthor(Integer AuthorId);
        tn queryTravelNoteById(Integer tnId);
        List<tn> queryTravelNoteByStatus(String status);
}
