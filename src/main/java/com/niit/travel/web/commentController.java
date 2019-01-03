package com.niit.travel.web;

import com.niit.travel.entity.comment;
import com.niit.travel.entity.users;
import com.niit.travel.service.commentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("/comment")
public class commentController{
    @Autowired
    private commentService commentservice;

    //获取该游记的所有评论
    @RequestMapping(value = "/showcomment",method = RequestMethod.GET)
    private Map<String, Object> showComment(HttpSession session){
        Map<String,Object>modelMap=new HashMap<String,Object>();
        Integer tnid= (Integer) session.getAttribute("tnid");
        List<comment> commentList=commentservice.getCommentByNote(tnid);
        System.out.println("tnid:"+tnid);
        System.out.println(commentList);
        modelMap.put("success",true);
        modelMap.put("colist",commentList);
        return modelMap;
    }

    //添加评论
    @RequestMapping(value = "/addcomment",method = RequestMethod.POST)
    private Map<String, Object> addComment(HttpSession session,HttpServletRequest request){
        Map<String,Object>modelMap=new HashMap<String,Object>();
        Integer userid= (Integer) session.getAttribute("id");
        if(userid!=null){
            Integer tnid= (Integer) session.getAttribute("tnid");
            DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date=dateFormat.format(new Date());
            String details=request.getParameter("details");
            comment comment=new comment();
            users user=new users();
            user.setUId(userid);
            comment.setCODate(date);
            comment.setCODetails(details);
            comment.setCOTN_id(tnid);
            comment.setUsers(user);
            comment.setCOStatus("Z");
            boolean flag=commentservice.insertComment(comment);
            if(flag){
                modelMap.put("success",true);
            }else {
                modelMap.put("success",false);
                modelMap.put("errMsg","评论失败！");
            }
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","请先登录！");
        }
        return modelMap;
    }

    //获取该用户的所有评论
    @RequestMapping(value = "/showusercomment",method = RequestMethod.GET)
    private Map<String, Object> showUserComment(HttpSession session){
        Map<String,Object>modelMap=new HashMap<String,Object>();
        Integer userid= (Integer) session.getAttribute("id");
        List<comment> list=commentservice.getCommentByUser(userid);
        modelMap.put("success",true);
        modelMap.put("usercommentlist",list);
        return modelMap;
    }

    @RequestMapping(value="/deletecomment",method=RequestMethod.POST)
    private Map<String, Object> deleteComment(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        String id=request.getParameter("commentid");
        Integer coId=Integer.parseInt(id);
        boolean flag=commentservice.deleteComment(coId);
        if (flag) {
            modelMap.put("success",true);
        } else{
            modelMap.put("success",false);
            modelMap.put("errMsg","删除失败！");
        }
        return modelMap;
    }
}
