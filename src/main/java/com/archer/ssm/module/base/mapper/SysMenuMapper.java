package com.archer.ssm.module.base.mapper;

import com.archer.ssm.module.base.pojo.SysMenu;
import com.archer.ssm.module.base.pojo.ZNode;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysMenuMapper extends BaseMapper<SysMenu>{

    // 查询条数
    public int getCount(SysMenu entity);

    // 分页条件查询
    public List<SysMenu> getPageList(@Param("pageSize") int pageSize,@Param("pageIndex") int pageIndex,@Param("entity") SysMenu entity);

    // 查询父级菜单
    public List<SysMenu> getSupMenus(int menuLevel);


    // 根据角色id获取ztree菜单集合
    public List<ZNode> getZNodes(String roleId);

    // 根据角色id查询菜单集合
    public List<SysMenu> getListByRoleId(String roleId);


}
