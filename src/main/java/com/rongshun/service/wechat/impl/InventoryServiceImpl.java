package com.rongshun.service.wechat.impl;

import com.rongshun.common.ServerResponse;
import com.rongshun.dao.wechat.InventoryMapper;
import com.rongshun.pojo.wechat.Inventory;
import com.rongshun.service.wechat.IInventoryService;
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
}
