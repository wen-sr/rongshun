package com.rongshun.service.wechat.impl;

import com.rongshun.dao.wechat.InventoryMapper;
import com.rongshun.service.wechat.IInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
