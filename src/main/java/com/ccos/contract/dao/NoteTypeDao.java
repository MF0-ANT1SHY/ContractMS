package com.ccos.contract.dao;

import com.ccos.contract.po.NoteType;
import com.ccos.contract.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class NoteTypeDao {
    public List<NoteType> findTypeListByUserId(Integer userId){
        //定义sql语句
        String sql = "select typeId, typeName, userId from tb_note_type where userId = ?";
        //set param list
        List<Object> params = new ArrayList<>();
        params.add(userId);
        //调用BaseDao,返回集合
        List<NoteType> list = BaseDao.queryRows(sql,params,NoteType.class);

        return list;
    }

    public long findNoteCountByTypeId(String typeId) {
        String sql = "select count(1) from tb_note where typeId = ?";
        List<Object> params = new ArrayList<>();
        params.add(typeId);
        //call BaseDao
        long count = (long)BaseDao.findSingleValue(sql,params);
        return count;
    }

    public int deleteTypeById(String typeId) {
        String sql = "delete from tb_note_type where typeId = ?";
        List<Object> params = new ArrayList<>();
        params.add(typeId);

        int row = BaseDao.executeUpdate(sql,params);
        return row;
    }

    //验证id是否唯一，1成功，0失败
    public Integer checkTypeName(String typeName, Integer userId, String typeId) {
        String sql = "select * from tb_note_type where userId = ? and typeName = ?";
        //set params
        List<Object> params = new ArrayList<>();
        params.add(userId);
        params.add(typeName);
        //执行查询操作
        NoteType noteType = (NoteType) BaseDao.queryRow(sql,params,NoteType.class);
        //null?
        if (noteType == null){
            return 1;
        }else {
            //修改？
            if (typeId.equals(noteType.getTypeId().toString())){
                return 1;
            }
        }
        return 0;
    }

    //添加方法，返回主键
    public Integer addType(String typeName, Integer userId) {
        Integer key = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            //得到数据库连接
            connection = DBUtil.getConnection();
            String sql = "insert into tb_note_type (typeName, userId) values (?,?)";
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,typeName);
            preparedStatement.setInt(2,userId);
            int row = preparedStatement.executeUpdate();
            //judge row
            if (row>0){
                //get Key
                resultSet = preparedStatement.getGeneratedKeys();
                //得到主键的值
                if (resultSet.next()){
                    key = resultSet.getInt(1);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUtil.close(resultSet,preparedStatement,connection);
        }

        return key;
    }

    //更新操作
    public Integer updateType(String typeName, String typeId) {
        String sql = "update tb_note_type set typeName = ? where typeId = ?";
        List<Object> params = new ArrayList<>();
        params.add(typeName);
        params.add(typeId);
        //call Dao
        int row = BaseDao.executeUpdate(sql,params);
        return row;
    }
}
