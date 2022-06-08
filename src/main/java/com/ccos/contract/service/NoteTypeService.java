package com.ccos.contract.service;

import cn.hutool.core.util.StrUtil;
import com.ccos.contract.dao.NoteTypeDao;
import com.ccos.contract.po.NoteType;
import com.ccos.contract.vo.ResultInfo;

import java.util.List;

public class NoteTypeService {

    private NoteTypeDao typeDao = new NoteTypeDao();
    public List<NoteType> findTypeList(Integer userId){
        List<NoteType> typeList = typeDao.findTypeListByUserId(userId);
        return typeList;
    }

    public ResultInfo<NoteType> deleteType(String typeId) {
        ResultInfo<NoteType> resultInfo = new ResultInfo<>();
        //param null?
        if (StrUtil.isBlank(typeId)){
            resultInfo.setCode(0);
            resultInfo.setMsg("ERROR!try again");
            return resultInfo;
        }

        //call Dao
        long noteCount = typeDao.findNoteCountByTypeId(typeId);

        //contract's number?
        if (noteCount>0){
            resultInfo.setCode(0);
            resultInfo.setMsg("Some contracts in this type!");
            return resultInfo;
        }

        //delete
        int row = typeDao.deleteTypeById(typeId);

        //check row
        if (row>0){
            resultInfo.setCode(1);
        }else {
            resultInfo.setCode(0);
            resultInfo.setMsg("delete fail!");
        }


        return resultInfo;
    }

    public ResultInfo<Integer> addOrUpdate(String typeName, Integer userId, String typeId) {
        ResultInfo<Integer> resultInfo = new ResultInfo<>();

        if (StrUtil.isBlank(typeName)){
            resultInfo.setCode(0);
            resultInfo.setMsg("类型名称不可空");
            return resultInfo;
        }

        //call Dao
        Integer code = typeDao.checkTypeName(typeName,userId,typeId);
        //judge code
        if (code==0){
            resultInfo.setCode(0);
            resultInfo.setMsg("类型名称重复！");
            return resultInfo;
        }

        //judge Id null?
        Integer key = null;
        if(StrUtil.isBlank(typeId)){
            key = typeDao.addType(typeName,userId);
        }else {
            key = typeDao.updateType(typeName,typeId);
        }

        //check successful?
        if (key>0){
            resultInfo.setCode(1);
            resultInfo.setResult(key);
        }else{
            resultInfo.setCode(0);
            resultInfo.setMsg("更新失败!");
        }
        return resultInfo;
    }
}
