package com.exam.service.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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

}
