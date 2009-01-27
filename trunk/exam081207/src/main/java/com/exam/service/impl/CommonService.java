package com.exam.service.impl;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exam.service.ICommonService;
import com.exam.tools.PadProperty;
import com.exam.utils.PageInfo;
import com.exam.utils.SqlUtils;

@Service("commonService")
@Transactional
public class CommonService implements ICommonService {

	@Autowired
	@Qualifier("sessionFactory")
	protected SessionFactory sessionFactory;

	public Session getSession(){
		return this.sessionFactory.getCurrentSession();
	}

	public Object load(Class c, Serializable id){
		return getSession().load(c, id);
	}

	public Object get(Class c, Serializable id){
		return getSession().get(c, id);
	}

	public void save(Object obj){
		getSession().save(obj);
	}

	public void save(Collection<Object> list){
		for(Object obj:list){
			this.save(obj);
		}
	}
	
	public void processSaveObj(Object obj ,String isActive , String sessionIdStr){
		Date date = new Date();
		Long userid = (Long)ServletActionContext.getRequest().getSession().getAttribute( sessionIdStr );
		PadProperty.padBean(obj , "createdby", date );
		PadProperty.padBean(obj , "updatedby", date );
		PadProperty.padBean(obj , "updated", userid );
		PadProperty.padBean(obj , "updated", userid );
		PadProperty.padBean(obj , "isActive", isActive );
	}

	public void saveOrUpdate(Object obj){
		getSession().saveOrUpdate(obj);
	}

	public void saveOrUpdate(Collection<Object> list){
		for(Object obj:list){
			this.saveOrUpdate(obj);
		}
	}

	public void update(Object obj){
		getSession().update(obj);
	}

	public void update(Collection<Object> list){
		for(Object obj:list){
			this.update(obj);
		}
	}
	
	public void processUpdateObj(Object obj ,String isActive,String sessionIdStr){
		Long userid = (Long)ServletActionContext.getRequest().getSession().getAttribute( sessionIdStr );
		PadProperty.padBean(obj , "updated", userid );
		PadProperty.padBean(obj , "updated", userid );
		PadProperty.padBean(obj , "isActive", isActive );
	}

	public void delete(Object obj){
		getSession().delete(obj);
	}

	public void delete(Collection<Object> list){
		for(Object obj:list){
			this.delete(obj);
		}
	}

	public Object uniqueResult(String hql, Object... args){
		Query q = getSession().createQuery(hql);
		for (int i = 0; i < args.length; ++i){
			q.setParameter(i, args[i]);
		}
		q.setMaxResults(1);
		return q.uniqueResult();
	}

	public List listHql(String queryHql,String orderHql , Object... args){
		queryHql = SqlUtils.combineSQL(queryHql, orderHql);
		Query q = getSession().createQuery(queryHql);
		for (int i = 0; i < args.length; ++i){
			q.setParameter(i, args[i]);
		}
		return q.list();
	}

	public List listHql(String hql, int start, int rowNum, Object... args){
		Query q = getSession().createQuery(hql);
		for (int i = 0; i < args.length; ++i){
			q.setParameter(i, args[i]);
		}
		if (start > 0){
			q.setFirstResult(start);
		}
		if (rowNum > 0){
			q.setMaxResults(rowNum);
		}
		return q.list();
	}


	public List listSQL(String querySql,String orderSql,  Object... args){
		querySql = SqlUtils.combineSQL(querySql, orderSql);
		Query q = getSession().createSQLQuery(querySql);
		for (int i = 0; i < args.length; i++) {
			q.setParameter(i, args[i]);
		}
		return q.list();
	}

	public List listSQL(String sql, int start, int rowNum, Object... args){
		Query q = getSession().createSQLQuery(sql);
		for (int i = 0; i < args.length; i++) {
			q.setParameter(i, args[i]);
		}
		if (start > 0)
			q.setFirstResult(start);
		if (rowNum > 0)
			q.setMaxResults(rowNum);
		return q.list();	
	}



	public PageInfo listQueryResult(String queryHql, String orderHql , PageInfo pi, Object... args){
		queryHql = SqlUtils.combineSQL(queryHql, orderHql);
		if (pi == null){
			pi = new PageInfo();
		}
		pi.setTotal( Integer.valueOf(this.uniqueResult(SqlUtils.getCountSql(queryHql), args).toString()));
		pi.setData(this.listHql(queryHql, pi.getStartOfPage(), pi.getPageSize(), args));
		return pi;
	}


	public PageInfo listQueryResultBySql(String querySql, String orderSql ,  PageInfo pi, Object... args){
		querySql = SqlUtils.combineSQL(querySql, orderSql);
		if (pi == null) {
			pi = new PageInfo();
		}
		pi.setTotal( Integer.valueOf(this.uniqueResult(SqlUtils.getCountSql(querySql), args).toString()));
		pi.setData(this.listSQL(querySql, pi.getStartOfPage(), pi.getPageSize(), args));
		return pi;
	}


	@Deprecated
	public void execModifyProcedure() {
		//		Transaction tx = this.getSession().beginTransaction();//开始事务
		Connection con = this.getSession().connection(); //从Session 中得到Connection

		//				String procedure = "{call updateBook(?) }";//存储过程名,?是参数的位置，如果有多个参数就加多个？
		//				try{
		//					CallableStatement cstmt = con.prepareCall(procedure);
		//					cstmt.setString(1, "aaaaaaa"); //设置参数
		//					cstmt.execute();
		//				}catch( SQLException  se){
		//					se.printStackTrace();
		//				}
		//				tx.commit();

		//下面的代码是测试“executeBatch”
		String procedure = "{call insertBook(?,?) }";//存储过程名,?是参数的位置，如果有多个参数就加多个？
		long l1 = System.currentTimeMillis();
		Integer  x = 10000;
		try{
			CallableStatement cstmt = con.prepareCall(procedure);
			for(int i=0;i<10000;i++){
				x++;
				cstmt.setString(1, x.toString() ); //设置参数
				cstmt.setString(2, RandomUtils.nextInt()+""); //设置参数
				cstmt.addBatch();
			}
			cstmt.executeBatch();
		}catch( SQLException  se){
			se.printStackTrace();
		}
		System.out.println(System.currentTimeMillis()-l1);
	}

	@Deprecated
	public void execQueryProcedure(){
		//				Session s = this.getSession(); 
		//				List lst = s.getNamedQuery("queryBook").list();
		//				System.out.println(lst.size());

		Connection con = this.getSession().connection(); //从Session 中得到Connection
		String procedure = "{call queryBook() }";//存储过程名,?是参数的位置，如果有多个参数就加多个？
		try{
			CallableStatement cstmt = con.prepareCall(procedure);
			ResultSet s = cstmt.executeQuery();
			ResultSetMetaData rd = s.getMetaData();
			System.out.println(rd.getColumnCount());
		}catch( SQLException  se){
			se.printStackTrace();
		}
	}

}
