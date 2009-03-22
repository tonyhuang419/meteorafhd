package com.baoz.yx.service;

import java.util.List;

import com.baoz.core.utils.PageInfo;

public interface IYXQueryService {

	public PageInfo listQueryResultBySql(String sql, PageInfo pi, Object... args);
	
	public PageInfo listQueryResultBySql(String sql, String sql2, PageInfo pi, Object... args) ;
	
	public List listQueryNoPage(String sql, Object... args);
	
}
