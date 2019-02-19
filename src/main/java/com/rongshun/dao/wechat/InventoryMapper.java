package com.rongshun.dao.wechat;

import com.rongshun.pojo.wechat.Inventory;
import org.apache.ibatis.annotations.Param;

public interface InventoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Inventory record);

    int insertSelective(Inventory record);

    Inventory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Inventory record);

    int updateByPrimaryKey(Inventory record);

    Inventory selectBySkuId(@Param("skuName") String skuName, @Param("supplier") String supplier);
}