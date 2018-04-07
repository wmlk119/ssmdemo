package com.archer.ssm.module.base.service;

import com.archer.ssm.module.base.pojo.SysMenu;
import com.archer.ssm.module.base.pojo.ZNode;

import java.util.List;

public interface SysMenuService {

    // 新增
    public int add(SysMenu entity);

    // 查询条数
    public int getCount(SysMenu entity);

    // 分页条件查询
    public List<SysMenu> getPageList(int pageSize, int pageIndex, SysMenu entity);

    // 查询父级菜单
    public List<SysMenu> getSupMenus(int menuLevel);

    // 查询菜单集合
    public List<SysMenu> getList();

    // 根据角色ID获取ztree菜单集合
    public List<ZNode> getZNodes(String roleId);

    // 根据角色id查询菜单集合
    public List<SysMenu> getListByRoleId(String roleId);

    // 更新
    public int update(SysMenu entity);

    // 删除
    public int delete(String menuId);

    // 根据菜单ID查询子集菜单
    public List<SysMenu> getListBySupMenuId(String supMenuId);


}
