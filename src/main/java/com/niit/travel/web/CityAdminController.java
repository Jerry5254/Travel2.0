/*********************************************************
 * 文件名: CityAdminController
 * 作者: 魏捷宇
 * 说明:
 *********************************************************/
package com.niit.travel.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/city")
public class CityAdminController {

    @RequestMapping(value = "/getcity")
    public String getCity() {
        return "showcity";
    }
}
