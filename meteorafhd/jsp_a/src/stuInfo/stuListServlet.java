package stuInfo;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class stuListServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");	
		String name,no;
		name = (String)request.getParameter("name");
		no = (String)request.getParameter("no");
		
		stu.addStu(name, no);
		
		this.getServletContext().setAttribute("stuNameList", stu.getStuNameList());
		this.getServletContext().setAttribute("stuNoList", stu.getStuNoList());

		response.sendRedirect("../addStu.html");		
	}
}
