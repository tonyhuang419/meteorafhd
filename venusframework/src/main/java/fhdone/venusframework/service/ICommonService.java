package fhdone.venusframework.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.Session;

import fhdone.venusframework.utils.PageInfo;

public interface ICommonService {

	public Session getSession();

	public Object uniqueResult(String hql, Object... args);

	/**
	 * get 和 load 的区别
	 * http://hi.baidu.com/bailang3106/blog/item/93bc2ac7c89b78d5d0006047.html
	 * @param c
	 * @param id
	 * @return
	 */
	public Object load(Class c, Serializable id);
	
	public Object get(Class c, Serializable id);

	public void save(Object obj);
	
	public void save(Collection<Object> list);
	
	public void processSaveObj(Object obj ,String isActive , String sessionIdStr);

	public void saveOrUpdate(Object obj);
	
	public void saveOrUpdate(Collection<Object> list);

	public void update(Object obj);
	
	public void update(Collection<Object> list);
	
	public void processUpdateObj(Object obj ,String isActive,String sessionIdStr);

	public void delete(Object obj);
	
	public void delete(Collection<Object> list);

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
