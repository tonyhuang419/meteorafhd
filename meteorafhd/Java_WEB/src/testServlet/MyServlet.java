package testServlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class MyServlet implements Servlet{
//
//	public void destroy() {
//		// TODO 自动生成方法存根
//		
//	}
//
//	public ServletConfig getServletConfig() {
//		// TODO 自动生成方法存根
//		return null;
//	}
//
//	public String getServletInfo() {
//		// TODO 自动生成方法存根
//		return null;
//	}
//
//	public void init(ServletConfig arg0) throws ServletException {
//		// TODO 自动生成方法存根
//		
//	}
//
//	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
//		res.setContentType("text/html;charset=gb2312");
//		PrintWriter out = res.getWriter();
//		out.println("<html>");
//		out.println("<body>");
//		out.println("<h1>hello,servlet</h1>");
//		out.println("</body>");
//		out.println("</html>");
//		out.flush();
//		out.close();
//	}
	public  void destroy()  {
		System.out.println("destroy");
	}
	public  ServletConfig getServletConfig()  {
		System.out.println("getServletConfig");
		return null;
	}
	public java.lang.String getServletInfo()  {
		System.out.println("getServletInfo");
		return null;
	}
	public void init(ServletConfig config){
		System.out.println("init");
	}
	public void service(ServletRequest req, ServletResponse res) 
		throws IOException{
		System.out.println("service_two");
		res.setContentType("text/html;charset=gb2312");
		PrintWriter out = res.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<h1>hello,servlet_xx</h1>");
		out.println("</body>");
		out.println("</html>");
		out.flush();
		out.close();
	}
}
