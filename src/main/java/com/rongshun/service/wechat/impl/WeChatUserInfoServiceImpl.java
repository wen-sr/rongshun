package com.rongshun.service.wechat.impl;

import com.rongshun.common.ServerResponse;
import com.rongshun.dao.wechat.WeChatUserInfoMapper;
import com.rongshun.pojo.wechat.WeChatUserInfo;
import com.rongshun.service.wechat.IWeChatUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author: wen-sir
 * Description:
 * DateCrated: 2019/2/19 13:23
 * Modified By：
 */
@Service
@Transactional
public class WeChatUserInfoServiceImpl implements IWeChatUserInfoService {

    @Autowired
    WeChatUserInfoMapper weChatUserInfoMapper;

    @Override
    public ServerResponse<List> info(WeChatUserInfo weChatUserInfo) {
        List<WeChatUserInfo> weChatUserInfoList = weChatUserInfoMapper.selectAll(weChatUserInfo);
        for(WeChatUserInfo w: weChatUserInfoList){
            if(w.getAuth() == null || "0".equalsIgnoreCase(w.getAuth())){
                w.setAuth("无");
            } else {
                w.setAuth("有");
            }
        }
        return ServerResponse.createBySuccess(weChatUserInfoList);

    }

    @Override
    public ServerResponse updateAuth(WeChatUserInfo weChatUserInfo) {
        weChatUserInfoMapper.updateByPrimaryKeySelective(weChatUserInfo);
        return ServerResponse.createBySuccessMsg("修改权限成功");
    }
}
