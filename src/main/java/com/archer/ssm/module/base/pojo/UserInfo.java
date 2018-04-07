package com.archer.ssm.module.base.pojo;

import java.io.Serializable;

/**
 * 用户
 *
 * @author Administrator
 * @create 2018-03-16 17:06
 */
public class UserInfo extends BaseEntity implements Serializable{
    private static final long serialVersionUID = 2102662732922932189L;
    // 用户ID
    private String userId;
    // 登录名
    private String loginName;
    // 密码
    private String loginPwd;
    // 加密盐值
    private String pwdSalt;
    // 用户名
    private String userName;
    // 性别
    private Integer sex;
    // 联系电话
    private String phoneNum;
    // 所属区县
    private Integer districtCode;
    // 角色ID
    private String roleId;
    // 是否有效
    private Integer isEnable;
    // 备注
    private String remark;

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

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getPwdSalt() {
        return pwdSalt;
    }

    public void setPwdSalt(String pwdSalt) {
        this.pwdSalt = pwdSalt;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Integer getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(Integer districtCode) {
        this.districtCode = districtCode;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
