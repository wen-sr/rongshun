package com.rongshun.pojo.wechat;

import java.util.Date;

public class Sku {
    private Integer id;

    private String name;

    private Date adddate;

    private String addwho;

    public Sku(Integer id, String name, Date adddate, String addwho) {
        this.id = id;
        this.name = name;
        this.adddate = adddate;
        this.addwho = addwho;
    }

    public Sku() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getAdddate() {
        return adddate;
    }

    public void setAdddate(Date adddate) {
        this.adddate = adddate;
    }

    public String getAddwho() {
        return addwho;
    }

    public void setAddwho(String addwho) {
        this.addwho = addwho == null ? null : addwho.trim();
    }
}