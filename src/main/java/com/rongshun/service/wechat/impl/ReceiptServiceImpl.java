package com.rongshun.service.wechat.impl;

import com.rongshun.common.RequestHolder;
import com.rongshun.common.ServerResponse;
import com.rongshun.dao.wechat.InventoryMapper;
import com.rongshun.dao.wechat.ReceiptMapper;
import com.rongshun.dao.wechat.SkuMapper;
import com.rongshun.pojo.wechat.Inventory;
import com.rongshun.pojo.wechat.Receipt;
import com.rongshun.pojo.wechat.Sku;
import com.rongshun.service.wechat.IReceiptService;
import com.rongshun.vo.wechat.ReceiptVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author: wen-sir
 * Description:
 * DateCrated: 2019/2/19 13:15
 * Modified By：
 */
@Service
@Transactional
public class ReceiptServiceImpl implements IReceiptService {
    @Autowired
    ReceiptMapper receiptMapper;

    @Autowired
    SkuMapper skuMapper;

    @Autowired
    InventoryMapper inventoryMapper;

    @Override
    public ServerResponse add(Receipt receipt) {
        //判断商品是否第一次收货
        Sku sku = skuMapper.selectByName(receipt.getSkuName());
        if(sku == null) {
            sku.setAddwho(RequestHolder.getCurrentUser().getNickname());
            skuMapper.insertSelective(sku);
        }
        //增加库存
        Inventory inventory = inventoryMapper.selectBySkuId(sku.getName(),receipt.getSupplier());
        if(inventory == null){
            inventory.setSkuId(sku.getId());
            inventory.setSkuName(sku.getName());
            inventory.setQtyReceipt(receipt.getQty());
            inventory.setQtyShipped(0);
            inventory.setSupplier(receipt.getSupplier());
            inventoryMapper.insertSelective(inventory);
        }else {
            inventory.setQtyReceipt(inventory.getQtyShipped() + receipt.getQty());
        }
        //写入收货明细
        receiptMapper.insertSelective(receipt);
        return ServerResponse.createBySuccessMsg("收货成功");
    }

    @Override
    public List<ReceiptVo> receiptSkuTips(Receipt receipt) {
        List<ReceiptVo> receiptVoList = receiptMapper.selectSku(receipt);
        return receiptVoList;
    }

    @Override
    public List<ReceiptVo> receiptSupplierTips(Receipt receipt) {
        List<ReceiptVo> receiptVoList = receiptMapper.selectSupplier(receipt);
        return receiptVoList;
    }
}
