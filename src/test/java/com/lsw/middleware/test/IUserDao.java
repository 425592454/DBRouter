package com.lsw.middleware.test;

import com.lsw.middleware.db.router.annotation.DBRouter;

/**
 * @description:
 * @author：liushiwei
 * @date: 2023/5/19
 * @Copyright：425592454@qq.com
 */
public interface IUserDao {

    @DBRouter(key = "userId")
    void insertUser(String req);

}
