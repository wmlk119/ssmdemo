package com.archer.ssm.module.base.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * BootstrapTable结果实体
 *
 * @author Administrator
 * @create 2018-03-22 10:16
 */
public class BootstrapTableResult<T> implements Serializable{
    private static final long serialVersionUID = 1513544066052655954L;
    // 总数
    private Integer total;
    // 分页查询结果集
    private List<T> rows;
    // 消息
    private String msg;
    // 结果代码 000=成功，001=失败，002=异常，003=登录超时
    private String code;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
