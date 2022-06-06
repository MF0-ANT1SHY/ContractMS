package com.ccos.contract.service;

import com.ccos.contract.dao.NoteTypeDao;
import com.ccos.contract.po.NoteType;

import java.util.List;

public class NoteTypeService {

    private NoteTypeDao typeDao = new NoteTypeDao();
    public List<NoteType> findTypeList(Integer userId){
        List<NoteType> typeList = typeDao.findTypeListByUserId(userId);
        return typeList;
    }
}
