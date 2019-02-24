package com.rongshun.mp.handler;

import com.rongshun.dao.wechat.WeChatUserInfoMapper;
import com.rongshun.mp.builder.TextBuilder;
import com.rongshun.pojo.wechat.WeChatUserInfo;
import com.rongshun.service.wechat.IWeChatUserInfoService;
import com.rongshun.service.wechat.WeixinService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Binary Wang
 */
@Component
public class SubscribeHandler extends AbstractHandler {

  @Autowired
  IWeChatUserInfoService weChatUserInfoService;

  @Autowired
  WeChatUserInfoMapper weChatUserInfoMapper;

  @Override
  public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
                                  WxSessionManager sessionManager) throws WxErrorException {

    this.logger.info("新关注用户 OPENID: " + wxMessage.getFromUser());

    WeixinService weixinService = (WeixinService) wxMpService;

    // 获取微信用户基本信息
    WxMpUser userWxInfo = weixinService.getUserService().userInfo(wxMessage.getFromUser(), null);

    if (userWxInfo != null) {
      WeChatUserInfo userInfo = weChatUserInfoMapper.selectByOpenid(userWxInfo.getUnionId());
      if(userInfo == null){
        userInfo = new WeChatUserInfo();
        userInfo.setCity(userWxInfo.getCity());
        userInfo.setCountry(userWxInfo.getCountry());
        userInfo.setGroupid(userWxInfo.getUnionId());
        userInfo.setHeadimgurl(userWxInfo.getHeadImgUrl());
        userInfo.setLanguage(userWxInfo.getLanguage());
        userInfo.setNickname(userWxInfo.getNickname());
        userInfo.setOpenid(userWxInfo.getOpenId());
        userInfo.setProvince(userWxInfo.getProvince());
        userInfo.setRemark(userWxInfo.getRemark());
        userInfo.setSex(userInfo.getSex());
        userInfo.setSubscribe(1);
        userInfo.setLoginId("0");
        weChatUserInfoMapper.insertSelective(userInfo);
      }else {
        userInfo.setSubscribe(1);
        weChatUserInfoMapper.updateByPrimaryKeySelective(userInfo);
      }
    }

    WxMpXmlOutMessage responseResult = null;
    try {
      responseResult = handleSpecial(wxMessage);
    } catch (Exception e) {
      this.logger.error(e.getMessage(), e);
    }

    if (responseResult != null) {
      return responseResult;
    }

    try {
      return new TextBuilder().build("感谢关注", wxMessage, weixinService);
    } catch (Exception e) {
      this.logger.error(e.getMessage(), e);
    }

    return null;
  }

  /**
   * 处理特殊请求，比如如果是扫码进来的，可以做相应处理
   */
  protected WxMpXmlOutMessage handleSpecial(WxMpXmlMessage wxMessage) throws Exception {
    //TODO
    return null;
  }

}
