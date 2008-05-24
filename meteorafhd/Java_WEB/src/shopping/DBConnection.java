package shopping;
import java.sql.*;
import java.util.*;
import java.io.*;

public class DBConnection {
	private String drivername;
	private String url;
	private String userid;
	private String userpwd;
	private static Connection conn=null;
	private static Statement stmt = null;
	
	public DBConnection(){
		init();
	}
	private void init(){
		try{
			Properties p = new Properties();
			p.load(this.getClass().getResourceAsStream("..\\..\\..\\..\\db.properties"));
			drivername = p.getProperty("drivername","");
			url = p.getProperty("url","");
			userid = p.getProperty("userid","");
			userpwd = p.getProperty("userpwd","");		
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public void openConnection(){
		try{
			Class.forName(drivername);
			conn = DriverManager.getConnection(url,userid,userpwd);
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public ResultSet executeQuery(String sql){
		ResultSet rs = null;
		try{
			if(conn == null)
				this.openConnection();
			rs = stmt.executeQuery(sql);
		}catch(Exception e){
			e.printStackTrace();
		}
		return rs;
	}
	public void executeUpdate(String sql){
		try{
			if(conn == null)
				this.openConnection();
			stmt.executeUpdate(sql);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void close(){
		try{
			stmt.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
