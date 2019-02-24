package com.rongshun.service.wechat.impl;

import com.rongshun.common.RequestHolder;
import com.rongshun.common.ServerResponse;
import com.rongshun.dao.wechat.InventoryMapper;
import com.rongshun.dao.wechat.OrdersDetailMapper;
import com.rongshun.dao.wechat.OrdersMapper;
import com.rongshun.dao.wechat.ReceiptMapper;
import com.rongshun.exception.MyException;
import com.rongshun.pojo.wechat.Inventory;
import com.rongshun.pojo.wechat.Orders;
import com.rongshun.pojo.wechat.OrdersDetail;
import com.rongshun.pojo.wechat.Receipt;
import com.rongshun.service.wechat.IOrdersService;
import com.rongshun.util.DateTimeUtil;
import com.rongshun.vo.wechat.OrdersVo;
import com.rongshun.vo.wechat.ReceiptVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Override
    public ServerResponse getHis(Orders orders) {
        List<Orders> ordersList = ordersMapper.selectHis(orders);
        int i = 0;
        if(ordersList != null && ordersList.size() >0){
            for (Orders o : ordersList){
                i += (o.getPaid() - o.getPayable());
            }
        }
        return ServerResponse.createBySuccess(i);
    }

    @Override
    public ServerResponse add(OrdersVo o) {
        Orders orders = new Orders();
        orders.setCustomer(o.getCustomer());
        orders.setStatus("-1");
        List<Orders> ordersList = ordersMapper.findAll(orders);
        //添加表头
        if(ordersList != null && ordersList.size() > 0){
            orders = ordersList.get(0);
            if(o.getPriceOut() != null){
                ordersDetailMapper.selectByOrderIdAndSku(new OrdersDetail(orders.getId(),o.getSkuName(),o.getSupplier()));
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
            orders.setDd(DateTimeUtil.strToDate(o.getDd(), "yyyy-MM-dd"));
            ordersMapper.insertSelective(orders);
        }
        //占用库存
        Inventory inventory = inventoryMapper.selectBySkuId(o.getSkuName(),o.getSupplier());
        inventory.setQtyShipped(inventory.getQtyShipped() + o.getQty());
        if(inventory.getQtyFree() - o.getQty() < 0){
            throw new MyException(-1, "库存不足，添加配件失败");
        }
        inventory.setQtyFree(inventory.getQtyFree() - o.getQty());
        inventoryMapper.updateByPrimaryKeySelective(inventory);
        //添加明细
        OrdersDetail ordersDetail = ordersDetailMapper.selectByOrderIdAndSku(new OrdersDetail(orders.getId(),o.getSkuName(),o.getSupplier()));
        if(ordersDetail == null){
            ordersDetail = new OrdersDetail();
            ordersDetail.setOrdersId(orders.getId());
            ordersDetail.setPriceOut(o.getPriceOut());
            ReceiptVo receiptVo = receiptMapper.selectSku(new Receipt(o.getSkuName(), o.getSupplier())).get(0);
            ordersDetail.setPriceIn(receiptVo.getPrice());
            ordersDetail.setQty(o.getQty());
            ordersDetail.setSkuId(receiptVo.getSkuId());
            ordersDetail.setSkuName(o.getSkuName());
            ordersDetail.setSupplier(o.getSupplier());
            ordersDetailMapper.insertSelective(ordersDetail);
        }else {
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
            return ServerResponse.createBySuccessMsg("下单成功");
        }
        return ServerResponse.createByErrorMessage("下单失败");
    }

    @Override
    public List<Orders> getCustomer(Orders orders) {
        return ordersMapper.getCustomer(orders);
    }

    @Override
    public ServerResponse hisInfo(Orders orders) {
        List<OrdersVo> ordersList = ordersMapper.hisInfo(orders);
        return ServerResponse.createBySuccess(ordersList);
    }
}
