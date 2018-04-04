package com.archer.ssm.module.base.controller;

import com.alibaba.fastjson.JSON;
import com.archer.ssm.module.base.pojo.*;
import com.archer.ssm.module.base.service.SysMenuService;
import com.archer.ssm.utils.common.DateUtils;
import com.archer.ssm.utils.common.UniqId;
import jdk.nashorn.internal.runtime.regexp.joni.constants.EncloseType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 菜单控制器
 *
 * @author Administrator
 * @create 2018-03-23 11:10
 */
@Controller
@RequestMapping("/ssm/sysmenu")
public class SysMenuController extends BaseController{
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 查询菜单集合
     * @param pageSize
     * @param pageIndex
     * @param menuName
     * @param supMenuName
     * @param menuLevel
     * @return
     */
    @RequestMapping(value = "/menulist",method = RequestMethod.POST)
    @ResponseBody
    public BootstrapTableResult<SysMenu> getMenuList(HttpServletRequest request, String pageSize, String pageIndex,String menuName, String supMenuName,String menuLevel){
        BootstrapTableResult<SysMenu> res = new BootstrapTableResult<SysMenu>();
        try {
            // 分页参数验证
            if(StringUtils.isEmpty(pageSize) || StringUtils.isEmpty(pageIndex)){
                res.setTotal(0);
                res.setMsg("分页查询参数为空");
                return res;
            }
            // 构造查询参数
            SysMenu paraEntity = new SysMenu();
            if(!StringUtils.isEmpty(menuName)){
                paraEntity.setMenuName(menuName);
            }
            if(!StringUtils.isEmpty(supMenuName)){
                paraEntity.setSupMenuName(supMenuName);
            }
            if(!StringUtils.isEmpty(menuLevel)){
                paraEntity.setMenuLevel(Integer.parseInt(menuLevel));
            }
            // 查询条数
            int count = sysMenuService.getCount(paraEntity);
            List<SysMenu> list = new ArrayList<SysMenu>();
            if(count > 0){
                list = sysMenuService.getPageList(Integer.parseInt(pageSize),Integer.parseInt(pageIndex),paraEntity);
            }
            res.setRows(list);
            res.setTotal(count);
            res.setCode("000");
            res.setMsg("查询菜单成功");
        } catch (Exception e) {
            log.error("查询菜单异常：",e);
            res.setTotal(0);
            res.setMsg("查询菜单异常");
        }
        return res;
    }

    /**
     * 新增菜单
     * @param paraEntity
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public ResultBody doAdd(HttpServletRequest request, SysMenu paraEntity){
        ResultBody res = new ResultBody();
        try {
            // 参数验证
            String menuName = paraEntity.getMenuName();
            Integer menuLevel = paraEntity.getMenuLevel();
            String supMenuId = paraEntity.getSupMenuId();
            String supMenuName = paraEntity.getSupMenuName();
            Integer menuSeq = paraEntity.getMenuSeq();
            if(StringUtils.isEmpty(menuName) || null == menuLevel){
                res.setCode("001");
                res.setMsg("菜单必填参数为空");
                return res;
            }
            // 父级参数验证
            if(menuLevel > 1 && (StringUtils.isEmpty(supMenuId) || StringUtils.isEmpty(supMenuName))){
                res.setCode("001");
                res.setMsg("父级菜单参数为空");
                return res;
            }else if(menuLevel ==1){
                paraEntity.setSupMenuId("");
                paraEntity.setSupMenuName("");
            }
            // 新增菜单
            if(null == menuSeq){
                paraEntity.setMenuSeq(0);
            }
            paraEntity.setMenuId(UniqId.getInstance().getWorkId().toString());
            paraEntity.setCreateTime(DateUtils.formatDateTime(new Date()));
            sysMenuService.add(paraEntity);
            res.setCode("000");
            res.setMsg("新增菜单成功");
        } catch (Exception e) {
            log.error("新增菜单异常：",e);
            res.setCode("002");
            res.setMsg("新增菜单异常");
        }
        return res;
    }

    /**
     * 获取父级菜单集合
     * @param menuLevel
     * @return
     */
    @RequestMapping(value = "/supMenus",method = RequestMethod.POST)
    @ResponseBody
    public ResultBody getSupMenus(HttpServletRequest request,String menuLevel){
        ResultBody res = new ResultBody();
        try {
            // 参数验证
            if(StringUtils.isEmpty(menuLevel)){
                res.setCode("001");
                res.setMsg("菜单级别参数为空");
                return res;
            }
            // 获取父级菜单集合
            List<SysMenu> list = sysMenuService.getSupMenus(Integer.parseInt(menuLevel)-1);
            res.setMsg("查询父级菜单成功");
            res.setCode("000");
            res.setResult(list);
        } catch (Exception e) {
            log.error("查询父级菜单异常：",e);
            res.setCode("002");
            res.setMsg("查询父级菜单异常");
        }
        return res;
    }

    /**
     * 根据角色ID查询ztree菜单
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/znodes",method = RequestMethod.POST)
    @ResponseBody
    public ResultBody getZNodes(HttpServletRequest request,String roleId){
        ResultBody res = new ResultBody();
        try {
            // 参数验证
            if(StringUtils.isEmpty(roleId)){
                res.setCode("001");
                res.setMsg("角色id为空");
                return res;
            }
            // 查询
            List<ZNode> list = sysMenuService.getZNodes(roleId);
            if(CollectionUtils.isEmpty(list)){
                res.setCode("001");
                res.setMsg("菜单数据为空");
                return res;
            }
            // 获取角色选中菜单id集合
            List<String> selMenuIds = list.stream().filter(node ->node.getChecked()).map(node -> node.getId()).collect(Collectors.toList());
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("znodes",list);
            map.put("selIds",selMenuIds);
            res.setResult(map);
            res.setCode("000");
            res.setMsg("查询ztree菜单成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 更新菜单
     * @param request
     * @param sysMenu
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public ResultBody doUpdate(HttpServletRequest request,SysMenu sysMenu){
        ResultBody res = new ResultBody();
        try {
            // 验证参数
            if(StringUtils.isEmpty(sysMenu.getMenuId())){
                res.setCode("001");
                res.setMsg("菜单ID为空");
                return res;
            }
            // 更新菜单
            sysMenuService.update(sysMenu);
            res.setCode("000");
            res.setMsg("更新菜单成功");
        } catch (Exception e) {
            res.setCode("002");
            res.setMsg("更新菜单异常");
            log.error("更新菜单异常：",e);
        }
        return res;
    }


}
