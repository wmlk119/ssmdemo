package com.archer.ssm.module.base.service;

import com.archer.ssm.module.base.pojo.LoginLog;
import com.archer.ssm.module.base.pojo.UserInfo;

/**
 * 登陆日志服务
 */
public interface LoginLogService {

    // 新增
    public int add(String ipAddress, UserInfo userInfo);

}
