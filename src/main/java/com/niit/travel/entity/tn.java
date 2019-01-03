package com.niit.travel.entity;


public class tn {
    private Integer TNId;
    private users users;
    private String TN_Date;
    private String TN_Title;
    private String TN_Content;
    private String TN_Status;
    private String TN_Pics;
    private Integer TNHit_Number;
    private String TNCity;

    public com.niit.travel.entity.users getUsers() {
        return users;
    }

    public void setUsers(com.niit.travel.entity.users users) {
        this.users = users;
    }

    public Integer getTNId() {
        return TNId;
    }

    public void setTNId(Integer TNId) {
        this.TNId = TNId;
    }


    public String getTN_Date() {
        return TN_Date;
    }

    public void setTN_Date(String TN_Date) {
        this.TN_Date = TN_Date;
    }

    public String getTN_Title() {
        return TN_Title;
    }

    public void setTN_Title(String TN_Title) {
        this.TN_Title = TN_Title;
    }

    public String getTN_Content() {
        return TN_Content;
    }

    public void setTN_Content(String TN_Content) {
        this.TN_Content = TN_Content;
    }

    public String getTN_Status() {
        return TN_Status;
    }

    public void setTN_Status(String TN_Status) {
        this.TN_Status = TN_Status;
    }

    public String getTN_Pics() {
        return TN_Pics;
    }

    public void setTN_Pics(String TN_Pics) {
        this.TN_Pics = TN_Pics;
    }

    public Integer getTNHit_Number() {
        return TNHit_Number;
    }

    public void setTNHit_Number(Integer TNHit_Number) {
        this.TNHit_Number = TNHit_Number;
    }

    public String getTNCity() {
        return TNCity;
    }

    public void setTNCity(String TNCity) {
        this.TNCity = TNCity;
    }
}
