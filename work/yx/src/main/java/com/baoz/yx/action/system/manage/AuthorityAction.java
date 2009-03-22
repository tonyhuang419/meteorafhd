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
import com.baoz.yx.entity.Authority;
import com.baoz.yx.service.IAuthorityService;
import com.baoz.yx.utils.UserUtils;

/**
 * 
 * @author shaoyx
 *
 * Jun 12, 2008, 6:22:51 PM
 *
 * todo: 权限增删改
 */

@Results( { @Result(name = "success", type = ServletRedirectResult.class, value = "/system/manage/authorityQuery.action"),
		@Result(name = "enterSave", value = "/WEB-INF/jsp/system/manage/authorityForm.jsp") })
@ParentPackage("yx")
public class AuthorityAction extends DispatchAction {

	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;
	
	@Autowired
	@Qualifier("authorityService")
	private IAuthorityService authorityService;

	private Authority authority;
	
	private List authorityList;// 作为父权限来选择

	/**
	 * 进入新增、编辑权限页
	 * 
	 * @return
	 * @throws Exception
	 */
	public String doDefault() throws Exception {

		authorityList = commonService.list("from Authority as a order by a.code");
		if(authority !=null && authority.getId() != null){
			authority = (Authority) commonService.load(Authority.class, authority.getId());
		}
		return ENTER_SAVE;
	}

	/**
	 * 保存权限 返回列表页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String saveAuthority() throws Exception {

		//当前用户id
		authorityService.saveOrUpdatAuthority(authority);
		return SUCCESS;
	}

	// setters and getters

	public IAuthorityService getAuthorityService() {
		return authorityService;
	}

	public void setAuthorityService(IAuthorityService authorityService) {
		this.authorityService = authorityService;
	}

	public Authority getAuthority() {
		return authority;
	}

	public void setAuthority(Authority authority) {
		this.authority = authority;
	}

	public List getAuthorityList() {
		return authorityList;
	}

	public void setAuthorityList(List authorityList) {
		this.authorityList = authorityList;
	}

	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

}
