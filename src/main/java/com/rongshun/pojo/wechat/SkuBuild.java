package com.rongshun.pojo.wechat;

public class SkuBuild {
    private Integer skuid;

    private Integer fooId;

    private Integer qty;

    public SkuBuild(Integer skuid, Integer fooId, Integer qty) {
        this.skuid = skuid;
        this.fooId = fooId;
        this.qty = qty;
    }

    public SkuBuild() {
        super();
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
}