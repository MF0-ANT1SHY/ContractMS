package com.ccos.contract.web;

import com.ccos.contract.po.User;
import com.ccos.contract.service.UserService;
import com.ccos.contract.vo.ResultInfo;
import org.apache.commons.io.FileUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@WebServlet("/user")
@MultipartConfig
public class UserServlet extends HttpServlet {

    private UserService userService = new UserService();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //设置首页导航高亮
        request.setAttribute("menu_page","user");

        //接受用户行为
        String actionName = request.getParameter("actionName");
        //judge actionName
        if ("login".equals(actionName)){
            userLogin(request,response);
        } else if ("logout".equals(actionName)){
            //用户推出
            userLogOut(request,response);
        }else if ("userCenter".equals(actionName)){
            userCenter(request,response);
        } else if("userHead".equals(actionName)){
            userHead(request,response);
        }else if ("checkNick".equals(actionName)){
            checkNick(request,response);
        }else if ("updateUser".equals(actionName)){
            //修改用户信息
            updateUser(request,response);
        }
    }

    // 修改用户信息
    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo<User> resultInfo = userService.updateUser(request);
        //将resultInfo存到request
        request.setAttribute("resultInfo",resultInfo);
        //请求转发跳转到个人页面
        request.getRequestDispatcher("user?actionName=userCenter").forward(request,response);
    }

    //检查昵称唯一性
    private void checkNick(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取参数
        String nick = request.getParameter("nick");
        User user = (User) request.getSession().getAttribute("user");
        Integer code = userService.checkNick(nick,user.getUserId());
        //输出
        response.getWriter().write(code+"");
        //关闭资源
        response.getWriter().close();
    }

    //加载头像
    private void userHead(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //得到参数
        String head = request.getParameter("imageName");
        //得到图片存放路径
        String realPath = request.getServletContext().getRealPath("/WEB-INF/upload/");
        //通过图片完整路径得到file对象
        File file = new File(realPath+"/"+head);
        //得到图片后缀
        String pic = head.substring(head.lastIndexOf(".")+1);
        //设置不同响应类型
        if ("PNG".equalsIgnoreCase(pic)){
            response.setContentType("image/png");
        } else if ("JPG".equalsIgnoreCase(pic)||"JPEG".equalsIgnoreCase(pic)) {
            response.setContentType("image/jpeg");
        } else if ("GIF".equalsIgnoreCase(pic)){
            response.setContentType("image/gif");
        }
        //将图片拷贝给浏览器
        FileUtils.copyFile(file,response.getOutputStream());
    }

    //进入个人中心
    private void userCenter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        1. 设置首页动态包含的页面值
        request.setAttribute("changePage","user/info.jsp");
        //请求转发到index。若用重定向，会导致请求域失效
        request.getRequestDispatcher("index.jsp").forward(request,response);
    }

    //用户退出
    private void userLogOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //销毁session
        request.getSession().invalidate();
        //删除cookie
        Cookie cookie = new Cookie("user", null);
        cookie.setMaxAge(0);//设置为0表示删除
        response.addCookie(cookie);
        response.sendRedirect("login.jsp");
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
            response.sendRedirect("index");

        }else {
            //fail
            //resinfo->request
            request.setAttribute("resultInfo",resultInfo);
            //jump page
            request.getRequestDispatcher("login.jsp").forward(request,response);
        }
    }
}
