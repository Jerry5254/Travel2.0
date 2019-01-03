package com.niit.travel.web;

import com.niit.travel.entity.score;
import com.niit.travel.service.scoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/score")
public class scoreController {
    @Autowired
    private scoreService scoreservice;

    //添加评分
    @RequestMapping(value = "/addscore",method = RequestMethod.POST)
    private Map<String, Object> addscore(HttpSession session, HttpServletRequest request){
        Map<String,Object>modelMap=new HashMap<String,Object>();
        Integer userid= (Integer) session.getAttribute("id");
        if(userid!=null){
            score sc=new score();
            String tnid=request.getParameter("tnid");
            String authorid=request.getParameter("authorid");
            Integer authorid1=Integer.parseInt(authorid);
            Integer tnid1=Integer.parseInt(tnid);
            if(userid==authorid1){
                modelMap.put("success",false);
                modelMap.put("errMsg","您不能为自己的游记评分！");
            }else{
                sc.setSCUser_id(userid);
                sc.setSCNote_id(tnid1);
                System.out.println(tnid1);
                boolean flag2=scoreservice.checkScore(tnid1,userid);
                if(flag2){
                    String score=request.getParameter("score");
                    Float score1=Float.parseFloat(score);
                    sc.setSCUser_id(userid);
                    sc.setSCNote_id(tnid1);
                    sc.setSCScore(score1);
                    boolean flag=scoreservice.insertScore(sc);
                    if (flag){
                        modelMap.put("success",true);
                    }else{
                        modelMap.put("success",false);
                        modelMap.put("errMsg","插入失败！");
                    }
                }else{
                    modelMap.put("success",false);
                    modelMap.put("errMsg","您已为该游记评过分了！");
                }
            }
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","请登录后再操作！");
        }
        return modelMap;
    }

    //获得平均分
    @RequestMapping(value = "/getavgscore",method = RequestMethod.GET)
    private Map<String, Object> getavgscore(HttpSession session, HttpServletRequest request){
        Map<String,Object>modelMap=new HashMap<String,Object>();
        Integer noteid= (Integer) session.getAttribute("tnid");
        Float avg=scoreservice.getAvgScore(noteid);
        modelMap.put("success",true);
        modelMap.put("avg",avg);
        return modelMap;
    }

    //获取该用户的所有评分
    @RequestMapping(value = "/getscorebyuser",method = RequestMethod.GET)
    private Map<String, Object> getscorebyuser(HttpSession session){
        Map<String,Object>modelMap=new HashMap<String,Object>();
        Integer userid= (Integer) session.getAttribute("id");
        List<score> list=scoreservice.getScoreByUser(userid);
        modelMap.put("success",true);
        modelMap.put("scorelist",list);
        return modelMap;
    }
}
