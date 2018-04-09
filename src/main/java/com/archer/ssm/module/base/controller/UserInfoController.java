package com.archer.ssm.module.base.controller;

import com.archer.ssm.module.base.pojo.*;
import com.archer.ssm.module.base.service.LoginLogService;
import com.archer.ssm.module.base.service.SysMenuService;
import com.archer.ssm.module.base.service.SysRoleService;
import com.archer.ssm.module.base.service.UserInfoService;
import com.archer.ssm.utils.common.DateUtils;
import com.archer.ssm.utils.common.PasswordHash;
import com.archer.ssm.utils.common.PasswordUtil;
import com.archer.ssm.utils.common.UniqId;
import com.archer.ssm.utils.dataSource.DataSourceContextHolder;
import com.archer.ssm.utils.dataSource.DataSourceType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @create 2018-03-16 17:25
 */
@Controller
@RequestMapping("/ssm/userinfo")
public class UserInfoController extends BaseController{

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private LoginLogService loginLogService;


    @RequestMapping(value = "/getById",method = RequestMethod.GET)
    @ResponseBody
    public UserInfo showUser(Long userId){
        UserInfo user = userInfoService.getById(userId);
        return user;
    }

    @RequestMapping("/getById/{userId}")
    @ResponseBody
    public UserInfo showUser2(@PathVariable("userId") Long userId){
        UserInfo user = userInfoService.getById(userId);
        return user;
    }

    @RequestMapping("/getDDSInfo")
    @ResponseBody
    public List<UserInfo> getUserInfoOfDDS(){
        List<UserInfo> list = new ArrayList<UserInfo>();
        UserInfo user_ssm = userInfoService.getById(new Long(1));
        DataSourceContextHolder.clearDbType();
        // 切换数据源
        DataSourceContextHolder.setDbType(DataSourceType.DS_SSMB);
        UserInfo user_ssmb = userInfoService.getById(new Long(1));
        DataSourceContextHolder.clearDbType();
        list.add(user_ssm);
        list.add(user_ssmb);
        return list;
    }

    /**
     * 用户登录
     * @param loginName
     * @param loginPwd
     * @param districtCode
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public ResultBody doLogin(HttpServletRequest request, String loginName, String loginPwd, String districtCode){
        ResultBody res = new ResultBody();
        try {
            // 参数验证
            if(StringUtils.isEmpty(loginName) || StringUtils.isEmpty(loginPwd) || StringUtils.isEmpty(districtCode)){
                res.setCode("001");
                res.setMsg("登录信息为空");
                return res;
            }
            // 根据登录名、区域代码查询用户
            UserInfo paraEntity = new UserInfo();
            paraEntity.setLoginName(loginName);
            paraEntity.setDistrictCode(Integer.parseInt(districtCode));
            List<UserInfo> list = userInfoService.getByCondition(paraEntity);
            if(CollectionUtils.isEmpty(list)){
                res.setCode("001");
                res.setMsg("账户不存在");
                return res;
            }
            if(list.size()>1){
                log.warn("此账户[登录名："+loginName+"]-[地区代码："+districtCode+"]有多条记录");
                res.setCode("001");
                res.setMsg("该账户存在异常");
                return res;
            }
            UserInfo entity = list.get(0);
            // 验证密码
            if(!PasswordUtil.verify(loginPwd,entity.getPwdSalt(),entity.getLoginPwd())){
                res.setCode("001");
                res.setMsg("用户名或密码错误");
                return res;
            }
            // 保存登录信息
            // 本机：0:0:0:0:0:0:0:1，127.0.0.1，192.168.*.*
            String ipaddress = getIpAddr(request);
            ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    loginLogService.add(ipaddress,entity);
                }
            });

            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(entity.getUserId());
            userInfo.setUserName(entity.getUserName());
            userInfo.setDistrictCode(entity.getDistrictCode());
            // 保存session
            request.getSession().setAttribute("sysUser",entity);
            res.setResult(userInfo);
            res.setCode("000");
            res.setMsg("登录成功");
        } catch (Exception e) {
            log.error("登录异常[登录名："+loginName+"]-[地区代码："+districtCode+"]：",e);
            res.setCode("002");
            res.setMsg("登录异常");
        }
        return res;
    }


    /**
     * 用户列表查询
     * @param pageSize
     * @param pageIndex
     * @param userName
     * @param loginName
     * @param isEnable
     * @return
     */
    @RequestMapping(value = "/userlist",method = RequestMethod.POST)
    @ResponseBody
    public BootstrapTableResult<UserInfo> getUserList(HttpServletRequest request,String pageSize, String pageIndex, String userName, String loginName, String isEnable){
        BootstrapTableResult<UserInfo> res = new BootstrapTableResult<UserInfo>();
        try {
            if(StringUtils.isEmpty(pageSize) || StringUtils.isEmpty(pageIndex)){
                res.setTotal(0);
                res.setMsg("分页查询参数为空");
                return res;
            }
            // 构造查询参数
            UserInfo paraEntity = new UserInfo();
            if(!StringUtils.isEmpty(userName)){
                paraEntity.setUserName(userName);
            }
            if(!StringUtils.isEmpty(loginName)){
                paraEntity.setLoginName(loginName);
            }
            if(!StringUtils.isEmpty(isEnable)){
                paraEntity.setIsEnable(Integer.parseInt(isEnable));
            }
            // 查询条数
            List<UserInfo> list = new ArrayList<UserInfo>();
            int count = userInfoService.getCount(paraEntity);
            if(count > 0){
                list = userInfoService.getPageList(Integer.parseInt(pageSize),Integer.parseInt(pageIndex),paraEntity);
                list.stream().forEach(userInfo -> {
                    userInfo.setLoginPwd("");
                    userInfo.setPwdSalt("");
                });
            }
            res.setTotal(count);
            res.setRows(list);
            res.setCode("000");
            res.setMsg("查询成功");
        } catch (Exception e) {
            log.error("用户分页查询异常：",e);
            res.setTotal(0);
            res.setMsg("用户分页查询异常");
        }
        return res;
    }

    /**
     * 新增用户
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public ResultBody doAdd(HttpServletRequest request, UserInfo paraEntity){
        ResultBody res = new ResultBody();
        try {
            // 输入参数验证
            String loginName = paraEntity.getLoginName();
            String userName = paraEntity.getUserName();
            Integer sex = paraEntity.getSex();
            String phoneNum = paraEntity.getPhoneNum();
            Integer districtCode = paraEntity.getDistrictCode();
            String roleId = paraEntity.getRoleId();
            String remark = paraEntity.getRemark();
            // 非空验证
            if(StringUtils.isEmpty(loginName) || StringUtils.isEmpty(userName) || StringUtils.isEmpty(phoneNum) || StringUtils.isEmpty(roleId) || null == sex || null == districtCode){
                res.setCode("001");
                res.setMsg("用户参数不能为空");
                return res;
            }
            if(loginName.length() > 32 || userName.length() >32 || remark.length() >255){
                res.setCode("001");
                res.setMsg("用户参数长度超出限制");
                return res;
            }
            // 生成加密密码
            PasswordHash passwordHash = PasswordUtil.encrypt("000000");
            // 保存账号
            paraEntity.setUserId(UniqId.getInstance().getWorkId().toString());
            paraEntity.setLoginPwd(passwordHash.getHexEncoded());
            paraEntity.setPwdSalt(passwordHash.getSalt());
            paraEntity.setCreateTime(DateUtils.formatDateTime(new Date()));
            paraEntity.setIsEnable(1);
            userInfoService.add(paraEntity);
            res.setCode("000");
            res.setMsg("新增用户成功");
        } catch (Exception e) {
            res.setCode("002");
            res.setMsg("新增用户异常");
            log.error("新增用户异常：",e);
        }
        return res;
    }

    /**
     * 更新用户信息
     * @param paraEntity
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public ResultBody doUpdate(UserInfo paraEntity){
        ResultBody res = new ResultBody();

        try {
            // 参数验证
            if(StringUtils.isEmpty(paraEntity.getUserId())){
                res.setCode("001");
                res.setMsg("用户ID为空");
                return res;
            }
            // 用户信息验证
            if(StringUtils.isEmpty(paraEntity.getUserName()) && null==paraEntity.getSex() && StringUtils.isEmpty(paraEntity.getPhoneNum())
                        && StringUtils.isEmpty(paraEntity.getRoleId()) && StringUtils.isEmpty(paraEntity.getRemark())){
                res.setCode("001");
                res.setMsg("无变更用户信息");
                return res;
            }
            // 更新用户信息
            userInfoService.update(paraEntity);
            res.setCode("000");
            res.setMsg("更新用户信息成功");
        } catch (Exception e) {
            log.error("更新用户信息异常：",e);
            res.setCode("002");
            res.setMsg("更新用户信息异常");
        }
        return res;
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public ResultBody doDelete(String userId){
        ResultBody res = new ResultBody();
        try {
            // 参数验证
            if(StringUtils.isEmpty(userId)){
                res.setCode("001");
                res.setMsg("用户ID为空");
                return res;
            }
            // 删除用户
            userInfoService.delete(userId);
            res.setCode("000");
            res.setMsg("删除用户成功");
        } catch (Exception e) {
            log.error("删除用户异常：",e);
            res.setCode("002");
            res.setMsg("删除用户异常");
        }
        return res;
    }


    /**
     * 主页初始化
     * @param request
     * @return
     */
    @RequestMapping(value = "/index",method = RequestMethod.POST)
    @ResponseBody
    public ResultBody indexInit(HttpServletRequest request){
        ResultBody res = new ResultBody();
        try {
            // 登录验证
            UserInfo user = getUserInfo(request);
            if(null == user){
                res.setCode("003");
                res.setMsg("登录超时");
                return res;
            }
            // 获取用户所属角色菜单集合
            List<SysMenu> list = sysMenuService.getListByRoleId(user.getRoleId());
            if(CollectionUtils.isEmpty(list)){
                res.setCode("001");
                res.setMsg("账户暂未分配权限，请联系管理员");
                return res;
            }
            List<SysMenu> toplist = list.stream().filter(menu -> menu.getMenuLevel()==1)
                    .sorted(Comparator.comparing(SysMenu::getMenuSeq)).collect(Collectors.toList());
            List<SysMenu> leftlist = list.stream().filter(menu -> menu.getMenuLevel()>1).collect(Collectors.toList());
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("top",toplist);
            map.put("left",leftlist);
            res.setResult(map);
            res.setCode("000");
            res.setMsg("初始化成功");
        } catch (Exception e) {
            log.error("主页初始化异常:",e);
            res.setCode("002");
            res.setMsg("初始化异常");
        }
        return res;
    }

    /**
     * 退出登录
     * @param request
     * @return
     */
    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    @ResponseBody
    public ResultBody logOut(HttpServletRequest request){
        ResultBody res = new ResultBody();
        try {
            request.getSession().removeAttribute("sysUser");
            res.setCode("000");
            res.setMsg("退出成功");
        } catch (Exception e) {
            res.setCode("002");
            res.setMsg(e.getMessage());
            log.error("退出登录:",e);
        }
        return res;
    }

    /**
     * 个人信息初始化
     * @param userId
     * @return
     */
    @RequestMapping(value = "/initInfo",method = RequestMethod.POST)
    @ResponseBody
    public ResultBody initInfo(String userId){
        ResultBody res = new ResultBody();
        try {
            // 参数验证
            if(StringUtils.isEmpty(userId)){
                res.setCode("001");
                res.setMsg("用户ID为空");
                return res;
            }
            // 根据ID获取账户信息
            UserInfo userInfo = userInfoService.get(userId);
            if(null == userInfo){
                res.setCode("001");
                res.setMsg("用户不存在");
                return res;
            }
            // 获取角色信息
            SysRole role = sysRoleService.get(userInfo.getRoleId());
            // 重构用户信息
            UserInfo entity = new UserInfo();
            entity.setUserId(userId);
            entity.setLoginName(userInfo.getLoginName());
            entity.setUserName(userInfo.getUserName());
            entity.setSex(userInfo.getSex());
            entity.setPhoneNum(userInfo.getPhoneNum());
            entity.setDistrictCode(userInfo.getDistrictCode());
            if(null!=role){
                entity.setBakParam1(role.getRoleName());
            }
            entity.setRemark(userInfo.getRemark());
            res.setResult(entity);
            res.setCode("000");
            res.setMsg("初始化个人信息成功");
        } catch (Exception e) {
            res.setMsg("个人信息初始化异常");
            res.setCode("002");
            log.error("个人信息初始化异常：",e);
        }
        return res;
    }

    /**
     * 个人信息更新
     * @param userInfo
     * @return
     */
    @RequestMapping(value = "/updateinfo",method = RequestMethod.POST)
    @ResponseBody
    public ResultBody doUpdateInfo(UserInfo userInfo){
        ResultBody res = new ResultBody();

        try {
            // 参数验证
            if(StringUtils.isEmpty(userInfo.getUserId())){
                res.setCode("001");
                res.setMsg("用户ID为空");
                return res;
            }
            // 用户信息验证
            if(StringUtils.isEmpty(userInfo.getUserName()) && null==userInfo.getSex() && StringUtils.isEmpty(userInfo.getPhoneNum())
                    && StringUtils.isEmpty(userInfo.getRemark())){
                res.setCode("001");
                res.setMsg("无变更用户信息");
                return res;
            }
            UserInfo entity = new UserInfo();
            entity.setUserId(userInfo.getUserId());
            entity.setUserName(userInfo.getUserName());
            entity.setSex(userInfo.getSex());
            entity.setPhoneNum(userInfo.getPhoneNum());
            entity.setRemark(userInfo.getRemark());
            // 更新用户信息
            userInfoService.update(entity);
            res.setCode("000");
            res.setMsg("个人信息更细成功");
        } catch (Exception e) {
            log.error("个人信息更新异常：",e);
            res.setCode("002");
            res.setMsg("个人信息更新异常");
        }
        return res;
    }

    /**
     * 修改用户密码
     * @param userId
     * @param oldPwd
     * @param newPwd
     * @return
     */
    @RequestMapping(value = "/updatepwd",method = RequestMethod.POST)
    @ResponseBody
    public ResultBody doUpdatePwd(HttpServletRequest request,String userId, String oldPwd, String newPwd){
        ResultBody res = new ResultBody();
        try {
            // 参数验证
            if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(oldPwd) || StringUtils.isEmpty(newPwd)){
                res.setCode("001");
                res.setMsg("参数为空");
                return res;
            }
            // 验证用户老密码
            UserInfo userInfo = userInfoService.get(userId);
            if(null == userInfo){
                res.setCode("001");
                res.setMsg("用户信息为空");
                return res;
            }
            if(!PasswordUtil.verify(oldPwd,userInfo.getPwdSalt(),userInfo.getLoginPwd())){
                res.setCode("001");
                res.setMsg("原密码不正确");
                return res;
            }
            // 更新密码
            PasswordHash passwordHash = PasswordUtil.encrypt(newPwd);
            UserInfo entity = new UserInfo();
            entity.setUserId(userInfo.getUserId());
            entity.setLoginPwd(passwordHash.getHexEncoded());
            entity.setPwdSalt(passwordHash.getSalt());
            userInfoService.update(entity);
            // 清除session
            request.getSession().removeAttribute("sysUser");
            res.setMsg("密码修改成功，请重新登陆");
            res.setCode("000");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }


}
