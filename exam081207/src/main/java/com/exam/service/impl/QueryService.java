package com.exam.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.exam.service.ICommonService;
import com.exam.service.IQueryService;
import com.exam.utils.PageInfo;
import com.exam.utils.SqlUtils;

public class QueryService implements IQueryService {

	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;

	public int getTotalNum(String hql, Object[] args){
		return this.commonService.executeStat(SqlUtils.getCountSql(hql)  , args).intValue();
	}

	public PageInfo listQueryResult(String hql, PageInfo pi, Object[] args) {
		if (pi == null){
			pi = new PageInfo();
		}
		pi.setTotalCount(getTotalNum(hql, args));
		pi.setResult(this.commonService.list(hql, pi.getStartOfPage(), pi.getPageSize(), args));
		return pi;
	}

}
