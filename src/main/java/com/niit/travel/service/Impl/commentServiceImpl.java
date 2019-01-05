package com.niit.travel.service.Impl;

import com.niit.travel.dao.commentDao;
import com.niit.travel.entity.comment;
import com.niit.travel.entity.tn;
import com.niit.travel.entity.users;
import com.niit.travel.service.commentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class commentServiceImpl implements commentService {
    @Autowired
    private commentDao commentdao;

    @Override
    public List<comment> getCommentByNote(Integer tnid) {
        return commentdao.queryCommentByNote(tnid);
    }

    @Transactional
    @Override
    public boolean insertComment(comment comment) {
        try{
            int effectNum=commentdao.insertComment(comment);
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
    public List<comment> getCommentByUser(Integer userid) {
        return commentdao.queryCommentByUser(userid);
    }

    @Override
    public boolean deleteComment(Integer coid) {
        int effectedNum=commentdao.deleteComment(coid);
        if(effectedNum>0){
            return true;
        }else {
            throw new RuntimeException("删除信息失败！");
        }
    }
}

