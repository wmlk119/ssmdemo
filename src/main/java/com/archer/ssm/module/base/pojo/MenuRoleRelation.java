package com.archer.ssm.module.base.pojo;

import java.io.Serializable;

/**
 * 菜单角色关联实体
 *
 * @author Administrator
 * @create 2018-03-30 11:33
 */
public class MenuRoleRelation extends BaseEntity implements Serializable {
    // 关联id
    private String relationId;
    // 角色id
    private String roleId;
    // 菜单id
    private String menuId;

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
}
