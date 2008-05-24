package login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ValidationServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		response.setContentType("text/xml");
		String message = "1111111111";
		out.println("<response>");
		out.println("<message>"+message+"</message>");
		out.println("</response>");	
		out.flush();
		out.close();
		
		response.setContentType("text/xml");
		String message2 = "2222222222";
		out.println("<response>");
		out.println("<message>"+message2+"</message>");
		out.println("</response>");	
		out.flush();
		out.close();
	}


}
