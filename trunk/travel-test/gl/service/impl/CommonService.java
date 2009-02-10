package test.gl.service.impl;

import java.io.Serializable;
import java.util.List;

import test.gl.service.ICommonService;

import com.baosight.core.hibernate3.BHibernateDaoSupport;

public class CommonService extends BHibernateDaoSupport implements ICommonService{
	
	public Object load(Class c, Serializable id){
		return getSession().load(c, id);
	}
	
	public List queryList(String hql){
		return queryByHql(hql);
	}
	
	public void delete(Object obj){
		getSession().delete(obj);
	}
	
}
