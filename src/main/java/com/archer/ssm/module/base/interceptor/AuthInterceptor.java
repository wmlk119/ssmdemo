package com.archer.ssm.module.base.interceptor;

import com.alibaba.fastjson.JSON;
import com.archer.ssm.module.base.pojo.ResultBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 权限验证拦截器
 * @author Administrator
 * @since 2018-4-4 14:38:01
 */
public class AuthInterceptor implements HandlerInterceptor {
    public static final Logger log = LoggerFactory.getLogger(AuthInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        System.out.println("====== 权限验证拦截器 ======");
        System.out.println("请求URI："+httpServletRequest.getRequestURI());
        // 超时验证
        Object obj = httpServletRequest.getSession().getAttribute("sysUser");
        if(null==obj){
            ResultBody res = new ResultBody();
            res.setCode("003");
            res.setMsg("登录超时");
            WriteData(httpServletResponse, JSON.toJSONString(res));
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    /**
     * 返回前台数据
     * @param response
     * @param data
     */
    public void WriteData(HttpServletResponse response, String data) {
        PrintWriter pw = null;
        try {
            response.setContentType("application/json; charset=utf-8");
            pw = response.getWriter();
            pw.print(data);
        } catch (Exception e) {
            log.error("返回前台数据异常：",e);
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            if (pw != null) {
                pw.print("");
            }
        } finally {
            if (null != pw) {
                pw.close();
            }
        }
    }

}
