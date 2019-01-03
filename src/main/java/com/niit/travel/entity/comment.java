package com.niit.travel.entity;

public class comment {
    private Integer COId;
    private Integer COTN_id;
    private String CODate;
    private String COStatus;
    private String CODetails;
    private users users;

    public Integer getCOTN_id() {
        return COTN_id;
    }

    public void setCOTN_id(Integer COTN_id) {
        this.COTN_id = COTN_id;
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
