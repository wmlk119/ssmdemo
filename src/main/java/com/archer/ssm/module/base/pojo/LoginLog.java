package com.archer.ssm.module.base.pojo;

import java.io.Serializable;

public class LoginLog extends BaseEntity implements Serializable{
    // 日志ID
    private String logId;
    // 用户ID
    private String userId;
    // 登录名
    private String loginName;
    // 用户名
    private String userName;
    // 访问IP
    private String loginIp;
    // 访问地址
    private String address;
    // 坐标地址
    private String point;

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }
}
