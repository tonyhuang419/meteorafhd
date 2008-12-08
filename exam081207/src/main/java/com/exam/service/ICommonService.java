package com.exam.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.Session;


public  interface ICommonService{

	public  Session getSession() ;

	public  void save(Object paramObject);

	public  void saveOrUpdate(Object paramObject);

	public  void save(Collection<Object> paramCollection);

	@SuppressWarnings("unchecked")
	public  List list(String hql, Object... args);

	@SuppressWarnings("unchecked")
	public  List list(String hql, int paramInt1, int paramInt2, Object... args);

	@SuppressWarnings("unchecked")
	public  Object load(Class paramClass, Serializable paramSerializable);

	public  void delete(Object paramObject);

	public  void delete(Collection<Object> paramCollection);

	public  void update(Object paramObject);

	public  void update(Collection<Object> paramCollection);

	public  Object uniqueResult(String hql, Object... args);

	@SuppressWarnings("unchecked")
	public  List listSQL(String sql, Object... args);

	@SuppressWarnings("unchecked")
	public  List listSQL(String sql, int start, int rowNum, Object... args);
	
	public Number executeStat(String hql, Object[] args);
	
}
