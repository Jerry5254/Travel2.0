/*********************************************************
 * 文件名: Food
 * 作者: 魏捷宇
 * 说明:
 *********************************************************/
package com.niit.travel.entity;

public class Food {
    private Integer FId;
    private String FName;
    private String FCity;
    private String FDes;
    private String FPic;

    public Integer getFId() {
        return FId;
    }

    public void setFId(Integer FId) {
        this.FId = FId;
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public String getFCity() {
        return FCity;
    }

    public void setFCity(String FCity) {
        this.FCity = FCity;
    }

    public String getFDes() {
        return FDes;
    }

    public void setFDes(String FDes) {
        this.FDes = FDes;
    }

    public String getFPic() {
        return FPic;
    }

    public void setFPic(String FPic) {
        this.FPic = FPic;
    }
}
