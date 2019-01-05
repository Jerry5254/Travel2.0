package com.niit.travel.web;

import com.niit.travel.entity.collect;
import com.niit.travel.entity.tn;
import com.niit.travel.service.collectService;
import com.niit.travel.service.tnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/collect")
public class collectController {
    @Autowired
    private collectService collectservice;
    @Autowired
    private tnService tnservice;

    @RequestMapping(value = "/showcollect",method = RequestMethod.GET)
    private Map<String, Object> showCollect(HttpSession session){
        Map<String,Object>modelMap=new HashMap<String,Object>();
        Integer userid= (Integer) session.getAttribute("id");
        List<collect> commentList=collectservice.getUserCollect(userid);
        modelMap.put("success",true);
        modelMap.put("collectlist",commentList);
        return modelMap;
    }

    @RequestMapping(value = "/insertcollect",method = RequestMethod.POST)
    private Map<String, Object> insertCollect(HttpSession session){
        Map<String,Object>modelMap=new HashMap<String,Object>();
        Integer userid= (Integer) session.getAttribute("id");
        if(userid!=null){
            collect collect=new collect();
            collect.setCollect_Userid(userid);
            Integer tnid= (Integer) session.getAttribute("tnid");
            tn tn=new tn();
            tn.setTNId(tnid);
            collect.setTn(tn);
            DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date=dateFormat.format(new Date());
            collect.setCollect_Date(date);
            boolean flag=collectservice.insertCollect(collect);
            if(flag){
                tn tn2=tnservice.GettravelNoteById(tnid);
                Integer number=tn2.getTNHit_Number()+1;
                tn2.setTNHit_Number(number);
                tnservice.updateTravelNote(tn2);
                System.out.println("number="+tn2.getTNHit_Number()+" number2="+number);
                modelMap.put("success",true);
            }else{
                modelMap.put("success",false);
                modelMap.put("errMsg","收藏失败！");
            }
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","请登录后再操作！");
        }
        return modelMap;
    }

    @RequestMapping(value = "/getcollectUserTn",method = RequestMethod.GET)
    private Map<String, Object> getcollectUserTn(HttpSession session){
        Map<String,Object>modelMap=new HashMap<String,Object>();
        Integer tnid= (Integer) session.getAttribute("tnid");
        Integer userid= (Integer) session.getAttribute("id");
        tn tn=new tn();
        tn.setTNId(tnid);
        collect c=collectservice.getCollectByTnAndUser(tn,userid);
        if(c!=null){
            modelMap.put("success",true);
            modelMap.put("status","已收藏");
            modelMap.put("collect",c);
        }else{
            modelMap.put("success",true);
            modelMap.put("status","未收藏");
        }
        return modelMap;
    }

    @RequestMapping(value = "/deletecollect",method = RequestMethod.POST)
    private Map<String, Object> deleteCollect(HttpServletRequest request){
        Map<String,Object>modelMap=new HashMap<String,Object>();
        String id=request.getParameter("collectid");
        Integer collectid=Integer.parseInt(id);
        String tnid=request.getParameter("tnid");
        Integer tnid2=Integer.parseInt(tnid);
        tn tn=tnservice.GettravelNoteById(tnid2);
        Integer number=tn.getTNHit_Number()-1;
        tn.setTNHit_Number(number);
        tnservice.updateTravelNote(tn);
        System.out.println("number="+tn.getTNHit_Number()+" number2="+number);
        boolean flag=collectservice.deleteCollect(collectid);
        System.out.println(flag);
        if(flag){
            modelMap.put("success",true);
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","删除失败！");
        }
        return modelMap;
    }
}
