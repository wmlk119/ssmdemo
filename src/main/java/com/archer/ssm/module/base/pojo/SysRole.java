package com.archer.ssm.module.base.pojo;

import java.io.Serializable;

/**
 * 菜单角色实体类
 *
 * @author Administrator
 * @create 2018-03-23 10:36
 */
public class SysRole extends BaseEntity implements Serializable{
    private static final long serialVersionUID = 7110505153518776096L;
    // 角色ID
    private String roleId;
    // 角色名称
    private String roleName;
    // 角色描述
    private String roleDes;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDes() {
        return roleDes;
    }

    public void setRoleDes(String roleDes) {
        this.roleDes = roleDes;
    }

}
