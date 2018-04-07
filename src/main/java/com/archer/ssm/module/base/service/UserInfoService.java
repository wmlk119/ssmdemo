package com.archer.ssm.module.base.service;

import com.archer.ssm.module.base.pojo.UserInfo;

import java.util.List;

public interface UserInfoService {

    // 根据ID查询
    public UserInfo getById(Long id);

    // 查询
    public UserInfo get(String userId);

    // 新增
    public int add(UserInfo entity);

    // 根据ID删除
    public int delete(String id);

    // 修改
    public int update(UserInfo entity);

    // 查询条数
    public int getCount(UserInfo entity);

    // 分页条件查询
    public List<UserInfo> getPageList(int pageSize, int offset, UserInfo entity);

    // 根据条件查询用户
    public List<UserInfo> getByCondition(UserInfo entity);

}
