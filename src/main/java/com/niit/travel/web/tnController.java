package com.niit.travel.web;

import com.niit.travel.entity.tn;
import com.niit.travel.entity.users;
import com.niit.travel.service.tnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/tn")
public class tnController {
    @Autowired
    private tnService tnservice;

    //添加游记
    @ResponseBody
    @RequestMapping(value = "/addtravelnote",method = RequestMethod.POST)
    private Map<String, Object> addtn(HttpSession session,HttpServletRequest request){
        Map<String,Object>modelMap=new HashMap<String,Object>();
        tn travelnote=new tn();
        Integer authorid= (Integer) session.getAttribute("id");
        if(authorid!=null){
            users user=new users();
            user.setUId(authorid);
            travelnote.setUsers(user);
            DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date=dateFormat.format(new Date());
            //System.out.println(date);
            String ueditor=request.getParameter("ueditor");
            String title=request.getParameter("title");
            String city=request.getParameter("city");
            travelnote.setTN_Content(ueditor);
            travelnote.setTN_Title(title);
            travelnote.setTNCity(city);
            travelnote.setTN_Status("公开");//默认为公开
            travelnote.setTN_Date(date);
            travelnote.setTNHit_Number(0);
            MultipartFile file=((MultipartHttpServletRequest)request).getFile("pic");
            try {
                InputStream inputStream=file.getInputStream();
                boolean flag=tnservice.addTravelNote(travelnote,file,file.getOriginalFilename());
                if(flag){
                    modelMap.put("success",true);
                    modelMap.put("TravelNote",travelnote);
                }else{
                    modelMap.put("success",false);
                    modelMap.put("errMsg","添加失败！");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","您还没有登录！请登录");
        }
        return modelMap;
    }

    //获取游记信息
    @ResponseBody
    @RequestMapping(value="/gettravelnote",method=RequestMethod.GET)
    private Map<String, Object> gettn(HttpSession session){
        Map<String,Object>modelMap=new HashMap<String,Object>();
        Integer tnId= (Integer) session.getAttribute("tnid");
        tn Tn=tnservice.GettravelNoteById(tnId);
        if(Tn!=null){
            System.out.println(Tn.getTN_Title());
            modelMap.put("success",true);
            modelMap.put("showtn",Tn);
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","该游记不存在！");
        }
        return modelMap;
    }

    //转跳游记页面
    @RequestMapping(value="/totravelnote",method=RequestMethod.GET)
    private String showtn(@RequestParam("travelnoteid") Integer tnId,HttpSession session){
        session.setAttribute("tnid",tnId);//把当前想要转跳的游记的id存入session中
        return "showTravelNote";
    }

    //获取该用户写的游记
    @ResponseBody
    @RequestMapping(value = "/gettnlistbyauthor",method=RequestMethod.GET)
    private Map<String,Object> gettnlist(HttpSession session){
        Map<String,Object>modelMap=new HashMap<String,Object>();
        Integer userid= (Integer) session.getAttribute("id");//从session中获取游记id
        List<tn> tnList=tnservice.getTnByAuthorId(userid);
        if(tnList.size()==0){
            modelMap.put("success",true);
            modelMap.put("tnStatus","no");
        }else{
            modelMap.put("success",true);
            modelMap.put("tnlist",tnList);
        }
        return modelMap;
    }

    //获取所有游记
    @ResponseBody
    @RequestMapping(value="/getalltn",method = RequestMethod.GET)
    private Map<String,Object> getalltn(){
        Map<String,Object>modelMap=new HashMap<String,Object>();
        String status="公开";
        List<tn> tnlist=tnservice.getAllTravelNote(status);
        if(tnlist!=null){
            modelMap.put("success",true);
            modelMap.put("tnlist",tnlist);
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","获取失败！");
        }
        return modelMap;
    }

    //获取前六个游记
    @ResponseBody
    @RequestMapping(value="/gettnsix",method = RequestMethod.GET)
    private Map<String,Object> getatnsix(){
        Map<String,Object>modelMap=new HashMap<String,Object>();
        String status="公开";
        List<tn> tnlist=tnservice.getAllTravelNote(status);
        List<tn> six=new ArrayList<>();
        int flag = 0;
        for (tn tn : tnlist) {
            if (flag > 5) {
                break;
            }
            if(tn.getTN_Status().equals(status)){
                six.add(tn);
            }
            flag++;
        }
        if(six!=null){
            modelMap.put("success",true);
            modelMap.put("tnlistsix",six);
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","获取失败！");
        }
        return modelMap;
    }

    //获取状态为“公开”的游记
    @ResponseBody
    @RequestMapping(value="/getshowtn",method = RequestMethod.GET)
    private Map<String,Object> getshowtn() {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        String status="公开";
        List<tn> tnlist=tnservice.getShowTravelNote(status);
        if(tnlist!=null){
            modelMap.put("success",true);
            modelMap.put("tnshowlist",tnlist);
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","获取失败！");
        }
        return modelMap;
    }

    //修改游记
    @ResponseBody
    @RequestMapping(value="/updatetn",method=RequestMethod.POST)
    private Map<String,Object> updatetn(HttpServletRequest request,HttpSession session) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        tn TN=new tn();
        Integer id= (Integer) session.getAttribute("tnid");
        String content=request.getParameter("ueditor");
        String status=request.getParameter("status");
        TN.setTNId(id);
        TN.setTN_Content(content);
        TN.setTN_Status(status);
        boolean flag=tnservice.updateTravelNote(TN);
        if (flag) {
            modelMap.put("success",true);
        } else{
            modelMap.put("success",false);
            modelMap.put("errMsg","更新失败！");
        }
        return modelMap;
    }

    //转跳修改游记页面
    @RequestMapping(value="/toupdatetravelnote",method=RequestMethod.GET)
    private String toupdatetn(@RequestParam("travelnoteid") Integer tnId,HttpSession session){
        session.setAttribute("tnid",tnId);//把当前想要修改的游记的id存入session中
        return "updateTravelNote";
    }

    //删除游记
    @ResponseBody
    @RequestMapping(value="/deletetn",method=RequestMethod.POST)
    private Map<String,Object> deletetn(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        String id=request.getParameter("tnid");
        Integer tnId=Integer.parseInt(id);
        boolean flag=tnservice.deleteTravelNote(tnId);
        if (flag) {
            modelMap.put("success",true);
        } else{
            modelMap.put("success",false);
            modelMap.put("errMsg","删除失败！");
        }
        return modelMap;
    }

    @RequestMapping(value = "/toalltravel",method= RequestMethod.GET)
    public String toAllTravel(){
        return "showAllTravelNotes";
    }

    //获取该城市的游记
    @ResponseBody
    @RequestMapping(value="/gettravelnotebycity",method=RequestMethod.GET)
    private Map<String,Object> tncity(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        String city=request.getParameter("cityname");
        List<tn> tnlist=tnservice.getTravelNoteByCity(city);
        modelMap.put("success",true);
        modelMap.put("tncitylist",tnlist);
        return modelMap;
    }

}
