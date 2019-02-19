package com.rongshun.dao.wechat;

import com.rongshun.pojo.wechat.Sku;

public interface SkuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Sku record);

    int insertSelective(Sku record);

    Sku selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Sku record);

    int updateByPrimaryKey(Sku record);

    Sku selectByName(String skuName);
}