package com.baoz.yx.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baoz.core.utils.PageInfo;
import com.baoz.yx.dao.IYXCommonDao;
import com.baoz.yx.service.IYXQueryService;

@Service("yxQueryService")
@Transactional
public class YXQueryService implements IYXQueryService {
	
	@Autowired
	@Qualifier("yxCommonDao")
	private IYXCommonDao	yxCommonDao;

	
	public PageInfo listQueryResultBySql(String sql, PageInfo pi, Object... args){
		if (pi == null) {
			pi = new PageInfo();
		}
		pi.setTotalCount((Integer)yxCommonDao.listSQL(sql, args).get(0));
		pi.setResult(yxCommonDao.listSQL(sql, pi.getStartOfPage(), pi.getPageSize(), args));
		return pi;
	}
	
	public PageInfo listQueryResultBySql(String sql, String sql2, PageInfo pi, Object... args){
		if (pi == null) {
			pi = new PageInfo();
		}
		pi.setTotalCount(Integer.parseInt(yxCommonDao.listSQL(sql, args).get(0).toString()));
		pi.setResult(yxCommonDao.listSQL(sql2, pi.getStartOfPage(), pi.getPageSize(), args));
		return pi;
	}
	
	public List listQueryNoPage(String sql, Object... args){
		return yxCommonDao.listSQL(sql, args);
	}
}
