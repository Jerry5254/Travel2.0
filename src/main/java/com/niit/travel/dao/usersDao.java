package com.niit.travel.dao;

import com.niit.travel.entity.users;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface usersDao {
    List<users> queryUsers();
    users queryUsersById(int UId);
    users queryUsersByMail(String UMail);
    int insertUsers(users user);
    int updateUsers(users user);
    int deleteUsers(int UId);
    users checkEmail(String email);
}
