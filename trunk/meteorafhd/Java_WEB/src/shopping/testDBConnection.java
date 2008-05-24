package shopping;

import java.sql.ResultSet;

class DAO extends DBConnection{
	public void query(){
		String sql = "select * from demo1";
		try{
			ResultSet rs = this.executeQuery(sql);
			
			while(rs.next()){
				System.out.println(rs.getInt("id"));
				System.out.println(rs.getString("name"));	
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}


public class testDBConnection {
	public static void main(String[] args){
		new DAO().query();
	}
}
