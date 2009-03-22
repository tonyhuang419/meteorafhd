package com.baoz.yx.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baoz.core.dao.ICommonDao;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.service.IYXClientCodeService;
@Service("yXClietCodeService")
@Transactional
public class YXClientCodeService implements IYXClientCodeService {
	@Autowired
	@Qualifier("commonDao")
	private ICommonDao commonDao;
	public YXClientCode getYXClientCode(Long idNo) {
		YXClientCode client=(YXClientCode) commonDao.load(YXClientCode.class, idNo);
		return client;
	}
	public ICommonDao getCommonDao() {
		return commonDao;
	}
	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}

}
