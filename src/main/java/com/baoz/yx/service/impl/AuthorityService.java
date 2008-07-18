package com.baoz.yx.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baoz.core.dao.ICommonDao;
import com.baoz.yx.entity.Authority;
import com.baoz.yx.entity.Role;
import com.baoz.yx.entity.RoleAuthority;
import com.baoz.yx.service.IAuthorityService;

@Service("authorityService")
@Transactional
public class AuthorityService implements IAuthorityService {

	@Autowired
	@Qualifier("commonDao")
	private ICommonDao commonDao;

	public void saveOrUpdatAuthority(Authority authority) {

		commonDao.saveOrUpdate(authority);

	}

	public ICommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}

}
