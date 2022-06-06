package com.ccos.contract.web;

import com.ccos.contract.po.NoteType;
import com.ccos.contract.po.User;
import com.ccos.contract.service.NoteTypeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/type")
public class NoteTypeServlet extends HttpServlet {
    private NoteTypeService typeService = new NoteTypeService();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //得到用户行为
        String actionName = request.getParameter("actionName");

        //判断用户行为
        if ("list".equals(actionName)){
            //查询类型列表
            typeList(request,response);
        }
    }

 //查询类型列表
    private void typeList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //get user in session
        User user = (User) request.getSession().getAttribute("user");
        //call Service method findingType
        List<NoteType> typeList = typeService.findTypeList(user.getUserId());
        //set list into request
        request.setAttribute("typeList",typeList);
        //设置首页动态包含的页面值
        request.setAttribute("changePage","type/list.jsp");
        //back to index.jsp
        request.getRequestDispatcher("index.jsp").forward(request,response);
    }
}
