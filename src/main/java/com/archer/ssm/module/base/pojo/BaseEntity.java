package com.archer.ssm.module.base.pojo;

/**
 * 基础实体类
 *
 * @author Administrator
 * @create 2018-03-28 13:45
 */
public class BaseEntity {
    public String createTime;
    public Integer rownumber;
    public String bakParam1;
    public String bakParam2;
    public String bakParam3;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getRownumber() {
        return rownumber;
    }

    public void setRownumber(Integer rownumber) {
        this.rownumber = rownumber;
    }

    public String getBakParam1() {
        return bakParam1;
    }

    public void setBakParam1(String bakParam1) {
        this.bakParam1 = bakParam1;
    }

    public String getBakParam2() {
        return bakParam2;
    }

    public void setBakParam2(String bakParam2) {
        this.bakParam2 = bakParam2;
    }

    public String getBakParam3() {
        return bakParam3;
    }

    public void setBakParam3(String bakParam3) {
        this.bakParam3 = bakParam3;
    }
}
