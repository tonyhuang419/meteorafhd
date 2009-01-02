package com.exam.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;

import com.exam.utils.PageInfo;

public interface ICommonService {

	public Session getSession();

	public Object uniqueResult(String hql, Object... args);

	public Object load(Class c, Serializable id);

	public void save(Object paramObject);

	public void saveOrUpdate(Object paramObject);

	public void update(Object paramObject);

	public void delete(Object paramObject);

	public List listHql(String quertHql, String orderHql, Object... args);

	public List listHql(String hql, int start, int rowNum, Object... args);

	public List listSQL(String querySql, String orderSql, Object... args);

	public List listSQL(String sql, int start, int rowNum, Object... args);

	public PageInfo listQueryResult(String quertHql, String orderHql , PageInfo pageinfo, Object... args);

	public PageInfo listQueryResultBySql(String querySql, String orderSql ,  PageInfo pi, Object... args);

	@Deprecated
	public void execModifyProcedure();

	@Deprecated
	public void execQueryProcedure();

}
