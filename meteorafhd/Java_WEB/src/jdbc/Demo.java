package jdbc;

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

public class Demo extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=gb2312");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
	//------------------------------MSSQL	
//		Connection con;
//		ResultSet rs;
//		Statement stmt;
//		
//		String dbUrl = "jdbc:microsoft:sqlserver://127.0.0.1:1433;databaseName=Northwind";
//		String user="sa";
//		String pwd="";
//		
//		int cid;
//		String cname;
//		String desc;
//		try
//		{
//			//out.println("connect to sql server......");
//			Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
//			con = DriverManager.getConnection(dbUrl,user,pwd);
//			stmt = con.createStatement();
//			rs = stmt.executeQuery("select categoryid,categoryname,description from categories");
//			
//			out.println("categoryid      categoryname      description");
//			
//			out.println(" <table width='500' border='1'>  ");
//			out.println(" <tr>  ");
//			while(rs.next())
//			{
//				cid = rs.getInt(1);
//				cname = rs.getString("categoryname");
//				desc = rs.getString("description");
//				out.println("<td>" +cid+ "</td>");
//				out.println("<td>" +cname+ "</td>");
//				out.println("<td>" +desc+ "</td>");
//				out.println("</tr>");
//			}
//			
//			rs.close();
//			stmt.close();
//			con.close();
//		}
//		catch(Exception e){}
//		------------------------------ORACLE
//		
//		Connection con;
//		Statement stmt;
//		ResultSet rs;
//		
//		int deptno;
//		String dname;
//		String loc;
//		try{
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//			con =  DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:master","scott","tiger");
//			stmt = con.createStatement();
//			rs = stmt.executeQuery("select * from dept");
//			out.println(" <table width='500' border='1'>  ");
//			while(rs.next()){
//				out.println(" <tr>  ");
//				deptno = rs.getInt(1);
//				dname = rs.getString("dname");
//				loc = rs.getString("loc");
//				out.print("<td>" +deptno+ "</td>");
//				out.print("<td>" +dname+ "</td>");
//				out.print("<td>" +loc+ "</td>");
//				out.println("</tr>");
//			}
//		}catch(Exception e){}
//		
		Connection con;
		Statement stmt;
		ResultSet rs;
		
		int d_id;
		String dname;
		try{
			Class.forName("org.gjt.mm.mysql.Driver");
			con =  DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "fhdone", "lzhouwen");
			stmt = con.createStatement();
			rs = stmt.executeQuery("select * from demo1");
			out.println(" <table width='500' border='1'>  ");
			while(rs.next()){
				out.println(" <tr>  ");
				d_id = rs.getInt("id");
				dname = rs.getString("name");
				out.print("<td>" +d_id+ "</td>");
				out.print("<td>" +dname+ "</td>");
				out.println("</tr>");
			}
			rs.close();
			stmt.close();
			con.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();	
		
		
		
		
	}

}

