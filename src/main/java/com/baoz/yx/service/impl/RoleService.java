package com.baoz.yx.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baoz.core.dao.ICommonDao;
import com.baoz.yx.entity.Authority;
import com.baoz.yx.entity.Role;
import com.baoz.yx.entity.RoleAuthority;
import com.baoz.yx.service.IRoleService;

@Service("roleService")
@Transactional
public class RoleService implements IRoleService {

	@Autowired
	@Qualifier("commonDao")
	private ICommonDao commonDao;

	// public void saveOrUpdatRole(String authority_ids, Role role, Long uid) {
	// if (role.getId() != null) {
	//			
	// commonDao.executeUpdate("delete from RoleAuthority ru where
	// ru.role.id=?", role.getId());
	// } else {
	//			
	// }
	// commonDao.saveOrUpdate(role);
	//
	// if (StringUtils.isNotEmpty(authority_ids)) {
	// authority_ids = authority_ids.substring(0, authority_ids.length() - 1);
	// for (String id : authority_ids.split(",")) {
	// RoleAuthority ra = new RoleAuthority();
	// ra.setRoleId(role.getId());
	// ra.setAuthorityId(Long.valueOf(id));
	// commonDao.save(ra);
	// }
	// }
	// }

	public void saveOrUpdatRole(Role role) {

		commonDao.saveOrUpdate(role);
	}

	public void deleteRoles(String[] roleIds) {

		Long id = null;
		for (int i = 0; i < roleIds.length; i++) {

			id = Long.valueOf(roleIds[i]);
			commonDao.delete(commonDao.load(Role.class, id));
		}
	}

	public void saveOrUpdatRoleAuthority(Role role, String[] authorityIds) {

		// 先将RoleAuthority中该role的权限干光，然后重新设置
		commonDao.executeUpdate(
				"delete from RoleAuthority ra where ra.roleId=?", role.getId());

		// 添加
		for (int i = 0; authorityIds != null && i < authorityIds.length; i++) {

			RoleAuthority ra = new RoleAuthority();
			ra.setRoleId(role.getId());
			ra.setAuthorityId(Long.valueOf(authorityIds[i]));
			
			commonDao.save(ra);
		}

	}

	public ICommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}

}
