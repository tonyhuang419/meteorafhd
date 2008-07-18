package com.baoz.yx.service;

public interface IUserAuthorityService {

	public void saveUserRoles(String[] roleIds, Long userId);
	
	public void saveUserAuthoritys(String[] authorityIds, Long userId);
}
