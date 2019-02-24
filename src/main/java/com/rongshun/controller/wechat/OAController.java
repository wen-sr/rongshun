package com.rongshun.controller.wechat;

import com.rongshun.common.Constant;
import com.rongshun.common.RequestHolder;
import com.rongshun.common.ServerResponse;
import com.rongshun.dao.wechat.WeChatUserInfoMapper;
import com.rongshun.pojo.wechat.WeChatUserInfo;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequestMapping("/oa/wechat")
public class OAController {

    @Autowired
    WxMpService wxMpService;

    @Autowired
    WeChatUserInfoMapper userInfoService;


    @RequestMapping("/auth")
    public String auth(String returnUrl) throws UnsupportedEncodingException {
        String url = Constant.WECHATDOMAIN + "/management/oa/wechat/userInfo";
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_BASE, URLEncoder.encode(returnUrl,"utf-8"));
//        System.out.println(redirectUrl);
        return "redirect:" + redirectUrl;
    }

    @RequestMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code,
                           @RequestParam("state") String returnUrl,
                           HttpSession session) {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = (WxMpOAuth2AccessToken) session.getAttribute(Constant.WXMPOAUTH2ACCESSTOKEN);
//        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            if(wxMpOAuth2AccessToken == null || wxMpOAuth2AccessToken.getOpenId() == null ){
                wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
                session.setAttribute(Constant.WXMPOAUTH2ACCESSTOKEN, wxMpOAuth2AccessToken);
            }
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        //将openid放入到session中
        //根据openid找到对应的工号
        String openId = wxMpOAuth2AccessToken.getOpenId();
        WeChatUserInfo userInfo = userInfoService.selectByOpenid(openId);
        if(userInfo == null || "0".equalsIgnoreCase(userInfo.getLoginId())) {
            return "redirect:/page/error/wechatNoAccess.html";
        }
        RequestHolder.add(userInfo);
        return "redirect:" + returnUrl;
    }


    @RequestMapping("/deviceNeedRepair")
    public ServerResponse addRepair(){
        System.out.println("*******************************************");
        return null;
    }


}
