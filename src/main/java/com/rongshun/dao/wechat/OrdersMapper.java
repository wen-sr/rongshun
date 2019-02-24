package com.rongshun.dao.wechat;

import com.rongshun.pojo.wechat.Orders;
import com.rongshun.vo.wechat.OrdersVo;

import java.util.List;

public interface OrdersMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Orders record);

    int insertSelective(Orders record);

    Orders selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Orders record);

    int updateByPrimaryKey(Orders record);

    List<Orders> selectHis(Orders orders);

    List<Orders> findAll(Orders orders);

    List<Orders> getCustomer(Orders orders);

    List<OrdersVo> hisInfo(Orders orders);
}