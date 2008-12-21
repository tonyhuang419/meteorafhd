package com.exam.service;

import com.exam.utils.PageInfo;

public interface IQueryService {

	public   Long getTotalNum(String s, Object aobj[]);

	public   PageInfo listQueryResult(String hql, PageInfo pageinfo, Object aobj[]);
	
	public PageInfo listQueryResultBySql(String sql, PageInfo pi, Object... args);
	
}
