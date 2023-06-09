package com.lsw.middleware.db.router.annotation;

import java.lang.annotation.*;

/**
 * @description:    路由注解
 * @author：liushiwei
 * @date: 2023/5/18
 * @Copyright：425592454@qq.com
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface DBRouter {
    /**
     * 分库分表字段
     */
    String key() default "";
}
