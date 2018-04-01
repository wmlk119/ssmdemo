package com.archer.ssm.utils.dataSource;

/**
 * 数据源切换类
 * @author Administrator
 * @create 2018-03-17 10:05
 */
public class DataSourceContextHolder {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    public static void setDbType(String dbType) {
        contextHolder.set(dbType);
    }

    public static String getDbType() {
        return ((String) contextHolder.get());
    }

    public static void clearDbType() {
        contextHolder.remove();
    }
}
