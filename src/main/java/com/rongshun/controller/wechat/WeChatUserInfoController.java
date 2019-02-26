package com.rongshun.controller.wechat;

import com.rongshun.common.EasyuiTableResponse;
import com.rongshun.common.RequestHolder;
import com.rongshun.common.ServerResponse;
import com.rongshun.exception.MyException;
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
        WeChatUserInfo w = RequestHolder.getCurrentUser();
        if(w == null || !("oi05j1rBhYzBjnzgp-ipCdDsdwhs".equalsIgnoreCase(w.getOpenid()))){
            throw new MyException(-1, "未授权的用户");
        }
        ServerResponse<List> response = weChatUserInfoService.info(weChatUserInfo);
        return  response.parseToEasyuiTableCommonResponse(response.getStatus(),response.getData(), response.getMsg(), response.getData().size(), response.getData().size());
    }

    @RequestMapping("/auth")
    public ServerResponse auth(WeChatUserInfo weChatUserInfo){
        WeChatUserInfo w = RequestHolder.getCurrentUser();
        if(w == null || !("oi05j1rBhYzBjnzgp-ipCdDsdwhs".equalsIgnoreCase(w.getOpenid()))){
            throw new MyException(-1, "未授权的用户");
        }
        return weChatUserInfoService.updateAuth(weChatUserInfo);
    }
}
