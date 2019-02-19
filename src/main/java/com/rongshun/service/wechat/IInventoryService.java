package com.rongshun.service.wechat;

import com.rongshun.common.ServerResponse;
import com.rongshun.pojo.wechat.Inventory;

/**
 * Author: wen-sir
 * Description:
 * DateCrated: 2019/2/19 13:16
 * Modified By：
 */
public interface IInventoryService {
    ServerResponse info(Inventory inventory);
}
