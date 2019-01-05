package com.niit.travel.service;

import com.niit.travel.entity.comment;
import com.niit.travel.entity.tn;
import com.niit.travel.entity.users;

import java.util.List;

public interface commentService {
    List<comment> getCommentByNote(Integer tnid);
    boolean insertComment(comment comment);
    List<comment> getCommentByUser(Integer userid);
    boolean deleteComment(Integer coid);
}
