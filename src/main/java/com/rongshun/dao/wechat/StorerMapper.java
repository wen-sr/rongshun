package com.rongshun.dao.wechat;

import com.rongshun.pojo.wechat.Storer;

public interface StorerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Storer record);

    int insertSelective(Storer record);

    Storer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Storer record);

    int updateByPrimaryKey(Storer record);
}