package com.ccos.contract.dao;

import com.ccos.contract.po.User;
import com.ccos.contract.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
    public User queryUserByName(String userName) {
        // 1. 定义sql语句
        String sql = "select * from tb_user where uname = ?";

        // 2. 设置参数集合
        List<Object> params = new ArrayList<>();
        params.add(userName);

        // 3. 调用BaseDao的查询方法
        User user = (User) BaseDao.queryRow(sql, params, User.class);

        return user;
    }
    public User queryUserByName01(String userName){
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
                user.setMood(resultSet.getString("mood"));
                user.setNick(resultSet.getString("nick"));
                user.setUpwd(resultSet.getString("upwd"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //关闭资源
            DBUtil.close(resultSet,preparedStatement,connection);
        }
        return user;
    }

    //通过昵称和ID查询用户对象
    public User queryUserByNickAndUserID(String nick, Integer userId) {
        //定义SQL语句
        String sql = "select * from tb_user where nick = ? and userId != ?";
        //设置参数集合
        List<Object> params = new ArrayList<>();
        params.add(nick);
        params.add(userId);
        //调用BaseDao查询方法
        User user = (User) BaseDao.queryRow(sql,params,User.class);
        return user;
    }

    //更新Dao
    public int updateUser(User user) {
        String sql ="update tb_user set nick = ?, mood = ?, head = ? Where userId = ?";
        //设置参数集合
        List<Object> params = new ArrayList<>();
        params.add(user.getNick());
        params.add(user.getMood());
        params.add(user.getHead());
        params.add(user.getUserId());
        //调用BaseDao更新方法，返回受影响行数
        int row = BaseDao.executeUpdate(sql,params);
        return row;
    }
}
