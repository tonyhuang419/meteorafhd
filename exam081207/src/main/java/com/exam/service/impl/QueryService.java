package com.exam.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exam.service.ICommonService;
import com.exam.service.IQueryService;
import com.exam.utils.PageInfo;
import com.exam.utils.SqlUtils;


@Service("queryService")
@Transactional
public class QueryService implements IQueryService {

	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;

	public Long getTotalNum(String hql, Object[] args){
		return this.commonService.executeStat(SqlUtils.getCountSql(hql)  , args).longValue();
	}

	public PageInfo listQueryResult(String hql, PageInfo pi, Object... args){
		if (pi == null){
			pi = new PageInfo();
		}
		pi.setTotal(getTotalNum(hql, args));
		pi.setData(this.commonService.list(hql, pi.getStartOfPage(), pi.getPageSize(), args));
		return pi;
	}

	
	public PageInfo listQueryResultBySql(String sql, PageInfo pi, Object... args){
		if (pi == null) {
			pi = new PageInfo();
		}
		pi.setTotal((Long)commonService.listSQL(SqlUtils.getCountSql(sql), args).get(0));
		pi.setData(commonService.listSQL(sql, pi.getStartOfPage(), pi.getPageSize(), args));
		return pi;
	}
	
}
