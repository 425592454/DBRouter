package com.lsw.middleware.db.router.dynamic;

import com.lsw.middleware.db.router.DBContextHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @description:    动态数据源获取，每当切换数据源，都要从这个里面进行获取
 * @author：liushiwei
 * @date: 2023/5/18
 * @Copyright：425592454@qq.com
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return "db" + DBContextHolder.getDBKey();
    }
}
