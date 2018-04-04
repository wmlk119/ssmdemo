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
}
