package com.lsw.middleware.db.router;

/**
 * @description:    数据源基础配置
 * @author：liushiwei
 * @date: 2023/5/18
 * @Copyright：425592454@qq.com
 */
public class DBRouterBase {
    private String tbIdx;

    public String getTbIdx(){return DBContextHolder.getTBKey();}
}
