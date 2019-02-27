package com.rongshun.service.wechat.impl;

import com.google.common.collect.Sets;
import com.rongshun.common.ServerResponse;
import com.rongshun.dao.wechat.SkuBuildMapper;
import com.rongshun.dao.wechat.SkuMapper;
import com.rongshun.pojo.wechat.Sku;
import com.rongshun.pojo.wechat.SkuBuild;
import com.rongshun.service.wechat.ISkuService;
import com.rongshun.util.DateTimeUtil;
import com.rongshun.vo.wechat.SkuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Override
    public Set<Sku> selectChildrenById(Integer skuId) {
        Set<Sku> skuSet = new HashSet<>();
        getChildrenRecursive(skuSet, skuId);
        return skuSet;
    }

    @Override
    public ServerResponse build(Integer skuId, String skuName, Integer qty) {
        Sku sku = new Sku();
        sku.setName(skuName);
        sku.setFooId(1);
        skuMapper.insertSelective(sku);
        skuBuildMapper.insertSelective(new SkuBuild(skuId,sku.getId(),qty));
        return ServerResponse.createBySuccessMsg("添加零件成功");
    }

    @Override
    public ServerResponse buildDetail(String skuName) {
        Sku sku = skuMapper.selectByName(skuName);
        if(sku == null) {
            return ServerResponse.createBySuccess();
        }
        return getChildrenParallel(sku.getId());
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

    public ServerResponse getChildrenParallel(Integer skuId) {
        List<SkuVo> skuVoList = skuBuildMapper.selectByParentId(skuId);
        return ServerResponse.createBySuccess(skuVoList);
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
