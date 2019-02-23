package com.rongshun.pojo.wechat;

public class OrdersDetail {
    private Integer id;

    private Integer ordersId;

    private Integer skuId;

    private String skuName;

    private String supplier;

    private Double priceIn;

    private Double priceOut;

    private Integer qty;

    public OrdersDetail(Integer id, Integer ordersId, Integer skuId, String skuName, String supplier, Double priceIn, Double priceOut, Integer qty) {
        this.id = id;
        this.ordersId = ordersId;
        this.skuId = skuId;
        this.skuName = skuName;
        this.supplier = supplier;
        this.priceIn = priceIn;
        this.priceOut = priceOut;
        this.qty = qty;
    }

    public OrdersDetail(Integer ordersId) {
        this.ordersId = ordersId;
    }

    public OrdersDetail() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(Integer ordersId) {
        this.ordersId = ordersId;
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

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier == null ? null : supplier.trim();
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