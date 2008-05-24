package DAO;

 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
  
public class Dao {   
  
    private Connection conn = null;   
  
    Statement statement = null;   
  
    ResultSet rs = null;   
  
    public Dao() {   
        try {   
            Class.forName("com.mysql.jdbc.Driver");// 加载驱动程序   
            conn = DriverManager.getConnection(   
                    "jdbc:mysql://localhost/test", "root", "lzhouwen");   
            statement = conn.createStatement();   
        } catch (Exception e) {   
            e.printStackTrace();   
            conn = null;   
        }   
    }   
  
    public Dao(String dbname) {   
        try {   
            Class.forName("com.mysql.jdbc.Driver");// 加载驱动程序   
            String url = "jdbc:mysql://localhost/" + dbname;   
            conn = DriverManager.getConnection(url, "root", "lzhouwen");   
            statement = conn.createStatement();   
        } catch (Exception e) {   
            e.printStackTrace();   
            conn = null;   
        }   
    }   
  
    public void update(String sql) {   
        try {   
            statement.executeUpdate(sql);   
        } catch (SQLException e) {   
            e.printStackTrace();   
        }   
    }   
  
    public ResultSet query(String sql) {   
        ResultSet rs = null;   
        try {   
            rs = statement.executeQuery(sql);   
        } catch (SQLException e) {   
            e.printStackTrace();   
        }   
        return rs;   
    }   
  
    public void close() {   
        try {   
            if (rs != null) {   
                rs.close();   
                rs = null;   
            }   
  
            if (statement != null) {   
                statement.close();   
                statement = null;   
            }   
  
            if (conn != null) {   
                conn.close();   
                conn = null;   
            }   
  
        } catch (Exception e) {   
            e.printStackTrace();   
        }   
    }   
}      

