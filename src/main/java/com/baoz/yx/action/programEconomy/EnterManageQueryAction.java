package com.baoz.yx.action.programEconomy;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.config.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.utils.DepartmentUtils;
import com.baoz.yx.utils.ParameterUtils;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.UserDetail;

/**
 * 录入管理查询操作
 * 
 * @author yang yuan (yangyuan@baoz.com.cn)
 */
@Result(name = "queryList", value = "/WEB-INF/jsp/programEconomy/enterManageList.jsp")
public class EnterManageQueryAction extends DispatchAction {

	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	@Autowired
	@Qualifier("systemService")
	private ISystemService systemService;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;
	private PageInfo info;
	private String str3;  // 执行阶段
	private String str4;  // 设备总表
	private Long id;      // 客户
	private Long sid;     // 销售员 
	private String str7;  // 工程编号
	private String str8;  // 设计委托进度s
	private String str9;  // 设计委托进度e
	private HttpServletRequest request = null;
	private String str10;

	private Long clientId;

	 private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
	    		"EnterManageQueryParameters",this,new String[]{"str3","str4","clientId","sid","str7","str8","str9","str10"});


	@Override
	public String doDefault() throws Exception {
		ParameterUtils.prepareParameters(holdTool);
		this.logger.info("");
		UserDetail user = UserUtils.getUserDetail();
		//true组长
		if(!DepartmentUtils.isTeamLeader(user.getPosition().getOrganizationCode())){
			sid = user.getUser().getId();
		}else if(StringUtils.isBlank(str10)){
			//是组长，只查本组的
			str10 = user.getPosition().getOrganizationCode();
		}
		//在录入管理里只查询出state=3确认通过的信息
		String hqlms="select pe,s,e.name,orgTree from YXOProgramEconomy pe left join pe.sectionInfo as s,Employee e,OrganizationTree orgTree where (s.id ="+
		"(select max(s1.id) from YXOSectionInfo s1 where s1.economy = pe) or s.id is null) " +
		"and e.id = pe.exployeeId and pe.is_active=1 and pe.state=3 and e.position=orgTree.id ";
		String economyLoginHql=systemService.getSqlC(hqlms, str3,str4,clientId, sid, str7, str8, str9,str10);

		info = queryService.listQueryResult(economyLoginHql,info);
		
	    return "queryList";
	}

	public IQueryService getQueryService() {
		return queryService;
	}

	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}

	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
	}

	public ISystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}

	public String getStr3() {
		return str3;
	}

	public void setStr3(String str3) {
		this.str3 = str3;
	}

	public String getStr4() {
		return str4;
	}

	public void setStr4(String str4) {
		this.str4 = str4;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSid() {
		return sid;
	}

	public void setSid(Long sid) {
		this.sid = sid;
	}

	public String getStr7() {
		return str7;
	}

	public void setStr7(String str7) {
		this.str7 = str7;
	}

	public String getStr8() {
		return str8;
	}

	public void setStr8(String str8) {
		this.str8 = str8;
	}

	

	public String getStr9() {
		return str9;
	}

	public void setStr9(String str9) {
		this.str9 = str9;
	}

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public String getStr10() {
		return str10;
	}

	public void setStr10(String str10) {
		this.str10 = str10;
	}
	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
}
