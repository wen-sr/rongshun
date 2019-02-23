package com.rongshun.pojo.wechat;

import java.util.Date;

public class Receipt {
    private Integer id;

    private Integer skuId;

    private String skuName;

    private Double price;

    private Integer qty;

    private String supplier;

    private String addwho;

    private Date adddate;

    public Receipt(Integer id, Integer skuId, String skuName, Double price, Integer qty, String supplier, String addwho, Date adddate) {
        this.id = id;
        this.skuId = skuId;
        this.skuName = skuName;
        this.price = price;
        this.qty = qty;
        this.supplier = supplier;
        this.addwho = addwho;
        this.adddate = adddate;
    }

    public Receipt() {
        super();
    }

    public Receipt(String skuName, String supplier) {
        this.skuName = skuName;
        this.supplier = supplier;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName == null ? null : skuName.trim();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier == null ? null : supplier.trim();
    }

    public String getAddwho() {
        return addwho;
    }

    public void setAddwho(String addwho) {
        this.addwho = addwho == null ? null : addwho.trim();
    }

    public Date getAdddate() {
        return adddate;
    }

    public void setAdddate(Date adddate) {
        this.adddate = adddate;
    }
}