/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.37
 * Generated at: 2022-06-02 10:24:08 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class login_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n");
      out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">\n");
      out.write("<head>\n");
      out.write("    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n");
      out.write("    <title>ContractMS</title>\n");
      out.write("    <link href=\"static/css/login.css\" rel=\"stylesheet\" type=\"text/css\" />\n");
      out.write("    <script src=\"static/js/jquery-1.11.3.js\" type=text/javascript></script>\n");
      out.write("    <script src=\"static/js/config.js\" type=text/javascript></script>\n");
      out.write("    <script src=\"static/js/util.js\" type=text/javascript></script>\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("<!--head-->\n");
      out.write("<div id=\"head\">\n");
      out.write("    <div class=\"top\">\n");
      out.write("        <div class=\"fl yahei18\">CityChainofSupply</div>\n");
      out.write("    </div>\n");
      out.write("</div>\n");
      out.write("\n");
      out.write("<!--login box-->\n");
      out.write("<div class=\"wrapper\">\n");
      out.write("    <div id=\"box\">\n");
      out.write("        <div class=\"loginbar\">用户登录</div>\n");
      out.write("        <div id=\"tabcon\">\n");
      out.write("            <div class=\"box show\">\n");
      out.write("                <form action=\"user\" method=\"post\" id=\"loginForm\">\n");
      out.write("                ");
      out.write("\n");
      out.write("                    <input type=\"text\" class=\"user yahei16\" id=\"userName\" name=\"userName\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${resultInfo.result.uname}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("\" /><br /><br />\n");
      out.write("                    <input type=\"password\" class=\"pwd yahei16\" id=\"userPwd\"  name=\"userPwd\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${resultInfo.result.upwd}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("\"/><br /><br />\n");
      out.write("                ");
      out.write("\n");
      out.write("                    <input name=\"rem\" type=\"checkbox\" value=\"1\"  class=\"inputcheckbox\"/> <label>Remember me</label>&nbsp; &nbsp;\n");
      out.write("                ");
      out.write("\n");
      out.write("                    <input type=\"hidden\" name=\"actionName\" value=\"login\">\n");
      out.write("                ");
      out.write("\n");
      out.write("                    <span id=\"msg\" style=\"color:red;font-size:12px\">");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${resultInfo.msg}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("</span><br /><br />\n");
      out.write("                    <input type=\"button\" class=\"log jc yahei16\" value=\"Log in\" onclick=\"checkLogin()\" />&nbsp; &nbsp; &nbsp;\n");
      out.write("                    <input type=\"reset\" value=\"Cancel\" class=\"reg jc yahei18\" />\n");
      out.write("                </form>\n");
      out.write("            </div>\n");
      out.write("        </div>\n");
      out.write("    </div>\n");
      out.write("</div>\n");
      out.write("\n");
      out.write("<div id=\"flash\">\n");
      out.write("    <img src=\"./static/images/banner-pic1.jpg\" style=\"height: 700px;\">\n");
      out.write("</div>\n");
      out.write("\n");
      out.write("<!--bottom-->\n");
      out.write("<div id=\"bottom\">\n");
      out.write("    <div id=\"copyright\">\n");
      out.write('\n');
      out.write('\n');
      out.write('\n');
      out.write('\n');
      out.write('\n');
      out.write('\n');
      out.write('\n');
      out.write('\n');
      out.write("\n");
      out.write("        <div class=\"text\">\n");
      out.write("            Copyright © 2021-2022  CityChainofSupply  All Rights Reserved\n");
      out.write("        </div>\n");
      out.write("    </div>\n");
      out.write("</div>\n");
      out.write("</body>\n");
      out.write("\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
