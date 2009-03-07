package htmlUnit.waterKing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Dao {  

	static Connection con = null;  
	static Statement statement = null;  
	static ResultSet rs = null;  

	public static void setConnection () {  
		try {  
			Class.forName("com.mysql.jdbc.Driver");// 加载驱动程序  
			con = DriverManager.getConnection(  
					"jdbc:mysql://localhost/test", "root", "root");  
		} catch (Exception e) {  
			e.printStackTrace();  
			con = null;  
		}  
	} 


	public static  void update(String sql) {  
		try {  
			Statement statement = con.createStatement();
			statement.executeUpdate(sql);  
		} catch (SQLException e) {  
			System.out.println("update error");
			e.printStackTrace();  
		}  
	}  

	public static ResultSet query(String sql) {  
		try{
			Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = null;
			rs = statement.executeQuery(sql); 
			return rs; 
		}catch(SQLException sqle){
			System.out.println("create query statement error");
			sqle.printStackTrace();
		}
		return null;
	}  

	public static void close() {  
		try {  
			if (rs != null) {  
				rs.close();  
				rs = null;  
			}  

			if (statement != null) {  
				statement.close();  
				statement = null;  
			}  

			if (con != null) {  
				con.close();  
				con = null;  
			}  

		} catch (Exception e) {  
			e.printStackTrace();  
		}  
	} 


	public static void main(String args[]) throws Exception{

		Dao.setConnection();
		ResultSet s = Dao.query("select * from peoples ");

		ResultSetMetaData md = s.getMetaData();
		for( int i=1; i < md.getColumnCount();i++ ){
				System.out.println(md.getColumnTypeName(i)+"|"+md.getColumnName(i));
		}
		
		while (s.next()){
			System.out.print(s.getLong(1)+"|");
			System.out.println(s.getString(2));
		}
		Dao.close();

	}

}      
