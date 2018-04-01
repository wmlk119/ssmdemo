package com.archer.ssm.module.base.service.impl;

import com.archer.ssm.module.base.mapper.SysRoleMapper;
import com.archer.ssm.module.base.pojo.SysRole;
import com.archer.ssm.module.base.service.SysRoleService;
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
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public int add(SysRole entity) {
        return sysRoleMapper.add(entity);
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
