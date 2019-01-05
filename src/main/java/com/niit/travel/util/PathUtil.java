package com.niit.travel.util;

public class PathUtil {
    private static String separator=System.getProperty("file.separator");
    public static String getImgBasePath() {
        String os=System.getProperty("os.name");
        String basePath="";
        if(os.toLowerCase().startsWith("win")) {
            basePath="E:/idea/image/";
        }else {
            basePath="/home/Summer/image/";
        }
        basePath=basePath.replace("/", separator);
        return basePath;
    }
    public static String getUserImagePath(long userid) {
        String imagePath="upload/item/users/"+userid+"/";
        return imagePath.replace("/", separator);
    }

    public static String getTnPicPath(long tnid) {
        String imagePath="upload/item/travelnote/"+tnid+"/";
        return imagePath.replace("/", separator);
    }

    public static String getCityImagePath(int cityId) {
        String imagePath="upload/item/city/"+cityId+"/";
        return imagePath.replace("/", separator);
    }

    public static String getScenicImagePath(int scenicId) {
        String imagePath="upload/item/scenic/"+scenicId+"/";
        return imagePath.replace("/", separator);
    }

    public static String getFoodImagePath(int foodId) {
        String imagePath = "upload/item/food/" + foodId + "/";
        return imagePath.replace("/", separator);
    }

}
