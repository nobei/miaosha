package com.jwh.miaosha.Data;

import java.util.Date;

public class Order {
    private Integer id;

    private Integer goodid;

    private Integer userid;

    private Integer bugcount;

    private Date bugtime;

    private Integer status;

    private Double bugprice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoodid() {
        return goodid;
    }

    public void setGoodid(Integer goodid) {
        this.goodid = goodid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getBugcount() {
        return bugcount;
    }

    public void setBugcount(Integer bugcount) {
        this.bugcount = bugcount;
    }

    public Date getBugtime() {
        return bugtime;
    }

    public void setBugtime(Date bugtime) {
        this.bugtime = bugtime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getBugprice() {
        return bugprice;
    }

    public void setBugprice(Double bugprice) {
        this.bugprice = bugprice;
    }
}