package com.rongshun.service.wechat;

import com.rongshun.common.ServerResponse;
import com.rongshun.pojo.wechat.Receipt;
import com.rongshun.vo.wechat.ReceiptVo;

import java.util.List;

/**
 * Author: wen-sir
 * Description:
 * DateCrated: 2019/2/19 13:14
 * Modified By：
 */
public interface IReceiptService {
    ServerResponse add(Receipt receipt);

    List<ReceiptVo> receiptSkuTips(Receipt receipt);

    List<ReceiptVo> receiptSupplierTips(Receipt receipt);
}
