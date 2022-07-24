package com.fuyusakaiori.csms.service;


import com.fuyusakaiori.csms.dto.Message;
import com.fuyusakaiori.csms.entity.LocalAccount;
import com.fuyusakaiori.csms.exception.LocalAccountException;

import java.util.Date;

public interface LocalAccountService
{
    /**
     * <h3>用户登录</h3>
     * @param username 用户名
     * @param password 密码
     * @return 登录的账号
     */
    Message<LocalAccount> loginByUsernameAndPassword(String username, String password);

    /**
     * <h3>通过用户查询相应的账号</h3>
     * @return 账号
     */
    Message<LocalAccount> findLocalAccountByUser(int userId);

    /**
     * <h3>绑定微信</h3>
     * @param account
     * @return
     */
    Message<LocalAccount> registerLocalAccount(LocalAccount account) throws LocalAccountException;

    /**
     * <h3>更新账号密码</h3>
     */
    Message<LocalAccount> updateLocalAccount(int userId, String username, String password, String newPassword, Date updateTime) throws LocalAccountException;
}