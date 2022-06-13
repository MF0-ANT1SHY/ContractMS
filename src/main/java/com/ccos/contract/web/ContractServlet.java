package com.ccos.contract.web;

import com.ccos.contract.po.Contract;
import com.ccos.contract.po.NoteType;
import com.ccos.contract.po.User;
import com.ccos.contract.service.ContractService;
import com.ccos.contract.service.NoteTypeService;
import com.ccos.contract.vo.ResultInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

@WebServlet("/note")
public class ContractServlet extends HttpServlet {

    ContractService contractService = new ContractService();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //得到用户行为
        String actionName = request.getParameter("actionName");

        //判断用户行为
        if ("view".equals(actionName)){
            //进入发布页面
            ContractView(request,response);

        } else if ("addOrUpdate".equals(actionName)){
            //添加
            addOrUpdate(request,response);
        }else if ("detail".equals(actionName)){
            //添加
            noteDetail(request,response);
        }
    }

    //查询详情
    private void noteDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String noteId = request.getParameter("noteId");
        Contract contract = contractService.findContractById(noteId);

        request.setAttribute("note",contract);
        request.setAttribute("changePage","note/detail.jsp");
        request.getRequestDispatcher("index.jsp").forward(request,response);
    }

    private void addOrUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String typeId = request.getParameter("typeId");
        String title = request.getParameter("title");
        String content = request.getParameter("content");

        //调用service层
        ResultInfo<Contract> resultInfo = contractService.addOrUpdate(typeId,title,content);

        //judge
        if (resultInfo.getCode()==1){
            response.sendRedirect("index");
        } else{
            request.setAttribute("resultInfo",resultInfo);
            request.getRequestDispatcher("note?actionName=view").forward(request,response);
        }
    }

    //进入合约编辑页面
    private void ContractView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        List<NoteType> typeList = new NoteTypeService().findTypeList(user.getUserId());
        request.setAttribute("typeList",typeList);
        request.setAttribute("changePage","note/view.jsp");
        request.getRequestDispatcher("index.jsp").forward(request,response);
    }
}
