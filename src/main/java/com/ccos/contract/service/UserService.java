package com.ccos.contract.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.ccos.contract.dao.UserDao;
import com.ccos.contract.po.User;
import com.ccos.contract.vo.ResultInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

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

    //验证昵称的唯一性
    public Integer checkNick(String nick, Integer userId) {
        //判断昵称是否为空
        if (StrUtil.isBlank(nick)){
            return 0;
        }
        //调用Dao层
        User user = userDao.queryUserByNickAndUserID(nick, userId);

        //判断用户对象存在
        if (user!=null){
            return 0;
        }
        return 1;
    }

    public ResultInfo<User> updateUser(HttpServletRequest request) {
        ResultInfo<User> resultInfo = new ResultInfo<>();
        //get para
        String nick = request.getParameter("nick");
        String mood = request.getParameter("mood");

        //check null?
        if (StrUtil.isBlank(nick)){
            resultInfo.setCode(0);
            resultInfo.setMsg("nick cannnot be null!");
            return resultInfo;
        }

        //get info from session
        User user = (User)request.getSession().getAttribute("user");
        //set nick and mood
        user.setNick(nick);
        user.setMood(mood);

        //upload file
        try {
            //1. get Part object,获取文件域
            Part part = request.getPart("img");
            //从头部信息中获取上传的文件名
            String header = part.getHeader("Content-Disposition");
            //获取具体的请求头对应的值
            String str = header.substring(header.lastIndexOf("=")+2);
            //获取上传的文件名
            String fileName = str.substring(0,str.length()-1);
            //判断文件名是否为空
            if (!StrUtil.isBlank(fileName)){
                //如果上传了头像，则更新用户头像
                user.setHead(fileName);
                //获取文件存放路径
                String filePath = request.getServletContext().getRealPath("WEB-INF/upload/");
                //文件上传
                part.write(filePath+"/"+fileName);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //call Dao, return row
        int row = userDao.updateUser(user);
        if (row>0){
            resultInfo.setCode(1);
            //更新session
            request.getSession().setAttribute("user",user);
        }else {
            resultInfo.setCode(0);
            resultInfo.setMsg("更新失败!");
        }
        return resultInfo;

    }

}
