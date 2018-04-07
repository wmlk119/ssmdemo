package com.archer.ssm.module.base.service.impl;

import com.archer.ssm.module.base.mapper.MenuRoleRelationMapper;
import com.archer.ssm.module.base.mapper.SysMenuMapper;
import com.archer.ssm.module.base.pojo.SysMenu;
import com.archer.ssm.module.base.pojo.ZNode;
import com.archer.ssm.module.base.service.SysMenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 菜单服务实现类
 *
 * @author Administrator
 * @create 2018-03-23 11:09
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {
    public static final Logger log = LoggerFactory.getLogger(SysMenuServiceImpl.class);

    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private MenuRoleRelationMapper menuRoleRelationMapper;


    @Override
    public int add(SysMenu entity) {
        return sysMenuMapper.add(entity);
    }

    // 查询条数
    @Override
    public int getCount(SysMenu entity) {
        return sysMenuMapper.getCount(entity);
    }

    // 分页条件查询
    @Override
    public List<SysMenu> getPageList(int pageSize, int pageIndex, SysMenu entity) {
        return sysMenuMapper.getPageList(pageSize,pageIndex,entity);
    }

    // 查询父级菜单
    @Override
    public List<SysMenu> getSupMenus(int menuLevel) {
        return sysMenuMapper.getSupMenus(menuLevel);
    }

    // 查询菜单集合
    @Override
    public List<SysMenu> getList() {
        return sysMenuMapper.getList();
    }

    @Override
    public List<ZNode> getZNodes(String roleId) {
        return sysMenuMapper.getZNodes(roleId);
    }

    // 根据角色id查询菜单集合
    @Override
    public List<SysMenu> getListByRoleId(String roleId) {
        return sysMenuMapper.getListByRoleId(roleId);
    }

    // 更新
    @Override
    public int update(SysMenu entity) {
        return sysMenuMapper.update(entity);
    }

    // 删除
    @Override
    public int delete(String menuId) {
        int res = 1;
        try {
            // 删除菜单
            sysMenuMapper.delete(menuId);
            // 删除菜单角色表
            menuRoleRelationMapper.delByMenuId(menuId);
        } catch (Exception e) {
            log.error("删除菜单异常：",e);
            res = -1;
        }
        return res;
    }

    // 根据菜单ID查询子集菜单
    @Override
    public List<SysMenu> getListBySupMenuId(String supMenuId) {
        return sysMenuMapper.getListBySupMenuId(supMenuId);
    }
}
