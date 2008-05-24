package chat;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class loginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>Login</TITLE></HEAD>");
		out.println("  <BODY>");
		String username = request.getParameter("tname_userName");
		if( username.equals("") ){
			response.sendRedirect("../chat/login.html");
			return;
		}
		//-------------Session
		HttpSession session = request.getSession(true);
		session.setAttribute("username", username);
		out.println("username");
		//-------------Session
		
//		//--------------Cookie
//		Cookie cookie = new Cookie("username", username);
//		cookie.setMaxAge(3600);
//		response.addCookie(cookie);
//		//--------------Cookie
		response.sendRedirect("../chat/main.htm");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

}
