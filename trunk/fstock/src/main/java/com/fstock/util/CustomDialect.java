package com.fstock.util;

import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.dialect.MySQL5Dialect;

public class CustomDialect extends MySQL5Dialect {  
	public CustomDialect() {  
		super();  
		registerHibernateType(Types.LONGVARCHAR, Hibernate.STRING.getName());  
	}  
}
