package com.rongshun.controller.wechat;

import com.rongshun.common.EasyuiTableResponse;
import com.rongshun.common.ServerResponse;
import com.rongshun.pojo.wechat.Inventory;
import com.rongshun.pojo.wechat.WeChatUserInfo;
import com.rongshun.service.wechat.IWeChatUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/weChatUserInfo")
public class WeChatUserInfoController {

    @Autowired
    IWeChatUserInfoService weChatUserInfoService;

    @RequestMapping("/info")
    public EasyuiTableResponse info(WeChatUserInfo weChatUserInfo){
        ServerResponse<List> response = weChatUserInfoService.info(weChatUserInfo);
        return  response.parseToEasyuiTableCommonResponse(response.getStatus(),response.getData(), response.getMsg(), response.getData().size(), response.getData().size());
    }

    @RequestMapping("/auth")
    public ServerResponse auth(WeChatUserInfo weChatUserInfo){
        return weChatUserInfoService.updateAuth(weChatUserInfo);
    }
}
