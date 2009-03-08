package htmlUnit.waterKing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Dao {  

	private Connection con = null;  
	private Statement statement = null;  
	private ResultSet rs = null;  

	public void setConnection () {  
		try {  
			Class.forName("com.mysql.jdbc.Driver");// 加载驱动程序  
			con = DriverManager.getConnection(  
					"jdbc:mysql://localhost/test?useUnicode=true&characterEncoding=UTF8", "root", "root");  
		} catch (Exception e) {  
			e.printStackTrace();  
			con = null;  
		}  
	} 


	public   void update(String sql) {  
		try {  
			Statement statement = con.createStatement();
			statement.executeUpdate(sql);  
		} catch (SQLException e) {  
			System.out.println("update error");
			e.printStackTrace();  
		}  
	}  

	public  ResultSet query(String sql) {  
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

	public  void close() {  
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
		Dao dao = new Dao();
		dao.setConnection();
		ResultSet s = dao.query("select * from peoples ");

		ResultSetMetaData md = s.getMetaData();
		for( int i=1; i < md.getColumnCount();i++ ){
			System.out.println(md.getColumnTypeName(i)+"|"+md.getColumnName(i));
		}

		while (s.next()){
			System.out.print(s.getLong(1)+"|");
			System.out.println(s.getString(2));
		}
		dao.close();

	}


	public Connection getCon() {
		return con;
	}


	public void setCon(Connection con) {
		this.con = con;
	}

}      
