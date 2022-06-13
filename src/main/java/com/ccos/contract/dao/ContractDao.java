package com.ccos.contract.dao;

import cn.hutool.core.util.StrUtil;
import com.ccos.contract.po.Contract;

import java.util.ArrayList;
import java.util.List;

public class ContractDao {
    public int addOrUpdate(Contract contract) {
        String sql = "";
        List<Object> params = new ArrayList<>();
        params.add(contract.getTypeId());
        params.add(contract.getTitle());
        params.add(contract.getContent());

        if (contract.getNoteId()==null){
            sql = "insert into tb_note (typeId, title, content, pubTime) values (?,?,?,now())";
        }else {
            sql = "update tb_note set typeId = ?, title=?, content=? where noteId = ?";
            params.add(contract.getNoteId());
        }

        //调用BaseDao的更新方法
        int row = BaseDao.executeUpdate(sql,params);
        return row;
    }

    public long findContractCount(Integer userId,String title) {
        // 定义SQL语句
        String sql = "SELECT count(1) FROM tb_note n INNER JOIN tb_note_type t on n.typeId = t.typeId  WHERE userId = ? ";

        // 设置参数
        List<Object> params = new ArrayList<>();
        params.add(userId);

        if (!StrUtil.isBlank(title)){
            sql+=" and title like concat('%',?,'%')";
            params.add(title);
        }

        // 调用BaseDao的查询方法
        long count = (long) BaseDao.findSingleValue(sql, params);

        return count;
    }

    public List<Contract> findContractListByPage(Integer userId, Integer index, Integer pageSize, String title) {
        // 定义SQL语句
        String sql = "SELECT noteId,title,pubTime FROM tb_note n INNER JOIN tb_note_type t on n.typeId = t.typeId WHERE userId = ?";

        // 设置参数
        List<Object> params = new ArrayList<>();
        params.add(userId);

        if (!StrUtil.isBlank(title)){
            sql+=" and title like concat('%',?,'%')";
            params.add(title);
        }

        sql+=" order by pubTime desc limit ?,?";
        params.add(index);
        params.add(pageSize);
        // 调用BaseDao的查询方法
        List<Contract> contractList = BaseDao.queryRows(sql, params, Contract.class);

        return contractList;
    }

    public Contract findContractById(String noteId) {
        String sql = "select noteId, title, content, pubTime,typeName, n.typeId from tb_note n inner join tb_note_type t on n.typeId=t.typeId where noteId = ?";
        List<Object> params = new ArrayList<>();
        params.add(noteId);

        Contract contract = (Contract) BaseDao.queryRow(sql,params,Contract.class);

        return contract;
    }

    public int deleteContractById(String noteId) {
        String sql = "delete from tb_note where noteId=?";
        List<Object> params = new ArrayList<>();
        params.add(noteId);

        int row = BaseDao.executeUpdate(sql,params);
        return row;
    }
}
