package com.ccos.contract.web;

import com.ccos.contract.po.Contract;
import com.ccos.contract.po.NoteType;
import com.ccos.contract.po.User;
import com.ccos.contract.service.NoteTypeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

@WebServlet("/note")
public class ContractServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //得到用户行为
        String actionName = request.getParameter("actionName");

        //判断用户行为
        if ("view".equals(actionName)){
            //进入发布页面
            ContractView(request,response);

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
