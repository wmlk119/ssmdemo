package com.archer.ssm.module.base.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.archer.ssm.module.base.mapper.LoginLogMapper;
import com.archer.ssm.module.base.pojo.LoginLog;
import com.archer.ssm.module.base.pojo.UserInfo;
import com.archer.ssm.module.base.service.LoginLogService;
import com.archer.ssm.utils.common.DateUtils;
import com.archer.ssm.utils.common.HttpClientUtil;
import com.archer.ssm.utils.common.RegExUtil;
import com.archer.ssm.utils.common.UniqId;
import com.archer.ssm.utils.constants.SysConst;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 登陆日志服务实现类
 */
@Service
public class LoginLogServiceImpl implements LoginLogService {
    // log
    private static final Logger log = LoggerFactory.getLogger(LoginLogServiceImpl.class);

    @Autowired
    private LoginLogMapper loginLogMapper;

    /**
     * 增加访问日志
     * @param ipAddress
     * @param userInfo
     * @return
     */
    @Override
    public int add(String ipAddress, UserInfo userInfo) {
        int res = 1;
        // 访问地址
        String address = "";
        // 坐标地址
        String point = "";
        String regEx = "^192.168.\\d{1,3}.\\d{1,3}$";
        try {
            // ip格式验证
            if("0:0:0:0:0:0:0:1".equals(ipAddress)||"127.0.0.1".equals(ipAddress)|| RegExUtil.validateInput(ipAddress,regEx)){ // 内网
                address = "内网请求";
            }else{ // 公网
                String url = SysConst.API_LOCATION+ipAddress;
                String result = HttpClientUtil.get(url);
                if(!StringUtils.isEmpty(result)){
                    JSONObject obj = JSON.parseObject(result);
                    int status = obj.getInteger("status");
                    if(0 == status){ // 查询成功
                        address = obj.getString("address");
                        JSONObject content = obj.getJSONObject("content");
                        JSONObject obj_point = content.getJSONObject("point");
                        point = JSON.toJSONString(obj_point);
                    }
                }
            }
            // add LoginLog
            LoginLog entity = new LoginLog();
            entity.setLogId(UniqId.getInstance().getWorkId().toString());
            entity.setUserId(userInfo.getUserId());
            entity.setLoginName(userInfo.getLoginName());
            entity.setUserName(userInfo.getUserName());
            entity.setLoginIp(ipAddress);
            entity.setAddress(address);
            entity.setPoint(point);
            entity.setCreateTime(DateUtils.formatDateTime(new Date()));
            loginLogMapper.add(entity);
        } catch (Exception e) {
            log.error("增加访问日志异常：",e);
            res = -1;
        }
        return res;
    }
}
