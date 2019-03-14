package com.rongshun.controller.wechat;

import com.rongshun.aspect.HttpAspect;
import com.rongshun.common.*;
import com.rongshun.pojo.wechat.Orders;
import com.rongshun.pojo.wechat.OrdersDetail;
import com.rongshun.pojo.wechat.WeChatUserInfo;
import com.rongshun.service.wechat.IOrdersService;
import com.rongshun.vo.wechat.OrdersVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final static Logger logger = LoggerFactory.getLogger(HttpAspect.class);
    @Autowired
    IOrdersService ordersService;

    @RequestMapping("/getHis")
    public ServerResponse getHis(Orders orders){
        WeChatUserInfo w = RequestHolder.getCurrentUser();
        logger.info("====用户信息：====" + w.toString());
        if(("oi05j1rBhYzBjnzgp-ipCdDsdwhs".equalsIgnoreCase(w.getOpenid())) || ("oi05j1icCldZEo4O7hFv661I4I-4".equalsIgnoreCase(w.getOpenid()))  || ("oi05j1gEC8-q6qTgrgpxjhb6sSvY".equalsIgnoreCase(w.getOpenid())) ){
            return ordersService.getHis(orders);
        }
        return new ServerResponse(ResponseCode.NEED_LOGIN.getCode());
    }

    @RequestMapping("/hisInfo")
    public ServerResponse hisInfo(Orders orders){
        return ordersService.hisInfo(orders);
    }

    @RequestMapping("/getCustomer")
    public List<Orders> getCustomer(Orders orders){
        return ordersService.getCustomer(orders);
    }

    @RequestMapping("/add")
    public ServerResponse add(OrdersVo o){
        WeChatUserInfo w = RequestHolder.getCurrentUser();
        logger.info("====用户信息：====" + w.toString());
        if(("oi05j1rBhYzBjnzgp-ipCdDsdwhs".equalsIgnoreCase(w.getOpenid())) || ("oi05j1icCldZEo4O7hFv661I4I-4".equalsIgnoreCase(w.getOpenid()))  || ("oi05j1gEC8-q6qTgrgpxjhb6sSvY".equalsIgnoreCase(w.getOpenid())) ){
            return ordersService.add(o);
        }
        return new ServerResponse(ResponseCode.NEED_LOGIN.getCode());
    }

    @RequestMapping("/detail")
    public ServerResponse detail(OrdersVo ordersVo){
        return ordersService.detail(ordersVo);
    }

    @RequestMapping("/confirm")
    public ServerResponse confirm(OrdersVo ordersVo){
        WeChatUserInfo w = RequestHolder.getCurrentUser();
        logger.info("====用户信息：====" + w.toString());
        if(("oi05j1rBhYzBjnzgp-ipCdDsdwhs".equalsIgnoreCase(w.getOpenid())) || ("oi05j1icCldZEo4O7hFv661I4I-4".equalsIgnoreCase(w.getOpenid()))  || ("oi05j1gEC8-q6qTgrgpxjhb6sSvY".equalsIgnoreCase(w.getOpenid())) ){
            return ordersService.confirm(ordersVo);
        }
        return new ServerResponse(ResponseCode.NEED_LOGIN.getCode());
    }

    @RequestMapping("/payDone")
    public ServerResponse payDone(OrdersVo ordersVo){
        return ordersService.payDone(ordersVo);
    }

    @RequestMapping("/addPayble")
    public ServerResponse addPayble(OrdersVo ordersVo){
        return ordersService.addPayble(ordersVo);
    }

    @RequestMapping("/salesInfo")
    public ServerResponse SalesInfo(String begin, String end){
        WeChatUserInfo w = RequestHolder.getCurrentUser();
        logger.info("====用户信息：====" + w.toString());
        if(("oi05j1rBhYzBjnzgp-ipCdDsdwhs".equalsIgnoreCase(w.getOpenid())) || ("oi05j1icCldZEo4O7hFv661I4I-4".equalsIgnoreCase(w.getOpenid()))  || ("oi05j1gEC8-q6qTgrgpxjhb6sSvY".equalsIgnoreCase(w.getOpenid())) ){
            return ordersService.SalesInfo(begin, end);
        }
        return new ServerResponse(ResponseCode.NEED_LOGIN.getCode());
    }

    @RequestMapping("/unclearInfo")
    public ServerResponse unclearInfo(String begin, String end){
        WeChatUserInfo w = RequestHolder.getCurrentUser();
        logger.info("====用户信息：====" + w.toString());
        if(("oi05j1rBhYzBjnzgp-ipCdDsdwhs".equalsIgnoreCase(w.getOpenid())) || ("oi05j1icCldZEo4O7hFv661I4I-4".equalsIgnoreCase(w.getOpenid()))  || ("oi05j1gEC8-q6qTgrgpxjhb6sSvY".equalsIgnoreCase(w.getOpenid())) ){
            return ordersService.unclearInfo(begin, end);
        }
        return new ServerResponse(ResponseCode.NEED_LOGIN.getCode());
    }


}