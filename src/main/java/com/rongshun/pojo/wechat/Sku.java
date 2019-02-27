package com.rongshun.pojo.wechat;

import java.util.Date;
import java.util.Objects;

public class Sku {
    private Integer id;

    private String name;

    private Date adddate;

    private String addwho;

    private Integer fooId;

    public Sku(Integer id, String name, Date adddate, String addwho, Integer fooId) {
        this.id = id;
        this.name = name;
        this.adddate = adddate;
        this.addwho = addwho;
        this.fooId = fooId;
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

    public Integer getFooId() {
        return fooId;
    }

    public void setFooId(Integer fooId) {
        this.fooId = fooId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sku sku = (Sku) o;
        return Objects.equals(id, sku.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}