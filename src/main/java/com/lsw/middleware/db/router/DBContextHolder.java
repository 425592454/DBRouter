package com.lsw.middleware.db.router;

/**
 * @description:    数据源上下文
 * @author：liushiwei
 * @date: 2023/5/18
 * @Copyright：425592454@qq.com
 */
public class DBContextHolder {
    private static final ThreadLocal<String> dbKey = new ThreadLocal<>();

    private static final ThreadLocal<String> tbKey = new ThreadLocal<>();

    public static void setDBKey(String dbKeyIdx){dbKey.set(dbKeyIdx);}

    public static String getDBKey() {return dbKey.get();}

    public static void setTBKey(String tbKeyIdx){tbKey.set(tbKeyIdx);}

    public static String getTBKey() {return tbKey.get();}

    public static void clearDBKey(){
        dbKey.remove();
    }

    public static void clearTBKey(){
        tbKey.remove();
    }
}
