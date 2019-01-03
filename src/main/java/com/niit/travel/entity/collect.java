package com.niit.travel.entity;

public class collect {
    Integer Collect_Id;
    tn tn;
    Integer Collect_Userid;
    String Collect_Date;

    public Integer getCollect_Id() {
        return Collect_Id;
    }

    public void setCollect_Id(Integer collect_Id) {
        Collect_Id = collect_Id;
    }

    public com.niit.travel.entity.tn getTn() {
        return tn;
    }

    public void setTn(com.niit.travel.entity.tn tn) {
        this.tn = tn;
    }

    public Integer getCollect_Userid() {
        return Collect_Userid;
    }

    public void setCollect_Userid(Integer collect_Userid) {
        Collect_Userid = collect_Userid;
    }

    public String getCollect_Date() {
        return Collect_Date;
    }

    public void setCollect_Date(String collect_Date) {
        Collect_Date = collect_Date;
    }
}
