package com.baoz.yx.vo;

import java.util.ArrayList;
import java.util.List;

import com.baoz.yx.entity.Authority;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.OrganizationTree;

/**
 * 
 * @author shaoyx
 * 
 * Jun 13, 2008, 11:20:25 AM
 * 
 * todo: 该类是登陆用户的详细信息，包括该用户的Employee实例，是否拥有某一权限
 */

public class UserDetail {

	private OrganizationTree position;
	
	private Employee user;

	//该员工的全部权限
	private List<Authority> authoritys;

	//该员工拥有权限的组，组长和普通员工只有本组的权限，部门经理拥有所有组的权限
	private List<Department> departments = new ArrayList<Department>();

	public UserDetail() {
		
	}

	public UserDetail(Employee user) {
		this.user = user;
	}

	public Employee getUser() {
		return user;
	}

	public void setUser(Employee user) {
		this.user = user;
	}

	public List<Authority> getAuthoritys() {
		return authoritys;
	}

	public void setAuthoritys(List<Authority> authoritys) {
		this.authoritys = authoritys;
	}
	/**
	 * list里面放的是Department,我所在部门，和我管理的下一级部门
	 * @return
	 */
	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}
	/**
	 * 职位
	 * return OrganizationTree
	 */
	public OrganizationTree getPosition() {
		return position;
	}

	public void setPosition(OrganizationTree position) {
		this.position = position;
	}


}
