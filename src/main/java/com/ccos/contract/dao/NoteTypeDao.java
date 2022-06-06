package com.ccos.contract.dao;

import com.ccos.contract.po.NoteType;

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
}
