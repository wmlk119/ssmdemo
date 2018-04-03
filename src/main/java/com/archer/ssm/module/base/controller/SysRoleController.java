package com.archer.ssm.module.base.controller;

import com.archer.ssm.module.base.pojo.BootstrapTableResult;
import com.archer.ssm.module.base.pojo.ResultBody;
import com.archer.ssm.module.base.pojo.SysRole;
import com.archer.ssm.module.base.service.SysRoleService;
import com.archer.ssm.utils.common.DateUtils;
import com.archer.ssm.utils.common.UniqId;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 菜单角色控制器
 *
 * @author Administrator
 * @create 2018-03-23 11:11
 */
@Controller
@RequestMapping("/ssm/sysrole")
public class SysRoleController extends BaseController{
    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 查询角色列表
     * @param pageSize
     * @param pageIndex
     * @param roleName
     * @return
     */
    @RequestMapping(value = "/rolelist",method = RequestMethod.POST)
    @ResponseBody
    public BootstrapTableResult<SysRole> getRoleList(String pageSize, String pageIndex, String roleName){
        BootstrapTableResult<SysRole> res = new BootstrapTableResult<SysRole>();
        try {
            if(StringUtils.isEmpty(pageSize) || StringUtils.isEmpty(pageIndex)){
                res.setTotal(0);
                res.setMsg("分页查询参数为空");
                return res;
            }
            // 查询条数
            SysRole paraEntity = new SysRole();
            if(!StringUtils.isEmpty(roleName)){
                paraEntity.setRoleName(roleName);
            }
            int count = sysRoleService.getCount(paraEntity);
            List<SysRole> list = new ArrayList<SysRole>();
            if(count > 0){
                list = sysRoleService.getPageList(Integer.parseInt(pageSize),Integer.parseInt(pageIndex),paraEntity);
            }
            res.setRows(list);
            res.setTotal(count);
            res.setMsg("查询角色成功");
        } catch (Exception e) {
            log.error("角色分页查询异常：",e);
            res.setTotal(0);
            res.setMsg("角色分页查询异常");
        }
        return res;
    }

    /**
     * 新增角色
     * @param roleName
     * @param roleDes
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public ResultBody doAdd(String roleName, String roleDes){
        ResultBody res = new ResultBody();
        if(StringUtils.isEmpty(roleName)){
            res.setCode("001");
            res.setMsg("角色名不能为空");
            return res;
        }
        // 新增角色
        SysRole entity = new SysRole();
        entity.setRoleId(UniqId.getInstance().getWorkId().toString());
        entity.setRoleName(roleName);
        entity.setRoleDes(roleDes);
        entity.setCreateTime(DateUtils.formatDateTime(new Date()));
        sysRoleService.add(entity);

        res.setCode("000");
        res.setMsg("新增角色成功");
        return res;
    }


    /**
     * 获取角色集合
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public ResultBody getList(){
        ResultBody res = new ResultBody();
        try {
            List<SysRole> list = sysRoleService.getList();
            res.setResult(list);
            res.setCode("000");
            res.setMsg("查询角色成功");
        } catch (Exception e) {
            log.error("获取角色集合失败：",e);
            res.setCode("002");
            res.setMsg("查询角色失败");
        }
        return res;
    }


}
