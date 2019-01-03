/*********************************************************
 * 文件名: IndexController
 * 作者: 魏捷宇
 * 说明:
 *********************************************************/
package com.niit.travel.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
