package test.gl.service;

import java.io.Serializable;
import java.util.List;

public interface ICommonService {
	
	public Object load(Class c, Serializable id);
	
	public List queryList(String hql );

	public void delete(Object obj);
	
}
