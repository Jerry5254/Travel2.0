package com.niit.travel.service.Impl;

import com.niit.travel.dao.tnDao;
import com.niit.travel.entity.tn;
import com.niit.travel.service.tnService;

import com.niit.travel.util.ImageUtil;
import com.niit.travel.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;


@Service
public class tnServiceImpl implements tnService {
    @Autowired
    private tnDao tndao;

    @Transactional
    @Override
    public boolean addTravelNote(tn TN,InputStream inputStream,String fileName) {
        if(TN.getTN_Title() !=null && !"".equals(TN.getTN_Title())){
            try{
                int effectedNum=tndao.insertTravelNote(TN);
                if(effectedNum>0){
                    if(inputStream!=null){
                        addTNPic(TN,inputStream,fileName);
                        effectedNum=tndao.updateTravelNote(TN);
                        if(effectedNum<=0){
                            throw new RuntimeException("更新图片失败！");
                        }
                    }
                }else{
                    throw new RuntimeException("插入信息失败！");
                }
            } catch (Exception e){
                throw new RuntimeException("插入信息失败："+e.getMessage());
            }
        }else{
            throw new RuntimeException("区域信息不能为空！");
        }
        return true;
    }

    @Override
    public tn GettravelNoteById(Integer tnId) {
        return tndao.queryTravelNoteById(tnId);
    }

    @Override
    public List<tn> getTnByAuthorId(Integer id) {
        return tndao.queryTravelNoteByAuthor(id);
    }

    @Override
    public List<tn> getAllTravelNote() {
        return tndao.queryTravelNote();
    }

    @Override
    public List<tn> getShowTravelNote(String status) {
        return tndao.queryTravelNoteByStatus(status);
    }

    @Override
    public boolean updateTravelNote(tn Tn) {
            try{
                int effectedNum=tndao.updateTravelNote(Tn);
                if(effectedNum>0){
                    return true;
                }else{
                    throw new RuntimeException("更新信息失败！");
                }
            } catch (Exception e){
                throw new RuntimeException("更新信息失败："+e.getMessage());
            }
        }

    @Override
    public boolean deleteTravelNote(Integer id) {
        if (id > 0) {
            try {
                int effectedNum = tndao.deleteTravelNote(id);
                if (effectedNum > 0) {
                    return true;
                } else {
                    throw new RuntimeException("删除信息失败！");
                }
            } catch (Exception e) {
                throw new RuntimeException("删除信息失败：" + e.toString());
            }
        } else {
            throw new RuntimeException("区域Id不能为空！");
        }
    }

    //向数据库中插入封面
    private void addTNPic(tn tn, InputStream inputStream, String fileName) {
        //获取图片的相对值路径
        String dest = PathUtil.getTnPicPath(tn.getTNId());
        String tnPicAddr = ImageUtil.generateThumbnail(inputStream,fileName, dest);
        tn.setTN_Pics(tnPicAddr);

    }
}
