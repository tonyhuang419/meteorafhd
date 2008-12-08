package com.exam.service.impl;

import java.util.List;

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

	public int getTotalNum(String hql, Object[] args)
	{
		return this.commonService.executeStat(SqlUtils.getCountSql(hql)  , args).intValue();
	}

	public PageInfo listQueryResult(String oql, PageInfo pi, Object[] args) {
		if (pi == null)
			pi = new PageInfo();

		pi.setTotalCount(getTotalNum(oql, args));
		pi.setResult(this.commonService.list(oql, pi.getStartOfPage(), pi.getPageSize(), args));
		return pi;
	}

	public PageInfo listQueryResult(String cql, String hql, PageInfo pi, Object[] args) {
		if (pi == null)
			pi = new PageInfo();

		pi.setTotalCount(this.commonService.executeStat(cql, args).intValue());
		pi.setResult(this.commonService.list(hql, pi.getStartOfPage(), pi.getPageSize(), args));
		return pi;
	}

	@SuppressWarnings("unchecked")
	public List queryResult(String oql, int start, int size, Object[] args) {
		return this.commonService.list(oql, start, size, args);
	}

}
