package com.rongshun.controller.wechat;

import com.rongshun.common.EasyuiTableResponse;
import com.rongshun.common.RequestHolder;
import com.rongshun.common.ResponseCode;
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
    public ServerResponse info(WeChatUserInfo weChatUserInfo){
        WeChatUserInfo w = RequestHolder.getCurrentUser();
//        if(w == null || !("oi05j1rBhYzBjnzgp-ipCdDsdwhs".equalsIgnoreCase(w.getOpenid())) || !("oi05j1icCldZEo4O7hFv661I4I-4".equalsIgnoreCase(w.getOpenid()))){
//            return new ServerResponse(ResponseCode.NEED_LOGIN.getCode());
//        }
        return weChatUserInfoService.info(weChatUserInfo);
    }

    @RequestMapping("/auth")
    public ServerResponse auth(WeChatUserInfo weChatUserInfo){
        WeChatUserInfo w = RequestHolder.getCurrentUser();
//        if(w == null || !("oi05j1rBhYzBjnzgp-ipCdDsdwhs".equalsIgnoreCase(w.getOpenid())) || !("oi05j1icCldZEo4O7hFv661I4I-4".equalsIgnoreCase(w.getOpenid())) ){
//            return new ServerResponse(ResponseCode.NEED_LOGIN.getCode());
//        }
        return weChatUserInfoService.updateAuth(weChatUserInfo);
    }
}
