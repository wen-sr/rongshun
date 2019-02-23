package com.rongshun.service.wechat.impl;

import com.rongshun.common.ServerResponse;
import com.rongshun.dao.wechat.InventoryMapper;
import com.rongshun.pojo.wechat.Inventory;
import com.rongshun.service.wechat.IInventoryService;
import com.rongshun.vo.wechat.InventoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author: wen-sir
 * Description:
 * DateCrated: 2019/2/19 13:17
 * Modified By：
 */
@Service
@Transactional
public class InventoryServiceImpl implements IInventoryService {
    @Autowired
    InventoryMapper inventoryMapper;

    @Override
    public ServerResponse info(Inventory inventory) {
        List<Inventory> inventoryList = inventoryMapper.selectAll(inventory);
        return ServerResponse.createBySuccess(inventoryList);
    }

//    public int add(InventoryVo inventoryVo){
//        Inventory inventory = inventoryMapper.selectBySkuId(inventoryVo.getSkuName(),inventoryVo.getSupplier());
//        if(inventory == null){
//            inventory = new Inventory();
//            inventory.setSkuId(inventoryVo.getId());
//            inventory.setSkuName(inventoryVo.getSkuName());
//            inventory.setQtyReceipt(inventoryVo.getQtyAdd());
//            inventory.setQtyShipped(0);
//            inventory.setQtyFree(inventoryVo.getQtyAdd());
//            inventory.setSupplier(inventoryVo.getSupplier());
//            inventoryMapper.insertSelective(inventory);
//        }else {
//            inventory.setQtyShipped(inventory.getQtyShipped() + inventoryVo.getQtyAdd());
//            inventory.setQtyFree(inventory.getQtyFree() - inventoryVo.getQtyAdd());
//            inventoryMapper.updateByPrimaryKeySelective(inventory);
//        }
//    }
}
