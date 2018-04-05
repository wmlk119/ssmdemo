package com.archer.ssm.module.base.service.impl;

import com.archer.ssm.module.base.mapper.MenuRoleRelationMapper;
import com.archer.ssm.module.base.mapper.SysRoleMapper;
import com.archer.ssm.module.base.pojo.SysRole;
import com.archer.ssm.module.base.service.SysRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 菜单角色实现类
 *
 * @author Administrator
 * @create 2018-03-23 11:09
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {
    public static final Logger log = LoggerFactory.getLogger(SysRoleServiceImpl.class);

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private MenuRoleRelationMapper menuRoleRelationMapper;

    @Override
    public int add(SysRole entity) {
        return sysRoleMapper.add(entity);
    }

    // 删除角色信息
    @Override
    public int delete(String roleId) {
        int res = 1;
        try {
            // 删除角色数据
            sysRoleMapper.delete(roleId);
            // 删除菜单角色关联数据
            menuRoleRelationMapper.delByRoleId(roleId);
        } catch (Exception e) {
            res = -1;
            log.error("删除角色信息异常：",e);
        }
        return res;
    }

    @Override
    public int update(SysRole entity) {
        return sysRoleMapper.update(entity);
    }

    @Override
    public List<SysRole> getList() {
        return sysRoleMapper.getList();
    }

    @Override
    public int getCount(SysRole entity) {
        return sysRoleMapper.getCount(entity);
    }

    @Override
    public List<SysRole> getPageList(int pageSize, int pageIndex, SysRole entity) {
        return sysRoleMapper.getPageList(pageSize,pageIndex,entity);
    }
}
