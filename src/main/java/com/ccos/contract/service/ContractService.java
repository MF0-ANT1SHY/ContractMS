package com.ccos.contract.service;

import cn.hutool.core.util.StrUtil;
import com.ccos.contract.dao.ContractDao;
import com.ccos.contract.po.Contract;
import com.ccos.contract.util.Page;
import com.ccos.contract.vo.ResultInfo;

import java.util.List;

public class ContractService {
    private ContractDao contractDao = new ContractDao();

    public ResultInfo<Contract> addOrUpdate(String typeId, String title, String content) {

        ResultInfo<Contract> resultInfo = new ResultInfo<>();

        if (StrUtil.isBlank(typeId)){
            resultInfo.setCode(0);
            resultInfo.setMsg("请选择合约类型");
            return resultInfo;
        }
        if (StrUtil.isBlank(title)){
            resultInfo.setCode(0);
            resultInfo.setMsg("合约标题不能为空");
            return resultInfo;
        }
        if (StrUtil.isBlank(content)){
            resultInfo.setCode(0);
            resultInfo.setMsg("合约内容不能为空");
            return resultInfo;
        }

        //设置回显
        Contract contract = new Contract();
        contract.setTitle(title);
        contract.setContent(content);
        contract.setTypeId(Integer.parseInt(typeId));
        resultInfo.setResult(contract);

        int row = contractDao.addOrUpdate(contract);

        if (row > 0){
            resultInfo.setCode(1);
        }else {
            resultInfo.setCode(0);
            resultInfo.setResult(contract);
            resultInfo.setMsg("更新失败");
        }

        return resultInfo;
    }

    public Page<Contract> findContractListByPage(String pageNumStr, String pageSizeStr,Integer userId,String title) {
        // 设置分页参数的默认值
        Integer pageNum = 1; // 默认当前页是第一页
        Integer pageSize = 10; // 默认每页显示5条数据
        // 1. 参数的非空校验 （如果参数不为空，则设置参数）
        if (!StrUtil.isBlank(pageNumStr)) {
            // 设置当前页
            pageNum = Integer.parseInt(pageNumStr);
        }
        if (!StrUtil.isBlank(pageSizeStr)) {
            // 设置每页显示的数量
            pageSize = Integer.parseInt(pageSizeStr);
        }

        // 2. 查询当前登录用户的云记数量，返回总记录数 （long类型）
        long count = contractDao.findContractCount(userId,title);

        // 3. 判断总记录数是否大于0
        if (count < 1) {
            return null;
        }

        // 4. 如果总记录数大于0，调用Page类的带参构造，得到其他分页参数的值，返回Page对象
        Page<Contract> page = new Page<>(pageNum, pageSize, count);

        // 得到数据库中分页查询的开始下标
        Integer index = (pageNum-1) * pageSize;

        // 5. 查询当前登录用户下当前页的数据列表，返回note集合
        List<Contract> contractList = contractDao.findContractListByPage(userId, index, pageSize,title);

        // 6. 将note集合设置到page对象中
        page.setDataList(contractList);

        // 7. 返回Page对象
        return page;
    }

    public Contract findContractById(String noteId) {
        if (StrUtil.isBlank(noteId)){
            return null;
        }
        Contract contract = contractDao.findContractById(noteId);
        return contract;
    }
}
