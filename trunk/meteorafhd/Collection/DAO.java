

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class DAO {
	static String dbUrl = "jdbc:sqlserver://localhost:1433;databaseName=Demo";
	static String username = "sa";
	static String password = "sa";
	static Connection con;

	public static void setConnection() throws Exception {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		con = DriverManager.getConnection(dbUrl, username, password);
	}

//	public static String[][] getResultSet(String sql) throws Exception {
//		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
//			
//		ResultSet rs = stmt.executeQuery(sql);
//		ResultSetMetaData md = rs.getMetaData();
//		rs.last();
//		int i = rs.getRow();
//		int colnum = md.getColumnCount();
//		String s[][] = new String[i][colnum];
//		rs.beforeFirst();
//		int j = 0;
//		while (rs.next()) {
//			for (int x = 0; x < colnum; x++) {
//				s[j][x] = rs.getObject(x + 1).toString();
//			}
//			j++;
//		}
//		rs.close();
//		stmt.close();
//		con.close();
//		return s;
//	}

	
	public static ResultSet getResultSet(String sql) throws Exception {
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
		
		ResultSet _rs = stmt.executeQuery(sql);
		return _rs;
	}
	
	
	public static int getUpdatedRows(String sql) throws Exception {
		Statement stmt = con.createStatement();
		int n = stmt.executeUpdate(sql);
		stmt.close();
		con.close();
		return n;
	}
	

}
