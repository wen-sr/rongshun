package com.rongshun.controller.wechat;

import com.rongshun.common.ServerResponse;
import com.rongshun.pojo.wechat.Receipt;
import com.rongshun.service.wechat.IReceiptService;
import com.rongshun.vo.wechat.ReceiptVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Author: wen-sir
 * Description:
 * DateCrated: 2019/2/19 13:13
 * Modified By：
 */
@RestController
@RequestMapping("/receipt")
public class ReceiptController {

    @Autowired
    IReceiptService receiptService;

    @RequestMapping("/add")
    public ServerResponse add(Receipt receipt){
        return receiptService.add(receipt);
    }

    @RequestMapping("/receiptSkuTips")
    public List<ReceiptVo> receiptSkuTips(Receipt receipt){
        return receiptService.receiptSkuTips(receipt);
    }

    @RequestMapping("/receiptSupplierTips")
    public List<ReceiptVo> receiptSupplierTips(Receipt receipt){
        return receiptService.receiptSupplierTips(receipt);
    }

}
