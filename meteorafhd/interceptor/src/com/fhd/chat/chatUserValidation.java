package com.fhd.chat;

import java.sql.ResultSet;
import DAO.Dao;

public class chatUserValidation {
	static public boolean validate(String name,String pwd){
		String dbname = null;
		String dbpwd = null;
		Dao d = new Dao("test");
		ResultSet rs = d.query("select * from demo1");

		try{
			while( rs.next() ){
				dbname = rs.getString("id");
				dbpwd = rs.getString("name");

				if( dbname.equals(name) && dbpwd.equals(pwd)){
					return true;
				}
				else{
					continue;
				}
			}
		}catch( Exception e){
			e.printStackTrace();
		}
		return false;
	}
}
