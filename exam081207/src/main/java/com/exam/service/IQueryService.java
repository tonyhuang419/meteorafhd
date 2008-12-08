package com.exam.service;

import java.util.List;

import com.exam.utils.PageInfo;

public interface IQueryService {

	@SuppressWarnings("unchecked")
	public   List queryResult(String s, int i, int j, Object aobj[]);

	public   PageInfo listQueryResult(String s, PageInfo pageinfo, Object aobj[]);

	public   PageInfo listQueryResult(String s, String s1, PageInfo pageinfo, Object aobj[]);

	public   int getTotalNum(String s, Object aobj[]);
}
