package com.baoz.yx.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baoz.yx.dao.IYXCommonDao;
import com.baoz.yx.service.IRealContractBillandRecePlanService;
@Service("recePlanService")
@Transactional
public class RealContractBillandRecePlanService implements
		IRealContractBillandRecePlanService {
	@Autowired
	@Qualifier("yxCommonDao")
	private IYXCommonDao commonDao;
	public List findRealContractBillandRecePlans(String hql) {
		
		return commonDao.list(hql);
	}
	public IYXCommonDao getCommonDao() {
		return commonDao;
	}
	public void setCommonDao(IYXCommonDao commonDao) {
		this.commonDao = commonDao;
	}

}
