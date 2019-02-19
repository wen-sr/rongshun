package com.rongshun.dao.wechat;

import com.rongshun.pojo.wechat.Receipt;
import com.rongshun.vo.wechat.ReceiptVo;

import java.util.List;

public interface ReceiptMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Receipt record);

    int insertSelective(Receipt record);

    Receipt selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Receipt record);

    int updateByPrimaryKey(Receipt record);

    List<ReceiptVo> selectSku(Receipt receipt);

    List<ReceiptVo> selectSupplier(Receipt receipt);
}