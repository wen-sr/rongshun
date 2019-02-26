package com.rongshun.service.wechat;

import com.rongshun.common.ServerResponse;
import com.rongshun.pojo.wechat.Orders;
import com.rongshun.vo.wechat.OrdersVo;

import java.util.List;

/**
 * Author: wen-sir
 * Description:
 * DateCrated: 2019/2/19 13:19
 * Modified By：
 */
public interface IOrdersService {
    ServerResponse getHis(Orders orders);

    ServerResponse add(OrdersVo ordersDetail);

    ServerResponse detail(OrdersVo ordersVo);

    ServerResponse confirm(OrdersVo ordersVo);

    List<Orders> getCustomer(Orders orders);

    ServerResponse hisInfo(Orders orders);

    ServerResponse payDone(OrdersVo ordersVo);
}
