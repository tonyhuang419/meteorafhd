package com.baoz.yx.service;

import com.baoz.yx.entity.Role;

public interface IRoleService {
	// public void saveOrUpdatRole(String authority_ids,Role role,Long uid);
	public void saveOrUpdatRole(Role role);
	
	public void deleteRoles(String[] roleIds);

	public void saveOrUpdatRoleAuthority(Role role, String[] authorityIds);
}
