package com.archer.ssm.module.base.mapper;

import java.util.List;

public interface BaseMapper<T> {
    // 新增
    public int add(T entity);

    // 修改
    public int update(T entity);

    // 删除
    public int delete(String id);

    // 根据ID查询
    public T get(String id);

    // 查询所有集合
    public List<T> getList();

}
