package com.archer.ssm.module.base.pojo;

import java.io.Serializable;

/**
 * 响应实体
 *
 * @author Administrator
 * @create 2018-03-21 9:48
 */
public class ResultBody implements Serializable {
    // 响应代码 成功=000，失败=001，异常=002
    private String code;
    // 响应信息
    private String msg;
    // 响应结果
    private Object result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
