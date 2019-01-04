package com.niit.travel.entity;

public class comment {
    private Integer COId;
    private tn tn;
    private String CODate;
    private String COStatus;
    private String CODetails;
    private users users;

    public com.niit.travel.entity.tn getTn() {
        return tn;
    }

    public void setTn(com.niit.travel.entity.tn tn) {
        this.tn = tn;
    }

    public com.niit.travel.entity.users getUsers() {
        return users;
    }

    public void setUsers(com.niit.travel.entity.users users) {
        this.users = users;
    }

    public Integer getCOId() {
        return COId;
    }

    public void setCOId(Integer COId) {
        this.COId = COId;
    }


    public String getCODate() {
        return CODate;
    }

    public void setCODate(String CODate) {
        this.CODate = CODate;
    }

    public String getCOStatus() {
        return COStatus;
    }

    public void setCOStatus(String COStatus) {
        this.COStatus = COStatus;
    }

    public String getCODetails() {
        return CODetails;
    }

    public void setCODetails(String CODetails) {
        this.CODetails = CODetails;
    }
}
