package com.archer.ssm.module.base.controller;

import com.alibaba.fastjson.JSON;
import com.archer.ssm.module.base.pojo.MenuRoleRelation;
import com.archer.ssm.module.base.pojo.ResultBody;
import com.archer.ssm.module.base.service.MenuRoleRelationService;
import com.archer.ssm.utils.common.DateUtils;
import com.archer.ssm.utils.common.UniqId;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 菜单角色控制器
 *
 * @author Administrator
 * @create 2018-4-2 15:25:38
 */
@Controller
@RequestMapping("/ssm/menurole")
public class MenuRoleRelationController extends BaseController{
    @Autowired
    private MenuRoleRelationService menuRoleRelationService;


    /**
     * 菜单分配操作
     * @param request
     *
     * @param addMenuIds
     * @param delMenuIds
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public ResultBody doUpdate(HttpServletRequest request,String addMenuIds,String delMenuIds,String roleId){
        ResultBody res = new ResultBody();
        // 参数验证
        if(StringUtils.isEmpty(roleId)||StringUtils.isEmpty(addMenuIds)||StringUtils.isEmpty(delMenuIds)){
            res.setMsg("请求参数为空");
            res.setCode("001");
            return res;
        }
        // JSON转换
        List<String> addMenuIdList = JSON.parseArray(addMenuIds,String.class);
        List<String> delMenuIdList = JSON.parseArray(delMenuIds,String.class);
        // 构造新增菜单角色关联集合
        List<MenuRoleRelation> menuRoleRelations = new ArrayList<MenuRoleRelation>();
        if(!CollectionUtils.isEmpty(addMenuIdList)){
            String createTime = DateUtils.formatDateTime(new Date());
            addMenuIdList.stream().forEach(menuId ->{
                MenuRoleRelation relation = new MenuRoleRelation();
                relation.setRelationId(UniqId.getInstance().getWorkId().toString());
                relation.setRoleId(roleId);
                relation.setMenuId(menuId);
                relation.setCreateTime(createTime);
                menuRoleRelations.add(relation);
            });
        }
        // 执行更新菜单分配操作
        if(!menuRoleRelationService.doUpdate(menuRoleRelations,roleId,delMenuIdList)){
            res.setMsg("更新菜单分配失败");
            res.setCode("001");
            return res;
        }
        res.setMsg("菜单分配成功");
        res.setCode("000");
        return res;
    }

}
