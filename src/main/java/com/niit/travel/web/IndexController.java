/*********************************************************
 * 文件名: IndexController
 * 作者: 魏捷宇
 * 说明:
 *********************************************************/
package com.niit.travel.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {
    @RequestMapping(value = "/index")
    public String testDemo() {
        return "index";
    }

    @RequestMapping(value = "/addfood")
    public String addFood() {
        return "addfood";
    }


    @RequestMapping(value = "/city")
    public String city() {
        return "citytest";
    }

    @RequestMapping(value = "/food")
    public String showFood() {
        return "showfood";
    }

    @RequestMapping(value = "/admin")
    public String showAdmin(HttpSession session) {
        Integer id= (Integer) session.getAttribute("id");
        if(id!=null){
            if(id==1){
                return "Admin";
            }else{
                return "index";
            }
        }else{
            return "index";
        }
    }
}
