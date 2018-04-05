package com.archer.ssm.module.base.mapper;

import com.archer.ssm.module.base.pojo.SysRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRoleMapper extends BaseMapper<SysRole>{

    // 查询条数
    public int getCount(SysRole entity);

    // 分页条件查询
    public List<SysRole> getPageList(@Param("pageSize") int pageSize,@Param("pageIndex") int pageIndex, @Param("entity") SysRole entity);

}
