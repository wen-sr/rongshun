package com.rongshun.dao.wechat;

import com.rongshun.pojo.wechat.SkuBuild;
import com.rongshun.vo.wechat.SkuVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SkuBuildMapper {
    int deleteByPrimaryKey(Integer skuid);

    int insert(SkuBuild record);

    int insertSelective(SkuBuild record);

    SkuBuild selectByPrimaryKey(Integer skuid);

    int updateByPrimaryKeySelective(SkuBuild record);

    int updateByPrimaryKey(SkuBuild record);

    List<SkuVo> selectByParentId(Integer foo_id);

    SkuBuild selectBySkuIdAndFooId(@Param("skuId") Integer skuId, @Param("fooId") Integer fooId);
}