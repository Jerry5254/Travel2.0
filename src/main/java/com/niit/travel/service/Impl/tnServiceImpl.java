package com.niit.travel.service.Impl;

import com.niit.travel.dao.tnDao;
import com.niit.travel.entity.tn;
import com.niit.travel.service.tnService;

import com.niit.travel.util.ImageUtil;
import com.niit.travel.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;


@Service
public class tnServiceImpl implements tnService {
    @Autowired
    private tnDao tndao;

    @Transactional
    @Override
    public boolean addTravelNote(tn TN,MultipartFile file,String fileName) {
        if(TN.getTN_Title() !=null && !"".equals(TN.getTN_Title())){
            try{
                int effectedNum=tndao.insertTravelNote(TN);
                if(effectedNum>0){
                    if(file!=null){
                        addTNPic(TN,file,fileName);
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
    public List<tn> getAllTravelNote(String status) {
        return tndao.queryTravelNote(status);
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

    @Override
    public List<tn> getTravelNoteByCity(String tncity,String status) {
        return tndao.queryTravelNoteByCity(tncity,status);
    }

    @Override
    public List<tn> getAllTravelNote() {
        return tndao.queryTravelNoteAll();
    }

    //向数据库中插入封面
    private void addTNPic(tn tn, MultipartFile file, String fileName) {
        //获取图片的相对值路径
        String dest = PathUtil.getTnPicPath(tn.getTNId());
        String tnPicAddr = ImageUtil.addPicture(file,fileName,dest);
        tn.setTN_Pics(tnPicAddr);

    }
}
