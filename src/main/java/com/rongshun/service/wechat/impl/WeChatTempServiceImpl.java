package com.rongshun.service.wechat.impl;

import com.rongshun.service.wechat.IWeChatTempService;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeChatTempServiceImpl implements IWeChatTempService {
    @Autowired
    WxMpService wxMpService;

    public void temp1(Object o){}



}
