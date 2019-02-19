package com.rongshun.pojo.wechat;

public class Storer {
    private Integer id;

    private String shortname;

    public Storer(Integer id, String shortname) {
        this.id = id;
        this.shortname = shortname;
    }

    public Storer() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname == null ? null : shortname.trim();
    }
}