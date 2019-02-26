package com.rongshun.service.wechat;

import com.rongshun.common.ServerResponse;
import com.rongshun.pojo.wechat.WeChatUserInfo;

import java.util.List;

/**
 * Author: wen-sir
 * Description:
 * DateCrated: 2019/2/19 13:23
 * Modified By：
 */
public interface IWeChatUserInfoService {
    ServerResponse<List> info(WeChatUserInfo weChatUserInfo);

    ServerResponse updateAuth(WeChatUserInfo weChatUserInfo);
}
