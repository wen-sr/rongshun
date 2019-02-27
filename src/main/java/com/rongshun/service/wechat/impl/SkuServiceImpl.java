package com.rongshun.service.wechat.impl;

import com.rongshun.common.RequestHolder;
import com.rongshun.common.ServerResponse;
import com.rongshun.dao.wechat.InventoryMapper;
import com.rongshun.dao.wechat.SkuBuildMapper;
import com.rongshun.dao.wechat.SkuMapper;
import com.rongshun.pojo.wechat.Sku;
import com.rongshun.pojo.wechat.SkuBuild;
import com.rongshun.service.wechat.ISkuService;
import com.rongshun.vo.wechat.SkuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Author: wen-sir
 * Description:
 * DateCrated: 2019/2/19 13:20
 * Modified By：
 */
@Service
@Transactional
public class SkuServiceImpl implements ISkuService {

    @Autowired
    SkuMapper skuMapper;

    @Autowired
    SkuBuildMapper skuBuildMapper;

    @Autowired
    InventoryMapper inventoryMapper;

    @Override
    public Set<Sku> selectChildrenById(Integer skuId) {
        Set<Sku> skuSet = new HashSet<>();
        getChildrenRecursive(skuSet, skuId);
        return skuSet;
    }

    @Override
    public ServerResponse build(Integer skuId, String skuName, Integer qty) {
//        更新资料，标记为总成配件
        Sku sku = skuMapper.selectByName(skuName);
        if(sku == null){
            sku = new Sku();
            sku.setName(skuName);
//            sku.setAddwho(RequestHolder.getCurrentUser().getNickname());
            sku.setAddwho("wen-sir");
            sku.setFooId(1);
            skuMapper.insertSelective(sku);
        }else {
            sku.setFooId(1);
            skuMapper.updateByPrimaryKeySelective(sku);
        }
        SkuBuild skuBuild = skuBuildMapper.selectBySkuIdAndFooId(skuId, sku.getId());
        if(skuBuild == null){
            skuBuildMapper.insertSelective(new SkuBuild(skuId,sku.getId(),qty));
        }else {
            skuBuild.setQty(skuBuild.getQty() + qty);
            skuBuildMapper.updateByPrimaryKeySelective(skuBuild);
        }
        //扣减子零件的库存
//        Inventory inventory = inventoryMapper.selectBySkuId(skuMapper.selectByPrimaryKey(skuId).getName());
//        inventory.setQtyReceipt(inventory.getQtyReceipt() - qty);
//        inventory.setQtyFree(inventory.getQtyFree() - qty);
//        inventoryMapper.updateByPrimaryKeySelective(inventory);
        //增加总成零件的库存
//        Inventory i = inventoryMapper.selectBySkuId(sku.getName());
//        if(i == null){
//            i = new Inventory();
//            i.setQtyFree(qty);
//            i.setQtyReceipt(qty);
//            inventoryMapper.insertSelective(i);
//        }else {
//            i.setQtyReceipt(i.getQtyReceipt() + qty);
//            i.setQtyFree(i.getQtyFree() + qty);
//            inventoryMapper.updateByPrimaryKeySelective(i);
//        }
        return ServerResponse.createBySuccessMsg("添加零件成功");
    }

    @Override
    public ServerResponse buildDetail(String skuName) {
        Sku sku = skuMapper.selectByName(skuName);
        if(sku == null) {
            return ServerResponse.createBySuccess();
        }
        List<SkuVo> skuVoList = getChildrenParallel(sku.getId());
        return ServerResponse.createBySuccess(skuVoList);
//        Set<Sku> skuSet = new HashSet<>();
//        getChildrenRecursive(skuSet, sku.getId());
//        List<SkuVo> skuVoList = new ArrayList<>();
//        SkuVo skuVo = null;
//        for(Sku s : skuSet){
//            skuVo = new SkuVo();
//            skuVo.setSkuid(s.getId());
//            skuVo.setAdddate(DateTimeUtil.dateToStr(s.getAdddate()));
//            skuVo.setAddwho(s.getAddwho());
//            skuVo.setFooId(sku.getId());
//            skuVo.setFooName(sku.getName());
//            skuVo.setIsFooId(s.getFooId());
//            skuVo.setQty();
//
//        }
    }

    @Override
    public List<Sku> skuTips(Sku sku) {
        return skuMapper.selectAll(sku);
    }

    @Override
    public List<SkuVo> getChildrenParallel(Integer skuId) {
        return skuBuildMapper.selectByParentId(skuId);
    }

    private Set<Sku> getChildrenRecursive(Set<Sku> skuSet, Integer skuId) {
        Sku sku = skuMapper.selectByPrimaryKey(skuId);
        if(sku != null) {
            skuSet.add(sku);
        }
        List<SkuVo> skuList = skuBuildMapper.selectByParentId(skuId);
        for(SkuVo s : skuList){
            getChildrenRecursive(skuSet, s.getId());
        }
        return skuSet;
    }


}
