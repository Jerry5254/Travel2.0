package com.niit.travel.dao;

import com.niit.travel.entity.comment;
import com.niit.travel.entity.tn;
import com.niit.travel.entity.users;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface commentDao {
    List<comment> queryCommentByNote(Integer tnid);
    int insertComment(comment comment);
    List<comment> queryCommentByUser(Integer userid);
    int deleteComment(Integer coid);
}
