package com.ccos.contract.dao;

import com.ccos.contract.po.User;
import com.ccos.contract.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/*
通过用户名查找用户对象，返回用户对象
1、获取数据库连接
2、定义sql语句
3、预编译
4、设置参数
5、执行查询，返回结果集
6、判断并分析结果集
7、关闭资源
 */
public class UserDao {
    public User queryUserByName(String userName){
        User user= null;
        //获取数据库连接
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
//获得数据库链接
            connection = DBUtil.getConnection();
            //定义sql语句
            String sql = "select * from tb_user where uname = ?";
            //预编译
            preparedStatement = connection.prepareStatement(sql);
            //设置参数
            preparedStatement.setString(1, userName);
            //查询
            resultSet = preparedStatement.executeQuery();
            //判断结果
            if (resultSet.next()) {
                user = new User();
                user.setUserId(resultSet.getInt("userId"));
                user.setUname(userName);
                user.setHead(resultSet.getString("head"));
                user.setHead(resultSet.getString("mood"));
                user.setHead(resultSet.getString("nick"));
                user.setHead(resultSet.getString("upwd"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //关闭资源
            DBUtil.close(resultSet,preparedStatement,connection);
        }
        return user;
    }
}
