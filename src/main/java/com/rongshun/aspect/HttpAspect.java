package com.rongshun.aspect;

import com.rongshun.common.Constant;
import com.rongshun.common.RequestHolder;
import com.rongshun.exception.MyException;
import com.rongshun.pojo.wechat.WeChatUserInfo;
import com.rongshun.util.IpUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by 廖师兄
 * 2017-01-15 12:31
 */
@Aspect
@Component
public class HttpAspect {

    private final static Logger logger = LoggerFactory.getLogger(HttpAspect.class);

//    @Pointcut("execution(public * com.management.controller.login.UserController.*(..))")
    @Pointcut("execution(public * com.rongshun.controller..*.*(..)) " +
            "&& !execution(public * com.rongshun.controller.wechat.OAController.*(..)) " +
            "&& !execution(public * com.rongshun.controller.wechat.WxMpPortalController.*(..)) " +
            "&& !execution(public * com.rongshun.controller.wechat.WxMenuController.*(..))" )
    public void log() {
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) throws IOException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpSession session = request.getSession();
        WeChatUserInfo weChatUserInfo = (WeChatUserInfo) session.getAttribute(Constant.CURRENT_USER);
        if(weChatUserInfo == null || "0".equalsIgnoreCase(weChatUserInfo.getAuth())) {
            logger.info("未授权的用户，无法继续进入程序");
            throw new MyException(10, "未授权的用户，无法继续进入程序");
        }
        RequestHolder.add(weChatUserInfo);
        logger.info("---------------成功获得登录信息："+ RequestHolder.getCurrentUser().getId() +"---------------");
        RequestHolder.add(request);

        logger.info("---------------这里是请求完成之前的操作---------------");
        //url
        logger.info("url={}", request.getRequestURL());

        //method
        logger.info("method={}", request.getMethod());

        //ip
        logger.info("ip={}", IpUtil.getUserIP(request));

        //类方法
        logger.info("class_method={}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());

        //参数
        logger.info("args={}", joinPoint.getArgs());

    }

    @After("log()")
    public void doAfter() {
        logger.info("---------------这里是请求完成之后的操作---------------");
    }

    @AfterReturning(returning = "object", pointcut = "log()")
    public void doAfterReturning(Object object) {
        logger.info("response={}",  object == null ? null :object.toString());
    }

}
