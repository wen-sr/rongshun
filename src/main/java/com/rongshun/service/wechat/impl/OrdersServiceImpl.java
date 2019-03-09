package com.rongshun.service.wechat.impl;

import com.rongshun.common.RequestHolder;
import com.rongshun.common.ServerResponse;
import com.rongshun.dao.wechat.*;
import com.rongshun.exception.MyException;
import com.rongshun.pojo.wechat.*;
import com.rongshun.service.wechat.IOrdersService;
import com.rongshun.util.DateTimeUtil;
import com.rongshun.vo.wechat.OrdersVo;
import com.rongshun.vo.wechat.ReceiptVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: wen-sir
 * Description:
 * DateCrated: 2019/2/19 13:20
 * Modified By：
 */
@Service
@Transactional
public class OrdersServiceImpl implements IOrdersService {

    @Autowired
    OrdersMapper ordersMapper;

    @Autowired
    ReceiptMapper receiptMapper;

    @Autowired
    OrdersDetailMapper ordersDetailMapper;

    @Autowired
    InventoryMapper inventoryMapper;

    @Autowired
    SkuMapper skuMapper;

    @Autowired
    SkuBuildMapper skuBuildMapper;

    @Override
    public ServerResponse getHis(Orders orders) {
        List<Orders> ordersList = ordersMapper.selectHis(orders);
        int i = 0,paid =  0,payable = 0;
        if(ordersList != null && ordersList.size() >0){
            for (Orders o : ordersList){
                i += (o.getPaid() - o.getPayable());
//                paid += o.getPaid();
//                payable += o.getPayable();
            }
        }
        Map<String, Object> map = new HashMap<>();
//        map.put("payable", payable);
        map.put("i", i);
        OrdersVo ordersVo = new OrdersVo();
        ordersVo.setCustomer(orders.getCustomer());
        ordersVo.setStatus(orders.getStatus());
        List<OrdersVo> ordersVoList = ordersDetailMapper.findAll(ordersVo);
        map.put("dd", "");
        for(OrdersVo oo : ordersVoList){
            paid += (oo.getPriceOut()*oo.getQty());
            map.put("dd", ordersVoList.get(0).getDd());
        }
        map.put("paid", paid);
        map.put("list", ordersVoList);
        return ServerResponse.createBySuccess(map);
    }

    @Override
    public ServerResponse add(OrdersVo o) {
        Orders orders = new Orders();
        orders.setCustomer(o.getCustomer());
        orders.setStatus("-1");
        List<Orders> ordersList = ordersMapper.findAll(orders);
        //添加表头
        if(ordersList != null && ordersList.size() > 0){
            if(o.getQty() < 0){
                throw new MyException(-1, "您要减少的配件未下过单");
            }
            orders = ordersList.get(0);
            if(o.getPriceOut() != null){
                ordersDetailMapper.selectByOrderIdAndSku(new OrdersDetail(orders.getId(),o.getSkuName()));
                orders.setPaid(orders.getPaid() + (o.getPriceOut()*o.getQty()));
                ordersMapper.updateByPrimaryKeySelective(orders);
            }
        }else {
            orders = new Orders();
            orders.setCustomer(o.getCustomer());
            orders.setStatus("-1");
            orders.setPaid(o.getPriceOut() * o.getQty());
//            orders.setAddwho(RequestHolder.getCurrentUser().getOpenid());
            orders.setAddwho("wen-sir");
            orders.setDd(DateTimeUtil.strToDate(o.getDd(), "yyyy/MM/dd"));
            ordersMapper.insertSelective(orders);
        }
        //占用库存
        Inventory inventory = inventoryMapper.selectBySkuId(o.getSkuName());
        inventory.setQtyShipped(inventory.getQtyShipped() + o.getQty());
        if(inventory.getQtyFree() - o.getQty() < 0){
            throw new MyException(-1, "库存不足，添加配件失败");
        }
        inventory.setQtyFree(inventory.getQtyFree() - o.getQty());
        inventoryMapper.updateByPrimaryKeySelective(inventory);

        //添加明细
        OrdersDetail ordersDetail = ordersDetailMapper.selectByOrderIdAndSku(new OrdersDetail(orders.getId(),o.getSkuName()));
        if(ordersDetail == null){
            if(o.getQty() < 0){
                throw new MyException(-1, "您要减少的配件未下过单");
            }
            ordersDetail = new OrdersDetail();
            ordersDetail.setOrdersId(orders.getId());
            ordersDetail.setPriceOut(o.getPriceOut());
//            Sku sku = skuMapper.selectByName(o.getSkuName());
            ordersDetail.setQty(o.getQty());
            ordersDetail.setSkuId(o.getSkuId());
            ordersDetail.setSkuName(o.getSkuName());
            ordersDetailMapper.insertSelective(ordersDetail);
        }else {
            if(ordersDetail.getPriceOut() - o.getPriceOut() != 0){
                throw new MyException(-1, "此次与之前添加的售价不同，之前为：" + ordersDetail.getPriceOut() + ", 此次为：" + o.getPriceOut());
            }
            if(ordersDetail.getQty() + o.getQty() == 0){
                ordersDetailMapper.deleteByPrimaryKey(ordersDetail.getId());
            }else {
                ordersDetail.setQty(ordersDetail.getQty() + o.getQty());
                ordersDetailMapper.updateByPrimaryKeySelective(ordersDetail);
            }
            orders.setPaid(orders.getPaid() + ordersDetail.getPriceOut()*ordersDetail.getQty());
            ordersMapper.updateByPrimaryKeySelective(orders);
        }

        return ServerResponse.createBySuccessMsg("操作成功");
    }

    @Override
    public ServerResponse detail(OrdersVo ordersVo) {
        List<OrdersVo> ordersVoList = ordersDetailMapper.findAll(ordersVo);
        return ServerResponse.createBySuccess(ordersVoList);
    }

    @Override
    public ServerResponse confirm(OrdersVo ordersVo) {
        List<Orders> ordersList = ordersMapper.findAll(new Orders(ordersVo.getCustomer(), "-1"));
        if(ordersList != null && ordersList.size() > 0){
            List<OrdersVo> ordersVoList = ordersDetailMapper.findAll(new OrdersVo(ordersList.get(0).getId()));
            double sumQty = 0;
            for(OrdersVo o : ordersVoList){
                sumQty += o.getQty()*o.getPriceOut();
                //增加库存
//                Inventory inventory = inventoryMapper.selectBySkuId(o.getSkuName(),o.getSupplier());
//                inventory.setQtyShipped(inventory.getQtyShipped() + o.getQty());
//                if(inventory.getQtyFree() - o.getQty() < 0){
//                    throw new MyException(-1, "供应商为" + o.getSupplier() + "的" + o.getSkuName() + "库存不足");
//                }
//                inventory.setQtyFree(inventory.getQtyFree() - o.getQty());
//                inventoryMapper.updateByPrimaryKeySelective(inventory);
            }
            Orders orders = ordersMapper.findAll(new Orders(ordersVo.getCustomer(), "-1")).get(0);
            orders.setPayable(ordersVo.getPayable());
            if (sumQty == ordersVo.getPayable()) {
                orders.setStatus("2");
            }else if(ordersVo.getPayable() == 0){
                orders.setStatus("0");
            }else {
                orders.setStatus("1");
            }

            ordersMapper.updateByPrimaryKeySelective(orders);
            //发送微信模板消息，通知下单成功
            return ServerResponse.createBySuccessMsg("下单成功");
        }
        return ServerResponse.createByErrorMessage("下单失败, 您还没有添加订单明细");
    }

    @Override
    public List<Orders> getCustomer(Orders orders) {
        return ordersMapper.getCustomer(orders);
    }

    @Override
    public ServerResponse hisInfo(Orders orders) {
        List<OrdersVo> ordersList = ordersMapper.hisInfo(orders);
        double paid = 0, payable = 0;
        String dd = ordersList.get(0).getDd();
        List<Orders> ordersList1 = ordersMapper.selectHis(orders);
        if(ordersList1 != null && ordersList1.size() > 0){
            for(Orders o : ordersList1){
                paid += o.getPaid();
                payable += o.getPayable();
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("paid", paid);
        map.put("payable", payable);
        map.put("list", ordersList);
        map.put("dd", dd);
        return ServerResponse.createBySuccess(map);
    }

    @Override
    public ServerResponse payDone(OrdersVo ordersVo) {
        Orders orders = ordersMapper.selectByPrimaryKey(ordersVo.getOrdersId());
        orders.setStatus("2");
        orders.setPayable(orders.getPaid());
        ordersMapper.updateByPrimaryKeySelective(orders);
        return ServerResponse.createBySuccessMsg("订单支付完成");
    }

    @Override
    public ServerResponse addPayble(OrdersVo ordersVo) {
        double addPayble = ordersVo.getPayable();
        if(addPayble == 0){
            return ServerResponse.createByErrorMessage("未输入补付金额");
        }
        Orders orders = new Orders();
        orders.setCustomer(ordersVo.getCustomer());
        List<Orders> ordersList = ordersMapper.selectHis(orders);
        if(ordersList != null && ordersList.size() > 0){
            for(Orders o : ordersList){
                if(o.getPayable() + addPayble > o.getPaid()){
                    addPayble = o.getPaid() - o.getPayable();
                    o.setPayable(o.getPaid());
                    o.setStatus("2");
                }else {
                    o.setPayable(o.getPayable() + addPayble);
                    o.setStatus("1");
                }
                ordersMapper.updateByPrimaryKeySelective(o);
            }
        }
        return ServerResponse.createBySuccessMsg("补付款成功");
    }

    @Override
    public ServerResponse SalesInfo(String begin, String end) {
        List<OrdersVo> ordersList = ordersMapper.salesInfo(begin, end);
        Map<String, Object> map = new HashMap<>();
        map.put("list", ordersList);
        double totalSales = 0,  getted = 0, ungetted = 0;
        if(ordersList != null && ordersList.size() > 0){
            for(OrdersVo o : ordersList) {
                totalSales += o.getPaid();
                getted += o.getPayable();
            }
        }

        ungetted = totalSales - getted;
        map.put("totalSales", totalSales);
        map.put("getted", getted);
        map.put("ungetted", ungetted);
        return ServerResponse.createBySuccess(map);
    }

    @Override
    public ServerResponse unclearInfo(String begin, String end) {
        List<OrdersVo> ordersList = ordersMapper.unclearInfo(begin, end);
        Map<String, Object> map = new HashMap<>();
        map.put("list", ordersList);
        double totalSales = 0,  getted = 0, ungetted = 0;
        if(ordersList != null && ordersList.size() > 0){
            for(OrdersVo o : ordersList) {
                totalSales += o.getPaid();
                getted += o.getPayable();
            }
        }

        ungetted = totalSales - getted;
        map.put("totalSales", totalSales);
        map.put("getted", getted);
        map.put("ungetted", ungetted);
        return ServerResponse.createBySuccess(map);
    }
}
