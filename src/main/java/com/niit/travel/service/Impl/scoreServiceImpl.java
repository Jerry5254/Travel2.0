package com.niit.travel.service.Impl;

import com.niit.travel.dao.scoreDao;
import com.niit.travel.entity.score;
import com.niit.travel.service.scoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class scoreServiceImpl implements scoreService {
    @Autowired
    private scoreDao scoredao;

    @Transactional
    @Override
    public boolean insertScore(score score) {
        try{
            int effectedNum=scoredao.insertScore(score);
            if(effectedNum>0){
                return true;
            }else{
                throw new RuntimeException("插入信息失败！");
            }
        } catch (Exception e){
            throw new RuntimeException("插入信息失败："+e.getMessage());
        }
    }

    @Override
    public Float getAvgScore(Integer noteid) {
        return scoredao.queryAvgScoreByNote(noteid);
    }

    @Override
    public boolean checkScore(Integer noteid,Integer userid) {
        score sc=scoredao.queryScoreByUserAndTN(noteid,userid);
        if(sc==null){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public List<score> getScoreByUser(Integer userid) {
        return scoredao.queryScoreByUser(userid);
    }
}
