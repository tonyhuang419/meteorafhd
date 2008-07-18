package com.baoz.yx.dao.impl;

import java.util.Iterator;

import org.hibernate.Query;

import com.baoz.core.dao.impl.CommonDao;
import com.baoz.yx.dao.IYXCommonDao;

/**
 * 类YXCommonDao.java的实现描述：通用dao
 * @author xurong Jun 10, 2008 3:02:06 PM
 */
public class YXCommonDao extends CommonDao implements IYXCommonDao {

	public Iterator iteratorResult(String hql, Object... args) {
		Query q = getSession().createQuery(hql);
		for (int i = 0; i < args.length; i++) {
			q.setParameter(i, args[i]);
		}
		return q.iterate();
	}
	public void flushSession(){
		super.getSession().flush();
	}
}
