package com.archer.ssm.utils.dataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 自定义数据源
 * @author Administrator
 * @create 2018-03-17 10:11
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDbType();
    }
}
