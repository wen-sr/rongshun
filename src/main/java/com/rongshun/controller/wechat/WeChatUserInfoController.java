package com.rongshun.controller.wechat;

import com.rongshun.aspect.HttpAspect;
import com.rongshun.common.RequestHolder;
import com.rongshun.common.ResponseCode;
import com.rongshun.common.ServerResponse;
import com.rongshun.pojo.wechat.WeChatUserInfo;
import com.rongshun.service.wechat.IWeChatUserInfoService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/weChatUserInfo")
public class WeChatUserInfoController {
    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(HttpAspect.class);
    @Autowired
    IWeChatUserInfoService weChatUserInfoService;

    @RequestMapping("/info")
    public ServerResponse info(WeChatUserInfo weChatUserInfo){
        WeChatUserInfo w = RequestHolder.getCurrentUser();
        logger.info("====用户信息：====" + w.toString());
        if(("oi05j1rBhYzBjnzgp-ipCdDsdwhs".equalsIgnoreCase(w.getOpenid())) || ("oi05j1icCldZEo4O7hFv661I4I-4".equalsIgnoreCase(w.getOpenid()))){
            logger.info("openid:" + w.getOpenid());
            logger.info("进入获得用户信息");
            return weChatUserInfoService.info(weChatUserInfo);
        }
        return new ServerResponse(ResponseCode.NEED_LOGIN.getCode());
    }

    @RequestMapping("/auth")
    public ServerResponse auth(WeChatUserInfo weChatUserInfo){
        WeChatUserInfo w = RequestHolder.getCurrentUser();
        logger.info("====用户信息：====" + w.toString());
        if(("oi05j1rBhYzBjnzgp-ipCdDsdwhs".equalsIgnoreCase(w.getOpenid())) || ("oi05j1icCldZEo4O7hFv661I4I-4".equalsIgnoreCase(w.getOpenid())) ){
            return weChatUserInfoService.updateAuth(weChatUserInfo);
        }
        return new ServerResponse(ResponseCode.NEED_LOGIN.getCode());
    }
}
