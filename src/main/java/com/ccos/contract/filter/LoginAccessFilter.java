package com.ccos.contract.filter;

import com.ccos.contract.po.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//划分放行的资源：
//        1. 登录页面（Login.jsp）、首页、注册页面（尽管没有）可以放行
//        2. 静态资源可以放行（static目录下的资源，包括js、css、images）
//        3. 指定的行为（登录接口等，用户无需登录即可执行的操作actionname=login）
//        4. 登录状态（判断session作用于中是否存在user对象；存在则放行，不存在则拦截并跳转到登录界面）
@WebFilter("/*")
public class LoginAccessFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //based on http
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //get path of res
        String path = request.getRequestURI();

        //page
        if(path.contains("/login.jsp")){
            filterChain.doFilter(request,response);
            return;
        }

        //static
        if (path.contains("/static")){
            filterChain.doFilter(request,response);
            return;
        }

        //action
        if (path.contains("/user")){
            //get actionname
            String actionName = request.getParameter("actionName");
            // == login?
            if ("login".equals(actionName)){
                filterChain.doFilter(request,response);
                return;
            }
        }

        //session
        User user = (User)request.getSession().getAttribute("user");
        //if null?
        if (user != null){
            filterChain.doFilter(request,response);
            return;
        }

        //拦截请求，跳转到登陆界面
        response.sendRedirect("login.jsp");

    }

    @Override
    public void destroy() {

    }
}
