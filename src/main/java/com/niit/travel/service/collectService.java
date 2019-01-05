package com.niit.travel.service;

import com.niit.travel.entity.collect;
import com.niit.travel.entity.tn;

import java.util.List;

public interface collectService {
    List<collect> getUserCollect(Integer userid);
    boolean insertCollect(collect collect);
    collect getCollectByTnAndUser(tn tn, Integer userid);
    boolean deleteCollect(Integer collectid);
    collect getCollectBYId(Integer collectid);
}
