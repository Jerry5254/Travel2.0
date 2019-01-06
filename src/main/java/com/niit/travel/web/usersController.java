package com.niit.travel.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.niit.travel.entity.users;
import com.niit.travel.service.usersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class usersController {
    @Autowired
    private usersService userservice;

    @RequestMapping(value = "/listusers",method = RequestMethod.GET)
    private Map<String,Object> listusers(){
        Map<String,Object> modelMap=new HashMap<String,Object>();
        List<users> list=userservice.queryUsers();
        modelMap.put("userslist",list);
        return modelMap;
    }

    //获取用户信息
    @RequestMapping(value = "/getuserbyid",method = RequestMethod.GET)
    private Map<String,Object> getUserById(Integer userid, HttpSession session){
        Map<String,Object>modelMap=new HashMap<String,Object>();
        userid= (Integer) session.getAttribute("id");
        users user=userservice.queryUsersById(userid);
        if(user!=null){
            //System.out.println("userid:"+userid);
           // System.out.println("username:"+user.getUName());
            modelMap.put("success",true);
            modelMap.put("user",user);
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","获取信息失败！");
        }
        return modelMap;
    }

    //用户注册
    @RequestMapping(value = "/adduser",method = RequestMethod.POST)
    public Map<String, Object> adduser(HttpServletRequest request){
        Map<String,Object>modelMap=new HashMap<String,Object>();
        users user=null;
        String userInfo=request.getParameter("userInfo");
        //System.out.println("user:"+userInfo);
        ObjectMapper mapper=new ObjectMapper();
        try{
            user=mapper.readValue(userInfo,users.class);
            System.out.println("userName:"+user.getUName());
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            System.out.println("error:"+e.getMessage());
        }
        //获取用户上传的头像
        MultipartFile file=((MultipartHttpServletRequest)request).getFile("userIcon");
        try {
            InputStream inputStream=file.getInputStream();
            String mail=request.getParameter("email");
            boolean re=userservice.checkemai(mail);
            if(re){
                boolean flag=userservice.register(user,inputStream,file.getOriginalFilename());
                if(flag)
                    modelMap.put("success",true);
                else{
                    modelMap.put("success",false);
                    modelMap.put("errMsg","添加失败");
                }
            }else{
                modelMap.put("success",false);
                modelMap.put("errMsg","该邮箱已被注册！");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return modelMap;
    }

    //修改用户信息
    @RequestMapping(value = "/modifyuser",method = RequestMethod.POST)
    private Map<String, Object> modifyuser(HttpServletRequest request){
        Map<String,Object>modelMap=new HashMap<String,Object>();
        users user=null;
        String userInfo=request.getParameter("userInfo");
        ObjectMapper mapper=new ObjectMapper();
        try {
            user=mapper.readValue(userInfo,users.class);
            boolean flag=userservice.modifyUsers(user);
            if(flag=true){
                modelMap.put("success",true);
            }else{
                modelMap.put("success",false);
                modelMap.put("errMsg","更新信息失败！");
            }
        } catch (IOException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
        }
//        boolean flag=userservice.modifyUsers(user);
//        if(flag=true){
//            modelMap.put("success",true);
//        }else{
//            modelMap.put("success",false);
//            modelMap.put("errMsg","更新信息失败！");
//        }
        return modelMap;
    }

    @RequestMapping(value = "/removeuser",method = RequestMethod.GET)
    private Map<String,Object> removeUser(Integer userID){
        Map<String,Object>modelMap=new HashMap<String,Object>();
        modelMap.put("success",userservice.deleteUsers(userID));
        return  modelMap;
    }

    //用户登录
    @RequestMapping(value = "/login",method = {RequestMethod.GET, RequestMethod.POST})
    private Map<String,Object> login(HttpServletRequest request, HttpSession session){
        Map<String,Object>modelMap=new HashMap<String,Object>();
        String email=request.getParameter("email");
        String pass=request.getParameter("password");
        System.out.println(email);
        System.out.println(pass);
        users user=userservice.queryUsersByMail(email,pass);
        if(user!=null){
            modelMap.put("success",true);
            modelMap.put("userInfo",user);
            Integer id=user.getUId();
            session.setAttribute("id",id);
            System.out.println("session id:"+session.getAttribute("id"));
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","用户名或密码错误！");
        }
        return modelMap;
    }

    //判断导航栏
    @RequestMapping(value = "/islogin",method = RequestMethod.GET)
    private Map<String,Object> daohang(HttpSession session){
        Map<String,Object>modelMap=new HashMap<String,Object>();
        Integer id= (Integer) session.getAttribute("id");
        if(id!=null){
            users user=userservice.queryUsersById(id);
            if(id==1){
                modelMap.put("success",true);
                modelMap.put("users",user);
                modelMap.put("status",1);//登录人为管理员
            }else{
                modelMap.put("success",true);
                modelMap.put("users",user);
                modelMap.put("status",2);//登录人为用户
            }
        }else{
            modelMap.put("success",true);
            modelMap.put("status",3);//未登录
        }
        return modelMap;
    }

    //退出登录
    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    private Map<String, Object> logout(HttpSession session){
        Map<String,Object>modelMap=new HashMap<String,Object>();
        session.removeAttribute("id");
        modelMap.put("success",true);
        return modelMap;
    }

    //修改用户头像
    @RequestMapping(value = "/altericon",method = RequestMethod.POST)
    private Map<String, Object> AlterIcon(HttpServletRequest request){
        Map<String,Object>modelMap=new HashMap<String,Object>();
        users user=null;
        String userInfo=request.getParameter("userInfo");
        ObjectMapper mapper=new ObjectMapper();
        try {
            user=mapper.readValue(userInfo,users.class);
        } catch (IOException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
        }
        MultipartFile file=((MultipartHttpServletRequest)request).getFile("userIcon");
        try {
            InputStream inputStream=file.getInputStream();
            String fileName=file.getOriginalFilename();
            boolean flag=userservice.alterIcon(user,inputStream,fileName);
            if(flag){
                modelMap.put("success",true);
            }else{
                modelMap.put("success",false);
                modelMap.put("errMsg","修改头像失败！");
            }
        } catch (IOException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }

    //获取用户历史头像
    @RequestMapping(value = "/gethistoryicon",method = RequestMethod.GET)
    private Map<String, Object> gethistryicon(HttpSession session) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Integer id= (Integer) session.getAttribute("id");
        System.out.println("his:"+id);
        List<String>list=userservice.GetHistoryIcon(id);
        System.out.println("con"+list);
        if(list!=null){
            System.out.println("listController:"+list);
            modelMap.put("success",true);
            modelMap.put("HistoryIconList",list);
        }else{
            modelMap.put("success",false);
        }
        return modelMap;
    }

}





