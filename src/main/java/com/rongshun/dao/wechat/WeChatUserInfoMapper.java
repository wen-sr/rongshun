package com.rongshun.dao.wechat;

import com.rongshun.pojo.wechat.WeChatUserInfo;

import java.util.List;

public interface WeChatUserInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WeChatUserInfo record);

    int insertSelective(WeChatUserInfo record);

    WeChatUserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeChatUserInfo record);

    int updateByPrimaryKey(WeChatUserInfo record);

    WeChatUserInfo selectByOpenid(String openId);

    List<WeChatUserInfo> selectAll(WeChatUserInfo weChatUserInfo);
}