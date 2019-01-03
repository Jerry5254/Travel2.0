package com.niit.travel.service;

import com.niit.travel.entity.score;

import java.util.List;

public interface scoreService {
    boolean insertScore(score score);
    Float getAvgScore(Integer noteid);
    boolean checkScore(Integer noteid,Integer userid);//检查改用户是否给该游记评过分
    List<score> getScoreByUser(Integer userid);
}
