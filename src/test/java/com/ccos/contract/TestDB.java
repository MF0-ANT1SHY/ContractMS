package com.ccos.contract;

import com.ccos.contract.util.DBUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestDB {
    //使用日志工厂类
    private Logger logger = LoggerFactory.getLogger(TestDB.class);
    //单元测试方法
    /*
    1、方法返回值最好是void
    2、参数列表最好是空参
    3、一般没有参数
    4、每一个方法上面都需要加上@Test注解
    5、每一个方法都可以单独运行
     */
    @Test
    public void testDB(){
        System.out.println(DBUtil.getConnection());
        //使用日志
        logger.info("获取数据库链接: {}",DBUtil.getConnection());
    }
}
