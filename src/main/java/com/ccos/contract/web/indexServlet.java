package com.ccos.contract.web;

import com.ccos.contract.po.Contract;
import com.ccos.contract.po.User;
import com.ccos.contract.service.ContractService;
import com.ccos.contract.util.Page;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/index")
public class indexServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("menu_page","index");

        String actionName = request.getParameter("actionName");
        if ("searchTitle".equals(actionName)){
            String title = request.getParameter("title");
            contractList(request,response,title);
        }else{
            contractList(request,response,null);
        }



        request.setAttribute("changePage","note/list.jsp");
        request.getRequestDispatcher("index.jsp").forward(request,response);
    }

    private void contractList(HttpServletRequest request, HttpServletResponse response, String title) {
        // 1. 接收参数 （当前页、每页显示的数量）
        String pageNum = request.getParameter("pageNum");
        String pageSize = request.getParameter("pageSize");

        // 2. 获取Session作用域中的user对象
        User user = (User) request.getSession().getAttribute("user");

        // 3. 调用Service层查询方法，返回Page对象
        Page<Contract> page = new ContractService().findContractListByPage(pageNum, pageSize, user.getUserId(),title);

        // 4. 将page对象设置到request作用域中
        request.setAttribute("page", page);
    }
}
