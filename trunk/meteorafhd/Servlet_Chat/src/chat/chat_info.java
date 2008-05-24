package chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class chat_info extends HttpServlet {
	String welcomeInfo;
	String name;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");	
		PrintWriter out = response.getWriter();		
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("<HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("<BODY>");
		out.println(welcomeInfo);
		
		Collection.addList(name,request.getParameter("n_info"));
		Iterator<info> it = Collection.readList();
		while(it.hasNext()){
			info temp = it.next();
			out.println(temp.name+":");
			out.println(temp.info+"<br>");
		}
		
		out.println("  </BODY>");
		out.println("</HTML>");

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
			response.sendRedirect("../login.html");
			return;
		}
		else{	
			welcomeInfo = "<label><h1>Welcome!!!" + name + "</h1></label><br>";
		}
		out.println(welcomeInfo);
		out.println("</BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}
	
	private String getName(HttpServletRequest request) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
	//Session
		HttpSession session = request.getSession(true);
		Object username = session.getAttribute("username");	
		return (String)username;
	} 
}
