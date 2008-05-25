package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Test1 {

	public static void main(String[] args) throws ClassNotFoundException,SQLException{
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

		//String connectionUrl="jdbc:sqlserver://localhost:1433;"+"databaseName=Demo;user=sa;password=sa;";
		
		Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=Demo","sa","sa");
		
		//Connection con = DriverManager.getConnection(connectionUrl);
		
		
//		Statement stam  = con.createStatement();
//		ResultSet rs = stam.executeQuery("select * from love");
//		while(rs.next()){
//			System.out.println(rs.getString("people"));
//			System.out.println(rs.getString("book"));
//			System.out.println(rs.getString("city"));
//			System.out.println(rs.getString("thing"));
//		}
		
		//PreparedStatement pstmt = con.prepareStatement("select * from love");
		
		PreparedStatement pstmt = con.prepareStatement(
				"update love set people=? where book=?");
		pstmt.setString(1, "aaaaa");
		pstmt.setInt(2, 1);
		int x = pstmt.executeUpdate();
		//ResultSetMetaData md = rs.getMetaData();
//		for(int i=1;i<=md.getColumnCount();i++){
//			System.out.println(md.getColumnName(i));
		//}
//		while(rs.next()){
//			System.out.println(rs.getString("people"));
//			System.out.println(rs.getString("book"));
//			System.out.println(rs.getString("city"));
//			System.out.println(rs.getString("thing"));
//		}
		System.out.println(x);
		
		//rs.close();
		pstmt.close();
		con.close();
	}
}
