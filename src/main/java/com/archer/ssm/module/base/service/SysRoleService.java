package com.archer.ssm.module.base.service;

import com.archer.ssm.module.base.pojo.SysRole;

import java.util.List;

public interface SysRoleService {

    // 新增
    public int add(SysRole entity);

    // 删除
    public int delete(String roleId);

    // 修改
    public int update(SysRole entity);

    // 获取角色集合
    public List<SysRole> getList();

    // 查询条数
    public int getCount(SysRole entity);

    // 分页条件查询
    public List<SysRole> getPageList(int pageSize, int pageIndex, SysRole entity);

    // 根据ID查询
    public SysRole get(String roleId);

}
