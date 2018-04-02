package com.archer.ssm.module.base.service.impl;

import com.archer.ssm.module.base.mapper.MenuRoleRelationMapper;
import com.archer.ssm.module.base.pojo.MenuRoleRelation;
import com.archer.ssm.module.base.service.MenuRoleRelationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
@Service
public class MenuRoleRelationServiceImpl implements MenuRoleRelationService {
    // log
    private static final Logger log = LoggerFactory.getLogger(MenuRoleRelationServiceImpl.class);

    @Autowired
    private MenuRoleRelationMapper menuRoleRelationMapper;

    @Override
    public int batchAdd(List<MenuRoleRelation> list) {
        return menuRoleRelationMapper.batchAdd(list);
    }

    @Override
    public int batchDel(String roleId, List<String> menuIds) {
        return menuRoleRelationMapper.batchDel(roleId,menuIds);
    }

    /**
     * 更新角色菜单分配
     * @param relationList
     * @param roleId
     * @param delMenuIdList
     * @return
     */
    @Override
    public Boolean doUpdate(List<MenuRoleRelation> relationList, String roleId, List<String> delMenuIdList) {
        Boolean res = false;
        try {
            // 新增菜单角色关联
            if(!CollectionUtils.isEmpty(relationList)){
                menuRoleRelationMapper.batchAdd(relationList);
            }
            // 删除菜单角色关联
            if(!CollectionUtils.isEmpty(delMenuIdList)){
                menuRoleRelationMapper.batchDel(roleId,delMenuIdList);
            }
            res = true;
        } catch (Exception e) {
            log.error("更新角色菜单分配:",e);
        }
        return res;
    }
}
