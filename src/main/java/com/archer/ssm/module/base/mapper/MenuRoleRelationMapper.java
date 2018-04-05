package com.archer.ssm.module.base.mapper;

import com.archer.ssm.module.base.pojo.MenuRoleRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRoleRelationMapper {

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
    public int batchDel(@Param("roleId") String roleId, @Param("menuIds") List<String> menuIds);

    /**
     * 根据角色ID删除
     * @param roleId
     * @return
     */
    public int delByRoleId(String roleId);


}
