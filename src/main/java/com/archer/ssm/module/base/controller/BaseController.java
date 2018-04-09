package com.archer.ssm.module.base.controller;

import com.archer.ssm.module.base.pojo.UserInfo;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * 基础控制器
 *
 * @author Administrator
 * @create 2018-03-27 14:18
 */
public class BaseController {
    public static final Logger log = LoggerFactory.getLogger(BaseController.class);

    /**
     * session中读取用户信息
     * @param request
     * @return
     */
    public UserInfo getUserInfo(HttpServletRequest request){
        UserInfo account = null;
        try {
            Object obj = request.getSession().getAttribute("sysUser");
            if(null != obj){
                account = (UserInfo) obj;
            }
        } catch (Exception e) {
            log.error("session中读取用户信息:",e);
        }
        return account;
    }

    /**
     * 获取请求IP地址
     * @param request
     * @return
     */
    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            if (ip.indexOf(",") != -1) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


}
