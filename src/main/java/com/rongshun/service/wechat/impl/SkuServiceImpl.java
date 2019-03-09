package com.rongshun.service.wechat.impl;

import com.rongshun.common.RequestHolder;
import com.rongshun.common.ServerResponse;
import com.rongshun.dao.wechat.InventoryMapper;
import com.rongshun.dao.wechat.SkuBuildMapper;
import com.rongshun.dao.wechat.SkuMapper;
import com.rongshun.exception.MyException;
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
            if(qty < 0){
                return ServerResponse.createByErrorMessage("该总成配件不存在，无法减少零件");
            }
            sku = new Sku();
            sku.setName(skuName);
//            sku.setAddwho(RequestHolder.getCurrentUser().getNickname());
            sku.setAddwho("wen-sir");
            sku.setFooId(1);
            skuMapper.insertSelective(sku);
        }else {
            if(sku.getFooId() != 1){
                sku.setFooId(1);
                skuMapper.updateByPrimaryKeySelective(sku);
            }
        }
        SkuBuild skuBuild = skuBuildMapper.selectBySkuIdAndFooId(skuId, sku.getId());
        if(skuBuild == null){
            if(qty < 0){
                throw new MyException(-1, "该总成未添加过该配件，无法减少");
            }
            skuBuildMapper.insertSelective(new SkuBuild(skuId,sku.getId(),qty));
        }else {
            if(skuBuild.getQty() + qty < 0){
                throw new MyException(-1, "该配件的数量为" + skuBuild.getQty() + ",无法减少" + (0-qty) + "个");
            }else if(skuBuild.getQty() + qty == 0){
                skuBuildMapper.deleteByPrimaryKey(skuBuild.getSkuid());
                return ServerResponse.createBySuccessMsg("减少零件成功");
            }
            skuBuild.setQty(skuBuild.getQty() + qty);
            skuBuildMapper.updateByPrimaryKeySelective(skuBuild);
        }
        if(qty > 0){
            return ServerResponse.createBySuccessMsg("添加零件成功");
        }else {
            return ServerResponse.createBySuccessMsg("减少零件成功");

        }
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

    @Override
    public ServerResponse add(String name) {
        Sku sku = skuMapper.selectByName(name);
        if(sku != null){
            return ServerResponse.createByErrorMessage("您要添加的配件名称已存在");
        }
        sku = new Sku();
        sku.setName(name);
        sku.setFooId(0);
        sku.setAddwho("wen-sir");
        skuMapper.insertSelective(sku);
        return ServerResponse.createBySuccessMsg("添加配件名称成功");
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
