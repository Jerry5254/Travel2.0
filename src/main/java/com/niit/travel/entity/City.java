/*********************************************************
 * 文件名: City
 * 作者: 魏捷宇
 * 说明:
 *********************************************************/
package com.niit.travel.entity;

public class City {
    private Integer CId;
    private String CName;
    private String CStatus;
    private String CDes;
    private String CPic;
    private Integer CHitNumber;

    public City() {
    }

    public City(Integer CId, String CName, String CStatus, String CDes, String CPic, Integer CHitNumber) {
        this.CId = CId;
        this.CName = CName;
        this.CStatus = CStatus;
        this.CDes = CDes;
        this.CPic = CPic;
        this.CHitNumber = CHitNumber;
    }

    public City(Integer CId, String CName, String CDes) {
        this.CId = CId;
        this.CName = CName;
        this.CDes = CDes;
    }

    public Integer getCId() {
        return CId;
    }

    public void setCId(Integer CId) {
        this.CId = CId;
    }

    public String getCName() {
        return CName;
    }

    public void setCName(String CName) {
        this.CName = CName;
    }

    public String getCStatus() {
        return CStatus;
    }

    public void setCStatus(String CStatus) {
        this.CStatus = CStatus;
    }

    public String getCDes() {
        return CDes;
    }

    public void setCDes(String CDes) {
        this.CDes = CDes;
    }

    public String getCPic() {
        return CPic;
    }

    public void setCPic(String CPic) {
        this.CPic = CPic;
    }

    public Integer getCHitNumber() {
        return CHitNumber;
    }

    public void setCHitNumber(Integer CHitNumber) {
        this.CHitNumber = CHitNumber;
    }
}
