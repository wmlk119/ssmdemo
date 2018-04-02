package com.archer.ssm.module.base.service;

import com.archer.ssm.module.base.pojo.MenuRoleRelation;

import java.util.List;

public interface MenuRoleRelationService {
    /**
     * 批量新增
     * @param list 菜单角色集合
     * @return
     */
    public int batchAdd(List<MenuRoleRelation> list);

    /**
     * 批量删除
     * @param roleId
     * @param menuIds
     * @return
     */
    public int batchDel(String roleId, List<String> menuIds);

    /**
     * 更新角色菜单分配
     * @param relationList
     * @param roleId
     * @param delMenuIdList
     * @return
     */
    public Boolean doUpdate(List<MenuRoleRelation> relationList,String roleId,List<String> delMenuIdList);

}
