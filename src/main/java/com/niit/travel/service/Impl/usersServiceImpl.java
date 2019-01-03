package com.niit.travel.service.Impl;


import com.niit.travel.dao.usersDao;
import com.niit.travel.entity.users;
import com.niit.travel.exception.UserException;
import com.niit.travel.service.usersService;
import com.niit.travel.util.GetPackageImageUtil;
import com.niit.travel.util.ImageUtil;
import com.niit.travel.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;

@Service
public class usersServiceImpl implements usersService {

    @Autowired
    private usersDao usersdao;

    @Override
    public List<users> queryUsers() {
        return usersdao.queryUsers();
    }

    @Override
    public users queryUsersById(int UId) {
        return usersdao.queryUsersById(UId);
    }

    @Transactional
    @Override
    public boolean addUsers(users user, InputStream userIconInputStream, String fileName) {
        if (user.getUName() != null && !"".equals(user.getUName())) {
            try {
                int effectedNum = usersdao.insertUsers(user);
                if (effectedNum < 0) {
                    throw new UserException("插入信息失败！");
                } else {
                    if (userIconInputStream != null) {
                        try {
                            addUserIcon(user, userIconInputStream,fileName);
                        } catch (Exception e) {
                            throw new UserException("addUserIcon error:" + e.getMessage());
                        }
                        effectedNum = usersdao.updateUsers(user);
                        if (effectedNum <= 0) {
                            throw new UserException("更新图片失败！");
                        }
                    }

                }

            } catch (Exception e) {
                throw new RuntimeException("插入信息失败：" + e.getMessage());
            }
        } else {
            throw new RuntimeException("区域信息不能为空！");
        }
        return true;
    }

    @Override
    public boolean modifyUsers(users user) {
        if (user.getUId() != null && user.getUId() > 0) {
            try {
                int effectedNum = usersdao.updateUsers(user);
                if (effectedNum > 0) {
                    return true;
                } else {
                    throw new RuntimeException("更新信息失败！");
                }
            } catch (Exception e) {
                throw new RuntimeException("更新信息失败：" + e.toString());
            }
        } else {
            throw new RuntimeException("区域信息不能为空！");
        }
    }

    @Override
    public boolean deleteUsers(int UId) {
        if (UId > 0) {
            try {
                int effectedNum = usersdao.deleteUsers(UId);
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
    public users queryUsersByMail(String UMail, String pass) {
        users user = usersdao.queryUsersByMail(UMail);
        if (user != null) {
            String UPass = user.getUPwd();
            System.out.println("正确的："+UPass);
            if (UPass.equals(pass))
                return user;
            else
                throw new RuntimeException("密码错误！");
        } else
            throw new RuntimeException("用户不存在！");


    }

    //向数据库中插入头像
    private void addUserIcon(users user, InputStream inputStream,String fileName) {
        //获取图片的相对值路径
        String dest = PathUtil.getUserImagePath(user.getUId());
        String UserImgAddr = ImageUtil.generateThumbnail(inputStream,fileName, dest);
        user.setUIcon(UserImgAddr);

    }

    @Transactional
    @Override
    public boolean register(users user,InputStream userIconInputStream, String fileName) {
        try {
            int effectedNum = usersdao.insertUsers(user);
            if (effectedNum > 0) {
                if(userIconInputStream!=null){
                    addUserIcon(user,userIconInputStream,fileName);
                    effectedNum=usersdao.updateUsers(user);
                    if(effectedNum>0)
                        return true;
                }
                return false;
            } else {
                throw new RuntimeException("插入信息失败！");
            }

        } catch (Exception e) {
            throw new RuntimeException("插入信息失败：" + e.getMessage());
        }
}

    @Override
    public boolean checkemai(String email) {
        users user=usersdao.checkEmail(email);
        if(user!=null)
            return false;
        else
            return true;
    }

    @Override
    public boolean alterIcon(users user, InputStream userIconInputStream, String fileName) {
        if(userIconInputStream!=null){
            addUserIcon(user, userIconInputStream,fileName);
            int effectNum=usersdao.updateUsers(user);
            if(effectNum>0){
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public List<String> GetHistoryIcon(Integer id) {
        List<String> list= GetPackageImageUtil.getPictures(id);
        return list;
    }


}
