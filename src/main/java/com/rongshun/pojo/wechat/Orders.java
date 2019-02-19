package com.rongshun.pojo.wechat;

import java.util.Date;

public class Orders {
    private Integer id;

    private Integer skuId;

    private String skuName;

    private Double priceIn;

    private String priceOut;

    private Integer qty;

    private String customer;

    private String addwho;

    private Date adddate;

    public Orders(Integer id, Integer skuId, String skuName, Double priceIn, String priceOut, Integer qty, String customer, String addwho, Date adddate) {
        this.id = id;
        this.skuId = skuId;
        this.skuName = skuName;
        this.priceIn = priceIn;
        this.priceOut = priceOut;
        this.qty = qty;
        this.customer = customer;
        this.addwho = addwho;
        this.adddate = adddate;
    }

    public Orders() {
        super();
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

    public Double getPriceIn() {
        return priceIn;
    }

    public void setPriceIn(Double priceIn) {
        this.priceIn = priceIn;
    }

    public String getPriceOut() {
        return priceOut;
    }

    public void setPriceOut(String priceOut) {
        this.priceOut = priceOut == null ? null : priceOut.trim();
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer == null ? null : customer.trim();
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