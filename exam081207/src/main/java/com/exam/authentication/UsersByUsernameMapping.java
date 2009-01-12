package com.exam.authentication;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.sql.DataSource;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.userdetails.User;
import org.acegisecurity.userdetails.UserDetails;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;

public class UsersByUsernameMapping extends MappingSqlQuery
{
	protected UsersByUsernameMapping(){	}
	
	protected UsersByUsernameMapping(DataSource dataSource,String sql){
		super(dataSource, sql);
		System.out.println(dataSource);
//		System.out.println(sql);
		declareParameter(new SqlParameter(Types.VARCHAR));
		compile();
	}

	protected Object mapRow(ResultSet rs, int rownum) throws SQLException
	{
		String username = rs.getString(1);
		String password = rs.getString(2);
		boolean active =  rs.getBoolean(3);

		UserDetails user = 
			new User(username, password, active,	new GrantedAuthority[]  {new GrantedAuthorityImpl("HOLDER")});
		return user;
	}


}
