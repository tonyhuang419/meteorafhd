package login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		
		Connection con;
		Statement stmt;
		ResultSet rs;
		
		String loc;  //loc---pwd
		String name,pwd;
		name = request.getParameter("t_userName");
		pwd =  request.getParameter("t_pwd");
		
		
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con =  DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:fhdone","scott","***");
			stmt = con.createStatement();
			
			String sqlString = "select loc from dept where dname = '" +name + "'";		
			rs = stmt.executeQuery(sqlString);
			
			if(rs!=null){
				while(rs.next()){
					loc = rs.getString("loc");
					if(pwd.equals(loc)){
						out.println("yes!!!");
					}
				}
			}
			else{
				out.println("wrong");
			}
		}catch(Exception e){
			out.println("wrong");
			e.printStackTrace();	
		}
		
		
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

}
