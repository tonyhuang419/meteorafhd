package com.exam.service;

import com.exam.utils.PageInfo;

public interface IQueryService {

	public   int getTotalNum(String s, Object aobj[]);

	public   PageInfo listQueryResult(String hql, PageInfo pageinfo, Object aobj[]);

}
