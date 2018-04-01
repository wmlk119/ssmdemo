package com.archer.ssm.module.base.pojo;

import java.io.Serializable;

/**
 * 菜单实体类
 *
 * @author Administrator
 * @create 2018-03-23 10:29
 */
public class SysMenu extends BaseEntity implements Serializable{
    // ID
    private String menuId;
    // 菜单级别
    private Integer menuLevel;
    // 菜单名称
    private String menuName;
    // 父级菜单ID
    private String supMenuId;
    // 父级菜单名称
    private String supMenuName;
    // 节点链接
    private String menuUrl;
    // 菜单顺序
    private Integer menuSeq;
    // 菜单图标
    private String menuIcon;

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public Integer getMenuLevel() {
        return menuLevel;
    }

    public void setMenuLevel(Integer menuLevel) {
        this.menuLevel = menuLevel;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getSupMenuId() {
        return supMenuId;
    }

    public void setSupMenuId(String supMenuId) {
        this.supMenuId = supMenuId;
    }

    public String getSupMenuName() {
        return supMenuName;
    }

    public void setSupMenuName(String supMenuName) {
        this.supMenuName = supMenuName;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public Integer getMenuSeq() {
        return menuSeq;
    }

    public void setMenuSeq(Integer menuSeq) {
        this.menuSeq = menuSeq;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

}
