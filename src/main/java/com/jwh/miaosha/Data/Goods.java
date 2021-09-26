package com.jwh.miaosha.Data;

public class Goods {
    private Integer id;

    private String goodname;

    private String gooddescribe;

    private Integer goodnum;

    private Double goodprice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoodname() {
        return goodname;
    }

    public void setGoodname(String goodname) {
        this.goodname = goodname;
    }

    public String getGooddescribe() {
        return gooddescribe;
    }

    public void setGooddescribe(String gooddescribe) {
        this.gooddescribe = gooddescribe;
    }

    public Integer getGoodnum() {
        return goodnum;
    }

    public void setGoodnum(Integer goodnum) {
        this.goodnum = goodnum;
    }

    public Double getGoodprice() {
        return goodprice;
    }

    public void setGoodprice(Double goodprice) {
        this.goodprice = goodprice;
    }
}