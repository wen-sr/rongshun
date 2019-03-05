package com.rongshun.service.wechat;

import com.rongshun.common.ServerResponse;
import com.rongshun.pojo.wechat.Sku;
import com.rongshun.vo.wechat.SkuVo;

import java.util.List;
import java.util.Set;

/**
 * Author: wen-sir
 * Description:
 * DateCrated: 2019/2/19 13:20
 * Modified By：
 */
public interface ISkuService {
    Set<Sku> selectChildrenById(Integer skuId);

    List<Sku> skuTips(Sku sku);

    ServerResponse build(Integer skuId, String skuName, Integer qty);

    ServerResponse buildDetail(String skuName);

    public List<SkuVo> getChildrenParallel(Integer skuId);

    ServerResponse add(String name);
}
