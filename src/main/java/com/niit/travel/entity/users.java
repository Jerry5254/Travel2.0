package com.niit.travel.entity;

public class users {
    private Integer UId;
    private String UName;
    private String UPwd;
    private String UMobile;
    private String UMail;
    private String UGender;
    private String UIcon;

    public users() {
    }

    @Override
    public String toString() {
        return "users{" +
                "UId=" + UId +
                ", UName='" + UName + '\'' +
                ", UPwd='" + UPwd + '\'' +
                ", UMobile='" + UMobile + '\'' +
                ", UMail='" + UMail + '\'' +
                ", UGender='" + UGender + '\'' +
                ", UIcon='" + UIcon + '\'' +
                '}';
    }

    public users(Integer UId, String UName, String UPwd, String UMobile, String UMail, String UGender, String UIcon) {
        this.UId = UId;
        this.UName = UName;
        this.UPwd = UPwd;
        this.UMobile = UMobile;
        this.UMail = UMail;
        this.UGender = UGender;
        this.UIcon = UIcon;
    }

    public Integer getUId() {
        return UId;
    }

    public void setUId(Integer UId) {
        this.UId = UId;
    }

    public String getUName() {
        return UName;
    }

    public void setUName(String UName) {
        this.UName = UName;
    }

    public String getUPwd() {
        return UPwd;
    }

    public void setUPwd(String UPwd) {
        this.UPwd = UPwd;
    }

    public String getUMobile() {
        return UMobile;
    }

    public void setUMobile(String UMobile) {
        this.UMobile = UMobile;
    }

    public String getUMail() {
        return UMail;
    }

    public void setUMail(String UMail) {
        this.UMail = UMail;
    }

    public String getUGender() {
        return UGender;
    }

    public void setUGender(String UGender) {
        this.UGender = UGender;
    }

    public String getUIcon() {
        return UIcon;
    }

    public void setUIcon(String UIcon) {
        this.UIcon = UIcon;
    }
}
