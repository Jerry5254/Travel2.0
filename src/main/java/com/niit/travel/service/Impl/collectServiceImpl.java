package com.niit.travel.service.Impl;

import com.niit.travel.dao.collectDao;
import com.niit.travel.entity.collect;
import com.niit.travel.entity.tn;
import com.niit.travel.service.collectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class collectServiceImpl implements collectService {
    @Autowired
    private collectDao collectdao;

    @Override
    public List<collect> getUserCollect(Integer userid) {
        return collectdao.queryCollectByUser(userid);
    }

    @Transactional
    @Override
    public boolean insertCollect(collect collect) {
        try{
            int effectNum=collectdao.insertCollect(collect);
            if(effectNum>0){
                return true;
            }else{
                throw new RuntimeException("插入信息失败！");
            }
        } catch (Exception e){
            throw new RuntimeException("插入信息失败："+e.getMessage());
        }
    }

    @Override
    public collect getCollectByTnAndUser(tn tn, Integer userid){
        return collectdao.queryCollectByUserTn(tn,userid);
    }

    @Override
    public boolean deleteCollect(Integer collectid) {
        int effectedNum=collectdao.deleteCollect(collectid);
        if(effectedNum>0){
            return true;
        }else {
            throw new RuntimeException("删除信息失败！");
        }
    }
}
