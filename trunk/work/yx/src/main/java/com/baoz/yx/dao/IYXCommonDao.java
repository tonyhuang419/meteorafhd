package com.baoz.yx.dao;

import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

import com.baoz.core.dao.ICommonDao;

/**
 * 类IYXCommonDao.java的实现描述：通用dao
 * @author xurong Jun 10, 2008 3:01:02 PM
 */
@SuppressWarnings("unchecked")
public interface IYXCommonDao extends ICommonDao {
	/**
	 * 通过迭代器访问结果，主要为了利用二级缓存
	 * @param hql
	 * @param args
	 * @return
	 */
	public Iterator iteratorResult(String hql, Object... args);
	/**
	 * flush Session ,执行缓存着的sql
	 */
	public void flushSession();
	
	public Connection getConnection();
		
	public List listSQL(String sql, Object... args);
	
	public List listSQL(String sql, int start, int rowNum, Object... args);
}
