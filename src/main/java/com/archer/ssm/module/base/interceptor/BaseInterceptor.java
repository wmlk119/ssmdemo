package com.archer.ssm.module.base.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 基础拦截器
 */
public class BaseInterceptor {
    public static final Logger log = LoggerFactory.getLogger(BaseInterceptor.class);

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
