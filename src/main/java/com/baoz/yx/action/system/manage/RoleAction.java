package com.baoz.yx.action.system.manage;

import com.baoz.core.service.ICommonService;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.Authority;
import com.baoz.yx.entity.Role;
import com.baoz.yx.service.IRoleService;
import java.util.List;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * 
 * @author shaoyx
 *
 * Jun 23, 2008, 7:28:51 PM
 *
 * todo: 权限操作
 */

@Results( {
		@Result(name = "success", type = ServletRedirectResult.class, value = "/system/manage/roleQuery.action"),
		@Result(name = "enterSave", value = "/WEB-INF/jsp/system/manage/roleForm.jsp"), 
		@Result(name = "distributeAuthority" ,value = "/WEB-INF/jsp/system/manage/distributeAuthority.jsp")})
@ParentPackage("yx")
@SuppressWarnings("unchecked")
public class RoleAction extends DispatchAction {

	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;

	@Autowired
	@Qualifier("roleService")
	private IRoleService roleService;

	private Role role;

//	private String authority_ids; 

//	private List<Authority> list;
//	private Long aid;
//	private List roles;

	private String[] checkboxrole;
	
	private String[] checkboxauthority; // 记录选中的权限的id
	
	List distributeAuthorityList;// 在弹出的权限列表中用来显示权限

	/**
	 * 新增角色
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addRole() throws Exception {

		return ENTER_SAVE;
	}

	/**
	 * 编辑角色
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editRole() throws Exception {

		role = (Role) commonService.load(Role.class, role.getId());

		return ENTER_SAVE;
	}

	/**
	 * 保存权限 返回列表页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String saveRole() throws Exception {

		roleService.saveOrUpdatRole(role);

		return SUCCESS;
	}

	/**
	 * 删除权限
	 */
	public String deleteRole() {

		roleService.deleteRoles(checkboxrole);
		return SUCCESS;
	}

	
	
	
	/**
	 * 获得权限列表
	 */
	public String distributeAuthority() throws Exception {

		distributeAuthorityList = 
			commonService.list(
					"select a, (select count(*) from RoleAuthority ra where ra.authorityId=a.id and ra.roleId=? ) from Authority a order by a.code", role.getId());
		return "distributeAuthority";
	}
	
	/**
	 * 
	 * todo: 保存角色的权限
	 *
	 * return String
	 */
	public String saveRoleAuthority() throws Exception {
		
		roleService.saveOrUpdatRoleAuthority(role, checkboxauthority);
		return SUCCESS;
	}

	/**
	 * 
	 * todo: 控制checkbox前面的空格
	 *
	 * return ICommonService
	 */
	public String printBlank(String code) {
		
		StringBuilder sb = new StringBuilder();
		
		
		for(int i=0; i<(code.length()/2); i++) {
			
			sb.append("&nbsp;&nbsp;&nbsp;&nbsp;");
		}
		
		
		return sb.toString();
	}
	
	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	public IRoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String[] getCheckboxrole() {
		return checkboxrole;
	}

	public void setCheckboxrole(String[] checkboxrole) {
		this.checkboxrole = checkboxrole;
	}

	public String[] getCheckboxauthority() {
		return checkboxauthority;
	}

	public void setCheckboxauthority(String[] checkboxauthority) {
		this.checkboxauthority = checkboxauthority;
	}

	public List getDistributeAuthorityList() {
		return distributeAuthorityList;
	}

	public void setDistributeAuthorityList(List distributeAuthorityList) {
		this.distributeAuthorityList = distributeAuthorityList;
	}
		

	

}
