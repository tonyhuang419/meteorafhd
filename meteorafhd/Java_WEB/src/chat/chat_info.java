package chat;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class chat_info extends HttpServlet {
	
	String info=""; 
	String name="";
	AboutXml xmlxx = new AboutXml();
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");	
		PrintWriter out = response.getWriter();		
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		info = info + name+":"+request.getParameter("info") + "<br>";
		out.println(info);		
		out.println("  </BODY>");
		out.println("</HTML>");
		fileExist();
		out.flush();
		out.close();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");	
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("<HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("<BODY>");
		
		name = getName(request);
		if(name==null){
			//out.println("username null");
			response.sendRedirect("../chat/login.html");
			return;
		}
		else
			info = "<label><h1>Welcome!!!" + name + "</h1></label><br>";
		out.println(info);
		out.println("</BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}
	
	public String getName(HttpServletRequest request)	throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
	//Session
		HttpSession session = request.getSession(true);
		Object username = session.getAttribute("username");	
	//Session	
		
//		Cookie cookies[] = request.getCookies();
//		if(cookies!=null){
//			for(int i=0;i<cookies.length;i++){
//				Cookie cookie = cookies[i];
//				if(cookie.getName().equals("username"));{
//					name = cookie.getValue();
//				}
//			}
//		}
		return (String)username;
	}
	  public void fileExist( )   
	  {  
		  File dir = new File("c:/info.xml");  
		  if ( dir.exists() ){
			 //System.out.println("The file exist");
			  xmlxx.insertXml(name, info, ".....");
		  }
		  else{
			  //System.out.println("The file is not exist");
			  xmlxx.createXml(name, info, ".....");
		  }
	  }
}
