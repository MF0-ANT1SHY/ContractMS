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
}
