package com.rongshun.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IpUtil
{
    private static final Logger log = LoggerFactory.getLogger(IpUtil.class);
    public static final String ERROR_IP = "127.0.0.1";
    public static final Pattern pattern = Pattern.compile("(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})");

    public static String getRemoteIp(HttpServletRequest request)
    {
        String ip = request.getHeader("x-real-ip");
        if (ip == null) {
            ip = request.getRemoteAddr();
        }
        String[] stemps = ip.split(",");
        if ((stemps != null) && (stemps.length >= 1)) {
            ip = stemps[0];
        }
        ip = ip.trim();
        if (ip.length() > 23) {
            ip = ip.substring(0, 23);
        }
        return ip;
    }

    public static String getUserIP(HttpServletRequest request)
    {
        String ip = request.getHeader("X-Real-IP");
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("x-forwarded-for");
        }
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip)))
        {
            ip = request.getRemoteAddr();
            if ("0:0:0:0:0:0:0:1".equals(ip)) {
                ip = "127.0.0.1";
            }
        }
        if ("unknown".equalsIgnoreCase(ip))
        {
            ip = "127.0.0.1";
            return ip;
        }
        int pos = ip.indexOf(',');
        if (pos >= 0) {
            ip = ip.substring(0, pos);
        }
        return ip;
    }

    public static String getLastIpSegment(HttpServletRequest request)
    {
        String ip = getUserIP(request);
        if (ip != null) {
            ip = ip.substring(ip.lastIndexOf('.') + 1);
        } else {
            ip = "0";
        }
        return ip;
    }

    public static boolean isValidIP(HttpServletRequest request)
    {
        String ip = getUserIP(request);
        return isValidIP(ip);
    }

    public static boolean isValidIP(String ip)
    {
        if (StringUtils.isEmpty(ip))
        {
            log.debug("ip is null. valid result is false");
            return false;
        }
        Matcher matcher = pattern.matcher(ip);
        boolean isValid = matcher.matches();
        log.debug("valid ip:" + ip + " result is: " + isValid);
        return isValid;
    }

    public static String getLastServerIpSegment()
    {
        String ip = getServerIP();
        if (ip != null) {
            ip = ip.substring(ip.lastIndexOf('.') + 1);
        } else {
            ip = "0";
        }
        return ip;
    }

    public static String getServerIP()
    {
        try
        {
            InetAddress inet = InetAddress.getLocalHost();
            return inet.getHostAddress();
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        return "127.0.0.1";
    }
}
