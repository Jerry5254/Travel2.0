package com.niit.travel.service;

import com.niit.travel.entity.users;

import java.io.InputStream;
import java.util.List;

public interface usersService {
        List<users> queryUsers();
        users queryUsersById(int UId);
        boolean addUsers(users user, InputStream userIconInputStream, String fileName);
        boolean modifyUsers(users user);
        boolean deleteUsers(int UId);
        users queryUsersByMail(String UMail, String pass);
        boolean register(users user, InputStream userIconInputStream, String fileName);
        boolean checkemai(String email);
        boolean alterIcon(users user, InputStream userIconInputStream, String fileName);
        List<String> GetHistoryIcon(Integer id);
}
