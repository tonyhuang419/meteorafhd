package com.baoz.yx.service;

import java.util.List;

import com.baoz.core.exception.ServiceException;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.OrganizationTree;
import com.baoz.yx.entity.YXOEmployeeClient;
import com.baoz.yx.vo.UserDetail;

public interface IUserService {
	
	public Employee getUser(String username, String password) throws ServiceException;
	
	public Employee getUser(String username);
	
	public void saveOrUpdate(Employee user, String password, List<YXOEmployeeClient> newExployeeClientList);

	public void deleteExployeeClient(YXOEmployeeClient deleteExployeeClient);

	public void deleteUser(Employee user);
	
	public void initUserDetail(UserDetail userDetail);
	
	/**
	 * 获得用户所在部门，不是用户的职位，是职位代码的头两位对应的部门
	 * @param user
	 * @return
	 */
	public OrganizationTree getDepartment(Employee user);
	
	/**
	 * 判断该用户是否拥有该权限
	 */ 
	public boolean hasAuthority(String action) ;
	public boolean hasAuthority(String action, String actionType);
	public boolean hasAuthority(String action, String method, String actionType);
}
