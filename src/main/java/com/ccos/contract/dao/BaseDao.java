package com.ccos.contract.dao;

import com.ccos.contract.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

/*
one of basic JDBC class
include:
    update data(add, alter, delete)
    search data:
        1. search data
        2. search array
        3. search object
 */
public class BaseDao {
    /*
    更新操作:
        1、得到数据库连接
        2、定义sql语句
        3、预编译
        4、有参数则设置参数
        5、执行更新，返回受影响的行数
        6、关闭资源
        PS：需要两种参数：sql语句、sql语句的参数集合
     */
    public static int executeUpdate(String sql, List<Object> params) {
        int row = 0; // 受影响的行数
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            //得到数据库链接
            connection = DBUtil.getConnection();
            //定义sql、预编译
            preparedStatement = connection.prepareStatement(sql);
            //判断有无参数
            if (params!=null&&params.size()>0){
                //设置参数
                for (int i=0;i<params.size();i++){
                    preparedStatement.setObject(i+1,params.get(i));
                }
            }
            //执行更新
            row = preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //关闭资源
            DBUtil.close(null,preparedStatement,connection);
        }
        return row;
    }
}
