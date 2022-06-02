package com.ccos.contract.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.ccos.contract.dao.UserDao;
import com.ccos.contract.po.User;
import com.ccos.contract.vo.ResultInfo;

/*
input: userName
output: resultInfo
判断参数是否null
    if null
        set ResultInfo's statecode&tips
        return ResultInfo
    else
        getResultInfobyName()
        if ResultInfo == null
            set ResultInfo's statecode&tips
            return ResultInfo
        else
            if ResultInfo.pwd != form.pwd
                set ResultInfo's statecode&tips
                return ResultInfo
            else
                set ...
                return ...
 */
public class UserService {
    private UserDao userDao = new UserDao();
    public ResultInfo<User> userLogin(String userName, String userPwd) {
        ResultInfo<User> resultInfo = new ResultInfo<>();
        //登录回显：
        User u = new User();
        u.setUname(userName);
        u.setUpwd(userPwd);
        resultInfo.setResult(u);

        //1. judge arg == null
        if (StrUtil.isBlank(userName)||StrUtil.isBlank(userPwd)){
            //set state code == 0
            resultInfo.setCode(0);
            resultInfo.setMsg("username&password can't be null!");
            //return resultInfo
            return resultInfo;
        }
        //2. search by username
        User user = userDao.queryUserByName(userName);

        //3. judge user == null
        if (user==null){
            resultInfo.setCode(0);
            resultInfo.setMsg("this user doesn't exit!");
            //return resultInfo
            return resultInfo;
        }

        //4. check password
        // encrypto pwd from frontend by MD5
        userPwd = DigestUtil.md5Hex(userPwd);
        if (!userPwd.equals(user.getUpwd())){
            resultInfo.setCode(0);
            resultInfo.setMsg("password isn't correct!");
            return resultInfo;
        }

        resultInfo.setCode(1);
        resultInfo.setResult(user);
        return resultInfo;
    }
}
