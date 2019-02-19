package com.rongshun.pojo.wechat;

public class Inventory {
    private Integer id;

    private Integer skuId;

    private String skuName;

    private Integer qtyReceipt;

    private Integer qtyShipped;

    private String supplier;

    public Inventory(Integer id, Integer skuId, String skuName, Integer qtyReceipt, Integer qtyShipped, String supplier) {
        this.id = id;
        this.skuId = skuId;
        this.skuName = skuName;
        this.qtyReceipt = qtyReceipt;
        this.qtyShipped = qtyShipped;
        this.supplier = supplier;
    }

    public Inventory() {
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

    public Integer getQtyReceipt() {
        return qtyReceipt;
    }

    public void setQtyReceipt(Integer qtyReceipt) {
        this.qtyReceipt = qtyReceipt;
    }

    public Integer getQtyShipped() {
        return qtyShipped;
    }

    public void setQtyShipped(Integer qtyShipped) {
        this.qtyShipped = qtyShipped;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier == null ? null : supplier.trim();
    }
}