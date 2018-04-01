package com.archer.ssm.module.base.mapper;

import com.archer.ssm.module.base.pojo.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInfoMapper {

    // 根据ID查询
    public UserInfo getById(Long id);

    // 新增
    public int add(UserInfo entity);

    // 根据ID删除
    public int deleteById(Long id);

    // 修改
    public int update(UserInfo entity);

    // 查询条数
    public int getCount(UserInfo entity);

    // 分页条件查询
    public List<UserInfo> getPageList(@Param("pageSize") int pageSize, @Param("offset") int offset, @Param("entity") UserInfo entity);

    // 根据条件查询用户
    public List<UserInfo> getByCondition(UserInfo entity);


}
