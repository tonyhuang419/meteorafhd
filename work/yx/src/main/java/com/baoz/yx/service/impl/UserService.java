package com.baoz.yx.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.views.xslt.StringAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baoz.core.dao.ICommonDao;
import com.baoz.core.exception.ServiceException;
import com.baoz.core.utils.MD5;
import com.baoz.yx.entity.Authority;
import com.baoz.yx.entity.AuthorityEmployee;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.OrganizationTree;
import com.baoz.yx.entity.Role;
import com.baoz.yx.entity.RoleAuthority;
import com.baoz.yx.entity.RoleEmployee;
import com.baoz.yx.entity.YXOEmployeeClient;
import com.baoz.yx.service.IUserService;
import com.baoz.yx.tools.append.helper.StringAppender;
import com.baoz.yx.utils.DepartmentUtils;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.utils.YxConstants;
import com.baoz.yx.vo.Department;
import com.baoz.yx.vo.UserDetail;

/**
 * 
 * @author 陈虎
 * 
 */
@Service("userService")
@Transactional
@SuppressWarnings("unchecked")
public class UserService implements IUserService {
	@Autowired
	@Qualifier("commonDao")
	private ICommonDao commonDao;

	protected Log logger = LogFactory.getLog(this.getClass());

	/**
	 * 验证用户是否存在
	 * 
	 * @return User
	 */
	
	public Employee getUser(String username, String password)
			throws ServiceException {
		Employee user = (Employee) commonDao.uniqueResult(
				"from Employee obj where obj.username=? and is_active = '1'", username);
		if (user != null) {
			if (!user.getPassword().equals(MD5.toMD5(password)))
				return null;
		}
		return user;

	}

	public Employee getUser(String username) {
		Employee user = (Employee) commonDao.uniqueResult(
				"from Employee obj where obj.username=? and is_active = '1'", username);
		return user;
	}

	/**
	 * 获得用户权限
	 * 
	 * @return List<Authority>
	 */
	@SuppressWarnings("unchecked")
	public List<Authority> getAuthoritys(Long uid) throws ServiceException {
		return commonDao
				.list(
						"from Authority obj where obj.id in(select ra.authority.id from RoleAuthority ra where ra.role.id in(select ru.role from RoleUser ru where ru.user.id=?))",
						uid);
	}

	/**
	 * 保存用户和与之关联的客户
	 * 
	 * @return
	 */
	public void saveOrUpdate(Employee user, String password,
			List<YXOEmployeeClient> newExployeeClientList) {
		Employee employee = UserUtils.getUser();
		if (user == null)
			return;
		if (user.getId() != null) {
			Employee e = (Employee) commonDao
					.load(Employee.class, user.getId());
			if (!password.equals(""))
				e.setPassword(MD5.toMD5(password));
			else {
				e.setUsername(user.getUsername());
				e.setUserCode(user.getUserCode());
				e.setName(user.getName());
				e.setCallPhone(user.getCallPhone());
				e.setPhone(user.getPhone());
				e.setEmail(user.getEmail());
				e.setPosition(user.getPosition());
				e.setOtherPhone(user.getOtherPhone());
				e.setIs_active("1");
			}
			e.setById(employee.getId());
			e.setUpdateBy(new Date());
			commonDao.saveOrUpdate(e);
		} else {
			user.setById(employee.getId());
			user.setUpdateBy(new Date());
			user.setIs_active("1");
			commonDao.saveOrUpdate(user);
		}

		// logger.info(user.getPassword());
		if (newExployeeClientList == null || newExployeeClientList.size() < 1)
			return;
		for (YXOEmployeeClient eclient : newExployeeClientList) {
			YXOEmployeeClient ec = (YXOEmployeeClient) commonDao
					.uniqueResult(
							"FROM YXOEmployeeClient AS ec WHERE ec.is_active!=0 and ec.cli.id=? and ec.exp.id=?",
							eclient.getCli().getId(), user.getId());
			if (ec != null)
				continue;
			else {
				eclient.setExp(user);
				eclient.setById(employee.getId());
				eclient.setUpdateBy(new Date());
				eclient.setIs_active("1");
			}
			commonDao.saveOrUpdate(eclient);
		}
	}

	/**
	 * 删除关联客户
	 * 
	 * @return null
	 */
	public void deleteExployeeClient(YXOEmployeeClient deleteExployeeClient) {
		Employee employee = UserUtils.getUser();
		if (deleteExployeeClient == null
				|| deleteExployeeClient.getId() == null)
			return;
		deleteExployeeClient = (YXOEmployeeClient) commonDao.load(
				YXOEmployeeClient.class, deleteExployeeClient.getId());
		if (deleteExployeeClient == null
				|| deleteExployeeClient.getId() == null)
			return;
		logger.info("删除关系:" + deleteExployeeClient.getExp().getName() + "-->"
				+ deleteExployeeClient.getCli().getName());
		deleteExployeeClient.setIs_active("0");
		deleteExployeeClient.setUpdateBy(new Date());
		deleteExployeeClient.setById(employee.getId());
		commonDao.saveOrUpdate(deleteExployeeClient);
	}

	/**
	 * 删除用户和他关联的操作
	 * 
	 * @param commonDao
	 */

	public void deleteUser(Employee user) {
		Employee employee = UserUtils.getUser();
		if (user == null || user.getId() == null)
			return;
		user = (Employee) commonDao.load(Employee.class, user.getId());
		user.setById(employee.getId());
		user.setUpdateBy(new Date());
		user.setIs_active("0");
		logger.info("删除用户(name=" + user.getName() + ")");
		commonDao.update(user);
		List<YXOEmployeeClient> exployeeclientList = commonDao
				.list(
						"FROM YXOEmployeeClient AS ec WHERE ec.is_active!=0 AND ec.exp.id=?",
						user.getId());
		for (YXOEmployeeClient exployeeclient : exployeeclientList) {
			exployeeclient.setById(employee.getId());
			exployeeclient.setUpdateBy(new Date());
			exployeeclient.setIs_active("0");
			commonDao.update(exployeeclient);
		}

	}

	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}

	public void initUserDetail(UserDetail userDetail) {
		
		// 处理该用户的权限
		List<AuthorityEmployee> authorityEmployeeList = commonDao.list("FROM AuthorityEmployee ae where ae.userId=?", userDetail.getUser().getId());
		List<RoleEmployee> roleEmployeeList = commonDao.list("FROM RoleEmployee re where re.userId=?", userDetail.getUser().getId());

		List<Authority> authorityList = new ArrayList<Authority>();
		List<Role> roleList = new ArrayList<Role>();
		
		for(int i=0; i<authorityEmployeeList.size(); i++) {
			
			authorityList.add((Authority)commonDao.load(Authority.class, authorityEmployeeList.get(i).getAuthorityId()));
		}
		
		for(int i=0; i<roleEmployeeList.size(); i++) {
			
			roleList.add((Role)commonDao.load(Role.class, roleEmployeeList.get(i).getRoleId()));
		}

		// 将role里的authority放到authorityList里
		for (int i = 0; i < roleList.size(); i++) {

			List<RoleAuthority> roleAuthorityList = commonDao.list("FROM RoleAuthority ra where ra.roleId=?", roleList.get(i).getId());

			for (int j = 0; j < roleAuthorityList.size(); j++) {

				authorityList.add((Authority) commonDao.load(Authority.class, roleAuthorityList.get(j).getAuthorityId()));
			}
		}

		userDetail.setAuthoritys(authorityList);

		
		// 根据userDetail.user，取得该用户的相应信息
		OrganizationTree position = (OrganizationTree) commonDao.load(OrganizationTree.class, userDetail.getUser().getPosition());
		userDetail.setPosition(position);
		String positionCode = position.getOrganizationCode();
		String positionName = position.getOrganizationName();
		
		Long departmentid = position.getId();
		
		// 处理用户拥有权限的部门
		// 用户的position，以两位长度的表示是所在部门的负责人，取它的下一级子部门（只取一级），以大于两位的人是普通员工，只取他自己所在的部门
		
		// 定义该员工拥有权限的部门，然后先把它自己所在的部门加进去
		List<Department> myDepartment = new ArrayList<Department>();
		if(DepartmentUtils.isDepartmentLeader(positionCode)){
			positionName = "整体";
		}
		myDepartment.add(new Department(departmentid,positionCode, positionName));
		// 如果是部门经理
		if(DepartmentUtils.isDepartmentLeader(positionCode)) {
			
			// 取出子部门
			List<OrganizationTree> subOrganizationTreeList = commonDao.list("from OrganizationTree as ot where ot.organizationCode like ? and ot.is_active = '1'", positionCode + "__");
			
			for (int i = 0; i < subOrganizationTreeList.size(); i++) {

				String myDepartmentCode = subOrganizationTreeList.get(i).getOrganizationCode();
				String myDepartmentName = subOrganizationTreeList.get(i).getOrganizationName();

				myDepartment.add(new Department(subOrganizationTreeList.get(i).getId(),myDepartmentCode, myDepartmentName));
			}
			
		}
		
		userDetail.setDepartments(myDepartment);
	}

	public OrganizationTree getDepartment(Employee user) {
		OrganizationTree postion = (OrganizationTree) commonDao.load(OrganizationTree.class, user.getPosition());
		OrganizationTree department = (OrganizationTree)  commonDao.load(OrganizationTree.class, DepartmentUtils.getDepartmentId(postion.getOrganizationCode()));
		return department;
	}

	
	// 判断该用户是否拥有该权限（3个重载方法）
	public boolean hasAuthority(String action) {
		return hasAuthority(action,null,null);
	}
	
	public boolean hasAuthority(String action, String actionType) {
		return hasAuthority(action,null,actionType);
	}
	
	public boolean hasAuthority(String action, String method, String actionType) {
		if(StringUtils.isBlank(action)){
			return true;
		}
		boolean flag = false;
		StringAppender hql = new StringAppender();
		hql.append("select 1 from Authority a where action ='"+action+"' ");
		hql.appendIfNotEmpty(" and a.type= '"+actionType+"' ", actionType);
		hql.appendIfNotEmpty(" and a.method= '"+method+"' ", method);
		//首先判断该权限有没有定义，如果没有定义，那么就认为所有用户拥有该权限
		List authorityList = commonDao.list(hql.getString());
		
		if(authorityList.size()==0) {
			//没有定义该action
			return true;
		}
		
		// 判断角色，管理员就返回true
		List<RoleEmployee> l = commonDao.list("from RoleEmployee re where re.userId=?", UserUtils.getUser().getId());
		for(int i=0; i<l.size(); i++) {
			
			Role role = (Role) commonDao.load(Role.class, l.get(i).getRoleId());
			if(YxConstants.adminRoleCode.equals(role.getCode())) {
				//管理员
				return true;
			}
		}
		
		
		UserDetail userDetail = UserUtils.getUserDetail();
		List<Authority> authoritys = userDetail.getAuthoritys();
		
		// 判断authoritys里面是否有该action，如果有就表示该用户有该权限
		for (int i = 0; i < authoritys.size(); i++) {
			// action相等
			if (StringUtils.equals(authoritys.get(i).getAction(), action)) {
				// 类型和方法不为空
				if(StringUtils.isNotBlank(actionType) && StringUtils.isNotBlank(method)){
					if(StringUtils.equals(authoritys.get(i).getType(), actionType) && StringUtils.equals(authoritys.get(i).getMethod(), method)){
						flag = true;
						break;
					}
				}else if(StringUtils.isNotBlank(actionType)){
					// 类型不为空，方法为空
					if(StringUtils.equals(authoritys.get(i).getType(), actionType)){
						flag = true;
						break;
					}
				}else{
					// 类型和方法都为空
					flag = true;
					break;
				}
			}
		}
		return flag;
	}
	
}
