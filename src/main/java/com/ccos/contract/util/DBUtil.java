package com.ccos.contract.util;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DBUtil {

    private static Properties properties = new Properties();
    static {
        try {
            //加载配置文件
            InputStream in = DBUtil.class.getClassLoader().getResourceAsStream("db.properties");
            //通过load方法将输入流的内容加载到properties中
            properties.load(in);
            //通过配置文件的getProperty方法获取驱动名，加载驱动
            Class.forName(properties.getProperty("jdbcName"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    获取数据库链接
     */
    public static Connection getConnection() {
        Connection connection = null;

        try {
            // 得到数据库相关链接的信息
            String dbUrl = properties.getProperty("dbUrl");
            String dbName = properties.getProperty("username");
            String dbPwd = properties.getProperty("password");
            //得到数据库链接
            connection = DriverManager.getConnection(dbUrl, dbName, dbPwd);
        } catch(SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }

    //关闭资源
    public static void close(ResultSet resultSet,
                             PreparedStatement preparedStatement,
                             Connection connection){
        try{
            //判断资源是否存在，如果不null则关闭
            if (resultSet!=null){
                resultSet.close();
            }
            if (preparedStatement!=null){
                preparedStatement.close();
            }
            if (connection!=null){
                connection.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
