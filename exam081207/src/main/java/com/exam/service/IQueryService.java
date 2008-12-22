package com.exam.service;

import com.exam.utils.PageInfo;

public interface IQueryService {

	public   int getTotalNum(String s, Object... args);

	public   PageInfo listQueryResult(String hql, PageInfo pageinfo, Object... args);

	public PageInfo listQueryResultBySql(String sql, PageInfo pi, Object... args);

}
