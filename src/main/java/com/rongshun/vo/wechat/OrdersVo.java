package com.rongshun.vo.wechat;

import java.math.BigDecimal;

public class OrdersVo {
    private Integer ordersId;

    private String customer;

    private Double payable;

    private Double paid;

    private String status;

    private String dd;

    private String addwho;

    private String adddate;

    private Integer ordersDetailId;

    private Integer skuId;

    private String skuName;

    private Double priceIn;

    private Double priceOut;

    private Integer qty;
    private String supplier;
    private BigDecimal my;


    public OrdersVo() {
    }

    public BigDecimal getMy() {
        return my;
    }

    public void setMy(BigDecimal my) {
        this.my = my;
    }

    public OrdersVo(Integer ordersId) {
        this.ordersId = ordersId;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public Integer getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(Integer ordersId) {
        this.ordersId = ordersId;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Double getPayable() {
        return payable;
    }

    public void setPayable(Double payable) {
        this.payable = payable;
    }

    public Double getPaid() {
        return paid;
    }

    public void setPaid(Double paid) {
        this.paid = paid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDd() {
        return dd;
    }

    public void setDd(String dd) {
        this.dd = dd;
    }

    public String getAddwho() {
        return addwho;
    }

    public void setAddwho(String addwho) {
        this.addwho = addwho;
    }

    public String getAdddate() {
        return adddate;
    }

    public void setAdddate(String adddate) {
        this.adddate = adddate;
    }

    public Integer getOrdersDetailId() {
        return ordersDetailId;
    }

    public void setOrdersDetailId(Integer ordersDetailId) {
        this.ordersDetailId = ordersDetailId;
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
        this.skuName = skuName;
    }

    public Double getPriceIn() {
        return priceIn;
    }

    public void setPriceIn(Double priceIn) {
        this.priceIn = priceIn;
    }

    public Double getPriceOut() {
        return priceOut;
    }

    public void setPriceOut(Double priceOut) {
        this.priceOut = priceOut;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }
}
