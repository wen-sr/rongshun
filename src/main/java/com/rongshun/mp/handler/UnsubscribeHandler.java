package com.rongshun.mp.handler;

import com.rongshun.aspect.HttpAspect;
import com.rongshun.dao.wechat.WeChatUserInfoMapper;
import com.rongshun.pojo.wechat.WeChatUserInfo;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Binary Wang
 */
@Component
public class UnsubscribeHandler extends AbstractHandler {
  private final static Logger logger = LoggerFactory.getLogger(UnsubscribeHandler.class);
  @Autowired
  WeChatUserInfoMapper weChatUserInfoMapper;

  @Override
  public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                  Map<String, Object> context, WxMpService wxMpService,
                                  WxSessionManager sessionManager) {
    String openId = wxMessage.getFromUser();
    logger.info("取消关注用户 OPENID: " + openId);
    // 可以更新本地数据库为取消关注状态
    WeChatUserInfo userInfo = weChatUserInfoMapper.selectByOpenid(openId);
    weChatUserInfoMapper.deleteByPrimaryKey(userInfo.getId());
    logger.info("成功删除" + userInfo.getNickname() + "的信息" );
    return null;
  }

}
