package com.rongshun.vo.wechat;

public class InventoryVo {
    private Integer id;

    private Integer skuId;

    private String skuName;

    private Integer qtyReceipt;

    private Integer qtyShipped;

    private Integer qtyFree;

    private String supplier;

    private Integer qtyAdd;

    public Integer getQtyAdd() {
        return qtyAdd;
    }

    public void setQtyAdd(Integer qtyAdd) {
        this.qtyAdd = qtyAdd;
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
        this.skuName = skuName;
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

    public Integer getQtyFree() {
        return qtyFree;
    }

    public void setQtyFree(Integer qtyFree) {
        this.qtyFree = qtyFree;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
}
