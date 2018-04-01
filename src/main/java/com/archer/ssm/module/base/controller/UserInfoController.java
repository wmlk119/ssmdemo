package com.archer.ssm.module.base.controller;

import com.alibaba.fastjson.JSON;
import com.archer.ssm.module.base.pojo.BootstrapTableResult;
import com.archer.ssm.module.base.pojo.ResultBody;
import com.archer.ssm.module.base.pojo.UserInfo;
import com.archer.ssm.module.base.service.UserInfoService;
import com.archer.ssm.utils.common.DateUtils;
import com.archer.ssm.utils.common.PasswordHash;
import com.archer.ssm.utils.common.PasswordUtil;
import com.archer.ssm.utils.common.UniqId;
import com.archer.ssm.utils.dataSource.DataSourceContextHolder;
import com.archer.ssm.utils.dataSource.DataSourceType;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 * @create 2018-03-16 17:25
 */
@Controller
@RequestMapping("/ssm/userinfo")
public class UserInfoController extends BaseController{

    @Autowired
    private UserInfoService userInfoService;

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
            UserInfo userInfo = new UserInfo();
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
            // 登录验证
            UserInfo user = (UserInfo) request.getSession().getAttribute("sysUser");

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
            }
            res.setTotal(count);
            res.setRows(list);
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
    public ResultBody doAdd(UserInfo paraEntity){
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
            paraEntity.setUserId(UniqId.getInstance().get19UniqID());
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





}
