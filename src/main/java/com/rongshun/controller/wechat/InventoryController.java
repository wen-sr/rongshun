package com.rongshun.controller.wechat;

import com.rongshun.common.EasyuiTableResponse;
import com.rongshun.common.ServerResponse;
import com.rongshun.pojo.wechat.Inventory;
import com.rongshun.service.wechat.IInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Author: wen-sir
 * Description:
 * DateCrated: 2019/2/19 13:24
 * Modified By：
 */
@RestController
@RequestMapping("/inventory")
public class InventoryController {
    @Autowired
    IInventoryService inventoryService;

    @RequestMapping("info")
    public EasyuiTableResponse info(Inventory inventory){
        ServerResponse<List> response = inventoryService.info(inventory);
        return  response.parseToEasyuiTableCommonResponse(response.getStatus(),response.getData(), response.getMsg(), response.getData().size(), response.getData().size());
    }

}
