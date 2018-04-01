package com.archer.ssm.module.base.mapper;

import com.archer.ssm.module.base.pojo.SysMenu;
import com.archer.ssm.module.base.pojo.ZNode;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysMenuMapper {

    // 新增
    public int add(SysMenu entity);

    // 查询条数
    public int getCount(SysMenu entity);

    // 分页条件查询
    public List<SysMenu> getPageList(@Param("pageSize") int pageSize,@Param("pageIndex") int pageIndex,@Param("entity") SysMenu entity);

    // 查询父级菜单
    public List<SysMenu> getSupMenus(int menuLevel);

    // 查询菜单集合
    public List<SysMenu> getList();

    // 根据角色id获取ztree菜单集合
    public List<ZNode> getZNodes(String roleId);

}
