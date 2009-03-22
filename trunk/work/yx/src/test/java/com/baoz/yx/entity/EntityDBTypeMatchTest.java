package com.baoz.yx.entity;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.persistence.Column;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SessionFactory;

import com.baoz.yx.YingXiaoBaseTest;
import com.baoz.yx.entity.contract.ContractItemMaininfo;

/**
 * 类EntityDBTypeMatchTest.java的实现描述：比对实体类和数据库字段的类型
 * @author xurong Jun 16, 2008 2:41:46 PM
 */
public class EntityDBTypeMatchTest extends YingXiaoBaseTest {
	private SessionFactory sessionFactory;
	/**
	 * 比较实体类和数据库字段的类型
	 * @throws Exception
	 */
	public void testCompareTypeMatch() throws Exception{
		printTypeMatchInfo(Authority.class);
	}
	private void printTypeMatchInfo(Class entityClass) throws SQLException {
		/////////////////////
		String tableName = getTableName(entityClass);
		Connection connection = sessionFactory.getCurrentSession().connection();
		Statement statment = connection.createStatement();
		ResultSet result = statment.executeQuery("select * from "+tableName+" where 1<>1");
		ResultSetMetaData resultMetaData = result.getMetaData();
		Field[] fields = entityClass.getDeclaredFields();
		for (Field field : fields) {
			if(isEntityField(field)){
				String columnName = getColumnName(field);
				System.out.println(StringUtils.rightPad("FieldName:"+field.getName(), 30)+
						StringUtils.rightPad("FieldType:"+field.getType(), 50)
						+"ColumnType:"+getColumnTypeName(resultMetaData,columnName));
			}
		}
		/////////////////////
		result.close();
		statment.close();
		connection.close();
	}
	private String getTableName(Class entityClass){
		Table annoTable = (Table)entityClass.getAnnotation(Table.class);
		return annoTable.name();
	}
	private String getColumnName(Field declareField){
		Column col = declareField.getAnnotation(Column.class);
		if(StringUtils.isNotEmpty(col.name())){
			return col.name();
		}else{
			return declareField.getName();
		}
	}
	private String getColumnTypeName(ResultSetMetaData resultMetaData,String columnName) throws SQLException {
		for (int i = 1; i <= resultMetaData.getColumnCount(); i++) {
			String colName = resultMetaData.getColumnName(i);
			if(colName.equalsIgnoreCase(columnName)){
				return resultMetaData.getColumnTypeName(i);
			}
		}
		return null;
	}
	private boolean isEntityField(Field declareField){
		Column col = declareField.getAnnotation(Column.class);
		return col != null;
	}
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
