package com.baoz.yx.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baoz.core.dao.ICommonDao;
import com.baoz.yx.entity.AuthorityEmployee;
import com.baoz.yx.entity.RoleEmployee;
import com.baoz.yx.service.IUserAuthorityService;

@Service("userAuthorityService")
@Transactional
public class UserAuthorityService implements IUserAuthorityService {

	@Autowired
	@Qualifier("commonDao")
	private ICommonDao commonDao;

	public void saveUserAuthoritys(String[] authorityIds, Long userId) {

		//将原来的用户的权限的全部干掉
		commonDao.executeUpdate("delete from AuthorityEmployee ae where ae.userId=? ", userId);
		
		//设置
		for(int i=0;authorityIds!=null && i<authorityIds.length; i++) {
			
			AuthorityEmployee ae = new AuthorityEmployee();
			ae.setAuthorityId(Long.valueOf(authorityIds[i]));
			ae.setUserId(userId);
			
			commonDao.save(ae);
		}
	}

	public void saveUserRoles(String[] roleIds, Long userId) {

		//将原来的用户的角色的全部干掉
		commonDao.executeUpdate("delete from RoleEmployee re where re.userId=? ", userId);
		
		//设置
		for(int i=0; roleIds!= null && i<roleIds.length; i++) {
			
			RoleEmployee re = new RoleEmployee();
			re.setRoleId(Long.valueOf(roleIds[i]));
			re.setUserId(userId);
			
			commonDao.save(re);
		}
	}

	public ICommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}

}
