package com.baoz.yx.action.system.manage;

import java.util.List;

import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.service.IUserAuthorityService;

/**
 * 
 * @author shaoyx
 *
 * Jun 23, 2008, 7:28:51 PM
 *
 * todo: 权限操作
 */

@Results( {
		@Result(name = "success", type = ServletRedirectResult.class, value = "/system/manage/userAuthorityQuery.action"),
		@Result(name = "distributeRole", value = "/WEB-INF/jsp/system/manage/userRoleDistribute.jsp"), 
		@Result(name = "distributeAuthority" ,value = "/WEB-INF/jsp/system/manage/userAuthorityDistribute.jsp")})
@ParentPackage("yx")
@SuppressWarnings("unchecked")
public class UserAuthorityAction extends DispatchAction {

	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;

	@Autowired
	@Qualifier("userAuthorityService")
	private IUserAuthorityService userAuthorityService;
	
	//前台List页面传过来的user的id
	Long userId = null;
	
	// action生成的authority和role的list，用来显示两个distribute页面
	List authorityList = null;
	List roleList = null;
	
	// 从前台接收到的选中的roles和authoritys的id
	String[] checkedrole = null;
	String[] checkedauthority = null;
	
	/**
	 * 编辑角色
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editRole() throws Exception {

		roleList = commonService.
			list("select r, (select count(*) from RoleEmployee re where re.roleId=r.id and re.userId=? ) from Role r ", userId);

		return "distributeRole";
	}

	
	/**
	 * 获得权限列表
	 */
	public String editAuthority() throws Exception {

		authorityList = commonService.
			list("select a, (select count(*) from AuthorityEmployee ae where ae.authorityId=a.id and ae.userId=? ) from Authority a order by a.code", userId);
		return "distributeAuthority";
	}
	
	
	
	/**
	 * 保存权限
	 * 
	 * @return
	 * @throws Exception
	 */
	public String saveRole() throws Exception {

		userAuthorityService.saveUserRoles(checkedrole, userId);

		return SUCCESS;
	}
	
	
	
	/**
	 * 
	 * todo: 保存权限
	 *
	 * return String
	 */
	public String saveAuthority() throws Exception {
		
		userAuthorityService.saveUserAuthoritys(checkedauthority, userId);
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


	public IUserAuthorityService getUserAuthorityService() {
		return userAuthorityService;
	}


	public void setUserAuthorityService(IUserAuthorityService userAuthorityService) {
		this.userAuthorityService = userAuthorityService;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public List getAuthorityList() {
		return authorityList;
	}


	public void setAuthorityList(List authorityList) {
		this.authorityList = authorityList;
	}


	public List getRoleList() {
		return roleList;
	}


	public void setRoleList(List roleList) {
		this.roleList = roleList;
	}


	public String[] getCheckedrole() {
		return checkedrole;
	}


	public void setCheckedrole(String[] checkedrole) {
		this.checkedrole = checkedrole;
	}


	public String[] getCheckedauthority() {
		return checkedauthority;
	}


	public void setCheckedauthority(String[] checkedauthority) {
		this.checkedauthority = checkedauthority;
	}


	
	
}
