package com.rongshun.controller.wechat;

import com.rongshun.common.ServerResponse;
import com.rongshun.pojo.wechat.Orders;
import com.rongshun.pojo.wechat.OrdersDetail;
import com.rongshun.service.wechat.IOrdersService;
import com.rongshun.vo.wechat.OrdersVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
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
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    IOrdersService ordersService;

    @RequestMapping("/getHis")
    public ServerResponse getHis(Orders orders){
        return ordersService.getHis(orders);
    }

    @RequestMapping("/getCustomer")
    public List<Orders> getCustomer(Orders orders){
        return ordersService.getCustomer(orders);
    }

    @RequestMapping("/add")
    public ServerResponse add(OrdersVo o){
        return ordersService.add(o);
    }

    @RequestMapping("/detail")
    public ServerResponse detail(OrdersVo ordersVo){
        return ordersService.detail(ordersVo);
    }

    @RequestMapping("/confirm")
    public ServerResponse confirm(OrdersVo ordersVo){
        return ordersService.confirm(ordersVo);
    }
}
