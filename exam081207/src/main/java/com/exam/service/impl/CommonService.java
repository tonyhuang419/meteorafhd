package com.exam.service.impl;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exam.service.ICommonService;

@Service("commonService")
@Transactional
public class CommonService implements ICommonService {

	@Autowired
	@Qualifier("sessionFactory")
	protected SessionFactory sessionFactory;

	public Session getSession(){
		return this.sessionFactory.getCurrentSession();
	}

	public void delete(Object obj){
		if (obj != null){
			getSession().delete(obj);
		}
	}


	@SuppressWarnings("unchecked")
	public List list(String hql, Object... args){
		Query q = getSession().createQuery(hql);
		for (int i = 0; i < args.length; ++i){
			q.setParameter(i, args[i]);
		}
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List list(String hql, int start, int rowNum, Object... args){
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

	@SuppressWarnings("unchecked")
	public Object load(Class c, Serializable id){
		return getSession().load(c, id);
	}

	public void save(Object obj){
		getSession().save(obj);
	}

	public void saveOrUpdate(Object obj){
		getSession().saveOrUpdate(obj);
	}

	public Object uniqueResult(String hql, Object... args){
		Query q = getSession().createQuery(hql);
		for (int i = 0; i < args.length; ++i){
			q.setParameter(i, args[i]);
		}
		q.setMaxResults(1);
		return q.uniqueResult();
	}

	public void update(Object o){
		getSession().update(o);
	}

	@SuppressWarnings("unchecked")
	public List listSQL(String sql, Object... args){
		Query q = getSession().createSQLQuery(sql);
		for (int i = 0; i < args.length; i++) {
			q.setParameter(i, args[i]);
		}
		return q.list();
	}

	@SuppressWarnings("unchecked")
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


	public Number executeStat(String hql, Object[] args){
		return ((Number)uniqueResult(hql, args));
	}


	public void testProcedure() {
//		Transaction tx = this.getSession().beginTransaction();//开始事务
		Connection con = this.getSession().connection(); //从Session 中得到Connection
		String procedure = "{call updateBook(?) }";//存储过程名,?是参数的位置，如果有多个参数就加多个？
		try{
		CallableStatement cstmt = con.prepareCall(procedure);
		cstmt.setString(1, "021111"); //设置参数
		cstmt.executeUpdate();
		}catch( SQLException  se){
			se.printStackTrace();
		}
//		tx.commit();
	}

}
