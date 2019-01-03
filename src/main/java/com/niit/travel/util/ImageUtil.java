package com.niit.travel.util;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ImageUtil {
    private static final SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random r=new Random();

    public static String generateThumbnail(InputStream inputStream,String fileName,String targetAddr) {
        String realFileName=getRandomFileName();
        String extension=getFileExtension(fileName);
        makeDirPath(targetAddr);
        String relativeAddr=targetAddr+realFileName+extension;
        File dest=new File(PathUtil.getImgBasePath()+relativeAddr);
        try{
            Thumbnails.of(inputStream).size(200,200).outputQuality(0.8f).toFile(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return relativeAddr;
    }

    public static  String addPicture(MultipartFile file, String fileName, String targetAddr){
        String realFileName=getRandomFileName();
        String extension=getFileExtension(fileName);
        makeDirPath(targetAddr);
        String relativeAddr=targetAddr+realFileName+extension;
        try {
            file.transferTo(new File(PathUtil.getImgBasePath()+relativeAddr));
        } catch (IOException e) {
            System.out.println(e);
        }
        return relativeAddr;
    }

    /**
     * 创建目标路劲所涉及的目录
     * @param targetAddr
     */
    private static void makeDirPath(String targetAddr) {
        String realFileParentPath=PathUtil.getImgBasePath()+targetAddr;
        File dirPath=new File(realFileParentPath);
        if(!dirPath.exists()) {
            dirPath.mkdirs();
        }

    }

    /**
     * 获取输入文件流的扩展名
     * @param
     * @return
     */
    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 生成随机文件名，当前年月日小时分钟秒+五位随机数
     * @return
     */
    public static String getRandomFileName() {
        //获取五位随机数
        int rannum=r.nextInt(89999)+10000;
        String nowTimestr=sDateFormat.format(new Date());
        return nowTimestr+rannum;
    }
    public static void main(String[] args) {

    }
}
