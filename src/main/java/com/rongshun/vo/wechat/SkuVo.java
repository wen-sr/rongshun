package com.rongshun.vo.wechat;

import java.util.Date;

public class SkuVo {
    private Integer id;

    private String name;

    private String adddate;

    private String addwho;

    private Integer isFooId;

    private Integer skuid;

    private Integer fooId;

    private String fooName;

    private Integer qty;

    public String getFooName() {
        return fooName;
    }

    public void setFooName(String fooName) {
        this.fooName = fooName;
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
        this.name = name;
    }

    public String getAddwho() {
        return addwho;
    }

    public void setAddwho(String addwho) {
        this.addwho = addwho;
    }

    public Integer getIsFooId() {
        return isFooId;
    }

    public void setIsFooId(Integer isFooId) {
        this.isFooId = isFooId;
    }

    public Integer getSkuid() {
        return skuid;
    }

    public void setSkuid(Integer skuid) {
        this.skuid = skuid;
    }

    public Integer getFooId() {
        return fooId;
    }

    public void setFooId(Integer fooId) {
        this.fooId = fooId;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getAdddate() {
        return adddate;
    }

    public void setAdddate(String adddate) {
        this.adddate = adddate;
    }
}
