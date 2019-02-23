package com.rongshun.dao.wechat;

import com.rongshun.pojo.wechat.OrdersDetail;
import com.rongshun.vo.wechat.OrdersVo;

import java.util.List;

public interface OrdersDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrdersDetail record);

    int insertSelective(OrdersDetail record);

    OrdersDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrdersDetail record);

    int updateByPrimaryKey(OrdersDetail record);

    List<OrdersVo> findAll(OrdersVo ordersVo);
}