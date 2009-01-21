package com.exam.authentication;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.sql.DataSource;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.userdetails.User;
import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;

public class XextendsJdbcDaoImpl extends JdbcDaoImpl {


	@Override
	protected void initMappingSqlQueries() {
		super.initMappingSqlQueries();
		this.setUsersByUsernameMapping(new UsersByUsernameMapping(this.getDataSource()));
	}
	
	
	public MappingSqlQuery getUsersByUsernameMapping() {
		return usersByUsernameMapping;
	}

	public void setUsersByUsernameMapping(MappingSqlQuery usersByUsernameMapping) {
		this.usersByUsernameMapping = usersByUsernameMapping;
	}
	

	protected class UsersByUsernameMapping extends MappingSqlQuery
	{
		
		protected UsersByUsernameMapping(DataSource dataSource){
			super(dataSource,getUsersByUsernameQuery());
			System.out.println(dataSource);
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rownum) throws SQLException
		{
			String username =  rs.getString("username");
			String password =  rs.getString("password");
			boolean active = false;
			if( ! rs.getString("isActive").equals("0") ){
				active = true;
			}
			UserDetails user = 	new User(username, password, active,	new GrantedAuthority[]  {new GrantedAuthorityImpl("HOLDER")});
			return user;
		}

	}


}
