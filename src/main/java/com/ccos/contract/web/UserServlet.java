package com.ccos.contract.web;

import com.ccos.contract.po.User;
import com.ccos.contract.service.UserService;
import com.ccos.contract.vo.ResultInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    private UserService userService = new UserService();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //接受用户行为
        String actionName = request.getParameter("actionName");
        //judge actionName
        if ("login".equals(actionName)){
            userLogin(request,response);
        }
    }

    /*
    1. get userName, password
    2. call method from Service and return ResultInfo
    3. judge isSuccessful(Login)
        if not
            set ResultInfo -> request;
            jump to the Login Page;
        if successful
            set UserInfo to session
            if rem == 1
                password, username, time -> cookie -> client
            else
                clear(cookie)
                jump to ReNavi Page;
     */
    private void userLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. get Args (username, password)
        String userName = request.getParameter("userName");
        String userPwd = request.getParameter("userPwd");

        //2. call method from Service layer and return ResultInfo
        ResultInfo<User> resultInfo = userService.userLogin(userName,userPwd);

        //3. if Login is successful
        if (resultInfo.getCode()==1){
            //successful
//            set UserInfo to session
            request.getSession().setAttribute("user",resultInfo.getResult());
//            if rem == 1
            String rem = request.getParameter("rem");
            if("1".equals(rem)){
                //            password, username, time -> cookie -> client
                //get Cookie
                Cookie cookie = new Cookie("user",userName+"-"+userPwd);
                cookie.setMaxAge(3*24*60*60);
                response.addCookie(cookie);
            }else {
                //            clear(cookie)
                Cookie cookie = new Cookie("user",null);
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
//            jump to ReNavi Page;
            response.sendRedirect("index.jsp");

        }else {
            //fail
            //resinfo->request
            request.setAttribute("resultInfo",resultInfo);
            //jump page
            request.getRequestDispatcher("login.jsp").forward(request,response);
        }
    }
}
