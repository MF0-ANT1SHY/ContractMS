package com.ccos.contract;

import com.ccos.contract.dao.UserDao;
import com.ccos.contract.po.User;
import org.junit.Test;

public class TestUser {
    @Test
    public void testQueryUserByName(){
        UserDao userDao = new UserDao();
        User user = userDao.queryUserByName("admin");
        System.out.println(user.getUpwd());

    }
}
