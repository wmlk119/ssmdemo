package com.archer.ssm.module.base.service.impl;

import com.archer.ssm.module.base.mapper.SysMenuMapper;
import com.archer.ssm.module.base.pojo.SysMenu;
import com.archer.ssm.module.base.pojo.ZNode;
import com.archer.ssm.module.base.service.SysMenuService;
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
    @Autowired
    private SysMenuMapper sysMenuMapper;

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
}
