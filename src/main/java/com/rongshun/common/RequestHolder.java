package com.rongshun.common;


import com.rongshun.pojo.wechat.WeChatUserInfo;

import javax.servlet.http.HttpServletRequest;

public class RequestHolder {

    private static final ThreadLocal<WeChatUserInfo> userHolder = new ThreadLocal<WeChatUserInfo>();

    private static final ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<HttpServletRequest>();

    public static void add(WeChatUserInfo sysUser) {
        userHolder.set(sysUser);
    }

    public static void add(HttpServletRequest request) {
        requestHolder.set(request);
    }

    public static WeChatUserInfo getCurrentUser() {
        return userHolder.get();
    }

    public static HttpServletRequest getCurrentRequest() {
        return requestHolder.get();
    }

    public static void remove() {
        userHolder.remove();
        requestHolder.remove();
    }
}
