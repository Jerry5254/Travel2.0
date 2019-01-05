package com.niit.travel.web;

import com.niit.travel.service.usersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping(value = "/users",method = {RequestMethod.GET})
public class UserOperationController {
    @Autowired
    private usersService userservice;

    @RequestMapping(value = "/registeruser",method= RequestMethod.GET)
    public String UserRegister(){
        return "RegisterUser";
    }

    @RequestMapping(value = "/tologin",method= RequestMethod.GET)
    public String Register(){
        return "login";
    }

    @RequestMapping(value = "/touserinfo",method= RequestMethod.GET)
    public String showUser(){
        return "userinfo";
    }



}
