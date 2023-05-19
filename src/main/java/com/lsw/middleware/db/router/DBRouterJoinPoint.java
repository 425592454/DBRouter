package com.lsw.middleware.db.router;


import com.lsw.middleware.db.router.annotation.DBRouter;
import com.lsw.middleware.db.router.strategy.IDBRouterStrategy;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * @description:    数据路由切面，通过自定义注解的方式，拦截被切面的方法，进行数据库路由
 * @author：liushiwei
 * @date: 2023/5/18
 * @Copyright：425592454@qq.com
 */
@Aspect
public class DBRouterJoinPoint {
    private Logger logger = LoggerFactory.getLogger(DBRouterJoinPoint.class);

    private DBRouterConfig dbRouterConfig;

    private IDBRouterStrategy dbRouterStrategy;

    public DBRouterJoinPoint(DBRouterConfig dbRouterConfig, IDBRouterStrategy dbRouterStrategy) {
        this.dbRouterConfig = dbRouterConfig;
        this.dbRouterStrategy = dbRouterStrategy;
    }

    @Pointcut("@annotation(com.lsw.middleware.db.router.annotation.DBRouter)")
    public void aopPoint(){
    }

    @Around("aopPoint() && @annotation(dbRouter)")
    public Object doRouter(ProceedingJoinPoint jp, DBRouter dbRouter) throws Throwable{
        String dbKey = dbRouter.key();
        if(StringUtils.isBlank(dbKey) && StringUtils.isBlank((dbRouterConfig.getRouterKey()))){
            throw new RuntimeException("annotation DBRouter key is null!");
        }

        dbKey = StringUtils.isBlank(dbKey) ? dbRouterConfig.getRouterKey() : dbKey;

        // 路由属性
        String dbKeyAttr = getAttrValue(dbKey, jp.getArgs());
        // 路由策略
        dbRouterStrategy.doRouter(dbKeyAttr);
        // 返回结果
        try{
            return jp.proceed();
        }finally{
            dbRouterStrategy.clear();
        }
    }

    public String getAttrValue(String attr, Object[] args){
        if(1 == args.length){
            Object arg = args[0];
            if(arg instanceof String){
                return arg.toString();
            }
        }

        String fieldValue = null;
        for(Object arg : args){
            try{
                if(StringUtils.isNotBlank(fieldValue)){
                    break;
                }
                fieldValue = String.valueOf(this.getValueByName(arg, attr));
            } catch (Exception e){
                logger.error("获取路由属性值失败 attr:{}", attr, e);
            }
        }
        return fieldValue;
    }

    /**
     * 获取对象的特定属性值
     *
     * @author tang
     * @param item 对象
     * @param name 属性名
     * @return 属性值
     */
    private Object getValueByName(Object item, String name){
        try{
            Field field  = getFieldByName(item, name);
            if(field == null){
                return null;
            }
            field.setAccessible(true);
            Object o = field.get(item);
            field.setAccessible(false);
            return o;
        } catch (IllegalAccessException e){
            return null;
        }
    }

    /**
     * 根据名称获取方法，该方法同时兼顾继承类获取父类的属性
     *
     * @author tang
     * @param item 对象
     * @param name 属性名
     * @return 该属性对应方法
     */
    private Field getFieldByName(Object item, String name){
        try{
            Field field;
            try{
                field = item.getClass().getDeclaredField(name);
            }catch(NoSuchFieldException e){
                field = item.getClass().getSuperclass().getDeclaredField(name);
            }
            return field;
        }catch(NoSuchFieldException e){
            return null;
        }
    }
}
