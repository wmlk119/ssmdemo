package com.archer.ssm.module.base.pojo;

import java.io.Serializable;

/**
 * ztree节点类
 *
 * @author Administrator
 * @create 2018-03-30 14:15
 */
public class ZNode implements Serializable {
    // id
    private String id;
    // 父级id
    private String pId;
    // 节点名称
    private String name;
    // 是否选中
    private Boolean checked;
    // 是否展开
    private Boolean open;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }
}
