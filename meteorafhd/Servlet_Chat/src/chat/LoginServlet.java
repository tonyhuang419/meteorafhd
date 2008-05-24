package chat;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		
		String username = request.getParameter("name");
		if( username.equals("") ){
			response.sendRedirect("../login.html");
			return;
		}
		//-------------Session
		HttpSession session = request.getSession(true);
		session.setAttribute("username", username);
		Collection.addNameList(username);
		response.sendRedirect("../chatroom.html");

	}
}
