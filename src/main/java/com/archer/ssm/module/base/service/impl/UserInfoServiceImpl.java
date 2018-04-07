package com.archer.ssm.module.base.service.impl;

import com.archer.ssm.module.base.mapper.UserInfoMapper;
import com.archer.ssm.module.base.pojo.UserInfo;
import com.archer.ssm.module.base.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 * @create 2018-03-16 17:19
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    // 根据ID查询
    @Override
    public UserInfo getById(Long id) {
        return userInfoMapper.getById(id);
    }

    @Override
    public UserInfo get(String userId) {
        return userInfoMapper.get(userId);
    }

    // 新增
    @Override
    public int add(UserInfo entity) {
        return userInfoMapper.add(entity);
    }

    // 根据ID删除
    @Override
    public int delete(String id) {
        return userInfoMapper.delete(id);
    }

    // 修改
    @Override
    public int update(UserInfo entity) {
        return userInfoMapper.update(entity);
    }

    // 查询条数
    @Override
    public int getCount(UserInfo entity) {
        return userInfoMapper.getCount(entity);
    }

    // 分页条件查询
    @Override
    public List<UserInfo> getPageList(int pageSize, int offset, UserInfo entity) {
        return userInfoMapper.getPageList(pageSize,offset,entity);
    }

    // 根据条件查询
    @Override
    public List<UserInfo> getByCondition(UserInfo entity) {
        return userInfoMapper.getByCondition(entity);
    }
}
