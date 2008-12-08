package com.exam.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exam.service.ICommonService;

@Service("commonService")
@Transactional
public class CommonService implements ICommonService {
	protected SessionFactory sessionFactory;

	public Session getSession(){
		return this.sessionFactory.getCurrentSession();
	}

	public void delete(Object obj){
		if (obj != null)
			getSession().delete(obj);
	}

	@SuppressWarnings("unchecked")
	public void delete(Collection<Object> obj){
		if (obj != null)
			for (Iterator localIterator = obj.iterator(); localIterator.hasNext(); ) { Object o = localIterator.next();
			delete(o);
			}
	}


	@SuppressWarnings("unchecked")
	public List list(String hql, Object[] args){
		Query q = getSession().createQuery(hql);
		for (int i = 0; i < args.length; ++i)
			q.setParameter(i, args[i]);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List list(String oql, int start, int rowNum, Object[] args){
		Query q = getSession().createQuery(oql);
		for (int i = 0; i < args.length; ++i)
			q.setParameter(i, args[i]);

		if (start > 0)
			q.setFirstResult(start);
		if (rowNum > 0)
			q.setMaxResults(rowNum);
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

	@SuppressWarnings("unchecked")
	public void save(Collection<Object> obList){
		if (obList != null)
			for (Iterator localIterator = obList.iterator(); localIterator.hasNext(); ) { Object o = localIterator.next();
			save(o);
			}
	}

	public Object uniqueResult(String hql, Object[] args){
		Query q = getSession().createQuery(hql);
		for (int i = 0; i < args.length; ++i)
			q.setParameter(i, args[i]);
		q.setMaxResults(1);
		return q.uniqueResult();
	}

	public void update(Object o){
		getSession().update(o);
	}

	@SuppressWarnings("unchecked")
	public void update(Collection<Object> c){
		if (c != null)
			for (Iterator localIterator = c.iterator(); localIterator.hasNext(); ) { Object o = localIterator.next();
			save(o);
			}
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


	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Number executeStat(String hql, Object[] args)
	{
		return ((Number)uniqueResult(hql, args));
	}

}
