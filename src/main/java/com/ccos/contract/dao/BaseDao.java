package com.ccos.contract.dao;

import com.ccos.contract.util.DBUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

    /*
    查询字段：
        只会返回一个字段
        1、得到连接
        2、定义sql
        3、预编译
        4、设置参数
        5、执行查询，返回结果
        6、判断并分析结果
        7、关闭资源
     */
    public static Object findSingleValue(String sql,List<Object> params){
        Object object = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            //获取数据库连接
            connection = DBUtil.getConnection();
            //预编译
            preparedStatement = connection.prepareStatement(sql);
            //判断有无参数
            if (params!=null&&params.size()>0){
                //设置参数
                for (int i=0;i<params.size();i++){
                    preparedStatement.setObject(i+1,params.get(i));
                }
            }
            //执行查询，返回结果集
            resultSet = preparedStatement.executeQuery();
            //判断并分析结果集
            if (resultSet.next()){
                object = resultSet.getObject(1);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            //关闭资源
            DBUtil.close(resultSet,preparedStatement,connection);
        }
        return object;
    }

    //查询集合 !!!JavaBean 的字段和数据库中的字段要对应，不能少
    /*
    需要知道字段（列名）、反射找到属性字段、拼接set方法，反射找到set方法，invoke调用
     */
    public static List queryRows(String sql, List<Object> params,Class cls){
        /*
        1、获取连接
        2、定义sql
        3、预编译
        4、设置参数
        5、执行查询
        6、得到结果集
        6、得到结果集的元数据（字段的数量、查询的字段类型）
        7、分析判断结果集
            1、实例化对象
            2、遍历字段数量，得到数据库中的每一个列名
            3、通过反射，使用列名得到对应field对象
            4、拼接set方法，得到字符串
            5、通过反射，将set方法的字符串反射成类中制定set方法
            6、通过invoke调用set方法
            7、将对应的javabean设置到集合中
        8、关闭资源
         */
        List list = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            //获取数据库连接
            connection = DBUtil.getConnection();
            //预编译
            preparedStatement = connection.prepareStatement(sql);
            //判断有无参数
            if (params!=null&&params.size()>0){
                //设置参数
                for (int i=0;i<params.size();i++){
                    preparedStatement.setObject(i+1,params.get(i));
                }
            }
            //执行查询
            resultSet = preparedStatement.executeQuery();

            //得到元数据
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            //得到字段数量
            int fieldNum = resultSetMetaData.getColumnCount();

            //分析结果集
            while(resultSet.next()){
                //实例化对象
                Object object = cls.newInstance();
                //遍历查询的字段数量，得到每一个列名
                for (int i=1;i<=fieldNum;i++){
                    //得到每一个列名
                    String columnName = resultSetMetaData.getColumnLabel(i);
                    //通过反射得到field对象
                    Field field = cls.getDeclaredField(columnName);
                    //拼接set
                    String setMethod = "set" + columnName.substring(0,1).toUpperCase()+columnName.substring(1);
                    //通过反射，将set方法字符串反射成类中对应的set方法
                    Method method = cls.getDeclaredMethod(setMethod,field.getType());
                    //得到需要查询的字段的值
                    Object value = resultSet.getObject(columnName);
                    //通过invoke调用set方法
                    method.invoke(object,value);
                }
                //将javabean设置到集合当中
                list.add(object);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUtil.close(resultSet,preparedStatement,connection);
        }

        return list;
    }

    //查询对象
    public static Object queryRow(String sql, List<Object> params,Class cls){
        List list = queryRows(sql,params,cls);
        Object object = null;

        if(list!=null&&list.size()>0){
            object=list.get(0);
        }

        return object;
    }
}
